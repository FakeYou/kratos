package org.kratos.tictactoe;

import org.kratos.framework.Kratos;

import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by FakeYou on 4/11/14.
 */
public class Board {
    private Kratos kratos;

    private Square playerSquare;
    private Square opponentSquare;

    private Square[][] board;

    private ArrayList<ActionListener> listeners;

    public enum Square {
        NOUGHT,
        CROSS,
        EMPTY
    };

    public Board(Kratos kratos) {
        this.kratos = kratos;

        board = new Square[3][3];
        listeners = new ArrayList<ActionListener>();

        if(kratos.getMatch().getPlayerToStart() == kratos.getMatch().getPlayer()) {
            playerSquare = Square.NOUGHT;
            opponentSquare = Square.CROSS;
        }
        else {
            playerSquare = Square.CROSS;
            opponentSquare = Square.NOUGHT;
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

        return clone;
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
}
