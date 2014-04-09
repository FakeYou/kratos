package org.kratos.framework.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yuri on 3/4/2014.
 */
public class GameView extends JFrame {

    public static final int TIC_TAC_TOE = 0;
    public static final int REVERSI = 1;

    private JLabel topText;
    private JButton forfeit;

    private GameBoard board;

    private JLabel player1NameLabel;
    private JLabel turnTimePlayer1;

    private JLabel player2NameLabel;
    private JLabel turnTimePlayer2;

    private String currentGameName = "";

    private String player1Name = "";
    private String player2Name = "";

    private int currentPlayer = 1;

    private int time = 10;

    private Timer timer;

    public GameView(int game, String player1Name, String player2Name, int startingPlayer){
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        currentPlayer = startingPlayer;
        initTurnTimer();
        init(game);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialize view
      */
    public void init(int game){
        // Depending on which game was requested, create an appropriate playing board.
        switch(game) {
            case TIC_TAC_TOE:
                this.board = new GameBoard(3, 3, 100, this);
                currentGameName = "Tic Tac Toe";
                break;
            case REVERSI:
                this.board = new GameBoard(8, 8, 50, this);
                currentGameName = "Reversi";
                break;
        }

        // Layout the individual components.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // add label
        topText = new JLabel("Playing " + currentGameName + " versus " + player2Name);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 5, 0);
        this.add(topText, c);

        // add forfeit button
        c = new GridBagConstraints();
        forfeit = new JButton("Forfeit");
        // TODO: Add button actionlistener.
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridwidth = c.REMAINDER;
        c.fill = c.NONE;
        this.add(forfeit, c);

        // add game board
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridwidth = c.REMAINDER;
        c.gridy = 1;
        this.add(board, c);

        // add players labels and time
        // Player 1
        player1NameLabel = new JLabel(player1Name);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 2;
        this.add(player1NameLabel, c);

        // Turntimer for player 1
        turnTimePlayer1 = new JLabel("0:05");
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 3;
        this.add(turnTimePlayer1, c);

        // Player 2
        player2NameLabel = new JLabel(player2Name);
        c = new GridBagConstraints();
        c.gridwidth = c.REMAINDER;
        c.fill = c.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 2;
        this.add(player2NameLabel, c);

        // Turn time for player 2
        turnTimePlayer2 = new JLabel("Opponent's turn");
        c = new GridBagConstraints();
        c.gridwidth = c.REMAINDER;
        c.fill = c.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 3;
        this.add(turnTimePlayer2, c);
    }

    /**
     * Change or set player one's name (should be done in constructor)
     * @param name
     */
    public void setPlayerOneName(String name){
        player1Name = name;
        player1NameLabel.setText(name);
    }

    /**
     * Change or set player two's name (should be done in constructor)
     * @param name
     */
    public void setPlayerTwoName(String name){
        player2Name = name;
        player2NameLabel.setText(name);
    }

    /**
     * Set turn to either player one or two
     * @param player 1 for player one, 2 for player two
     */
    public void setTurn(int player){
        currentPlayer = player;
    }

    /**
     * Swap the turn (called when a move has been made or timer reaches 0)
     */
    public void swapTurn(){
        time = 10;
        currentPlayer = currentPlayer == 2 ? 1 : 2;

        JLabel turnTimer = currentPlayer == 1 ? turnTimePlayer1 : turnTimePlayer2;
        turnTimer.setText(time > 9 ? "0:10" : "0:0" + time);

        JLabel opponentsTurnTime = (currentPlayer == 1) ? turnTimePlayer2 : turnTimePlayer1;
        opponentsTurnTime.setText("Opponent's turn...");
    }

    /**
     * Instantiate and start turn timer.
     */
    public void initTurnTimer(){
            timer = new Timer(1000, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    time = time == 11 ? time -= 1 : time;
                    time -= 1;
                    if(time == -1){
                        swapTurn();
                    }

                    JLabel turnTimer = currentPlayer == 1 ? turnTimePlayer1 : turnTimePlayer2;
                    turnTimer.setText(time > 9 ? "0:10" : "0:0" + time);
                }});
            timer.start();
    }

    /**
     * Method to check if it's currently player 1's turn (to prevent making moves in GUI while it's not your turn)
     * @return
     */
    public Boolean ableToMakeMove(){
        return currentPlayer == 1 ? true : false;
    }
}

