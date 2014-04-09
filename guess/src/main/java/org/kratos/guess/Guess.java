package org.kratos.guess;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.events.Move;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by FakeYou on 4/8/14.
 */
public class Guess implements Runnable {
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
    private JLabel playerLabel;
    private JLabel opponentLabel;
    private JLabel statusLabel;
    private JLabel opponentMoveLabel;
    private JLabel moveDetailsLabel;

    private Kratos kratos;
    private Interpreter interpreter;
    private Match match;
    private GuessGame guessGame;

    public Guess(final GuessGame guessGame, final Kratos kratos) {
        this.guessGame = guessGame;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();
        this.match = kratos.getMatch();

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();

                String move = source.getText();

                match.doMove(move);
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

        enableButtons(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void run() {
        while(true) {

//            playerLabel.setText(match.getPlayer().getUsername());
//            opponentLabel.setText(match.getOpponent().getUsername());

            ArrayList<Move> moves = match.getMoves();

            if(match.getState() == Match.States.LOSS) {
                statusLabel.setText("You lose!");
            }
            else if(match.getState() == Match.States.WIN) {
                statusLabel.setText("You win!");
            }
            else if(match.getState() == Match.States.OPPONENT_TURN) {
                statusLabel.setText("Opponent's turn");
                enableButtons(false);

                if(moves.size() > 1) {
                    Move move = moves.get(moves.size() - 1);
                    moveDetailsLabel.setText(move.getDetails());
                }
            }
            else if(match.getState() == Match.States.PLAYER_TURN) {
                statusLabel.setText("Your turn");
                enableButtons(true);

                if(moves.size() > 1) {
                    Move move = moves.get(moves.size() - 1);
                    opponentMoveLabel.setText(move.getMove());
                    moveDetailsLabel.setText(move.getDetails());
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) { }
        }
    }

    private void enableButtons(Boolean enable) {
        a1Button.setEnabled(enable);
        a2Button.setEnabled(enable);
        a3Button.setEnabled(enable);
        a4Button.setEnabled(enable);
        a5Button.setEnabled(enable);
        a6Button.setEnabled(enable);
        a7Button.setEnabled(enable);
        a8Button.setEnabled(enable);
        a9Button.setEnabled(enable);
        a10Button.setEnabled(enable);

        a1Button.validate();
        a2Button.validate();
        a3Button.validate();
        a4Button.validate();
        a5Button.validate();
        a6Button.validate();
        a7Button.validate();
        a8Button.validate();
        a9Button.validate();
        a10Button.validate();
    }
}
