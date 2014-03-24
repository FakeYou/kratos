package network.telnet;

import org.apache.commons.net.telnet.TelnetClient;
import org.omg.CosNaming._NamingContextExtStub;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by FakeYou on 3/22/14.
 */
public class TelnetHandler {
    private String host;
    private int port;
    private boolean ready;

    private TelnetClient client;
    private TelnetReader reader;
    private TelnetWriter writer;

    private Thread readerThread;
    private Thread writerThread;

    public TelnetHandler(String host, int port) {
        ready = false;

        this.host = host;
        this.port = port;

        client = new TelnetClient();
        reader = new TelnetReader(this, client);
        writer = new TelnetWriter(this, client);
    }

    public void connect() {
        try {
            client.connect(host, port);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        readerThread = new Thread(reader);
        writerThread = new Thread(writer);

        readerThread.start();
        writerThread.start();
    }

    public void disconnect() {
        try {
            client.disconnect();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        readerThread.stop();
        writerThread.stop();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public TelnetClient getClient() {
        return client;
    }

    public void setClient(TelnetClient client) {
        this.client = client;
    }

    public TelnetReader getReader() {
        return reader;
    }

    public TelnetWriter getWriter() {
        return writer;
    }
}
