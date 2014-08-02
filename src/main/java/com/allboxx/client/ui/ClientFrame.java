package com.allboxx.client.ui;

import javax.swing.*;
import javax.websocket.Session;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class ClientFrame extends JFrame {

    private ChatContainer chatContainer;
    private InputContainer inputContainer;
    private UserContainer userContainer;

    public ClientFrame(Session session) throws HeadlessException {
        this.chatContainer = new ChatContainer();
        this.inputContainer = new InputContainer(session);
        this.userContainer = new UserContainer();

        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(this.inputContainer);
        this.setSize(600, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
