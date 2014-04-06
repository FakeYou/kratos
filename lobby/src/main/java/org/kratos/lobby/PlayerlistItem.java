package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private String username;

    public PlayerlistItem(final App app, Kratos kratos) {
        this.app = app;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();

        final CommandListener listener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK) {
                    JOptionPane.showMessageDialog(app.getLobbyFrame(), "Challenge sent to " + username, "Challenge - kratos", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(status == Communication.status.ERROR_CHALLENGE_UNKNOWN_PLAYER) {
                    JOptionPane.showMessageDialog(app.getLobbyFrame(), "Unable to challenge this player", "Alert - kratos", JOptionPane.ERROR_MESSAGE);
                }
                else if(status == Communication.status.ERROR_CHALLENGE_UNKNOWN_GAME) {
                    JOptionPane.showMessageDialog(app.getLobbyFrame(), "Unable play this game", "Alert - kratos", JOptionPane.ERROR_MESSAGE);
                }
                else if(status == Communication.status.ERROR_CHALLENGE_ILLEGAL_ARGUMENTS) {
                    JOptionPane.showMessageDialog(app.getLobbyFrame(), "Unknown error", "Alert - kratos", JOptionPane.ERROR_MESSAGE);
                }

                challengeButton.setText("Challenge");
                challengeButton.setEnabled(true);
                System.out.println(status);
            }
        };

        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interpreter.challenge(username, app.getSelectedGame(), listener);
                challengeButton.setText("Sending challenge");
                challengeButton.setEnabled(false);
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
        usernameLabel.setText(username);

        if(app.getSelectedUsername().equals(username)) {
            challengeButton.setEnabled(false);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
