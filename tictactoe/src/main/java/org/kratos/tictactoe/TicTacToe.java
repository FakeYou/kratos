package org.kratos.tictactoe;

import org.kratos.framework.GUI.GameView;
import org.kratos.framework.Kratos;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.Player;
import org.kratos.framework.game.events.Move;

import java.util.ArrayList;

/**
 * Created by FakeYou on 10-4-14.
 */
public class TicTacToe{
    private Kratos kratos;
    private Player player;
    private Player opponent;
    private Match match;
    private GameView view;
    private ArrayList<Move> moves = new ArrayList<Move>();
    private int amountOfExecutedMoves = 0;

    public TicTacToe(Kratos kratos){
        this.kratos = kratos;
        this.match = kratos.getMatch();
        this.player = match.getPlayer();
        this.opponent = match.getOpponent();
        System.out.println("Starting view");

        String opponentName = opponent == null ? "Opponent" : opponent.getUsername();
        this.view = new GameView(GameView.TIC_TAC_TOE, player.getUsername(), opponentName, match, this);

        new Thread(new detectChanges()).start();
    }

    public void incrementAmountOfExecutedMoves(){
        amountOfExecutedMoves++;
    }

    private class detectChanges implements Runnable{
        public void run(){
            while(true){
                moves = match.getMoves();

                // See if we can update opponent's name.
                if(view.getPlayerTwoName() == "Opponent" && match.getOpponent() != null){
                    opponent = match.getOpponent();
                    view.setPlayerTwoName(opponent.getUsername());
                }

                // Check if there has been a new move
                if(amountOfExecutedMoves < moves.size()){
                    // If so, forward the new move to the gameview and increment amountOfExecutedMoves.
                    Move move = moves.get(amountOfExecutedMoves);
                    amountOfExecutedMoves++;

                    int playerInt = move.getPlayer() == opponent.getUsername() ? 2 : 1;
                    String[] coordinates = move.getMove().split(",");
                    view.makeMove(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), playerInt);
                }

                // Check if the game has ended in either a win, a loss or a draw.
                Match.States matchState = match.getState();
                if(matchState == Match.States.WIN){
                    view.endGame(0);
                }
                else if(matchState == Match.States.LOSS) {
                    view.endGame(1);
                }
                else if(matchState == Match.States.DRAW){
                    view.endGame(2);
                }

                // Ensure we can't make a move when it's the opponent's turn.
                if(matchState == Match.States.OPPONENT_TURN){
                    if(view.ableToMakeMove() == true){
                        view.setTurn(2);
                    }
                }

                // Ensure we can make a move when it's our turn.
                if(matchState == Match.States.PLAYER_TURN){
                    if(view.ableToMakeMove() == false){
                        view.setTurn(1);
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e)
                {
                }
            }
        }
    }
}
