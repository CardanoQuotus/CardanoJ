package com.cardanoj;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Example {
    public static void main(String[] args) {
        String host = "wss://mainnet-v5.ogmios-m1.demeter.run";
        String apiKey = "dmtr_ogmios1x3u4qctr2anrxemj2e6xcmn5gap4sdmdd3msu64uc8";
        String address = "addr_test1qpjakkgkv02glwf68x6ejstmnfp4dger88r6k6r5ngt33j09uu06ps6deturhlma60nqpqkpxzehacyl2eammvfxgp6sjf7y2v";

        final WebSocketClient[] client = {null}; // Using an array to hold the WebSocketClient

        try {
            URI uri = new URI(host);
            client[0] = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to server");
                    // Send requests here
                    if (client[0] != null) {
                        String authMessage = "{\"apiKey\": \"" + apiKey + "\"}";
                        client[0].send(authMessage);
                    }
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received message: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };

            client[0].connect();
            // You can send your requests after connecting to the server
            // Remember to handle authentication and message formatting according to the server's protocol
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
