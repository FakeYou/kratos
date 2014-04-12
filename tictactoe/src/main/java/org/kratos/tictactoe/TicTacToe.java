package org.kratos.tictactoe;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.Player;
import org.kratos.framework.game.events.Move;

/**
 * Created by FakeYou on 4/12/14.
 */
public class TicTacToe {
    private Kratos kratos;
    private Interpreter interpreter;
    private Parser parser;
    private Match match;

    private Player player;
    private Player opponent;

    private Board board;

    public TicTacToe(Kratos kratos) {
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();
        this.parser = kratos.getParser();

        this.match = kratos.getMatch();
        this.player = match.getPlayer();
        this.opponent = match.getOpponent();

        this.board = new Board(kratos);
        board.clear();

        registerGameListener();
    }

    public void registerGameListener() {
        kratos.getCommunication().getCommunicationListener("game").addListener(new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.GAME_MOVE) {
                    Move move = parser.parseMove(response);

                    String[] coords = move.getMove().split(",");
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);

                    if(move.getPlayer().equals(player.getUsername())) {
                        board.setSquare(x, y, board.getPlayerSquare());
                    }
                    else {
                        board.setSquare(x, y, board.getOpponentSquare());
                    }
                }
                else if(status == Communication.status.GAME_YOUR_TURN) {
                }
            }
        });
    }

    public void doMove(int x, int y) {
        // todo - check if move is valid
        interpreter.move(x + "," + y, null);
    }

    public Board getBoard() {
        return board;
    }
}
