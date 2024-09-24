package com.cardanoj.coreapi.config;

import com.cardanoj.util.OSUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationTest {

    @Test
    void isAndroid() {
        Configuration.INSTANCE.setAndroid(true);
        boolean isAndroid = Configuration.INSTANCE.isAndroid();
        Configuration.INSTANCE.setAndroid(false);

        assertTrue(isAndroid);
    }

    @Test
    void isAndroid_whenSetThroughOSUtil() {
        OSUtil.setAndroid(true);
        boolean isAndroid = OSUtil.isAndroid();
        Configuration.INSTANCE.setAndroid(false);

        assertTrue(isAndroid);
    }

    @Test
    void isAndroid_whenNotSetInConfig() {
        boolean isAndroid = Configuration.INSTANCE.isAndroid();

        assertFalse(isAndroid);
    }
}
