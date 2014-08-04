package com.allboxx.client.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * User: mtolstykh
 * Date: 10/22/13
 * Time: 3:11 PM
 */
public class PlayerItemAdapter extends MouseAdapter {

    private Container parent;
    private Component component;

    public PlayerItemAdapter(Component component) {
        this.component = component;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        try {
            Thread.sleep(0, 1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        for (int i = 1; i <= parent.getWidth(); i++) {
            Container container = parent.getParent();
            for (Component c : container.getComponents()) {
                c.setBounds(c.getX() - 1, c.getY(), c.getWidth(), c.getHeight());
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        parent = e.getComponent().getParent().getParent();
        for (Component c : parent.getComponents())
            for (Component comp : ((Container) c).getComponents()) {
                Color color = new Color(200, 200, 200);
                comp.setBackground(color);
            }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        parent = e.getComponent().getParent().getParent();
        for (Component c : parent.getComponents())
            for (Component comp : ((Container) c).getComponents()) {
                Color color = new Color(180, 180, 180);
                comp.setBackground(color);
            }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
