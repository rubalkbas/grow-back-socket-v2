package com.nezztech.apuesta.websocket;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForexWebSocketClient extends WebSocketClient {
	

	public ForexWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    	log.info("Connected to server: " + getConnection().getRemoteSocketAddress());
        // Optionally send a subscription message upon connection
    }

    @Override
    public void onMessage(String message) {
    	log.info("Message from server: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    	log.info("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendSubscriptionMessage(String symbol) {
        String subscribeMessage = "{\"action\": \"subscribe\", \"symbols\": \"" + symbol + "\"}";
        send(subscribeMessage);
    }

}
