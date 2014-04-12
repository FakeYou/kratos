package org.kratos.tictactoe.application;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.events.Move;
import org.kratos.framework.gui.GameBoard;
import org.kratos.tictactoe.Board;
import org.kratos.tictactoe.TicTacToe;

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
    private TicTacToe ticTacToe;

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

    public View(Kratos kratos, TicTacToe ticTacToe) {
        this.kratos = kratos;
        this.ticTacToe = ticTacToe;
        this.match = kratos.getMatch();
        this.parser = kratos.getParser();

        gameLabel.setText("Playing " + match.getGametype() + " against " + match.getOpponent().getUsername());
        playerUsernameLabel.setText(match.getPlayer().getUsername());
        opponentUsernameLabel.setText(match.getOpponent().getUsername());
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
        ticTacToe.getBoard().removeListener(boardListener);
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
                    JOptionPane.showMessageDialog(frame, "You have lost", "Tic Tac Toe - Kratos", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (status == Communication.status.GAME_WIN) {
                    JOptionPane.showMessageDialog(frame, "congratulations, you have won", "Tic Tac Toe - Kratos", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (status == Communication.status.GAME_LOSS) {
                    JOptionPane.showMessageDialog(frame, "It's a draw", "Tic Tac Toe - Kratos", JOptionPane.INFORMATION_MESSAGE);
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
        ticTacToe.getBoard().addListener(boardListener);

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
        gameBoard = new GameBoard(3, 3) {
            @Override
            public void click(int column, int row) {
                if(match.getState() == Match.States.PLAYER_TURN) {
                    ticTacToe.doMove(column, row);
                }
            }

            @Override
            public void paintSquare(int column, int row) {
                Graphics2D g = (Graphics2D) getGraphics();
                Board.Square square = ticTacToe.getBoard().getSquare(column, row);

                Point topLeft = new Point(column * getSquareHeight() + 30, row * getSquareWidth() + 30);
                Point topRight = new Point(topLeft.x + getSquareHeight() - 60, topLeft.y);
                Point bottomLeft = new Point(topLeft.x, topLeft.y + getSquareWidth() - 60);
                Point bottomRight = new Point(topRight.x, bottomLeft.y);

                g.setStroke(new BasicStroke(4));
                if(square == ticTacToe.getBoard().getPlayerSquare()) {
                    g.setColor(Color.BLACK);
                }
                else {
                    g.setColor(Color.GRAY);
                }

                if(square == Board.Square.CROSS) {

                    g.drawLine(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
                    g.drawLine(topRight.x, topRight.y, bottomLeft.x, bottomLeft.y);
                }
                else if(square == Board.Square.NOUGHT) {
                    g.drawOval(topLeft.x, topLeft.y, getSquareWidth() - 60, getSquareHeight() - 60);
                }
            }

            @Override
            public void paint() {
                Graphics2D g = (Graphics2D) getGraphics();
                g.setStroke(new BasicStroke(4));
                g.setColor(Color.RED);
                Board board = ticTacToe.getBoard();

                int[][] line = null;

                for(int i = 0; i < 3; i++) {
                    // check every column
                    if(board.getSquare(i, 0) != Board.Square.EMPTY && board.getSquare(i, 0) == board.getSquare(i, 1) && board.getSquare(i, 0) == board.getSquare(i, 2)) {
                        line = new int[][] {{i, 0}, {i, 2}};
                    }
                    else if(board.getSquare(0, i) != Board.Square.EMPTY && board.getSquare(0, i) == board.getSquare(1, i) && board.getSquare(0, i) == board.getSquare(2, i)) {
                        line = new int[][] {{0, i}, {2, i}};
                    }
                }

                if(line == null) {
                    if(board.getSquare(0, 0) != Board.Square.EMPTY && board.getSquare(0, 0) == board.getSquare(1,1) && board.getSquare(0, 0) == board.getSquare(2, 2)) {
                        line = new int[][] {{0, 0}, {2, 2}};
                    }
                    else if(board.getSquare(0, 2) != Board.Square.EMPTY && board.getSquare(0, 2) == board.getSquare(1,1) && board.getSquare(0,2) == board.getSquare(2, 0)) {
                        line = new int[][] {{0, 2}, {2, 0}};
                    }
                }

                if(line != null) {
                    int sx = line[0][0] * getSquareWidth() + getSquareWidth() / 2;
                    int sy = line[0][1] * getSquareHeight() + getSquareHeight() / 2;
                    int dx = line[1][0] * getSquareWidth() + getSquareHeight() / 2;
                    int dy = line[1][1] * getSquareHeight() + getSquareHeight() / 2;

                    g.drawLine(sx, sy, dx, dy);
                }
            }
        };
    }
}
