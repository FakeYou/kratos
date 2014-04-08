package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.AbstractCommand;
import org.kratos.framework.communication.command.MoveCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/8/14.
 */
public abstract class AbstractListener implements CommunicationListener {

    protected AbstractCommand command;
    protected Boolean listening = true;
    protected ArrayList<CommandListener> listeners;

    public AbstractListener() {
        this(null);
    }

    public AbstractListener(AbstractCommand command) {
        this.command = command;

        listeners = new ArrayList<CommandListener>();
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

    protected void informListeners(Communication.status status, String response) {
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
