use fraction::ToPrimitive;
use itertools::Itertools;

use super::fees;
use super::utils;
use super::*;
use crate::{tx_builder_utils::*, witness_builder::RedeemerWitnessKey};
use std::collections::{BTreeMap, BTreeSet, HashMap, HashSet};
use std::os::raw::{c_uint, c_double};



#[no_mangle]
pub extern "C" fn witness_keys_for_cert(
    tx_builder_ptr: *mut TransactionBuilder,
    cert_enum: u32, // Assume u32 enum representation for CertificateEnum variants
    script_witness_ptr: *const ScriptWitness,
) {
    unsafe {
        let tx_builder = &mut *tx_builder_ptr;
        let script_witness = if !script_witness_ptr.is_null() {
            Some(&*script_witness_ptr)
        } else {
            None
        };

        match cert_enum {
            0 => {} // CertificateEnum::StakeRegistration(_cert) => {}
            1 => { // CertificateEnum::StakeDeregistration(cert) =>
                let cert = &*(cert as *const StakeDeregistrationCert);
                match cert.stake_credential().kind() {
                    0 => { // StakeCredKind::Script =>
                        let hash = cert.stake_credential().to_scripthash().unwrap();
                        match script_witness {
                            Some(sw) => match sw.kind() {
                                0 => { // ScriptWitnessKind::NativeWitness =>
                                    let native_script = sw.as_native_witness().unwrap();
                                    if !tx_builder.input_types.scripts.contains(&hash) {
                                        tx_builder.add_native_script(&native_script);
                                        tx_builder.input_types.scripts.insert(hash.clone());
                                    }
                                }
                                1 => { // ScriptWitnessKind::PlutusWitness =>
                                    let plutus_witness = sw.as_plutus_witness().unwrap();
                                    if !tx_builder.input_types.scripts.contains(&hash)
                                        && plutus_witness.script().is_some()
                                    {
                                        match plutus_witness.version() {
                                            0 => { // LanguageKind::PlutusV1 =>
                                                tx_builder.add_plutus_script(&plutus_witness.script().unwrap());
                                            }
                                            1 => { // LanguageKind::PlutusV2 =>
                                                tx_builder.add_plutus_v2_script(&plutus_witness.script().unwrap());
                                            }
                                            2 => { // LanguageKind::PlutusV3 =>
                                                todo!("PlutusV3 not implemented yet.");
                                            }
                                            _ => {}
                                        }
                                        tx_builder.input_types.scripts.insert(hash.clone());
                                    }
                                    tx_builder.used_plutus_scripts.insert(hash.clone());
                                }
                                _ => {}
                            },
                            None => {}
                        }
                    }
                    1 => { // StakeCredKind::Key =>
                        tx_builder.input_types.vkeys.insert(cert.stake_credential().to_keyhash().unwrap());
                    }
                    _ => {}
                }
            }
            // Handle other variants similarly...
            _ => todo!("Conway certificates not yet implemented!"),
        }
    }
}

#[no_mangle]
pub extern "C" fn count_needed_vkeys(tx_builder_ptr: *const TransactionBuilder) -> usize {
    unsafe {
        let tx_builder = &*tx_builder_ptr;

        let mut input_hashes = HashSet::new();
        for key_hash in tx_builder.input_types.vkeys.iter() {
            input_hashes.insert(key_hash.clone());
        }

        // required signers field
        if let Some(required_signers) = &tx_builder.required_signers {
            for key_hash in required_signers.0.iter() {
                input_hashes.insert(key_hash.clone());
            }
        }

        match &tx_builder.native_scripts {
            None => input_hashes.len(),
            Some(scripts) => {
                // Union all input keys with native script keys
                let mut script_hashes = HashSet::new();
                for script in scripts.iter() {
                    // Assuming script is a HashSet of key hashes
                    for key_hash in script.iter() {
                        script_hashes.insert(key_hash.clone());
                    }
                }
                input_hashes.union(&script_hashes).count()
            }
        }
    }
}

