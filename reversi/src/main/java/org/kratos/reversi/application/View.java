package org.kratos.reversi.application;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.events.Move;
import org.kratos.framework.gui.GameBoard;
import org.kratos.reversi.Board;
import org.kratos.reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FakeYou on 4/12/14.
 */
public class View {
    private Kratos kratos;
    private Parser parser;
    private Match match;
    private Reversi reversi;

    private CommandListener gameListener;
    private ActionListener boardListener;

    private JButton forfeitButton;
    private JLabel gameLabel;
    private JPanel gameBoard;
    private JLabel playerTurnLabel;
    private JLabel opponentTurnLabel;
    private JLabel playerUsernameLabel;
    private JLabel opponentUsernameLabel;
    private JPanel panel;
    private JLabel playerScoreLabel;
    private JLabel opponentScoreLabel;

    public View(Kratos kratos, Reversi reversi){
        this.kratos = kratos;
        this.reversi = reversi;
        this.match = kratos.getMatch();
        this.parser = kratos.getParser();

        gameLabel.setText("Playing " + match.getGametype() + " against " + match.getOpponent().getUsername());
        playerUsernameLabel.setText(match.getPlayer().getUsername() + " (" + reversi.getBoard().getPlayerSquare() + ")");
        opponentUsernameLabel.setText(match.getOpponent().getUsername() + " (" + reversi.getBoard().getOpponentSquare() + ")");
        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(panel);
                unregisterGameListener();
                frame.dispose();
            }
        });

        registerGameListener();
    }

    public void unregisterGameListener() {
        kratos.getCommunication().getCommunicationListener("game").removeListener(gameListener);
        reversi.getBoard().removeListener(boardListener);
    }

    public void registerGameListener() {
        gameListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(panel);

                if (status == Communication.status.GAME_MOVE) {
                    Move move = parser.parseMove(response);

                    if(move.getPlayer().equals(match.getPlayer().getUsername())) {
                        playerTurnLabel.setText("");
                        opponentTurnLabel.setText("Opponent's turn");
                    }
                }
                else if (status == Communication.status.GAME_YOUR_TURN) {
                    playerTurnLabel.setText("Your turn");
                    opponentTurnLabel.setText("");
                }
                else if (status == Communication.status.GAME_LOSS) {
                    JOptionPane.showMessageDialog(frame, "You have lost", "Reversi - Kratos", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (status == Communication.status.GAME_WIN) {
                    JOptionPane.showMessageDialog(frame, "congratulations, you have won", "Reversi - Kratos", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (status == Communication.status.GAME_LOSS) {
                    JOptionPane.showMessageDialog(frame, "It's a draw", "Reversi - Kratos", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        boardListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        };

        kratos.getCommunication().getCommunicationListener("game").addListener(gameListener);
        reversi.getBoard().addListener(boardListener);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void refresh() {
        GameBoard board = (GameBoard) gameBoard;
        board.paintSquares();
        board.paint();
    }

    private void createUIComponents() {
        gameBoard = new GameBoard(8, 8) {
            @Override
            public void click(int column, int row) {
                if(match.getState() == Match.States.PLAYER_TURN) {
                    Boolean success = reversi.doMove(column, row);

                    if(!success) {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }
                }
            }

            @Override
            public void paintSquare(int column, int row) {
                Graphics2D g = (Graphics2D) getGraphics();
                Board.Square square = reversi.getBoard().getSquare(column, row);

                Point topLeft = new Point(column * getSquareHeight() + 5, row * getSquareWidth() + 5);
                Point topRight = new Point(topLeft.x + getSquareHeight() - 10, topLeft.y);
                Point bottomLeft = new Point(topLeft.x, topLeft.y + getSquareWidth() - 10);
                Point bottomRight = new Point(topRight.x, bottomLeft.y);

                g.setStroke(new BasicStroke(2));

                if(square == Board.Square.BLACK) {
                    g.setColor(Color.BLACK);
                    g.fillOval(topLeft.x, topLeft.y, getSquareWidth() - 10, getSquareHeight() - 10);
                    g.setColor(Color.GRAY);
                    g.drawOval(topLeft.x, topLeft.y, getSquareWidth() - 10, getSquareHeight() - 10);
                }
                else if(square == Board.Square.WHITE) {
                    g.setColor(Color.WHITE);
                    g.fillOval(topLeft.x, topLeft.y, getSquareWidth() - 10, getSquareHeight() - 10);
                    g.setColor(Color.GRAY);
                    g.drawOval(topLeft.x, topLeft.y, getSquareWidth() - 10, getSquareHeight() - 10);
                }
            }

            @Override
            public void paint() {

            }
        };
    }

}
