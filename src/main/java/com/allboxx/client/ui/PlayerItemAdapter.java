package com.allboxx.client.ui;

import com.allboxx.client.ClientEndpoint;
import com.allboxx.client.data.User;

import javax.swing.*;
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
    private JTextArea textArea;
    private User user;

    public PlayerItemAdapter(Component component, JTextArea textArea, User user) {
        this.user = user;
        this.textArea = textArea;
        this.component = component;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

//        try {
//            Thread.sleep(0, 1);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//
//        for (int i = 1; i <= parent.getWidth(); i++) {
//            Container container = parent.getParent();
//            for (Component c : container.getComponents()) {
//                c.setBounds(c.getX() - 1, c.getY(), c.getWidth(), c.getHeight());
//            }
//        }
        JPanel panel = (JPanel) component;
        System.out.println("Set user uid = " + panel.getToolTipText());
        ClientEndpoint.currentUser = panel.getToolTipText();
        Color background = new Color(200, 200, 200);
        if (component.getBackground().equals(background))
            component.setBackground(null);
        else {
            component.setBackground(background);
            for (Component c : component.getParent().getComponents()) {
                if (!c.equals(component))
                    c.setBackground(null);
            }
        }

        textArea.setText("");
        for (String msg : user.getMessages()) {
            textArea.append(user.getName() + ": " + msg + "\n");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        parent = e.getComponent().getParent().getParent();
//        for (Component c : parent.getComponents())
//            for (Component comp : ((Container) c).getComponents()) {
//                Color color = new Color(200, 200, 200);
//                comp.setBackground(color);
//            }
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        parent = e.getComponent().getParent().getParent();
//        for (Component c : parent.getComponents())
//            for (Component comp : ((Container) c).getComponents()) {
//                Color color = new Color(180, 180, 180);
//                comp.setBackground(color);
//            }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
