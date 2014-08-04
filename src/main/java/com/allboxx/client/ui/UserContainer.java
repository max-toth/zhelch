package com.allboxx.client.ui;

import com.allboxx.client.WebSocketUIClient;
import com.allboxx.client.data.User;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class UserContainer extends Container {

    private GridLayout layout;
    Map<String, Label> labels = new HashMap<String, Label>();

    public UserContainer() {
        layout = new GridLayout();
        layout.setColumns(1);
//        layout.setVgap(1);
        this.setBackground(new Color(180, 180, 180));
        this.setLayout(layout);
        this.setSize(200, 400);
        this.setVisible(true);
    }

    public void add(User user) {
        final Label label = new Label(user.getAcc());
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Set current user to " + label.getText());
                WebSocketUIClient.currentUser = label.getText();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        labels.put(user.getAcc(), label);
        this.add(label);
        this.doLayout();
    }

    public void del(String id) {
        Label label = labels.get(id);
        if (label!=null)
            this.remove(label);

        this.doLayout();
    }
}
