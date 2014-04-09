package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.ChallengeAcceptCommand;

/**
 * Created by FakeYou on 4/9/14.
 */
public class ChallengeAcceptListener extends AbstractListener {

    public ChallengeAcceptListener(ChallengeAcceptCommand command) {
        super(command);
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String InvalidNumberPattern = "^(ERR Invalid challenge number \\')[0-9]+(\\')$";
        String InvalidArgumentsPattern = "^(ERR Illegal argument\\(s\\) for command)$";

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "challenge accepted");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(InvalidNumberPattern)) {
            informListeners(Communication.status.ERROR_CHALLENGE_INVALID_NUMBER, message);
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(InvalidArgumentsPattern)) {
            informListeners(Communication.status.ERROR_CHALLENGE_ILLEGAL_ARGUMENTS, message);
            listening = false;
            return resolved.COMPLETE;
        }

        return null;
    }
}
