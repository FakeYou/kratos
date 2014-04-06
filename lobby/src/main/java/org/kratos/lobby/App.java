package org.kratos.lobby;

import org.kratos.framework.Kratos;

import javax.swing.*;
import java.awt.*;

/**
 * Created by FakeYou on 3-4-14.
 */
public class App {
    private Kratos kratos;

    private Login login;
    private Lobby lobby;

    private JFrame loginFrame;
    private JFrame lobbyFrame;

    public App() {
        kratos = new Kratos();

        login = new Login(this, kratos);
        lobby = new Lobby(this, kratos);

        loginFrame = new JFrame("Login - kratos");
        lobbyFrame = new JFrame("Lobby - kratos");

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.getContentPane().add(login.getPanel());
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);

        lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lobbyFrame.getContentPane().add(lobby.getPanel());
        lobbyFrame.pack();
        lobbyFrame.setLocationRelativeTo(null);
        lobbyFrame.setMinimumSize(new Dimension(700, 400));
        lobbyFrame.setVisible(false);
    }

    public static void main(String[] args) {
        new App();
    }

    public void openLobby() {
        loginFrame.setVisible(false);

        lobbyFrame.setVisible(true);
        lobbyFrame.toFront();
        lobbyFrame.requestFocus();

        lobby.reload();
    }
}
