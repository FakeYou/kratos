package org.kratos.framework.communication;

import org.kratos.framework.game.Challenge;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Interpreter {
    private Communication communication;

    public Interpreter(Communication communication) {
        this.communication = communication;
    }

    public void connect(String host, int port, CommandListener listener) {
        communication.getCommunicationListener("connect").addListener(listener);
        communication.connect(host, port);
    }

    public void disconnect() {
        communication.disconnect();
    }

    public void login(String username, CommandListener listener) {
        communication.command("login", listener, username);
    }

    public void logout(CommandListener listener) {
        communication.command("logout", listener);
    }

    public void challenge(String challengee, String game, CommandListener listener) {
        communication.command("challenge", listener, challengee, game);
    }

    public void getPlayerlist(CommandListener listener) {
        communication.command("getPlayerlist", listener);
    }

    public void getGamelist(CommandListener listener) {
        communication.command("getGamelist", listener);
    }

    public void subscribe(String game, CommandListener listener) {
        communication.command("subscribe", listener, game);
    }

    public void get(String argument, CommandListener listener) {
        communication.command("get", listener, argument);
    }
}
