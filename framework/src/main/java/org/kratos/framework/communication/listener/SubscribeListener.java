package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.SubscribeCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/6/14.
 */
public class SubscribeListener extends AbstractListener {

    public SubscribeListener(SubscribeCommand command) {
        super(command);
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
}
