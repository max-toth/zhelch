package com.allboxx.client.ui;

import com.allboxx.client.data.User;

import java.awt.*;
import java.util.List;

/**
 * User: mtolstykh
 * Date: 10/16/13
 * Time: 4:02 PM
 */
public class MainFrame extends Frame {

    public static final String TITLE = "Allboxx";
    ScrollPane pane;

    public MainFrame(List<User> users) throws HeadlessException {
        super(TITLE);

        Dimension size = new Dimension(300, 600);

        this.addWindowListener(new MainFrameAdapter(this));
        pane = PlayerContainer.getPane(users);
        this.add(pane);
        this.setLayout(new GridLayout(1, 1));
        this.setSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setResizable(false);
        this.setBackground(new Color(100, 100, 100));
        this.setVisible(true);
        this.doLayout();
    }

    public void fill(final List<User> users) {
        PlayerContainer container = (PlayerContainer) pane.getComponent(0).getComponentAt(0, 1);
        for (User user : users)
            container.addUser(user);
        container.revalidate();
        container.validate();
        revalidate();
        repaint();
    }
}
