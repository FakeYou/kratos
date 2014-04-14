package org.kratos.tictactoe.ai;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import org.kratos.framework.Kratos;
import org.kratos.framework.ai.AI;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.ai.Arguments;
import org.kratos.framework.utils.Best;
import org.kratos.tictactoe.Board;
import org.kratos.tictactoe.TicTacToe;

/**
 * Created by FakeYou on 4/14/14.
 */
public class TicTacToeAI extends AI {

    private TicTacToe ticTacToe;

    private CommandListener gameListener;
    private CommandListener challengeListener;

    private static final int AI_WIN         = 0;
    private static final int DRAW           = 1;
    private static final int UNCLEAR        = 2;
    private static final int OPPONENT_WIN   = 3;

    private enum Player {
        AI, OPPONENT
    }

    public TicTacToeAI() {
        super();
        ticTacToe = new TicTacToe(kratos);
    }

    public void start() {
        beforeStart();

        kratos.setUsername(getUsername());

        final CommandListener loginListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK && getChallenger() != null) {
                    kratos.getInterpreter().challenge(getChallenger(), "Tic Tac Toe", null);
                }
            }
        };

        CommandListener connectListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK) {
                    kratos.getInterpreter().login(getUsername(), loginListener);
                }
            }
        };

        kratos.getInterpreter().connect(getHost(), getPort(), connectListener);

        registerGameListener();
    }

    public void unregisterGameListener() {
        kratos.getCommunication().getCommunicationListener("game").removeListener(gameListener);
        kratos.getCommunication().getCommunicationListener("challengeRequest").removeListener(challengeListener);
    }

    public void registerGameListener() {
        gameListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {

                if (status == Communication.status.GAME_MATCH) {
                    System.out.println("[TicTacToeAI/start] match found");
                }
                else if (status == Communication.status.GAME_MOVE) {
                    System.out.println("[TicTacToeAI/start] move made");
                }
                else if (status == Communication.status.GAME_YOUR_TURN) {
                    System.out.println("[TicTacToeAI/start] my turn");

                    Best bestMove = getBestMove(Player.AI);
                    ticTacToe.doMove(bestMove.x, bestMove.y);
                }
                else if (status == Communication.status.GAME_LOSS) {
                    System.out.println("[TicTacToeAI/start] lost");
                    unregisterGameListener();
                    kratos.getInterpreter().logout(null);
                    kratos.getInterpreter().disconnect();
                    kratos.getCommunication().disconnect();
                }
                else if (status == Communication.status.GAME_WIN) {
                    System.out.println("[TicTacToeAI/start] won");
                    unregisterGameListener();
                    kratos.getInterpreter().logout(null);
                    kratos.getInterpreter().disconnect();
                    kratos.getCommunication().disconnect();
                }
                else if (status == Communication.status.GAME_DRAW) {
                    System.out.println("[TicTacToeAI/start] draw");
                    unregisterGameListener();
                    kratos.getInterpreter().logout(null);
                    kratos.getInterpreter().disconnect();
                    kratos.getCommunication().disconnect();
                }
            }
        };

        challengeListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                Challenge challenge = parser.parseChallengeRequest(response);

                if(challenge.getGame().equals("Tic Tac Toe")) {
                    System.out.println("[TicTacToeAI/start] accepting challenge from: " + challenge.getChallenger());

                    ticTacToe = new TicTacToe(kratos);

                    kratos.getInterpreter().challengeAccept(challenge.getNumber(), null);
                }
            }
        };

        kratos.getCommunication().getCommunicationListener("game").addListener(gameListener);
        kratos.getCommunication().getCommunicationListener("challengeRequest").addListener(challengeListener);
    }

    public Best getBestMove(Player player) {

        Best best = new Best(0, 1, 1);
        Best reply;
        Board board = ticTacToe.getBoard();
        int value;

        int conclusion = conclusion();
        if(conclusion != UNCLEAR) {
            return new Best(conclusion);
        }

        if(player == Player.AI) {
            value = OPPONENT_WIN;
        }
        else {
            value = AI_WIN;
        }

        for(int y = 0; y < board.getHeight(); y++) {
            for(int x = 0; x < board.getWidth(); x++) {
                if(board.getSquare(x, y) != Board.Square.EMPTY) {
                    continue;
                }

                if(player == Player.AI) {
                    board.setSquare(x, y, board.getPlayerSquare());

                    reply = getBestMove(Player.OPPONENT);

                    if(reply.value < value) {
                        best = new Best(reply.value, x, y);
                    }
                }
                else {
                    board.setSquare(x, y, board.getOpponentSquare());

                    reply = getBestMove(Player.AI);

                    if(reply.value > value) {
                        best = new Best(reply.value, x, y);
                    }
                }

                board.setSquare(x, y, Board.Square.EMPTY);
            }
        }

        return best;
    }

    public int conclusion() {
        if(hasWon(Player.AI)) {
            return AI_WIN;
        }
        else if(hasWon(Player.OPPONENT)) {
            return OPPONENT_WIN;
        }
        else if(ticTacToe.getBoard().IsFull()) {
            return DRAW;
        }
        else {
            return UNCLEAR;
        }
    }

    public Boolean hasWon(Player player) {
        Board board = ticTacToe.getBoard();
        Board.Square empty = Board.Square.EMPTY;
        Board.Square playerSquare;

        if(player == Player.AI) {
            playerSquare = board.getPlayerSquare();
        }
        else {
            playerSquare = board.getOpponentSquare();
        }

        for(int i = 0; i < 3; i++) {
            if(board.getSquare(i, 0) == playerSquare && board.getSquare(i, 0) == board.getSquare(i, 1) && board.getSquare(i, 1) == board.getSquare(i, 2)) {
                return true;
            }

            if(board.getSquare(0, i) == playerSquare && board.getSquare(0, i) == board.getSquare(1, i) && board.getSquare(1, i) == board.getSquare(2, i)) {
                return true;
            }
        }

        if(board.getSquare(0, 0) == playerSquare && board.getSquare(0, 0) == board.getSquare(1, 1) && board.getSquare(1, 1) == board.getSquare(2, 2)) {
            return true;
        }

        if(board.getSquare(0, 2) == playerSquare && board.getSquare(0, 2) == board.getSquare(1, 1) && board.getSquare(1, 1) == board.getSquare(2, 0)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        TicTacToeAI ai = new TicTacToeAI();

        try {
            Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.LONG_OR_COMPACT);
            ai.setArguments(arguments);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ai.start();
    }
}
