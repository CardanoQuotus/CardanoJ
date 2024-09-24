package com.cardanoj.backendmodule.koios.main;

import com.cardanoj.backend.api.MetadataService;
import com.cardanoj.backend.model.metadata.MetadataJSONContent;
import com.cardanoj.backend.model.metadata.MetadataLabel;
import com.cardanoj.coreapi.common.OrderEnum;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class KoiosMetadataServiceIT extends KoiosBaseTest {

    private MetadataService metadataService;

    @BeforeEach
    public void setup() {
        metadataService = backendService.getMetadataService();
    }

    @Test
    void testGetJSONMetadataByTxnHash() throws ApiException {
        String txHash = "83b9df2741b964ecd96e44f062e65fad451d22e2ac6ce70a58c56339feda525e";

        Result<List<MetadataJSONContent>> result = metadataService.getJSONMetadataByTxnHash(txHash);

        List<MetadataJSONContent> value = result.getValue();

        System.out.println(JsonUtil.getPrettyJson(value));

        List<String> labels = value.stream().map(MetadataJSONContent::getLabel).collect(Collectors.toList());

        assertThat(value, Matchers.notNullValue());
        assertThat(value, hasSize(1));
        assertThat(labels, hasItem("721"));
    }

    @Test
    void testGetMetadataLabels() throws ApiException {
        Result<List<MetadataLabel>> result = metadataService.getMetadataLabels(20, 1, OrderEnum.asc);

        List<MetadataLabel> value = result.getValue();

        System.out.println(JsonUtil.getPrettyJson(value));

        assertThat(value, Matchers.notNullValue());
        assertThat(value, hasSize(20));
    }
}
