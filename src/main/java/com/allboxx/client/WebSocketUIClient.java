package com.allboxx.client;

import com.allboxx.client.data.User;
import com.allboxx.client.ui.ClientFrame;
import com.allboxx.client.ui.MainFrame;
import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.*;

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
    public static Session session;
    private static final String uid = UUID.randomUUID().toString();
    private static List<User> users = new ArrayList<User>();
    private ClientFrame clientFrame;
    private MainFrame mainFrame;
    public static String currentUser;

    public WebSocketUIClient() {
        objectMapper = new ObjectMapper();
    }

    @OnMessage
    public void onMessage(String message) {
        String prefix_add = "user.add.list:";
        String prefix_del = "user.del.list:";
        String prefix_user_list = "user.list:";
        if (message.startsWith(prefix_user_list)) {
            try {
//                System.out.println(message.substring(prefix_user_list.length()));
                  users = objectMapper.readValue(message.substring(prefix_user_list.length()), ArrayList.class);
//                System.out.println(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(prefix_add)) {
            try {
                User user = objectMapper.readValue(message.substring(prefix_add.length()), User.class);
//                if (!users.contains(user.getAcc())) {
////                    users.add(user.getAcc());
////                    this.clientFrame.getUserContainer().add(user);
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (message.startsWith(prefix_del)) {
            this.clientFrame.getUserContainer().del(message.substring(prefix_del.length()));
        }
//        System.out.println("Received msg: " + message);
    }

    public void connect() {
        try {
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebSocketUIClient.class, URI.create(ws_server));
            if (session == null) return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void initUI() {
        this.mainFrame = new MainFrame(users);
//        this.clientFrame = new ClientFrame();
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
        WebSocketUIClient client = new WebSocketUIClient();
        client.initUI();
        client.connect();
        try {
            session.getBasicRemote().sendText("uid:" + uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wait4TerminateSignal();
    }

}
