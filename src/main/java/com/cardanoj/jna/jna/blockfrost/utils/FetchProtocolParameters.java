package com.cardanoj.jna.jna.blockfrost.utils;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.cardanoj.jna.jna.common.ConstantsApi.BF_API_KEY;
import static com.cardanoj.jna.jna.common.ConstantsApi.BF_PREVIEW_URL;

public class FetchProtocolParameters {

    public static JSONObject fetchProtocolParameters() throws Exception {
        URL url = new URL(BF_PREVIEW_URL + "/epochs/latest/parameters");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("project_id", BF_API_KEY);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to get protocol parameters: HTTP error code : " + responseCode);
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder inline = new StringBuilder();
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        String jsonResponseString = inline.toString();
        return new JSONObject(jsonResponseString);
    }
}
