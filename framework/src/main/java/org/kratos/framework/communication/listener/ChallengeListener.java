package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.ChallengeCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/6/14.
 */
public class ChallengeListener implements CommunicationListener {

    private ChallengeCommand command;
    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public ChallengeListener(ChallengeCommand command) {
        this.command = command;

        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrUnknownPlayerPattern = "^(ERR Unknown player: \\').+(\\')$";
        String ErrUnknownGamePattern = "^(ERR Unknown game: \\').+(\\')$";
        String ErrIllegalArguments = "^(ERR Illegal argument\\(s\\) for command)$";

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "challenge sent");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrUnknownPlayerPattern)) {
            informListeners(Communication.status.ERROR_CHALLENGE_UNKNOWN_PLAYER, message);
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrUnknownGamePattern)) {
            informListeners(Communication.status.ERROR_CHALLENGE_UNKNOWN_GAME, message);
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrIllegalArguments)) {
            informListeners(Communication.status.ERROR_CHALLENGE_ILLEGAL_ARGUMENTS, message);
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
