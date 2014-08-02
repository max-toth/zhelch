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
public class InputContainer extends Container {
    private JTextField textfield;

    public InputContainer(final Session session) {
        this.textfield = new JTextField(20);
        this.textfield.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textfield.getText();
                try {
                    session.getBasicRemote().sendText(text);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.err.println(text);

            }
        });

        this.setLayout(new FlowLayout());
        this.add(this.textfield);
        this.setVisible(true);
    }
}
