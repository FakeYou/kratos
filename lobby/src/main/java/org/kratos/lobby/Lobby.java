package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

    private Kratos kratos;
    private Interpreter interpreter;
    private App app;

    public Lobby(App app, Kratos kratos) {
        this.app = app;
        this.kratos = kratos;
        this.interpreter = kratos.getInterpreter();

        BoxLayout box = new BoxLayout(playerlistPanel, BoxLayout.PAGE_AXIS);
        playerlistPanel.setLayout(box);
    }

    public void reload() {
        reloadGamelist();
        reloadPlayerlist();
    }

    public void reloadGamelist() {
        CommandListener gamelistListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                String[] gamelist = interpreter.parseGamelist(response);

                for(String game : gamelist) {
                    gamelistCombobox.addItem(game);
                }

                gamelistCombobox.validate();
                panel.validate();
            }
        };

        gamelistCombobox.removeAllItems();
        interpreter.getGamelist(gamelistListener);
    }

    public void reloadPlayerlist() {
        CommandListener playerlistListener = new CommandListener() {
            @Override
            public void trigger(Communication.status status, String response) {
                String[] playerlist = interpreter.parsePlayerlist(response);

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

        playerlistPanel.removeAll();
        interpreter.getPlayerlist(playerlistListener);
    }

    public JPanel getPanel() {
        return panel;
    }
}
