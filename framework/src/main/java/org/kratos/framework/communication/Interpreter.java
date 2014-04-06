package org.kratos.framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Interpreter {
    private Communication communication;

    public Interpreter(Communication communication) {
        this.communication = communication;
    }

    public void connect(String host, int port, CommandListener listener) {
        communication.getCommunicaionListener("connect").addListener(listener);
        communication.connect(host, port);
    }

    public void disconnect() {
        communication.disconnect();
    }

    public void login(String username, CommandListener listener) {
        communication.command("login", listener, username);
    }

    public void get(String argument, CommandListener listener) {
        communication.command("get", listener, argument);
    }
}
