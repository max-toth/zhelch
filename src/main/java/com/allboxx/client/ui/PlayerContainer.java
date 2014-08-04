package com.allboxx.client.ui;

import com.allboxx.client.data.User;

import java.awt.*;
import java.util.List;

/**
 * User: mtolstykh
 * Date: 10/16/13
 * Time: 4:09 PM
 */
public class PlayerContainer extends Container {

    private GridLayout layout;

    public PlayerContainer(List<User> users) {

        layout = new GridLayout();
        layout.setVgap(1);
        layout.setColumns(2);
        layout.preferredLayoutSize(this);
        layout.minimumLayoutSize(this);

        Dimension size = new Dimension(290, 590);

        this.setPreferredSize(size);
        this.setSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        this.setLayout(layout);
        this.setBackground(new Color(180, 180, 180));
        this.setVisible(true);

        int row=1;
        addUsers(users, row);

        this.doLayout();
    }

    public void addUsers(List<User> users, int row) {
        for (User user : users){
            layout.setRows(++row);
            this.add(new PlayerItemContainer(user));
            this.doLayout();
        }
    }

    public static ScrollPane getPane(List<User> users){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(new PlayerContainer(users));
        return scrollPane;
    }

    public void addUser(User user) {
        this.layout.setRows(layout.getRows() + 1);
        this.add(new PlayerItemContainer(user));
        doLayout();
    }
}
