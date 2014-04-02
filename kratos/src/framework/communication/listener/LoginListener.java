package framework.communication.listener;

import framework.communication.CommandListener;
import framework.communication.CommunicationListener;
import framework.communication.command.LoginCommand;

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
            informListeners(true, "logged in");
            listening = false;
            return true;
        }
        else if(message.matches(ErrDuplicatePattern)) {
            informListeners(false, "duplicate name");
            listening = false;
            return true;
        }
        else if(message.matches(ErrLoggedInPattern)) {
            informListeners(false, "already logged in");
            listening = false;
            return true;
        }
        else if(message.matches(ErrPattern)) {
            informListeners(false, "unknown error");
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

    private void informListeners(Boolean success, String response) {
        for(CommandListener listener : listeners) {
            listener.trigger(success, response);
        }
    }
}
