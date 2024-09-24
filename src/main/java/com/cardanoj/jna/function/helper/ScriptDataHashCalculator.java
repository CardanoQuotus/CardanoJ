package com.cardanoj.jna.function.helper;

import co.nstant.in.cbor.CborException;
import com.cardanoj.jna.exception.CborRuntimeException;
import com.cardanoj.jna.exception.CborSerializationException;
import com.cardanoj.jna.function.TxBuilder;
import com.cardanoj.jna.function.TxBuilderContext;
import com.cardanoj.jna.plutus.spec.CostMdls;
import com.cardanoj.jna.plutus.spec.CostModel;
import com.cardanoj.jna.plutus.spec.Language;
import com.cardanoj.jna.plutus.util.ScriptDataHashGenerator;
import com.cardanoj.jna.transaction.spec.Transaction;
import com.cardanoj.jna.coreapi.transaction.util.CostModelUtil;

import java.util.Optional;

import static com.cardanoj.jna.coreapi.transaction.util.CostModelUtil.PlutusV1CostModel;
import static com.cardanoj.jna.coreapi.transaction.util.CostModelUtil.PlutusV2CostModel;

public class ScriptDataHashCalculator {

    public static TxBuilder calculateScriptDataHash() {
        return (context, txn) -> {
            calculateScriptDataHash(context, txn);
        };
    }

    public static void calculateScriptDataHash(TxBuilderContext ctx, Transaction transaction) {
        boolean containsPlutusScript = false;
        //check if plutusscript exists
        if ((transaction.getWitnessSet().getPlutusV1Scripts() != null
                && transaction.getWitnessSet().getPlutusV1Scripts().size() > 0)
                || (transaction.getWitnessSet().getPlutusV2Scripts() != null
                && transaction.getWitnessSet().getPlutusV2Scripts().size() > 0)
                || (transaction.getWitnessSet().getRedeemers() != null
                && transaction.getWitnessSet().getRedeemers().size() > 0)
        ) {
            containsPlutusScript = true;
        }

        CostMdls costMdls = ctx.getCostMdls();
        if (costMdls == null) {
            costMdls = new CostMdls();
            if (transaction.getWitnessSet().getPlutusV1Scripts() != null
                    && transaction.getWitnessSet().getPlutusV1Scripts().size() > 0) {
                Optional<CostModel>  costModel = CostModelUtil.getCostModelFromProtocolParams(ctx.getProtocolParams(), Language.PLUTUS_V1);
                costMdls.add(costModel.orElse(PlutusV1CostModel));
            }

            if (transaction.getWitnessSet().getPlutusV2Scripts() != null
                    && transaction.getWitnessSet().getPlutusV2Scripts().size() > 0) {
                Optional<CostModel>  costModel = CostModelUtil.getCostModelFromProtocolParams(ctx.getProtocolParams(), Language.PLUTUS_V2);
                costMdls.add(costModel.orElse(PlutusV2CostModel));
            }

            if (costMdls.isEmpty()) { //Check if costmodel can be decided from other fields
                if (transaction.getBody().getReferenceInputs() != null
                        && transaction.getBody().getReferenceInputs().size() > 0) { //If reference input is there, then plutus v2
                    Optional<CostModel> costModel = CostModelUtil.getCostModelFromProtocolParams(ctx.getProtocolParams(), Language.PLUTUS_V2);
                    costMdls.add(costModel.orElse(PlutusV2CostModel));
                }
            }
        }

        if (containsPlutusScript) {
            //Script dataHash
            byte[] scriptDataHash;
            try {
                scriptDataHash = ScriptDataHashGenerator.generate(transaction.getWitnessSet().getRedeemers(),
                        transaction.getWitnessSet().getPlutusDataList(), costMdls.getLanguageViewEncoding());
            } catch (CborSerializationException | CborException e) {
                throw new CborRuntimeException(e);
            }

            transaction.getBody().setScriptDataHash(scriptDataHash);
        }
    }
}
