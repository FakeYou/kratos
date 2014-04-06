package org.kratos.framework.communication;

/**
 * Created by FakeYou on 4/6/14.
 */
public class CommandExecuter {
    private String command;
    private CommandListener listener;
    private String[] arguments;

    public CommandExecuter(String command, CommandListener listener, String ... arguments) {
        this.command = command;
        this.listener = listener;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public CommandListener getListener() {
        return listener;
    }

    public String[] getArguments() {
        return arguments;
    }
}
