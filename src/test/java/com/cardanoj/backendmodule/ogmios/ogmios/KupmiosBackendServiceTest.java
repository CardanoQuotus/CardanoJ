package com.cardanoj.backendmodule.ogmios.ogmios;

import com.cardanoj.address.AddressProvider;
import com.cardanoj.backend.api.BackendService;
import com.cardanoj.backend.api.DefaultUtxoSupplier;
import com.cardanoj.common.model.Networks;
import com.cardanoj.coreapi.account.Account;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Amount;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.function.helper.ScriptUtxoFinders;
import com.cardanoj.function.helper.SignerProviders;
import com.cardanoj.plutus.spec.BigIntPlutusData;
import com.cardanoj.plutus.spec.PlutusV2Script;
import com.cardanoj.quicktx.QuickTxBuilder;
import com.cardanoj.quicktx.ScriptTx;
import com.cardanoj.quicktx.Tx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class KupmiosBackendServiceTest  extends OgmiosBaseTest {
    Account sender1;
    String sender1Addr;
    String receiver1 = "addr_test1qz3s0c370u8zzqn302nppuxl840gm6qdmjwqnxmqxme657ze964mar2m3r5jjv4qrsf62yduqns0tsw0hvzwar07qasqeamp0c";

    QuickTxBuilder quickTxBuilder;

    @BeforeEach
    void setup() {
        quickTxBuilder = new QuickTxBuilder(kupmiosBackendService);

        //addr_test1qp73ljurtknpm5fgey5r2y9aympd33ksgw0f8rc5khheg83y35rncur9mjvs665cg4052985ry9rzzmqend9sqw0cdksxvefah
        String senderMnemonic = "drive useless envelope shine range ability time copper alarm museum near flee wrist live type device meadow allow churn purity wisdom praise drop code";
        sender1 = new Account(Networks.testnet(), senderMnemonic);
        sender1Addr = sender1.baseAddress();
    }

    @Test
    void alwaysTrueScript() throws ApiException, InterruptedException {
        PlutusV2Script plutusScript = PlutusV2Script.builder()
                .type("PlutusScriptV2")
                .cborHex("49480100002221200101")
                .build();

        String scriptAddress = AddressProvider.getEntAddress(plutusScript, Networks.testnet()).toBech32();
        BigInteger scriptAmt = new BigInteger("2479280");

        Random rand = new Random();
        int randInt = rand.nextInt();
        BigIntPlutusData plutusData =  new BigIntPlutusData(BigInteger.valueOf(randInt)); //any random number

        Tx tx = new Tx();
        tx.payToContract(scriptAddress, Amount.lovelace(scriptAmt), plutusData)
                .from(sender1Addr);

        QuickTxBuilder quickTxBuilder = new QuickTxBuilder(kupmiosBackendService);
        Result<String> result = quickTxBuilder.compose(tx)
                .withSigner(SignerProviders.signerFrom(sender1))
                .completeAndWait(System.out::println);

        System.out.println(result.getResponse());
        checkIfUtxoAvailable(result.getValue(), scriptAddress);

        Optional<Utxo> optionalUtxo  = ScriptUtxoFinders.findFirstByInlineDatum(new DefaultUtxoSupplier(kupmiosBackendService.getUtxoService()),
                scriptAddress, plutusData);
        ScriptTx scriptTx = new ScriptTx()
                .collectFrom(optionalUtxo.get(), plutusData)
                .payToAddress(receiver1, Amount.lovelace(scriptAmt))
                .attachSpendingValidator(plutusScript)
                .withChangeAddress(scriptAddress, plutusData);

        Result<String> result1 = quickTxBuilder.compose(scriptTx)
                .feePayer(sender1Addr)
                .withSigner(SignerProviders.signerFrom(sender1))
                .completeAndWait(System.out::println);

        System.out.println(result1.getResponse());
        assertTrue(result1.isSuccessful());

        checkIfUtxoAvailable(result1.getValue(), sender1Addr);
    }

    protected void checkIfUtxoAvailable(String txHash, String address) {
        Optional<Utxo> utxo = Optional.empty();
        int count = 0;
        while (utxo.isEmpty()) {
            if (count++ >= 20)
                break;
            List<Utxo> utxos = new DefaultUtxoSupplier(kupmiosBackendService.getUtxoService()).getAll(address);
            utxo = utxos.stream().filter(u -> u.getTxHash().equals(txHash))
                    .findFirst();
            System.out.println("Try to get new output... txhash: " + txHash);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {}
        }
    }
}
