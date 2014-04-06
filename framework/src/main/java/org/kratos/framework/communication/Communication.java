package org.kratos.framework.communication;

import com.sun.scenario.Settings;
import org.kratos.framework.Kratos;
import org.kratos.framework.communication.command.GetCommand;
import org.kratos.framework.communication.command.LoginCommand;
import org.kratos.framework.communication.listener.ChallengeListener;
import org.kratos.framework.communication.listener.ConnectListener;
import org.kratos.framework.communication.telnet.TelnetHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Communication {

    private Kratos kratos;
    private CommunicationHandler handler;

    private String host;
    private int port;
    private boolean connected;

    private HashMap<String, CommunicationCommand> commands;
    private HashMap<String, CommunicationListener> listeners;

    private boolean loop;

    public enum status {
        OK,
        ERROR,
        ERROR_LOGIN_DUPLICATE_NAME,
        ERROR_LOGIN_ALREADY_LOGGED_IN,
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
    }

    public void registerCommands() {
        LoginCommand loginCommand = new LoginCommand(this);
        commands.put("login", loginCommand);
        handler.addListener(loginCommand.getListener());

        GetCommand getCommand = new GetCommand(this);
        commands.put("get", getCommand);
        handler.addListener(getCommand.getListener());
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
            try {
                commands.get(command).execute(arguments);
                commands.get(command).addListener(listener);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public CommunicationListener getCommunicaionListener(String name) {
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
