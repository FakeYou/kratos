package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.ChallengeCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 4/6/14.
 */
public class ChallengeListener extends AbstractListener {

    public ChallengeListener(ChallengeCommand command) {
        super(command);
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
}
