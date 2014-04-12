package org.kratos.tictactoe.application;

import org.kratos.framework.Kratos;
import org.kratos.tictactoe.TicTacToe;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by FakeYou on 4/12/14.
 */
public class TicTacToeApp {
    private Kratos kratos;
    private TicTacToe ticTacToe;
    private View view;

    public TicTacToeApp(Kratos kratos, JFrame lobbyFrame) {
        this.kratos = kratos;
        this.ticTacToe = new TicTacToe(kratos);
        this.view = new View(kratos, ticTacToe);

        JFrame frame = new JFrame("Tic Tac Toe - Kratos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(view.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(lobbyFrame);
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.unregisterGameListener();
            }

            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
    }
}
