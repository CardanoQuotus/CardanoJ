package com.cardanoj.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.ByteString;

import java.io.IOException;

public class TransactionWebSocketClient {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://dmtr_ogmios1f4nhw32zwsmnydmgfff5c3te2pvyuw23ffqs7jksrd.preprod-v5.ogmios-m1.demeter.run").build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("Connected to WebSocket");
                // Send request for transaction details after connecting
                webSocket.send("get_transaction_details");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("Received message: " + text);
                // Parse and handle the received transaction details
                handleTransactionDetails(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                System.out.println("Received bytes: " + bytes.hex());
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.err.println("WebSocket failure: " + t.getMessage());
            }
        };

        WebSocket webSocket = client.newWebSocket(request, listener);
    }

    private static void handleTransactionDetails(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse JSON message
            JsonNode rootNode = objectMapper.readTree(message);

            // Example: Assuming the JSON structure contains transaction details like transaction ID and amount
            String transactionId = rootNode.get("transactionId").asText();
            double amount = rootNode.get("amount").asDouble();

            // Process transaction details
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Amount: " + amount);

            // Add more parsing and processing logic as needed
        } catch (IOException e) {
            // Handle parsing exception
            System.err.println("Error parsing JSON message: " + e.getMessage());
        }
    }
}
