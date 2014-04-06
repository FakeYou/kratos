package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class ChallengeListener implements CommunicationListener {

    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public ChallengeListener() {
        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public resolved trigger(String message) {
        String ChallengePatten = "^(SVR GAME CHALLENGE \\{CHALLENGER: \").+(\", GAMETYPE: \")[a-zA-Z ]+(\", CHALLENGENUMBER: \")[0-9]+(\"\\})$";

        if(message.matches(ChallengePatten)) {
            informListeners(Communication.status.OK, message);
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

    @Override
    public void addListener(CommandListener listener) {
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(CommandListener listener) {
        listeners.remove(listener);
    }
}
