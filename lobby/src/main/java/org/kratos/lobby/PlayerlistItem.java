package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.Interpreter;

import javax.swing.*;

/**
 * Created by FakeYou on 4/6/14.
 */
public class PlayerlistItem {
    private JButton challengeButton;
    private JLabel usernameLabel;
    private JPanel panel;

    private App app;
    private Kratos kratos;
    private Interpreter interpreter;

    public PlayerlistItem(App app, Kratos kratos) {
        this.app = app;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public JPanel getPanel() {
        return panel;
    }
}
