package com.allboxx.client.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: mtolstykh
 * Date: 10/16/13
 * Time: 4:05 PM
 */
public class MainFrameAdapter extends WindowAdapter{

    private MainFrame mainFrame;

    public MainFrameAdapter(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        mainFrame.setVisible(false);
        mainFrame.dispose();
        super.windowClosing(e);
    }
}
