package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationHandler;
import org.kratos.framework.communication.CommunicationListener;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/5/14.
 */
public class ConnectListener implements CommunicationListener {

    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public ConnectListener() {
        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public resolved trigger(String message) {
        String WelcomeMessagePattern = "^(Strategic Game Server \\[Version 1.0\\])$";
        String CopyrightMessage = "^(\\(C\\) Copyright 2009 Hanze Hogeschool Groningen)$";
        String ConnectionRefusedPattern = "^(Connection refused: connect)$";
        String ConnectionErrorPattern = "^(error)$";

        if(message.matches(WelcomeMessagePattern)) {
            return resolved.PARTIAL;
        }
        else if(message.matches(CopyrightMessage)) {
            informListeners(Communication.status.OK, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(ConnectionRefusedPattern)) {
            informListeners(Communication.status.ERROR_CONNECT_REFUSED, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(ConnectionErrorPattern)) {
            informListeners(Communication.status.ERROR, message);
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

    private void informListeners(Communication.status status, String response) {
        for(CommandListener listener : listeners) {
            listener.trigger(status, response);
        }
    }

    @Override
    public void addListener(CommandListener listener) {
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
}
