package org.kratos.framework.communication.command;

import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;

/**
 * Created by FakeYou on 4/8/14.
 */
public class MoveCommand extends AbstractCommand {

    public MoveCommand(Communication communication) {
        super(communication);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 1) {
            throw new Exception("moveCommand: not enough arguments");
        }

        String move = arguments[0];

        listener.setListening(true);
        communication.getHandler().write("move " + move);
    }
}
