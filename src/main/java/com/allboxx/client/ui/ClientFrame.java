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

    public ClientFrame() throws HeadlessException {
        this.chatContainer = new ChatContainer();
        this.inputContainer = new InputContainer();
        this.userContainer = new UserContainer();

        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(chatContainer, BorderLayout.CENTER);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(userContainer);
        this.add(scrollPane, BorderLayout.EAST);
        this.add(inputContainer, BorderLayout.SOUTH);
        this.setSize(600, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public ChatContainer getChatContainer() {
        return chatContainer;
    }

    public void setChatContainer(ChatContainer chatContainer) {
        this.chatContainer = chatContainer;
    }

    public InputContainer getInputContainer() {
        return inputContainer;
    }

    public void setInputContainer(InputContainer inputContainer) {
        this.inputContainer = inputContainer;
    }

    public UserContainer getUserContainer() {
        return userContainer;
    }

    public void setUserContainer(UserContainer userContainer) {
        this.userContainer = userContainer;
    }
}
