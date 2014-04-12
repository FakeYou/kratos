package org.kratos.framework.game;

import gamemodule.game.Othello;
import gamemodule.game.TicTacToe;
import nl.hanze.t23i.gamemodule.extern.AbstractGameModule;
import nl.hanze.t23i.gamemodule.game.GuessGame;
import nl.hanze.t23i.gamemodule.game.GuessGameDeluxe;
import nl.hanze.t23i.gamemodule.game.UltraGuessGame;
import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Player;
import org.kratos.framework.game.events.Move;

import java.util.ArrayList;

/**
* Created by FakeYou on 4/8/14.
*/
public class Match {

    public enum States {
        SETUP,
        WIN,
        LOSS,
        DRAW,
        PLAYER_TURN,
        OPPONENT_TURN
    }

    private Kratos kratos;
    private Parser parser;
    private Interpreter interpreter;
    private Player player;
    private Player opponent;
    private States state;
    private String gametype;

    private Player playerToStart;
    private Player playerToMove;

    private ArrayList<Move> moves;

    private AbstractGameModule gameModule;

    public Match(Kratos kratos, final Player player) {
        this.kratos = kratos;
        this.parser = kratos.getParser();
        this.interpreter = kratos.getInterpreter();
        this.player = kratos.getPlayer();

        state = States.SETUP;

        moves = new ArrayList<Move>();

        kratos.getCommunication().getCommunicationListener("game").addListener(new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {

                if(status == Communication.status.GAME_MATCH) {
                    org.kratos.framework.game.events.Match match = parser.parseMatch(response);

                    opponent = new Player(match.getOpponent(), true);
                    gametype = match.getGametype();
                    start(match.getGametype(), match.getPlayerToMove());
                }
                else if(status == Communication.status.GAME_MOVE) {
                    Move move = parser.parseMove(response);
                    moves.add(move);

                    gameModule.doPlayerMove(move.getPlayer(), move.getMove());
                }
                else if(status == Communication.status.GAME_WIN) {
                    state = States.WIN;
                    System.out.println("###### WIN ######");
                }
                else if(status == Communication.status.GAME_LOSS) {
                    state = States.LOSS;
                    System.out.println("###### LOSS ######");
                }
                else if(status == Communication.status.GAME_DRAW) {
                    state = States.DRAW;
                    System.out.println("###### DRAW ######");
                }
                else if(status == Communication.status.GAME_YOUR_TURN) {
                    playerToMove = player;
                    state = States.PLAYER_TURN;
                }
            }
        });
    }

    public States getState() {
        return state;
    }

    public void start(String gametype, String playerToMove) {
        String startingPlayername;
        String secondPlayername;

        if(playerToMove.equals(player.getUsername())) {
            startingPlayername = player.getUsername();
            secondPlayername = opponent.getUsername();

            playerToStart = player;
        }
        else {
            startingPlayername = opponent.getUsername();
            secondPlayername = player.getUsername();

            playerToStart = opponent;
        }

        if(gametype.equals("Guess Game")) {
            gameModule = new GuessGame(startingPlayername, secondPlayername);
        }
        else if(gametype.equals("Ultra Guess Game")) {
            gameModule = new UltraGuessGame(startingPlayername, secondPlayername);
        }
        else if(gametype.equals("Guess Game Deluxe")) {
            gameModule = new GuessGameDeluxe(startingPlayername, secondPlayername);
        }
        else if(gametype.equals("Tic Tac Toe")) {
            gameModule = new TicTacToe(startingPlayername, secondPlayername);
        }
        else if(gametype.equals("Reversi")) {
            gameModule = new Othello(startingPlayername, secondPlayername);
        }

        gameModule.start();
    }

    public void doMove(String move) {
        if(state == States.PLAYER_TURN) {
            interpreter.move(move, null);
            state = States.OPPONENT_TURN;

            playerToMove = opponent;
        }
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Player getPlayerToStart() {
        return playerToStart;
    }

    public Player getPlayerToMove() {
        return playerToMove;
    }

    public String getGametype() {
        return gametype;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
