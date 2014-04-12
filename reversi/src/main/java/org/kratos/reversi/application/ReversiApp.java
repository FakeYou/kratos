package org.kratos.reversi.application;

import org.kratos.framework.Kratos;
import org.kratos.reversi.Reversi;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by FakeYou on 4/12/14.
 */
public class ReversiApp {
    private Kratos kratos;
    private Reversi reversi;
    private View view;

    public ReversiApp(Kratos kratos, JFrame lobbyFrame) {
        this.kratos = kratos;
        this.reversi = new Reversi(kratos);
        this.view = new View(kratos, reversi);

        JFrame frame = new JFrame("Reversi - Kratos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(view.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(lobbyFrame);
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.unregisterGameListener();
                reversi.unregisterGameListener();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                view.refresh();
            }

            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
    }
}
