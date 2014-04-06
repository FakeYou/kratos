package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.GetPlayerlistListener;

/**
 * Created by FakeYou on 3/29/14.
 */
public class GetPlayerlistCommand implements CommunicationCommand {

    private Communication communication;
    private GetPlayerlistListener listener;

    public GetPlayerlistCommand(Communication communication) {
        this.communication = communication;
        listener = new GetPlayerlistListener(this);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 0) {
            throw new Exception("GetPlayerlistCommand: not enough arguments");
        }

        listener.setListening(true);
        communication.getHandler().write("get playerlist");
    }

    @Override
    public void addListener(CommandListener listener) {
        this.listener.addListener(listener);
    }

    public CommunicationListener getListener() {
        return listener;
    }

}
