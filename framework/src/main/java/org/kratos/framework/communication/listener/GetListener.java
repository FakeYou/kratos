package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.GetCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class GetListener implements CommunicationListener {

    private GetCommand command;
    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public GetListener(GetCommand command) {
        this.command = command;

        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public Boolean trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrPattern = "^(ERR).+";
        String ErrUnknownArgumentPattern = "^(ERR Unknown GET argument: \').+(\')$";
        String PlayerlistPattern = "^(SVR PLAYERLIST \\[).*(\\])$";
        String GamelistPattern = "^(SVR GAMELIST \\[).*(\\])$";

        if(message.matches(OkPattern)) {
            return true;
        }
        else if(message.matches(PlayerlistPattern)) {
            informListeners(Communication.status.OK, message);
            listening = false;
            return true;
        }
        else if(message.matches(GamelistPattern)) {
            informListeners(Communication.status.OK, message);
            listening = false;
            return true;
        }
        else if(message.matches(ErrUnknownArgumentPattern)) {
            informListeners(Communication.status.ERROR_GET_UNKNOWN_ARGUMENT, "unknown argument");
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
