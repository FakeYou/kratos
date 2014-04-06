package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.SubscribeCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/6/14.
 */
public class SubscribeListener implements CommunicationListener {

    private SubscribeCommand command;
    private boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public SubscribeListener(SubscribeCommand command) {
        this.command = command;

        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrUnkownGamePattern = "^(ERR Unknown game: \').+(\')$";

        System.out.println("-" + message);

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "subscribed");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrUnkownGamePattern)) {
            informListeners(Communication.status.ERROR_SUBSCRIBE_UNKNOWN_GAME, "unknown game");
            listening = false;
            return resolved.COMPLETE;
        }

        return resolved.INCOMPLETE;
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
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(CommandListener listener) {
        listeners.remove(listener);
    }

    private void informListeners(Communication.status status, String response) {
        ArrayList<CommandListener> listeners = (ArrayList<CommandListener>) this.listeners.clone();

        for(CommandListener listener : listeners) {
            if(listener.active) {
                listener.trigger(status, response);
            }
            else {
                this.listeners.remove(listener);
            }
        }
    }
}
