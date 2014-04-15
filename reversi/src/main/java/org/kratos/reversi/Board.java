package org.kratos.reversi;

import org.kratos.framework.Kratos;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by FakeYou on 4/12/14.
 */
public class Board {
    private Kratos kratos;

    private Square playerSquare;
    private Square opponentSquare;

    private Square[][] board;

    private ArrayList<ActionListener> listeners;

    public enum Square {
        BLACK,
        WHITE,
        EMPTY
    }

    public Board(Kratos kratos) {
        this.kratos = kratos;

        board = new Square[8][8];
        listeners = new ArrayList<ActionListener>();

        if(kratos.getMatch().getPlayerToStart() == kratos.getMatch().getPlayer()) {
            playerSquare = Square.BLACK;
            opponentSquare = Square.WHITE;
        }
        else {
            playerSquare = Square.WHITE;
            opponentSquare = Square.BLACK;
        }
    }

    public void clear() {
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                board[y][x] = Square.EMPTY;
            }
        }
    }

    public void setSquare(int x, int y, Square square) {
        board[y][x] = square;

        notifyListeners();
    }

    public Square getSquare(int x, int y) {
        if(x < 0 || y < 0 || x >= board[0].length || y >= board.length) {
            return null;
        }

        return board[y][x];
    }

    public Square getPlayerSquare() {
        return playerSquare;
    }

    public Square getOpponentSquare() {
        return opponentSquare;
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }

    public Boolean IsFull() {
        for(int y = 0; y < getHeight(); y++) {
            for(int x = 0; x < getWidth(); x++) {

                if(getSquare(x, y) == Board.Square.EMPTY) {
                    return false;
                }

            }
        }

        return true;
    }

    public Board clone() {
        Board clone = new Board(kratos);

        for(int y = 0; y < clone.board.length; y++) {
            for(int x = 0; x < clone.board[y].length; x++) {
                clone.board[y][x] = board[y][x];
            }
        }

        clone.opponentSquare = getOpponentSquare();
        clone.playerSquare = getPlayerSquare();

        return clone;
    }

    public void switchSquares() {
        Square tempSquare = playerSquare;

        playerSquare = opponentSquare;
        opponentSquare = tempSquare;
    }

    public void notifyListeners() {
        for(ActionListener listener : listeners) {
            listener.actionPerformed(null);
        }
    }

    public void addListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public int getScore(Square square) {
        int score = 0;

        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                if(board[y][x] == square) {
                    score += 1;
                }
            }
        }

        return score;
    }

    public void debug() {
        System.out.println("---------");

        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                char c = ' ';

                if(board[y][x] == Square.BLACK) {
                    c = '@';
                }
                else if(board[y][x] == Square.WHITE) {
                    c = '.';
                }

                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println("---------");
    }
}
