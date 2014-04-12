package org.kratos.reversi;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.Player;
import org.kratos.framework.game.events.Move;

import java.util.Stack;

/**
 * Created by Yuri on 11/4/2014.
 */
public class Reversi {
    private Kratos kratos;
    private Interpreter interpreter;
    private Parser parser;
    private Match match;

    private Player player;
    private Player opponent;

    private CommandListener gameListener;

    private Board board;

    public Reversi(Kratos kratos){
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();
        this.parser = kratos.getParser();

        this.match = kratos.getMatch();
        this.player = match.getPlayer();
        this.opponent = match.getOpponent();

        this.board = new Board(kratos);
        board.clear();

        board.setSquare(3, 3, Board.Square.WHITE);
        board.setSquare(4, 3, Board.Square.BLACK);
        board.setSquare(4, 4, Board.Square.WHITE);
        board.setSquare(3, 4, Board.Square.BLACK);

        registerGameListener();
    }

    public void unregisterGameListener() {
        kratos.getCommunication().getCommunicationListener("game").removeListener(gameListener);
    }

    public void registerGameListener() {
        gameListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.GAME_MOVE) {
                    Move move = parser.parseMove(response);

                    String[] coords = move.getMove().split(",");
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);

                    if(move.getPlayer().equals(player.getUsername())) {
                        board.setSquare(x, y, board.getPlayerSquare());
                        flipTiles(x, y, board.getPlayerSquare());
                    }
                    else {
                        board.setSquare(x, y, board.getOpponentSquare());
                        flipTiles(x, y, board.getOpponentSquare());
                    }
                }
                else if(status == Communication.status.GAME_YOUR_TURN) {
                }
            }
        };

        kratos.getCommunication().getCommunicationListener("game").addListener(gameListener);
    }

    public Boolean doMove(int x, int y) {
        // todo - flip tiles

        if(board.getSquare(x, y) != Board.Square.EMPTY) {
            return false;
        }
        else if(!isLegalMove(x, y)) {
            return false;
        }
        else {
            interpreter.move(x + "," + y, null);
            return true;
        }
    }

    private Boolean isLegalMove(int x, int y) {
        return getChains(x, y, board.getPlayerSquare(), board.getOpponentSquare()).size() >= 1;
    }

    public void flipTiles(int x, int y, Board.Square square) {
        Stack<Stack<int[]>> chains;

        if(square == board.getPlayerSquare()) {
            chains = getChains(x, y, board.getPlayerSquare(), board.getOpponentSquare());
        }
        else {
            chains = getChains(x, y, board.getOpponentSquare(), board.getPlayerSquare());
        }

        for(Stack<int[]> chain : chains) {
            for(int[] coords : chain) {
                board.setSquare(coords[0], coords[1], square);
            }
        }
    }

    private Stack<Stack<int[]>> getChains(int x, int y, Board.Square borderSquare, Board.Square chainSquare) {
        Stack<Stack<int[]>> chains = new Stack<Stack<int[]>>();

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) { continue; }

                int _x = x + i;
                int _y = y + j;

                Stack<int[]> chain = new Stack<int[]>();
                chain.add(new int[] { x, y });
                Board.Square next = board.getSquare(_x, _y);

                // check for a chain of opponent squares
                while(next == chainSquare) {
                    chain.add(new int[] { _x, _y });

                    _x += 1 * i;
                    _y += 1 * j;

                    next = board.getSquare(_x, _y);
                }

                if(chain.size() > 1) {
                    // check if the last square is ours
                    if(board.getSquare(_x, _y) == borderSquare) {
                        chains.add(chain);
                    }
                }
            }
        }

        return chains;
    }

    public Board getBoard() {
        return board;
    }
}
