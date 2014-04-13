package org.kratos.framework.communication;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.command.*;
import org.kratos.framework.communication.listener.ChallengeRequestListener;
import org.kratos.framework.communication.listener.ConnectListener;
import org.kratos.framework.communication.listener.GameListener;
import org.kratos.framework.communication.telnet.TelnetHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Communication implements Runnable {

    private Kratos kratos;
    private CommunicationHandler handler;

    private String host;
    private int port;
    private boolean connected;

    private HashMap<String, CommunicationCommand> commands;
    private HashMap<String, CommunicationListener> listeners;

    private Stack<CommandExecuter> toExecute;

    private boolean loop;

    public enum status {
        OK,
        GAME_MOVE,
        GAME_LOSS,
        GAME_WIN,
        GAME_DRAW,
        GAME_MATCH,
        GAME_YOUR_TURN,
        CHALLENGE_CANCELLED,
        ERROR,
        ERROR_LOGIN_DUPLICATE_NAME,
        ERROR_LOGIN_ALREADY_LOGGED_IN,
        ERROR_LOGIN_NO_NAME,
        ERROR_CHALLENGE_UNKNOWN_PLAYER,
        ERROR_CHALLENGE_UNKNOWN_GAME,
        ERROR_CHALLENGE_ILLEGAL_ARGUMENTS,
        ERROR_CHALLENGE_INVALID_NUMBER,
        ERROR_GET_UNKNOWN_ARGUMENT,
        ERROR_SUBSCRIBE_UNKNOWN_GAME,
        ERROR_MOVE_NO_MOVE_ENTERED,
        ERROR_CONNECT_REFUSED
    }

    public Communication(Kratos kratos) {
        this.kratos = kratos;

        connected = false;
        loop = true;

        handler = new TelnetHandler();

        commands = new HashMap<String, CommunicationCommand>();
        registerCommands();

        listeners = new HashMap<String, CommunicationListener>();
        registerListeners();

        toExecute = new Stack<CommandExecuter>();
    }

    @Override
    public void run() {
        while(true) {
            if(!toExecute.isEmpty() && !handler.isBusy()) {

                CommandExecuter executer = toExecute.remove(0);
                System.out.println("[Communication/execute] " + executer.getCommand());

                try {
                    commands.get(executer.getCommand()).addListener(executer.getListener());
                    commands.get(executer.getCommand()).execute(executer.getArguments());
                    handler.setBusy(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e) { }
            }
        }
    }

    public void registerCommands() {
        LoginCommand loginCommand = new LoginCommand(this);
        commands.put("login", loginCommand);
        handler.addListener(loginCommand.getListener());

        LogoutCommand logoutCommand = new LogoutCommand(this);
        commands.put("logout", logoutCommand);

        GetPlayerlistCommand getPlayerlistCommand = new GetPlayerlistCommand(this);
        commands.put("getPlayerlist", getPlayerlistCommand);
        handler.addListener(getPlayerlistCommand.getListener());

        GetGamelistCommand getGamelistCommand = new GetGamelistCommand(this);
        commands.put("getGamelist", getGamelistCommand);
        handler.addListener(getGamelistCommand.getListener());

        ChallengeCommand challengeCommand = new ChallengeCommand(this);
        commands.put("challenge", challengeCommand);
        handler.addListener(challengeCommand.getListener());

        ChallengeAcceptCommand challengeAcceptCommand = new ChallengeAcceptCommand(this);
        commands.put("challengeAccept", challengeAcceptCommand);
        handler.addListener(challengeAcceptCommand.getListener());

        SubscribeCommand subscribeCommand = new SubscribeCommand(this);
        commands.put("subscribe", subscribeCommand);
        handler.addListener(subscribeCommand.getListener());

        MoveCommand moveCommand = new MoveCommand(this);
        commands.put("move", moveCommand);
        handler.addListener(moveCommand.getListener());
    }

    public void registerListeners() {
        ChallengeRequestListener challengeRequestListener = new ChallengeRequestListener();
        listeners.put("challengeRequest", challengeRequestListener);
        handler.addListener(challengeRequestListener);

        ConnectListener connectListener = new ConnectListener();
        listeners.put("connect", connectListener);
        handler.addListener(connectListener);

        GameListener gameListener = new GameListener();
        listeners.put("game", gameListener);
        handler.addListener(gameListener);
    }

    public void connect(String host, int port) {
        this.host = host.trim();
        this.port = port;

        try {
            handler.connect(host, port);
            connected = true;
        } catch (IOException e) {
            listeners.get("connect").trigger(e.getMessage());
        }
    }

    public void disconnect() {
        handler.disconnect();
        connected = false;
    }

    public void command(String command, CommandListener listener, String ... arguments) {
        if(connected) {
            toExecute.add(new CommandExecuter(command, listener, arguments));
        }
    }

    public CommunicationListener getCommunicationListener(String name) {
        if(listeners.containsKey(name)) {
            return listeners.get(name);
        }

        return null;
    }

    public boolean isConnected() {
        return connected;
    }

    public CommunicationHandler getHandler() {
        return handler;
    }
}
