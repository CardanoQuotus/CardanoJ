package com.cardanoj.jna.jna.blockfrost.utils;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import static com.cardanoj.jna.jna.common.ConstantsApi.BF_API_KEY;
import static com.cardanoj.jna.jna.common.ConstantsApi.BF_PREVIEW_URL;

public class FetchCurrentSlot {

    public static int fetchCurrentSlot() throws Exception {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                URL url = new URL(BF_PREVIEW_URL + "/blocks/latest");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("project_id", BF_API_KEY);

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("Failed to get current slot: HTTP error code : " + responseCode);
                }

                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder inline = new StringBuilder();
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                String jsonResponseString = inline.toString();
                JSONObject jsonResponse = new JSONObject(jsonResponseString);
                return jsonResponse.getInt("slot");
            } catch (UnknownHostException e) {
                retryCount--;
                if (retryCount == 0) {
                    throw new RuntimeException("Failed to resolve hostname: " + e.getMessage());
                }
                Thread.sleep(1000); // wait for a second before retrying
            }
        }
        throw new RuntimeException("Failed to fetch current slot after retries.");
    }
}

