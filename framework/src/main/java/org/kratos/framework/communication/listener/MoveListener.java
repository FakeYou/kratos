package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.MoveCommand;

/**
 * Created by FakeYou on 4/8/14.
 */
public class MoveListener extends AbstractListener {

    public MoveListener(MoveCommand command) {
        super(command);
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrNoMoveEntered = "^(ERR No move entered)$";

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "move set");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrNoMoveEntered)) {
            informListeners(Communication.status.ERROR_MOVE_NO_MOVE_ENTERED, "no move entered");
            listening = false;
            return resolved.COMPLETE;
        }

        return resolved.INCOMPLETE;
    }
}
