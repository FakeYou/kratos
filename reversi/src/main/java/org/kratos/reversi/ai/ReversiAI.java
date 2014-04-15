package org.kratos.reversi.ai;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import org.kratos.framework.ai.AI;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.ai.Arguments;
import org.kratos.framework.utils.Best;
import org.kratos.reversi.Board;
import org.kratos.reversi.Reversi;

/**
 * Created by FakeYou on 4/14/14.
 */
public class ReversiAI extends AI {

    private Reversi reversi;

    private CommandListener gameListener;
    private CommandListener challengeListener;

    public ReversiAI() {
        super();
        reversi = new Reversi(kratos);
    }

    public void start() {
        beforeStart();

        kratos.setUsername(getUsername());

        final CommandListener loginListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.OK && getChallenger() != null) {
                    kratos.getInterpreter().challenge(getChallenger(), "Reversi", null);
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
                if(status == Communication.status.GAME_MATCH) {
                    System.out.println("[ReversiAI/start] match found");

                    reversi = new Reversi(kratos);
                }
                else if(status == Communication.status.GAME_MOVE) {
                    System.out.println("[ReversiAI/start] move made");

                    reversi.getBoard().debug();
                }
                else if(status == Communication.status.GAME_YOUR_TURN) {
                    System.out.println("[ReversiAI/start] my turn");

                    Best bestMove = getBestMove(reversi.getBoard().getPlayerSquare());

                    System.out.println("[ReversiAI/start] best " + bestMove);
                    reversi.doMove(bestMove.x, bestMove.y);
                }
                else if(status == Communication.status.GAME_LOSS) {
                    System.out.println("[ReversiAI/start] lost");

                    if(getChallenger() != null) {
                        unregisterGameListener();
                        kratos.getInterpreter().logout(null);
                        kratos.getInterpreter().disconnect();
                        kratos.getCommunication().disconnect();
                    }
                }
                else if(status == Communication.status.GAME_WIN) {
                    System.out.println("[ReversiAI/start] won");

                    if(getChallenger() != null) {
                        unregisterGameListener();
                        kratos.getInterpreter().logout(null);
                        kratos.getInterpreter().disconnect();
                        kratos.getCommunication().disconnect();
                    }
                }
                else if(status == Communication.status.GAME_DRAW) {
                    System.out.println("[ReversiAI/start] draw");

                    if(getChallenger() != null) {
                        unregisterGameListener();
                        kratos.getInterpreter().logout(null);
                        kratos.getInterpreter().disconnect();
                        kratos.getCommunication().disconnect();
                    }
                }
            }
        };

        challengeListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                Challenge challenge = parser.parseChallengeRequest(response);

                if(challenge.getGame().equals("Reversi")) {
                    System.out.println("[ReversiAI/start] accepting challenge from: " + challenge.getChallenger());

                    kratos.getInterpreter().challengeAccept(challenge.getNumber(), null);
                }
            }
        };

        kratos.getCommunication().getCommunicationListener("game").addListener(gameListener);
        kratos.getCommunication().getCommunicationListener("challengeRequest").addListener(challengeListener);
    }

    public Best getBestMove(Board.Square square) {
        return getBestMove(square, reversi.getBoard().clone(), 0);
    }

    public Best getBestMove(Board.Square square, Board board, int depth) {

        Best best = new Best(-1, 1, 1);
        Best reply;
        int value;

        if(square == board.getPlayerSquare()) {
            value = board.getScore(board.getOpponentSquare());
        }
        else {
            value = board.getScore(board.getPlayerSquare());
        }

        if(depth >= getDepth()) {
            return new Best(value);
        }

        for(int y = 0; y < board.getHeight(); y++) {
            for(int x = 0; x < board.getWidth(); x++) {

                Board tempBoard = board.clone();
                reversi.setBoard(tempBoard);

                if(!reversi.isLegalMove(x, y, square) || board.getSquare(x, y) != Board.Square.EMPTY) {
                    reversi.setBoard(board);
                    continue;
                }

                best.x = x;
                best.y = y;

                if(square == reversi.getBoard().getPlayerSquare()) {

                    tempBoard.setSquare(x, y, square);
                    reversi.flipTiles(x, y, square);

                    reply = getBestMove(reversi.getBoard().getOpponentSquare(), tempBoard, depth + 1);

                    if((value == -1 && reply.value != -1) || reply.value < value) {
                        best = new Best(reply.value, x, y);
                    }
                }
                else {
                    tempBoard.setSquare(x, y, square);
                    reversi.flipTiles(x, y, square);

                    reply = getBestMove(reversi.getBoard().getPlayerSquare(), tempBoard, depth + 1);

                    if((value == -1 && reply.value != -1) || reply.value > value) {
                        best = new Best(reply.value, x, y);
                    }
                }

                reversi.setBoard(board);
            }
        }

//        System.out.println("[ReversiAI/getBestMove] square: " + square + " depth: " + depth + ", " + best);
        return best;
    }

    public static void main(String[] args) {
        ReversiAI ai = new ReversiAI();

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
