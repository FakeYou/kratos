package network.telnet;

import network.NetworkListener;
import org.apache.commons.net.telnet.TelnetClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by FakeYou on 3/22/14.
 */
public class TelnetReader implements Runnable {
    private TelnetHandler handler;
    private TelnetClient client;
    private String response;
    private Boolean listen;

    private ArrayList<NetworkListener> listeners;

    public TelnetReader(TelnetHandler handler, TelnetClient client) {
        this.handler = handler;
        this.client = client;

        response = new String();
        listen = true;
        listeners = new ArrayList<NetworkListener>();
    }

    public synchronized void run() {
        InputStream input = client.getInputStream();

        byte[] buffer = new byte[1024];
        int ret_read;

        try {
            while(listen) {
                ret_read = input.read(buffer);

                if(ret_read > 0) {
                    String chunk = new String(buffer, 0, ret_read);
//                    System.out.print(chunk);

                    response += chunk;

                    if(handler.isReady()) {
                        String lines[] = response.split("\\r?\\n");

                        for(int i = 0; i < lines.length; i++) {

                            ArrayList<NetworkListener> clonedListeners = (ArrayList<NetworkListener>) listeners.clone();
                            for(NetworkListener listener : clonedListeners) {
                                boolean success = listener.message(lines[i]);

                                if(success) {
                                    response = "";
                                    break;
                                }
                            }

                            response.replace(lines[i], "");
                        }
                    }
                }

                if(response.startsWith(
                    "Strategic Game Server [Version 1.0]\r\n" +
                    "(C) Copyright 2009 Hanze Hogeschool Groningen\r\n"
                )) {
                    System.out.println("handler is ready");
                    handler.setReady(true);
                    response = "";
                }
            }
        }
        catch (Exception e) {
            System.err.println("Exception while reading socket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void addListener(NetworkListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(NetworkListener listener) {
        listeners.remove(listener);
    }
}
