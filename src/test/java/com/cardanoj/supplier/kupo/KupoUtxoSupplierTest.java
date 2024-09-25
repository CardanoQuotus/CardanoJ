// package com.cardanoj.supplier.kupo;

// import com.cardanoj.coreapi.model.Result;
// import com.cardanoj.supplier.kupo.KupoUtxoSupplier;
// import com.cardanoj.supplier.kupo.model.KupoUtxo;
// import io.adabox.model.query.response.models.Utxo;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;

// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;

// public class KupoUtxoSupplierTest {

//    public enum OrderEnum {
//        ASC, DESC
//    }

//    @Test
//    void getPage_SuccessfulResult_ReturnsListOfUtxos() throws Exception {
//        // Arrange
//        KupoUtxoSupplier supplier = Mockito.spy(new KupoUtxoSupplier("baseUrl"));
//        Mockito.doReturn(Result.success("OK").withValue(Collections.emptyList()).code(200))
//                .when(supplier).getUtxos(anyString(), anyInt());

//        // Act
//        List<Utxo> utxos = supplier.getPage("address", 1, 1, OrderEnum.ASC);
//        // Assert
//        Assertions.assertNotNull(utxos);
//        Assertions.assertEquals(0, utxos.size());
//    }

//    @Test
//    void getTxOutput_SuccessfulResult_ReturnsOptionalUtxo() throws Exception {
//        // Arrange
//        KupoUtxoSupplier supplier = Mockito.spy(new KupoUtxoSupplier("baseUrl"));
//        KupoUtxo kupoUtxo = new KupoUtxo();
//        kupoUtxo.setTransactionId("txHash");
//        kupoUtxo.setOutputIndex(0);
//        Mockito.doReturn(Optional.of(supplier.convertToUtxo(kupoUtxo)))
//                .when(supplier).getTxOutput("txHash", 0);

//        // Act
//        Optional<com.cardanoj.coreapi.model.Utxo> utxoOptional = supplier.getTxOutput("txHash", 0);

//        // Assert
//        Assertions.assertTrue(utxoOptional.isPresent());
//        com.cardanoj.coreapi.model.Utxo utxo = utxoOptional.get();
//        Assertions.assertEquals("txHash", utxo.getTxHash());
//        Assertions.assertEquals(0, utxo.getOutputIndex());
//    }

//    @Test
//    void getAll_SuccessfulResult_ReturnsListOfUtxos() throws Exception {
//        // Arrange
//        KupoUtxoSupplier supplier = Mockito.spy(new KupoUtxoSupplier("baseUrl"));
//        Mockito.doReturn(Result.success("OK").withValue(Collections.emptyList()).code(200))
//                .when(supplier).getUtxos(anyString(), anyInt());

//        // Act
//        List<com.cardanoj.coreapi.model.Utxo> utxos = supplier.getAll("address");

//        // Assert
//        Assertions.assertNotNull(utxos);
//        Assertions.assertEquals(0, utxos.size());
//    }
// }

