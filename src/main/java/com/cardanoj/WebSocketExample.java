package com.cardanoj;


import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.ClientEndpoint;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

@ClientEndpoint
public class WebSocketExample {
    public static void main(String[] args) {
        String host = "wss://mainnet-v5.ogmios-m1.demeter.run";
        String apiKey = "dmtr_ogmios1x3u4qctr2anrxemj2e6xcmn5gap4sdmdd3msu64uc8";
        String address = "addr_test1qpjakkgkv02glwf68x6ejstmnfp4dger88r6k6r5ngt33j09uu06ps6deturhlma60nqpqkpxzehacyl2eammvfxgp6sjf7y2v";

        SslContextFactory sslContextFactory = new SslContextFactory.Client();
        WebSocketClient client = new WebSocketClient(sslContextFactory);

        try {
            client.start();
            URI uri = new URI(host);
            ClientUpgradeRequest request = new ClientUpgradeRequest();

            client.connect(new MySocket(apiKey, address), uri, request);

            TimeUnit.SECONDS.sleep(5); // Wait for connection establishment
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ClientEndpoint
    public static class MySocket extends Endpoint {
        private String apiKey;
        private String address;
        private Session session;

        public MySocket(String apiKey, String address) {
            this.apiKey = apiKey;
            this.address = address;
        }

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            this.session = session;
            System.out.println("Connected to " + session.getRequestURI());
            try {
                String json = "{\"jsonrpc\":\"2.0\",\"method\":\"queryLedgerState/utxo\",\"params\":{\"addresses\":[\"" + address + "\"]}}";
                session.getAsyncRemote().sendText(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onClose(Session session, CloseReason closeReason) {
            System.out.println("WebSocket connection closed.");
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println("Received from server: " + message);
        }
    }
}
