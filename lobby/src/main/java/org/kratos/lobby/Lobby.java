package org.kratos.lobby;

import org.kratos.framework.Kratos;

import javax.swing.*;

/**
 * Created by FakeYou on 3-4-14.
 */
public class Lobby {
    private Kratos kratos;
    private Login login;

    public Lobby() {
        kratos = new Kratos();

        Login login = new Login(kratos);

        JFrame frame = new JFrame("Kratos");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(login.getPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Lobby();
    }
}
