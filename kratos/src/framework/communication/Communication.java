package framework.communication;

import framework.Kratos;
import framework.communication.command.GetCommand;
import framework.communication.command.LoginCommand;
import framework.communication.listener.ChallengeListener;
import framework.communication.telnet.TelnetHandler;

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

    public Communication(Kratos kratos) {
        this.kratos = kratos;

        connected = false;
        loop = true;

        host = kratos.getSetting("host").getAsString();
        port = kratos.getSetting("port").getAsInt();

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
    }

    public void connect() {
        handler.connect(host, port);
        connected = true;
    }

    public void disconnect() {
        handler.disconnect();
        connected = false;
    }

    public void command(String command, CommandListener listener, String ... arguments) {
        if(connected && handler.isReady()) {
            try {
                commands.get(command).execute(arguments);
                commands.get(command).addListener(listener);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void login(String username, CommandListener listener) {
        if(connected && handler.isReady()) {
            try {
                commands.get("login").execute(username);
                commands.get("login").addListener(listener);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void get(String argument, CommandListener listener) {
        if(connected && handler.isReady()) {
            try {
                commands.get("get").execute(argument);
                commands.get("get").addListener(listener);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public CommunicationHandler getHandler() {
        return handler;
    }
}
