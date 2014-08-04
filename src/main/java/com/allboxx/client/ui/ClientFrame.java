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
        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        JPanel chatAndUsersPane = getChatAndUsersPane();
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setSize(300,100);
        inputPanel.add(new JTextField(16));
        mainPanel.add(chatAndUsersPane);
        mainPanel.add(inputPanel);
        add(mainPanel);

//        this.setLayout(new BorderLayout());
//
        this.setSize(600, 480);
//        this.chatContainer = new ChatContainer();
//        chatContainer.setSize(getSize());
//        this.inputContainer = new InputContainer();
//        this.userContainer = new UserContainer();
//
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.add(chatContainer, BorderLayout.CENTER);
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.add(userContainer);
//        this.add(scrollPane, BorderLayout.EAST);
//        this.add(inputContainer, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPanel getChatAndUsersPane() {
        JPanel chatAndUsersPane = new JPanel(new GridLayout(1,2));
        JTextArea textArea = new JTextArea("This is an editable JTextArea. " +
                "A text area is a \"plain\" text component, " +
                "which means that although it can display text " +
                "in any font, all of the text is in the same font.");
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        textArea.setEditable(false);
        chatAndUsersPane.add(scrollPane);

        JPanel usersPanel = new JPanel(new GridLayout(100,1,5,5));
        for (int i=0;i<5; i ++) {
            usersPanel.add(new JLabel("Label number " + i));
        }
        JScrollPane usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setPreferredSize(new Dimension(200, 250));
        chatAndUsersPane.add(usersScrollPane);

        return chatAndUsersPane;
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

    public static void main(String[] args) {
        new ClientFrame();
    }
}
