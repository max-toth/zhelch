package com.allboxx.client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class ChatContainer extends Container {
    private JTextArea textArea;

    public ChatContainer() {
        this.setLayout(new FlowLayout());
        this.textArea = new JTextArea(10,30);
        this.setSize(400, 400);
        this.add(textArea);
        this.setVisible(true);
    }
}
