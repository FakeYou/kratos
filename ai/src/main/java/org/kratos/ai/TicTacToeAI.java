package org.kratos.ai;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.events.Move;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/10/14.
 */
public class TicTacToeAI {
    private Kratos kratos;
    private Parser parser;
    private Interpreter interpreter;

    private Square[][] board;
    private Match.States lastState;

    private Square playerSquare;
    private Square opponentSquare;

    private enum Square {
        NOUGHT,
        CROSS,
        EMPTY
    };

    public TicTacToeAI(final Kratos kratos, org.kratos.framework.game.events.Match match) {
        this.kratos = kratos;
        this.parser = kratos.getParser();
        this.interpreter = kratos.getInterpreter();

        if(match.getPlayerToMove().equals(match.getOpponent())) {
            playerSquare = Square.CROSS;
            opponentSquare = Square.NOUGHT;
        }
        else {
            playerSquare = Square.NOUGHT;
            opponentSquare = Square.CROSS;
        }

        board = new Square[3][3];
        clearBoard();

        kratos.getCommunication().getCommunicationListener("game").addListener(new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.GAME_MOVE) {
                    Move move = parser.parseMove(response);

                    String[] coords = move.getMove().split(",");
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);

                    if(move.getPlayer().equals(kratos.getPlayer().getUsername())) {
                        makeMoveOnBoard(x, y, playerSquare);
                    }
                    else {
                        makeMoveOnBoard(x, y, opponentSquare);
                    }
                }
                else if(status == Communication.status.GAME_YOUR_TURN) {
                    int[] coords = getBestMove();
                    interpreter.move(coords[0] + "," + coords[1], null);
                }
            }
        });
    }

    public void clearBoard() {
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[0].length; x++) {
                board[y][x] = Square.EMPTY;
            }
        }
    }

    public void makeMoveOnBoard(int x, int y, Square move) {
        board[y][x] = move;

        printBoard();
    }

    private void printBoard() {
        System.out.println("----------------");
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[0].length; x++) {
                Square square = board[y][x];

                if(square == Square.NOUGHT) {
                    System.out.print("o");
                }
                else if(square == Square.CROSS) {
                    System.out.print("x");
                }
                else {
                    System.out.print(" ");
                }
            }

            System.out.print("\n");
        }
        System.out.println("----------------");
    }

    public int[] getBestMove() {
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[0].length; x++) {
                if(board[y][x] == Square.EMPTY) {
                    return new int[] {x, y};
                }
            }
        }

        return new int[] {-1,-1};
    }
}
