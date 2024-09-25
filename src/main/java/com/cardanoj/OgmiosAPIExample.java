package com.cardanoj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OgmiosAPIExample {

    public static void main(String[] args) throws IOException {
        String apiKey = "dmtr_ogmios1f4nhw32zwsmnydmgfff5c3te2pvyuw23ffqs7jksrd";
        String endpointURL = "https://preprod-v5.ogmios-m1.demeter.run";

        URL url = new URL(endpointURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("dmtr-api-key", apiKey);

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        System.out.println("Response Body: " + response.toString());

        connection.disconnect();
    }
}


