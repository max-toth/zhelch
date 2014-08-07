package com.allboxx.client;

import com.allboxx.client.data.User;
import com.allboxx.client.ui.ClientFrame;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.websocket.*;
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
    private Session session;

    public ClientEndpoint(ClientFrame clientFrame, String uid) {
        this.uid = uid;
        this.clientFrame = clientFrame;
        objectMapper = new ObjectMapper();
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
            session.getAsyncRemote().sendText("uid:" + uid);
            System.out.println(session);
            if (!session.isOpen())
                throw new Exception("wtf exception");
            this.session = session;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println(message);
        String prefix_add = "user.connected:";
        String prefix_del = "user.disconnected:";
        String prefix_msg = "user.message:";
        String prefix_user_list = "user.list:";
        try {
            if (message.startsWith(prefix_user_list)) {
                System.out.println(message);
                List<User> userList = objectMapper.readValue(message.substring(prefix_user_list.length()), new TypeReference<ArrayList<User>>() {
                });
                clientFrame.setUsers(userList);
            } else if (message.startsWith(prefix_add)) {
                User user = objectMapper.readValue(message.substring(prefix_add.length()), User.class);
                clientFrame.setUser(user);
            } else if (message.startsWith(prefix_del)) {
                clientFrame.delUser(message.substring(prefix_del.length()));
            } else if (message.startsWith(prefix_msg)) {
                message = message.substring(prefix_msg.length() + 1);
                System.out.println(message);
                int i = message.indexOf(':');
                String userId = "";
                if (i > 0) {
                    userId = message.substring(0, i);
                    if (currentUser == null)
                        currentUser = userId;
                    message = message.substring(i + 1);
                    clientFrame.getTextArea().append(message + "\n");
                    clientFrame.getUserMap().get(userId).getMessages().add(message);
                    clientFrame.toFront();
                }
            }
        } catch (IOException e) {
            session.close();
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Closed, because of " + reason);
    }

}
