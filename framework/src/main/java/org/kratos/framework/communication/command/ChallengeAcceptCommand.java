package org.kratos.framework.communication.command;

import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.listener.ChallengeAcceptListener;

/**
 * Created by FakeYou on 4/9/14.
 */
public class ChallengeAcceptCommand extends AbstractCommand {

    public ChallengeAcceptCommand(Communication communication) {
        super(communication);
        listener = new ChallengeAcceptListener(this);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 1) {
            throw new Exception("ChallengeAcceptCommand: not enough arguments");
        }

        String challengenumber = arguments[0];

        listener.setListening(true);
        communication.getHandler().write("challenge accept " + challengenumber);

    }
}
