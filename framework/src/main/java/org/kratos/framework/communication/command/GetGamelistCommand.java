package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.GetGamelistListener;
import org.kratos.framework.communication.listener.GetPlayerlistListener;

/**
 * Created by FakeYou on 3/29/14.
 */
public class GetGamelistCommand extends AbstractCommand {

    public GetGamelistCommand(Communication communication) {
        super(communication);
        listener = new GetGamelistListener(this);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 0) {
            throw new Exception("GetPlayerlistCommand: not enough arguments");
        }

        listener.setListening(true);
        communication.getHandler().write("get gamelist");
    }
}
