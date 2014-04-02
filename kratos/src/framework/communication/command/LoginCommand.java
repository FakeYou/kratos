package framework.communication.command;

import framework.communication.CommandListener;
import framework.communication.CommunicationCommand;
import framework.communication.Communication;
import framework.communication.CommunicationListener;
import framework.communication.listener.LoginListener;

/**
 * Created by FakeYou on 3/29/14.
 */
public class LoginCommand implements CommunicationCommand {

    private Communication communication;
    private LoginListener listener;

    public LoginCommand(Communication communication) {
        this.communication = communication;
        listener = new LoginListener(this);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 1) {
            throw new Exception("LoginCommand: not enough arguments");
        }

        String username = arguments[0];

        listener.setListening(true);
        communication.getHandler().write("login " + username);
    }

    @Override
    public void addListener(CommandListener listener) {
        this.listener.addListener(listener);
    }

    public CommunicationListener getListener() {
        return listener;
    }

}
