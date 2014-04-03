package org.kratos.lobby;

import org.kratos.framework.Kratos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FakeYou on 4/3/14.
 */
public class Login {
    private JButton loginButton;
    private JPanel panel;
    private JTextField usernameField;
    private JTextField hostField;
    private JTextField portField;
    private JLabel usernameLabel;
    private JLabel errorLabel;
    private JLabel hostLabel;
    private JLabel portLabel;

    private final Kratos kratos;

    public Login(final Kratos kratos) {
        this.kratos = kratos;

        final String host = kratos.getSetting("host").getAsString();
        int port = kratos.getSetting("port").getAsInt();

        hostField.setText(host);
        portField.setText(new Integer(port).toString());

        errorLabel.setText(" ");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostField.getText();
                int port = Integer.parseInt(portField.getText());

                System.out.println(host + ":" + port);

                kratos.connect(host, port);
            }
        });
    }

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    public JPanel getPanel() {
        return panel;
    }
}
