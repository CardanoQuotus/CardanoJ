package com.cardanoj.jna.coinselection.impl.model;

import com.cardanoj.jna.coreapi.model.Utxo;
import com.cardanoj.jna.transaction.spec.TransactionOutput;
import com.cardanoj.jna.transaction.spec.Value;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class SelectionResult {

    private final List<Utxo> selection;
    private final Set<TransactionOutput> outputs;
    private final List<Utxo> remaining;
    private final Value amount;
    private final Value change;
}