#[no_mangle]
pub extern "C" fn min_fee(tx_builder_ptr: *mut TransactionBuilder) -> u64 {
    unsafe {
        let tx_builder = &mut *tx_builder_ptr;

        let build_result = tx_builder.build();
        if let Err(_) = build_result {
            // Return some default value or handle the error appropriately
            return 0;
        }
        let build = build_result.unwrap();

        let full_tx_result = fake_full_tx(tx_builder, build);
        if let Err(_) = full_tx_result {
            // Return some default value or handle the error appropriately
            return 0;
        }
        let full_tx = full_tx_result.unwrap();

        fees::min_fee(
            &full_tx,
            &tx_builder.config.fee_algo,
            &tx_builder.config.ex_unit_prices,
        )
    }
}

#[repr(C)]
pub struct MockWitnessSet {
    vkeys_count: usize,
    vkeys: *const Ed25519KeyHash,
    scripts_count: usize,
    scripts: *const ScriptHash,
    bootstraps_count: usize,
    bootstraps: *const u8,
}

#[repr(C)]
pub struct TxBuilderInput {
    utxo: TransactionUnspentOutput,
    // input: TransactionInput,
    // amount: Value,
    redeemer: Option<Redeemer>,
}

#[repr(C)]
pub struct TxBuilderMint {
    policy_id: PolicyID,
    mint_assets: MintAssets,
    redeemer: Option<Redeemer>,
}

#[repr(C)]
pub struct TxBuilderCert {
    certificate: Certificate,
    redeemer: Option<Redeemer>,
}

#[repr(C)]
pub struct TxBuilderWithdrawal {
    reward_address: RewardAddress,
    coin: Coin,
    redeemer: Option<Redeemer>,
}

/*
MockWitnessSet: Represents a set of witnesses in a transaction. Converted to use raw pointers to arrays for vkeys, scripts, and bootstraps.
TxBuilderInput: Represents an input in a transaction. Fields are preserved as is.
TxBuilderMint: Represents minting assets in a transaction. Fields are preserved as is.
TxBuilderCert: Represents a certificate in a transaction. Fields are preserved as is.
TxBuilderWithdrawal: Represents a withdrawal in a transaction. Fields are preserved as is.
*/

#[repr(C)]
pub struct TransactionBuilderConfig {
    fee_algo: fees::LinearFee,
    pool_deposit_lo: u64,
    pool_deposit_hi: u64,
    key_deposit_lo: u64,
    key_deposit_hi: u64,
    max_value_size: u32,
    max_tx_size: u32,
    coins_per_utxo_byte_lo: u64,
    coins_per_utxo_byte_hi: u64,
    ex_unit_prices: ExUnitPrices, // Assuming ExUnitPrices is a struct with appropriate fields
    max_tx_ex_units_lo: u64,
    max_tx_ex_units_hi: u64,
    costmdls: Costmdls, // Assuming Costmdls is a struct with appropriate fields
    collateral_percentage: u32,
    max_collateral_inputs: u32,
    zero_time_lo: u64,
    zero_time_hi: u64,
    zero_slot_lo: u64,
    zero_slot_hi: u64,
    slot_length: u32,
    blockfrost: Blockfrost, // Assuming Blockfrost is a struct with appropriate fields
}

#[repr(C)]
pub struct TransactionBuilderConfigBuilder {
    fee_algo: *mut fees::LinearFee,
    pool_deposit_lo: u64,
    pool_deposit_hi: u64,
    key_deposit_lo: u64,
    key_deposit_hi: u64,
    max_value_size: u32,
    max_tx_size: u32,
    coins_per_utxo_byte_lo: u64,
    coins_per_utxo_byte_hi: u64,
    ex_unit_prices: *mut ExUnitPrices,
    max_tx_ex_units_lo: u64,
    max_tx_ex_units_hi: u64,
    costmdls: *mut Costmdls,
    collateral_percentage: u32,
    max_collateral_inputs: u32,
    zero_time_lo: u64,
    zero_time_hi: u64,
    zero_slot_lo: u64,
    zero_slot_hi: u64,
    slot_length: u32,
    blockfrost: *mut Blockfrost,
}


use std::os::raw::c_void; // Importing the c_void type for generic pointer

#[no_mangle]
pub extern "C" fn TransactionBuilderConfigBuilder_new() -> *mut c_void {
    Box::into_raw(Box::new(TransactionBuilderConfigBuilder::new())) as *mut c_void
}

