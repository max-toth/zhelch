package com.allboxx.client;

import com.allboxx.client.data.User;
import com.allboxx.client.ui.ClientFrame;
import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Maxim Tolstykh
 * @version 0.1
 */
@ClientEndpoint(configurator = Configurator.class)
public class WebSocketUIClient {

    private ObjectMapper objectMapper;

    private static final String ws_server = "ws://localhost:8081/chat";
    private static final Object waitLock = new Object();
    private WebSocketContainer container;
    private Session session;
    private static final String uid = UUID.randomUUID().toString();
    private List<User> users = new ArrayList<User>();

    public WebSocketUIClient() {
        objectMapper = new ObjectMapper();
    }

    @OnMessage
    public void onMessage(String message) {
        String prefix = "userList:";
        if (message.startsWith(prefix)) {
            try {
                User user = objectMapper.readValue(message.substring(prefix.length()), User.class);
                users.add(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Received msg: " + message);
    }

    public void connect() {
        try {
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebSocketUIClient.class, URI.create(ws_server));
            if (session == null) return;
            initUI();
            session.getBasicRemote().sendText("uid:" + uid);
            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initUI() {
        ClientFrame clientFrame = new ClientFrame(session);
    }

    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        new WebSocketUIClient().connect();
    }

}
