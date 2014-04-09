package org.kratos.framework.communication.telnet;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.OutputStream;
import java.util.Stack;

/**
 * Created by FakeYou on 3/29/14.
 */
public class TelnetWriter implements Runnable {

    private TelnetHandler handler;
    private TelnetClient client;

    private Stack<String> writeBuffer;

    public TelnetWriter(TelnetHandler handler, TelnetClient client) {
        this.handler = handler;
        this.client = client;

        writeBuffer = new Stack<String>();
    }

    @Override
    public void run() {
        while(true) {
            // nothing

            try {
                Thread.sleep(50);
            }
            catch(InterruptedException e) { }
        }
    }

    public void write(String message) throws Exception {
        if(handler.isConnected()) {
            System.out.println("[TelnetWriter/write] Sending message: \"" + message + "\"");

            OutputStream output = client.getOutputStream();
            message = message + "\r\n";

            output.write(message.getBytes());
            output.flush();
        }
    }
}
