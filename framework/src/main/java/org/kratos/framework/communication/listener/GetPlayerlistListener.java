package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.GetPlayerlistCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class GetPlayerlistListener extends AbstractListener {

    public GetPlayerlistListener(GetPlayerlistCommand command) {
        super(command);
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrPattern = "^(ERR).+";
        String ErrUnknownArgumentPattern = "^(ERR Unknown GET argument: \').+(\')$";
        String PlayerlistPattern = "^(SVR PLAYERLIST \\[).*(\\])$";

        if(message.matches(OkPattern)) {
            return resolved.PARTIAL;
        }
        else if(message.matches(PlayerlistPattern)) {
            informListeners(Communication.status.OK, message);
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrUnknownArgumentPattern)) {
            informListeners(Communication.status.ERROR_GET_UNKNOWN_ARGUMENT, "unknown argument");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrPattern)) {
            informListeners(Communication.status.ERROR, "unknown error");
            listening = false;
            return resolved.COMPLETE;
        }

        return resolved.INCOMPLETE;
    }
}
