package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;

/**
 * Created by FakeYou on 4/6/14.
 */
public class LogoutCommand implements CommunicationCommand {

    private Communication communication;

    public LogoutCommand(Communication communication) {
        this.communication = communication;
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
