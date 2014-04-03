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
                chunk = chunk.replace("\n", "").replace("\r", "");
                response += chunk;

//                System.out.print(chunk);

                if(handler.isReady()) {
                    for(CommunicationListener listener : listeners) {
                        if(!listener.isListening()) {
                            continue;
                        }

                        boolean success = listener.trigger(response);

                        if(success) {
                            response = "";
                            break;
                        }
                    }
                }
            }

            if(response.startsWith(
                "Strategic Game Server [Version 1.0]" +
                "(C) Copyright 2009 Hanze Hogeschool Groningen"
            )) {
                handler.setReady(true);
                response = "";
            }
        }
    }

    public void addListener(CommunicationListener listener) {
        listeners.add(listener);
    }
}
