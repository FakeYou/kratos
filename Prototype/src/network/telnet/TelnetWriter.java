package network.telnet;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by FakeYou on 3/22/14.
 */
public class TelnetWriter implements Runnable {
    private TelnetHandler handler;
    private TelnetClient client;
    private ArrayList<String> toSend;

    public TelnetWriter(TelnetHandler handler, TelnetClient client) {
        this.handler = handler;
        this.client = client;

        toSend = new ArrayList<String>();
    }

    public void send(String message) {
        toSend.add(message);
    }

    public void run() {
        OutputStream output = client.getOutputStream();

        while(true) {
            if(handler.isReady() && !toSend.isEmpty()) {
                try {
                    String message = toSend.remove(0) + "\r\n";

                    System.out.println("Sending: " + message.replace("\r\n", ""));

                    output.write(message.getBytes());
                    output.flush();
                }
                catch (Exception e) {
                    System.err.println("Exception while sending message: " + e.getMessage());
                }
            }
        }
    }
}
