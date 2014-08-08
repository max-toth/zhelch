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
import java.util.*;
import java.util.List;
import java.util.UUID;


/**
 * Created by max_tolstykh on 03/08/14.
 */
public class ClientFrame extends JFrame {

    private static final String ws_server_pref = "ws://";
    private static final String ws_server_suf = ":8081/chat";

    public static final String ALLBOXX = "allboxx.com";
    public static final String AMAZON1 = "54.200.85.175";
    public static final String AMAZON2 = "ec2-54-200-85-175.us-west-2.compute.amazonaws.com";
    public static final String AMAZON3 = "10.0.102.53";
    public static final int user_item_height = 40;
    public static final int user_item_width = 180;

    public static String server = ALLBOXX;

    private Map<String, User> userMap = new HashMap<String, User>();
    private JPanel usersPanel;
    private JScrollPane usersScrollPane;
    private JTextArea textArea;
    private Session session;

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setUsers(List<User> users) {

        if (usersPanel.getComponentCount() > 0)
            usersPanel.removeAll();

        for (User user : users) {
            userMap.put(user.getAcc(), user);
            JPanel lp = new JPanel();
            lp.setLayout(new BoxLayout(lp, BoxLayout.Y_AXIS));
            Dimension size = new Dimension(user_item_width, user_item_height);
            lp.setPreferredSize(size);
            lp.setSize(size);
            lp.setMaximumSize(size);
            lp.setMinimumSize(size);
            lp.setToolTipText(user.getAcc());
            lp.addMouseListener(new PlayerItemAdapter(lp, textArea, user));
            lp.add(new JLabel(user.getName()), CENTER_ALIGNMENT);
            JLabel phoneLabel = new JLabel(user.getPhone());
            phoneLabel.setFont(new Font("Courier New", Font.ITALIC, 10));
            lp.add(phoneLabel, CENTER_ALIGNMENT);
            lp.setBorder(BorderFactory.createTitledBorder(""));
            usersPanel.add(lp);
            usersPanel.validate();
            usersPanel.repaint();
        }
        usersScrollPane.validate();
        usersScrollPane.repaint();
    }

    public void setUserOffline(String user) {
        for (Component c : usersPanel.getComponents()) {
            JPanel p = (JPanel) c;
            if (p.getToolTipText().equals(user)) {
                p.setBackground(new Color(200, 135, 135));
                usersPanel.validate();
                usersPanel.repaint();
            }
        }
    }

    public void delUser(String user) {
        userMap.remove(user);
        for (Component c : usersPanel.getComponents()) {
            JPanel p = (JPanel) c;
            if (p.getToolTipText().equals(user)) {
                usersPanel.remove(c);
                usersPanel.validate();
                usersPanel.repaint();
            }
        }
        usersScrollPane.validate();
        usersScrollPane.repaint();
    }

    public void setUser(User user) {
        userMap.put(user.getAcc(), user);
        delUser(user.getAcc());
        JPanel lp = new JPanel();
        lp.setLayout(new BoxLayout(lp, BoxLayout.Y_AXIS));
        Dimension size = new Dimension(user_item_width, user_item_height);
        lp.setPreferredSize(size);
        lp.setSize(size);
        lp.setMaximumSize(size);
        lp.setMinimumSize(size);
        lp.setToolTipText(user.getAcc());
        lp.addMouseListener(new PlayerItemAdapter(lp, textArea, user));
        lp.add(new JLabel(user.getName()), CENTER_ALIGNMENT);
        JLabel phoneLabel = new JLabel(user.getPhone());
        phoneLabel.setFont(new Font("Courier New", Font.ITALIC, 10));
        lp.add(phoneLabel, CENTER_ALIGNMENT);
        lp.setBorder(BorderFactory.createTitledBorder(""));
        lp.setBackground(new Color(200, 230, 143));
        usersPanel.add(lp);
        usersPanel.validate();
        usersPanel.repaint();
        usersScrollPane.validate();
        usersScrollPane.repaint();
    }

    public ClientFrame() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel chatAndUsersPane = getChatAndUsersPane();
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.setPreferredSize(new Dimension(800, 100));
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(600, 30));
        JButton connect = new JButton("connect");
        connect.setPreferredSize(new Dimension(100, 30));
        JButton send = new JButton("send");
        send.setPreferredSize(new Dimension(100, 30));
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    session.close();
                    run();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        String[] labels = new String[]{ALLBOXX, AMAZON1, AMAZON2, AMAZON3};
        JComboBox comboBox = new JComboBox<String>(labels);
        comboBox.setSelectedIndex(0);
        comboBox.setPreferredSize(new Dimension(600, 30));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                server = (String) cb.getSelectedItem();
            }
        });

        textField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {
                    if (ClientEndpoint.currentUser != null)
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText("operator|" + ClientEndpoint.currentUser + "|" + text);
                            userMap.get(ClientEndpoint.currentUser).getMessages().add("Allboxx: " + text + "\n\n");
                            textArea.append("Allboxx: " + text + "\n\n");
                        } else
                            run();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                System.err.println(text);
                textField.setText("");
            }
        });
        send.addActionListener(textField.getActionListeners()[0]);

        JPanel inp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp.setPreferredSize(new Dimension(800, 30));

        inp.add(textField);
        inp.add(send);

        JPanel ref = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ref.setPreferredSize(new Dimension(800, 30));
        ref.add(comboBox);
        ref.add(connect);

        inputPanel.add(inp, Component.CENTER_ALIGNMENT);
        inputPanel.add(ref);

        mainPanel.add(chatAndUsersPane);
        mainPanel.add(inputPanel, Component.CENTER_ALIGNMENT);
        add(mainPanel);

        this.setSize(805, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }

    public JPanel getChatAndUsersPane() {
        JPanel chatAndUsersPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.ITALIC, 11));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        textArea.setEditable(false);


        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        Dimension preferredSize = new Dimension(180, 700);
        usersPanel.setPreferredSize(preferredSize);
        usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setPreferredSize(preferredSize);
        usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatAndUsersPane.add(scrollPane);
        chatAndUsersPane.add(usersScrollPane);

        return chatAndUsersPane;
    }

    public void run() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(new ClientEndpoint(this, UUID.randomUUID().toString()), URI.create(ws_server_pref + server + ws_server_suf));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientFrame().run();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
