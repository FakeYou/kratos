package org.kratos.framework;

import com.google.gson.JsonElement;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;
import org.kratos.framework.game.Player;
import org.kratos.framework.utils.Settings;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Kratos {
    private Settings settings;

    private Communication communication;
    private Interpreter interpreter;
    private Parser parser;

    private Match match;
    private Player player;

    private Thread communicationThread;

    public Kratos() {
        settings = new Settings();
        settings.loadSettingsFile("./framework/src/main/resources/settings.json");

        communication = new Communication(this);

        interpreter = new Interpreter(communication);
        parser = new Parser();

        communicationThread = new Thread(communication);
        communicationThread.start();
    }

    public void setUsername(String username) {
        if(player == null) {
            player = new Player(username, true);
            match = new Match(this, player);
        }
        else {
            player.setUsername(username);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public JsonElement getSetting(String... keys) {
        return settings.get(keys);
    }

    public Communication getCommunication() {
        return communication;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public Parser getParser() { return parser; }

    public static void main(String[] args) {
        new Kratos();
    }
}
