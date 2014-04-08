package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.ChallengeListener;

/**
 * Created by FakeYou on 4/6/14.
 */
public class ChallengeCommand extends AbstractCommand {

    public ChallengeCommand(Communication communication) {
        super(communication);
        listener = new ChallengeListener(this);
    }

    @Override
    public void execute(String ... arguments) throws Exception {
        if(arguments.length != 2) {
            throw new Exception("ChallengeCommand: not enough arguments");
        }

        String challengee = arguments[0];
        String game = arguments[1];

        listener.setListening(true);
        communication.getHandler().write("challenge \"" + challengee + "\" \"" + game + "\"");
    }
}
