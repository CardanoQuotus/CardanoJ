package com.cardanoj.cip.cip25.main;

import com.cardanoj.metadata.cbor.CBORMetadataList;
import com.cardanoj.metadata.cbor.CBORMetadataMap;

class NFTMetadataUtil {

    public static void addSingleOrMultipleValue(CBORMetadataMap map, String key, String value) {
        Object descValue = map.get(key);

        if (descValue == null) {
            map.put(key, value);
            return;
        }

        CBORMetadataList list = null;
        if (descValue instanceof String) {
            list = new CBORMetadataList();
            list.add((String)descValue);
            map.put(key, list);
        } else if (descValue instanceof CBORMetadataList) {
            list = (CBORMetadataList) descValue;
        } else {
            map.put(key, value);
            return;
        }

        list.add(value);
        return;
    }
}
