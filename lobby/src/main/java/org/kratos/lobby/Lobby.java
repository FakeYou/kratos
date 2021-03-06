package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Match;
import org.kratos.guess.GuessGame;
import org.kratos.reversi.ai.ReversiAI;
import org.kratos.reversi.application.ReversiApp;
import org.kratos.tictactoe.ai.TicTacToeAI;
import org.kratos.tictactoe.application.TicTacToeApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FakeYou on 4/6/14.
 */
public class Lobby {
    private JComboBox gamelistCombobox;
    private JButton randomOpponentButton;
    private JButton singleplayerButton;
    private JButton logoutButton;
    private JPanel panel;
    private JPanel playerlistPanel;
    private JPanel controlsPanel;
    private JButton refreshPlayerListButton;
    private JLabel statusLabel;

    private Kratos kratos;
    private Interpreter interpreter;
    private Parser parser;
    private App app;

    private CommandListener gamelistListener;
    private CommandListener playerlistListener;
    private CommandListener subscribeListener;

    public Lobby(final App app, final Kratos kratos) {
        this.app = app;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();
        this.parser = kratos.getParser();

        BoxLayout box = new BoxLayout(playerlistPanel, BoxLayout.PAGE_AXIS);
        playerlistPanel.setLayout(box);

        kratos.getCommunication().getCommunicationListener("challengeRequest").addListener(new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                Challenge challenge = parser.parseChallengeRequest(response);

                if(challenge.getChallenger().matches("^(Bot ).+")) {
                    challenge.setAccepted(true);
                    interpreter.challengeAccept(challenge.getNumber(), null);
                }
                else {
                    String message = "You have been challenged by " + challenge.getChallenger() + " to play " + challenge.getGame() + ".\n\r" +
                            "Do you wish to accept the challenge?";

                    int answer = JOptionPane.showConfirmDialog(app.getLobbyFrame(), message, "Challenge - kratos", JOptionPane.YES_NO_OPTION);

                    if(answer == JOptionPane.YES_OPTION) {
                        challenge.setAccepted(true);
                        interpreter.challengeAccept(challenge.getNumber(), null);
                    }
                    else {
                        challenge.setAccepted(false);
                    }
                }
            }
        });

        kratos.getCommunication().getCommunicationListener("game").addListener(new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                if(status == Communication.status.GAME_MATCH) {

                    Match match = parser.parseMatch(response);
                    String gametype = match.getGametype();

                    if(gametype.equals("Tic Tac Toe")){
                        new TicTacToeApp(kratos, app.getLobbyFrame());
                    } else if (gametype.equals("Reversi")){
                        new ReversiApp(kratos, app.getLobbyFrame());
                    } else {
                        JOptionPane.showMessageDialog(app.getLobbyFrame(), "Sorry, this game is not supported yet", "Kratos", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        gamelistCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setSelectedGame((String) gamelistCombobox.getSelectedItem());
            }
        });

        randomOpponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interpreter.subscribe(app.getSelectedGame(), subscribeListener());
                statusLabel.setText("Finding opponent");
                statusLabel.validate();
            }
        });

        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(app.getSelectedGame().equals("Tic Tac Toe")) {
                    TicTacToeAI ticTacToeAI = new TicTacToeAI();
                    ticTacToeAI.setRandomUsername();
                    ticTacToeAI.setHost(kratos.getCommunication().getHost());
                    ticTacToeAI.setPort(kratos.getCommunication().getPort());
                    ticTacToeAI.setChallenger(kratos.getPlayer().getUsername());
                    ticTacToeAI.start();
                }
                else if(app.getSelectedGame().equals("Reversi")) {
                    ReversiAI reversiAI = new ReversiAI();
                    reversiAI.setRandomUsername();
                    reversiAI.setHost(kratos.getCommunication().getHost());
                    reversiAI.setPort(kratos.getCommunication().getPort());
                    reversiAI.setChallenger(kratos.getPlayer().getUsername());
                    reversiAI.start();
                }
                else {
                    JOptionPane.showMessageDialog(app.getLobbyFrame(), "Sorry, this game is not supported yet", "Kratos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interpreter.logout(null);
                interpreter.disconnect();
                app.openLogin();
            }
        });

        refreshPlayerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadPlayerlist();
            }
        });
    }

    public void reload() {
        reloadPlayerlist();
        reloadGamelist();
    }

    public void reloadGamelist() {
        gamelistCombobox.removeAllItems();
        gamelistCombobox.validate();
        interpreter.getGamelist(gamelistListener());
    }

    public void reloadPlayerlist() {
        playerlistPanel.removeAll();
        playerlistPanel.validate();
        interpreter.getPlayerlist(playerlistListener());
    }

    private CommandListener gamelistListener() {
        if(gamelistListener == null) {
            gamelistListener =  new CommandListener() {
                @Override
                public void trigger(Communication.status status, String response) {
                    String[] gamelist = parser.parseGamelist(response);

                    app.setSelectedGame(gamelist[0]);

                    for(String game : gamelist) {
                        gamelistCombobox.addItem(game);
                    }

                    gamelistCombobox.validate();
                    panel.validate();
                }
            };
        }

        return gamelistListener;
    }

    private CommandListener playerlistListener() {
        if(playerlistListener == null) {
            playerlistListener = new CommandListener() {
                @Override
                public void trigger(Communication.status status, String response) {
                    String[] playerlist = parser.parsePlayerlist(response);

                    for(int i = 0; i < playerlist.length; i++) {
                        String player = playerlist[i];

                        PlayerlistItem item = new PlayerlistItem(app, kratos);
                        item.setUsername(player);

                        if(i % 2 == 1) {
                            item.getPanel().setBackground(new Color(225, 225, 225));
                        }

                        playerlistPanel.add(item.getPanel());
                        panel.validate();
                    }
                }
            };
        }

        return playerlistListener;
    }

    private CommandListener subscribeListener() {
        if(subscribeListener == null) {
            subscribeListener = new CommandListener() {
                @Override
                public void trigger(Communication.status status, String response) {
                    System.out.println(status + " - " + response);
                    if(status == Communication.status.OK) {
                        statusLabel.setText("Subscribed to " + app.getSelectedGame());
                        statusLabel.validate();
                    }
                }
            };
        }

        return subscribeListener;
    }

    public JPanel getPanel() {
        return panel;
    }
}
