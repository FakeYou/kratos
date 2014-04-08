package org.kratos.framework.communication.command;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationCommand;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.listener.AbstractListener;

/**
 * Created by FakeYou on 4/8/14.
 */
public abstract class AbstractCommand implements CommunicationCommand {

    protected Communication communication;
    protected AbstractListener listener;

    public AbstractCommand(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void addListener(CommandListener listener) {
        this.listener.addListener(listener);
    }

    public CommunicationListener getListener() {
        return listener;
    }
}
