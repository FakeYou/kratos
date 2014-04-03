package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.LoginListener;

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
