package org.kratos.guess;

import org.kratos.framework.Kratos;

import javax.swing.*;

/**
 * Created by FakeYou on 4/8/14.
 */
public class App {
    private Kratos kratos;

    private Guess guess;

    private JFrame GuessFrame;

    public App() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) { }

        kratos = new Kratos();

        guess = new Guess(this, kratos);

        GuessFrame = new JFrame("Guess Game - kratos");

        GuessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GuessFrame.getContentPane().add(guess.getPanel());
        GuessFrame.pack();
        GuessFrame.setLocationRelativeTo(null);
        GuessFrame.setResizable(false);
        GuessFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
