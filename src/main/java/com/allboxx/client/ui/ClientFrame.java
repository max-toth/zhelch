package com.allboxx.client.ui;

import com.allboxx.client.ClientEndpoint;
import com.allboxx.client.data.User;

import javax.swing.*;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by max_tolstykh on 03/08/14.
 */
public class ClientFrame extends JFrame {

    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        if (usersPanel.getComponentCount() > 0)
            usersPanel.removeAll();
        this.users = users;
        for (User user : this.users) {
            JPanel lp = new JPanel(new GridLayout(0, 1));
            Dimension size = new Dimension(100, 20);
            lp.setPreferredSize(size);
            lp.setSize(new Dimension(100, 100));
            lp.setMaximumSize(size);
            lp.setMinimumSize(size);
            lp.setToolTipText(user.getAcc());
            lp.addMouseListener(new PlayerItemAdapter(lp, textArea, user));
            lp.add(new JLabel(user.getName() + ": " + user.getPhone()));
            usersPanel.add(lp);
            usersPanel.validate();
            usersPanel.repaint();
        }
        usersScrollPane.validate();
        usersScrollPane.repaint();
    }

    public ClientFrame() throws HeadlessException {
        JPanel mainPanel = new JPanel(new FlowLayout());
        JPanel chatAndUsersPane = getChatAndUsersPane();
        JPanel inputPanel = new JPanel(new BorderLayout(1, 1));
        inputPanel.setPreferredSize(new Dimension(700, 30));
        final JTextField textField = new JTextField(16);
        inputPanel.add(textField);
        textField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {
                    if (ClientEndpoint.currentUser != null)
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText("operator|" + ClientEndpoint.currentUser + "|" + text);
                            textArea.append("Allboxx: " + text + "\n");
                        }
                        else
                            run();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                System.err.println(text);
                textField.setText("");
            }
        });
        mainPanel.add(chatAndUsersPane);
        mainPanel.add(inputPanel);
        add(mainPanel);

        this.setSize(800, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    JPanel usersPanel;
    JScrollPane usersScrollPane;
    JTextArea textArea;

    public JTextArea getTextArea() {
        return textArea;
    }

    public JPanel getChatAndUsersPane() {
        JPanel chatAndUsersPane = new JPanel(new GridLayout(1, 2));
        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 430));
        textArea.setEditable(false);
        chatAndUsersPane.add(scrollPane);

        usersPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setMaximumSize(new Dimension(100, 430));
        usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatAndUsersPane.add(usersScrollPane);

        return chatAndUsersPane;
    }

    Session session;
    private static final String uid = UUID.randomUUID().toString();
    private static final String ws_server = "ws://localhost:8081/chat";
    private static final Object waitLock = new Object();

    public void run() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(new ClientEndpoint(this, uid), URI.create(ws_server));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        new ClientFrame().run();
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
}
