package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;

/**
 * Created by FakeYou on 4/6/14.
 */
public class LogoutCommand extends AbstractCommand {

    public LogoutCommand(Communication communication) {
        super(communication);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 0) {
            throw new Exception("LogoutCommand: not enough arguments");
        }

        communication.getHandler().write("logout");
    }

    @Override
    public void addListener(CommandListener listener) {
    }

}
