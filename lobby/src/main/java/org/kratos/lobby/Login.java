package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;

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
    private App app;

    public Login(final App app, final Kratos kratos) {
        this.app = app;
        this.kratos = kratos;

        final String host = kratos.getSetting("host").getAsString();
        int port = kratos.getSetting("port").getAsInt();

        hostField.setText(host);
        portField.setText(new Integer(port).toString());

        errorLabel.setText(" ");

        final CommandListener loginListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK) {
                    setErrorLabel(" ");
                    app.setSelectedUsername(usernameField.getText());
                    app.openLobby();
                }
                else if(status == Communication.status.ERROR_LOGIN_DUPLICATE_NAME) {
                    setErrorLabel("Duplicate username");
                }
                else if(status == Communication.status.ERROR_LOGIN_ALREADY_LOGGED_IN) {
                    setErrorLabel("Already logged in");
                }
                else if(status == Communication.status.ERROR_LOGIN_NO_NAME) {
                    setErrorLabel("No name entered");
                }
                else {
                    setErrorLabel("Unknown error");
                }
            }
        };

        final CommandListener connectListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK) {
                    setErrorLabel(" ");

                    // connection was successful so now we can go login
                    kratos.getInterpreter().login(usernameField.getText(), loginListener);
                }
                else if(status == Communication.status.ERROR_CONNECT_REFUSED) {
                    setErrorLabel("Connection refused");
                }
                else {
                    setErrorLabel("Unknown error");
                    kratos.getInterpreter().disconnect();
                }
            }
        };

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if we're already connected then directly login
                if(kratos.getCommunication().isConnected()) {
                    String username = usernameField.getText();

                    kratos.getInterpreter().login(username, loginListener);
                }
                // else connect to the server first
                else {
                    String host = hostField.getText();
                    int port = Integer.parseInt(portField.getText());

                    kratos.getInterpreter().connect(host, port, connectListener);
                }
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
