package org.kratos.framework.communication.telnet;

import org.kratos.framework.communication.CommunicationListener;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class TelnetReader implements Runnable {
    private String response;

    private TelnetClient client;
    private TelnetHandler handler;

    private ArrayList<CommunicationListener> listeners;

    public TelnetReader(TelnetHandler handler, TelnetClient client) {
        this.handler = handler;
        this.client = client;

        listeners = new ArrayList<CommunicationListener>();
        response = new String();
    }

    @Override
    public void run() {
        InputStream input = client.getInputStream();

        byte[] buffer = new byte[1024 * 16];
        int reader = 0;

        while(true) {
            try {
                reader = input.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(reader > 0) {
                String chunk = new String(buffer, 0, reader);
                response += chunk;

                String[] responses = response.split("\r\n");

                for(int i = 0; i < responses.length; i++) {
                    String response = responses[i].replace("\r\n", "");
                    System.out.println("[TelnetReader/read] " + response);

                    for(CommunicationListener listener : listeners) {
                        if(!listener.isListening()) {
                            continue;
                        }

                        CommunicationListener.resolved status = listener.trigger(response);

                        if(status == CommunicationListener.resolved.COMPLETE) {
                            this.response = "";
                            handler.setBusy(false);
                            break;
                        }
                        else if(status == CommunicationListener.resolved.PARTIAL) {
                            this.response = "";
                            break;
                        }
                    }
                }
            }

            try {
                Thread.sleep(50);
            }
            catch(InterruptedException e) { }
        }
    }

    public void addListener(CommunicationListener listener) {
        listeners.add(listener);
    }
}
