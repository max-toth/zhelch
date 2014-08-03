package com.allboxx.client.ui;

import com.allboxx.client.WebSocketUIClient;

import javax.swing.*;
import javax.websocket.Session;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class InputContainer extends Container {
    private JTextField textfield;

    public InputContainer() {
        this.textfield = new JTextField(30);
        this.textfield.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textfield.getText();
                try {
                    WebSocketUIClient.session.getBasicRemote().sendText("operator|" + WebSocketUIClient.currentUser + "|" + text);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.err.println(text);
            }
        });
        this.setSize(600, 80);
        this.setLayout(new FlowLayout());
        this.add(this.textfield);
        this.setVisible(true);
    }
}
