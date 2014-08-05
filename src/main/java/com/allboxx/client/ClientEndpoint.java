package com.allboxx.client;

import com.allboxx.client.data.User;
import com.allboxx.client.ui.ClientFrame;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tolstykh
 * @version 0.1
 */
@javax.websocket.ClientEndpoint(configurator = Configurator.class)
public class ClientEndpoint {

    private ObjectMapper objectMapper;
    public static String currentUser;
    private ClientFrame clientFrame;
    private String uid;

    public ClientEndpoint() {
    }

    public ClientEndpoint(ClientFrame clientFrame, String uid) {
        this.uid = uid;
        this.clientFrame = clientFrame;
        objectMapper = new ObjectMapper();
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
            session.getBasicRemote().sendText("uid:" + uid);
        } catch (IOException ex) {
        }
    }

    @OnMessage
    public void onMessage(String message) {
        String prefix_add = "user.add.list:";
        String prefix_del = "user.del.list:";
        String prefix_user_list = "user.list:";
        if (message.startsWith(prefix_user_list)) {
            try {
                System.out.println(message);
                List<User> userList = objectMapper.readValue(message.substring(prefix_user_list.length()), new TypeReference<ArrayList<User>>() {});
                clientFrame.setUsers(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(prefix_add)) {
            try {
                User user = objectMapper.readValue(message.substring(prefix_add.length()), User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(prefix_del)) {

        } else {
            int i = message.indexOf(':');
            String userId = "";
            if (i > 0) {
                userId = message.substring(0, i);
                if (currentUser == null)
                    currentUser = userId;
                clientFrame.getTextArea().append(message.substring(i + 1) + "\n");
            }
        }
    }
}
