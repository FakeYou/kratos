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
    public Boolean trigger(String message) {
        String WelcomeMessagePattern = "^(Strategic Game Server \\[Version 1.0\\]\\(C\\) Copyright 2009 Hanze Hogeschool Groningen)$";
        String ConnectionRefusedPattern = "^(Connection refused: connect)$";
        String ConnectionErrorPattern = "^(error)$";

        if(message.matches(WelcomeMessagePattern)) {
            System.out.println(message);
            informListeners(Communication.status.OK, message);
            return true;
        }
        else if(message.matches(ConnectionRefusedPattern)) {
            System.out.println(message);
            informListeners(Communication.status.ERROR_CONNECT_REFUSED, message);
            return true;
        }
        else if(message.matches(ConnectionErrorPattern)) {
            System.out.println(message);
            informListeners(Communication.status.ERROR, message);
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

    private void informListeners(Communication.status status, String response) {
        System.out.println(listeners.size());

        for(CommandListener listener : listeners) {
            System.out.println(status);
            listener.trigger(status, response);
        }
    }

    @Override
    public void addListener(CommandListener listener) {
        listeners.add(listener);
    }
}
