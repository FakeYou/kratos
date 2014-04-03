package org.kratos.framework.communication.telnet;

import org.kratos.framework.communication.CommunicationHandler;
import org.kratos.framework.communication.CommunicationListener;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class TelnetHandler implements CommunicationHandler {
    private boolean ready;

    private TelnetClient client;
    private TelnetReader reader;
    private TelnetWriter writer;

    private Thread readerThread;

    private ArrayList<CommunicationListener> listeners;

    public TelnetHandler() {
        ready = false;

        client = new TelnetClient();
        reader = new TelnetReader(this, client);
        writer = new TelnetWriter(this, client);
    }

    @Override
    public void connect(String host, int port) {
        ready = false;

        try {
            client.connect(host, port);
        }
        catch (SocketException e) {
            System.err.println("[TelnetHandler/connect] " + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("[TelnetHandler/connect] " + e.getMessage());
            e.printStackTrace();
        }

        readerThread = new Thread(reader);
        readerThread.start();
    }

    @Override
    public void disconnect() {
        ready = false;

        try {
            client.disconnect();
        }
        catch (IOException e) {
            System.err.println("[TelnetHandler/connect] " + e.getMessage());
            e.printStackTrace();
        }

        readerThread.stop();
    }

    @Override
    public Boolean isReady() {
        return ready;
    }

    @Override
    public void setReady(Boolean ready) {
        System.out.println("[TelnetHandler/setReady] " + ready);
        this.ready = ready;
    }

    public void addListener(CommunicationListener listener) {
        reader.addListener(listener);
    }

    public void write(String message) {
        try {
            writer.write(message);
        }
        catch (Exception e) {
            System.err.println("[TelnetHandler/write] " + e.getMessage());
            e.printStackTrace();
        }
    }
}
