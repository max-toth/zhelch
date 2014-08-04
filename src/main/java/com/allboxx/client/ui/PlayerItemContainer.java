package com.allboxx.client.ui;

import com.allboxx.client.data.User;

import java.awt.*;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.VERTICAL;

/**
 * User: mtolstykh
 * Date: 10/18/13
 * Time: 2:03 PM
 */
public class PlayerItemContainer extends Container {

    User user;

    public PlayerItemContainer(User user) {
        this.user = user;

        Dimension size = new Dimension(250, 50);

        this.setMaximumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;

        this.add(getPlayerName(), constraints);

        constraints.gridy++;

        this.add(getPlayerTeam(), constraints);

        constraints.gridy++;

        this.add(getPlayerBorn(), constraints);

        constraints.fill = VERTICAL;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 3;

        this.add(getPlayerRating(), constraints);
        this.setVisible(true);

        for (Component component : this.getComponents())
            for (Component c : ((Container) component).getComponents())
                c.addMouseListener(new PlayerItemAdapter(c));

//        this.doLayout();
    }

    private Container getPlayerName() {
        Label label = new Label(user.getName());
        Container lcont = new Container();
        lcont.setLayout(new GridLayout());
        lcont.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        lcont.add(label);
        lcont.setVisible(true);
        setLabelSize(lcont);
        return lcont;
    }

    private void setLabelSize(Container lcont) {
        Dimension size = new Dimension(200, 20);
        lcont.setMaximumSize(size);
        lcont.setMaximumSize(size);
        lcont.setSize(size);
        lcont.setPreferredSize(size);
    }

    private Container getPlayerTeam() {
        Label label = new Label(user.getPhone());
        Container lcont = new Container();
        lcont.setLayout(new GridLayout());
        lcont.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        lcont.add(label);
        lcont.setVisible(true);
        setLabelSize(lcont);
        return lcont;
    }

    private Container getPlayerBorn() {
        Label label = new Label(user.getAcc());
        Container lcont = new Container();
        lcont.setLayout(new GridLayout());
        lcont.setFont(new Font(Font.DIALOG, Font.ITALIC, 10));
        lcont.add(label);
        lcont.setVisible(true);
        setLabelSize(lcont);
        return lcont;
    }

    private Container getPlayerRating() {
        Label label = new Label(user.getCode());
        Container lcont = new Container();
        lcont.setLayout(new GridLayout());
        lcont.setFont(new Font(Font.DIALOG, Font.ITALIC, 10));
        lcont.add(label);
        lcont.setVisible(true);
        setLabelSize(lcont);
        return lcont;
    }
}
