package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class ChallengeRequestListener extends AbstractListener {

    public ChallengeRequestListener() {
        super();
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
}