#[no_mangle]
pub extern "C" fn TransactionBuilderConfigBuilder_fee_algo(
    builder_ptr: *mut c_void,
    fee_algo_ptr: *const fees::LinearFee,
) {
    unsafe {
        let builder = &mut *(builder_ptr as *mut TransactionBuilderConfigBuilder);
        let fee_algo = &*fee_algo_ptr;
        *builder = builder.fee_algo(fee_algo);
    }
}

use std::os::raw::c_void; // Importing the c_void type for generic pointer

#[no_mangle]
pub extern "C" fn TransactionBuilderConfigBuilder_coins_per_utxo_byte(
    builder_ptr: *mut c_void,
    coins_per_utxo_byte_ptr: *const Coin,
) {
    unsafe {
        let builder = &mut *(builder_ptr as *mut TransactionBuilderConfigBuilder);
        let coins_per_utxo_byte = &*coins_per_utxo_byte_ptr;
        *builder = builder.coins_per_utxo_byte(coins_per_utxo_byte);
    }
}

#[no_mangle]
pub extern "C" fn TransactionBuilderConfigBuilder_build(
    builder_ptr: *mut c_void,
) -> *mut TransactionBuilderConfig {
    unsafe {
        let builder = &mut *(builder_ptr as *mut TransactionBuilderConfigBuilder);
        match builder.build() {
            Ok(config) => Box::into_raw(Box::new(config)),
            Err(_) => std::ptr::null_mut(), // Handle error appropriately
        }
    }
}

// #[no_mangle]
// pub extern "C" fn TransactionBuilderConfigBuilder_build(ptr: *mut TransactionBuilderConfig) {
//     unsafe {
//         if !ptr.is_null() {
//             Box::from_raw(ptr);
//         }
//     }
// }

#[repr(C)]
pub struct TransactionBuilder {
    config: TransactionBuilderConfig,
    inputs_count: usize,
    inputs: *const TxBuilderInput,
    outputs: TransactionOutputs,
    change_outputs: TransactionOutputs,
    fee: Option<Coin>,
    ttl: Option<Slot>, // absolute slot number
    certs_count: usize,
    certs: *const TxBuilderCert,
    withdrawals_count: usize,
    withdrawals: *const TxBuilderWithdrawal,
    auxiliary_data: Option<AuxiliaryData>,
    validity_start_interval: Option<Slot>,
    input_types: MockWitnessSet,
    mint_count: usize,
    mint: *const TxBuilderMint,
    native_scripts: Option<NativeScripts>,
    script_data_hash: Option<ScriptDataHash>,
    collateral_count: usize,
    collateral: *const TxBuilderInput,
    required_signers: Option<RequiredSigners>,
    network_id: Option<NetworkId>,
    plutus_scripts: Option<PlutusScripts>,
    plutus_data: Option<*const BTreeMap<DataHash, PlutusData>>,
    redeemers: Option<Redeemers>,
    //Babbage
    collateral_return: Option<TransactionOutput>,
    total_collateral: Option<Coin>,
    reference_inputs: Option<TransactionUnspentOutputs>,
    plutus_v2_scripts: Option<PlutusScripts>,
    used_plutus_scripts_count: usize,
    used_plutus_scripts: *const ScriptHash,
    plutus_versions_count: usize,
    plutus_versions_keys: *const ScriptHash,
    plutus_versions_values: *const Language,
}


#[no_mangle]
extern "C" fn add_inputs_from(
    builder: *mut TransactionBuilder,
    inputs: *const TransactionUnspentOutputs,
    change_address: *const Address,
    weights: *const c_uint,
    weights_length: c_uint,
) -> Result<(), JsError> {
    // Ensure all pointers are valid
    if builder.is_null() || inputs.is_null() || change_address.is_null() || weights.is_null() {
        return Err(JsError::from_str("Null pointer passed"));
    }

    // Convert weights array to Rust slice
    let weights_slice = unsafe { std::slice::from_raw_parts(weights, weights_length as usize) };

    // Convert input and change address pointers to Rust references
    let inputs_ref = unsafe { &*inputs };
    let change_address_ref = unsafe { &*change_address };

    // Call the original Rust function
    let result = unsafe {
        (*builder).add_inputs_from(
            inputs_ref,
            change_address_ref,
            weights_slice,
        )
    };

    // Convert the Rust Result to a C-style return value
    match result {
        Ok(_) => Ok(()),
        Err(err) => Err(err),
    }
}
