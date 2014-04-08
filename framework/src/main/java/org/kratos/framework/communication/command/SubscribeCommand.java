package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.SubscribeListener;

/**
 * Created by FakeYou on 4/6/14.
 */
public class SubscribeCommand extends AbstractCommand {

    public SubscribeCommand(Communication communication) {
        super(communication);
        listener = new SubscribeListener(this);
    }

    @Override
    public void execute(String ... arguments) throws Exception {
        if(arguments.length != 1) {
            throw new Exception("SubscribeCommand: not enough arguments");
        }

        String game = arguments[0];

        listener.setListening(true);
        communication.getHandler().write("subscribe " + game);
    }
}
