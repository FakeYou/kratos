package framework.communication.command;

import framework.communication.CommandListener;
import framework.communication.Communication;
import framework.communication.CommunicationCommand;
import framework.communication.CommunicationListener;
import framework.communication.listener.GetListener;

/**
 * Created by FakeYou on 3/29/14.
 */
public class GetCommand implements CommunicationCommand {

    private Communication communication;
    private GetListener listener;

    public GetCommand(Communication communication) {
        this.communication = communication;
        listener = new GetListener(this);
    }

    @Override
    public void execute(String... arguments) throws Exception {
        if(arguments.length != 1) {
            throw new Exception("GetCommand: not enough arguments");
        }

        if(!arguments[0].equals("playerlist") && !arguments[0].equals("gamelist")) {
            throw new Exception("GetCommand: invalid argument");
        }

        listener.setListening(true);
        communication.getHandler().write("get " + arguments[0]);
    }

    @Override
    public void addListener(CommandListener listener) {
        this.listener.addListener(listener);
    }

    public CommunicationListener getListener() {
        return listener;
    }

}
