package com.allboxx.client.ui;

import com.allboxx.client.data.User;

import java.awt.*;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class UserRowContainer extends Container {
    public UserRowContainer(User user) {
        this.setLayout(new FlowLayout());
        this.add(new Label(user.getAcc()));
        this.setVisible(true);
        this.doLayout();
    }
}
