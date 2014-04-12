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

    public void debug() {
        System.out.println("---------");

        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                char c = ' ';

                if(board[y][x] == Square.BLACK) {
                    c = 'B';
                }
                else if(board[y][x] == Square.WHITE) {
                    c = 'W';
                }

                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println("---------");
    }
}
