package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.LoginCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class LoginListener implements CommunicationListener {

    private LoginCommand command;
    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public LoginListener(LoginCommand command) {
        this.command = command;

        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public Boolean trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrPattern = "^(ERR).+";
        String ErrDuplicatePattern = "^(ERR Duplicate name exists)$";
        String ErrLoggedInPattern = "^(ERR Already logged in)$";

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "logged in");
            listening = false;
            return true;
        }
        else if(message.matches(ErrDuplicatePattern)) {
            informListeners(Communication.status.ERROR_LOGIN_DUPLICATE_NAME, "duplicate name");
            listening = false;
            return true;
        }
        else if(message.matches(ErrLoggedInPattern)) {
            informListeners(Communication.status.ERROR_LOGIN_ALREADY_LOGGED_IN, "already logged in");
            listening = false;
            return true;
        }
        else if(message.matches(ErrPattern)) {
            informListeners(Communication.status.ERROR, "unknown error");
            listening = false;
            return true;
        }

        return false;
    }

    @Override
    public void setListening(Boolean listening) {
        this.listening = listening;
    }

    @Override
    public Boolean isListening() {
        return listening;
    }

    @Override
    public void addListener(CommandListener listener) {
        listeners.add(listener);
    }

    private void informListeners(Communication.status status, String response) {
        for(CommandListener listener : listeners) {
            listener.trigger(status, response);
        }
    }
}
