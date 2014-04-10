package org.kratos.guess;

import org.kratos.framework.Kratos;

import javax.swing.*;

/**
 * Created by FakeYou on 4/8/14.
 */
public class GuessGame {
    private Kratos kratos;

    private Guess guess;
    private JFrame guessFrame;

    public GuessGame(Kratos kratos) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) { }

        this.kratos = kratos;

        guess = new Guess(this, kratos);

        guessFrame = new JFrame("Guess Game - kratos");

        guessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        guessFrame.getContentPane().add(guess.getPanel());
        guessFrame.pack();
        guessFrame.setLocationRelativeTo(null);
        guessFrame.setResizable(false);
        guessFrame.setVisible(false);

        new Thread(guess).start();
    }

    public void setVisible(Boolean visible) {
        guessFrame.setVisible(visible);
    }
}
