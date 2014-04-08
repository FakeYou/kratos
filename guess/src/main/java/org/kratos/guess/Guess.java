package org.kratos.guess;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.Interpreter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FakeYou on 4/8/14.
 */
public class Guess {
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a10Button;
    private JPanel panel;

    private Kratos kratos;
    private Interpreter interpreter;
    private App app;

    public Guess(final App app, final Kratos kratos) {
        this.app = app;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();

                System.out.println(source.getText());
            }
        };

        a1Button.addActionListener(buttonListener);
        a2Button.addActionListener(buttonListener);
        a3Button.addActionListener(buttonListener);
        a4Button.addActionListener(buttonListener);
        a5Button.addActionListener(buttonListener);
        a6Button.addActionListener(buttonListener);
        a7Button.addActionListener(buttonListener);
        a8Button.addActionListener(buttonListener);
        a9Button.addActionListener(buttonListener);
        a10Button.addActionListener(buttonListener);
    }

    public JPanel getPanel() {
        return panel;
    }
}
