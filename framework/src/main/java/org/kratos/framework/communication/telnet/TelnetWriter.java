package org.kratos.framework.communication.telnet;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.OutputStream;

/**
 * Created by FakeYou on 3/29/14.
 */
public class TelnetWriter {

    private TelnetHandler handler;
    private TelnetClient client;

    public TelnetWriter(TelnetHandler handler, TelnetClient client) {
        this.handler = handler;
        this.client = client;
    }

    public void write(String message) throws Exception {
        if(handler.isReady()) {
            System.out.println("[TelnetWriter/write] Sending message: \"" + message + "\"");

            OutputStream output = client.getOutputStream();
            message = message + "\r\n";

            output.write(message.getBytes());
            output.flush();
        }
        else {
            throw new Exception("Handler not ready");
        }
    }
}
