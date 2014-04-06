package org.kratos.framework.communication;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.command.GetGamelistCommand;
import org.kratos.framework.communication.command.GetPlayerlistCommand;
import org.kratos.framework.communication.command.LoginCommand;
import org.kratos.framework.communication.command.LogoutCommand;
import org.kratos.framework.communication.listener.ChallengeListener;
import org.kratos.framework.communication.listener.ConnectListener;
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
        ERROR,
        ERROR_LOGIN_DUPLICATE_NAME,
        ERROR_LOGIN_ALREADY_LOGGED_IN,
        ERROR_LOGIN_NO_NAME,
        ERROR_GET_UNKNOWN_ARGUMENT,
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
    }

    public void registerListeners() {
        ChallengeListener challengeListener = new ChallengeListener();
        listeners.put("challenge", challengeListener);
        handler.addListener(challengeListener);

        ConnectListener connectListener = new ConnectListener();
        listeners.put("connect", connectListener);
        handler.addListener(connectListener);
    }

    public void connect(String host, int port) {
        this.host = host;
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
