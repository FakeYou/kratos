package network;

import network.telnet.*;

/**
 * Created by FakeYou on 3/22/14.
 */
public class Interpreter {
    public enum Protocol {
        TELNET
    };

    private TelnetHandler protocolHandler;
    private Protocol protocol;
    private String host;
    private int port;

    public Interpreter(Protocol protocol, String host, int port) throws Exception {
        this.protocol = protocol;
        this.host = host;
        this.port = port;

        System.out.println("host: " + host + ", port: " + port);

        if(protocol == Protocol.TELNET) {
            protocolHandler = new TelnetHandler(host, port);
        }
        else {
            throw new Exception("Protocol not supported");
        }
    }

    public void connect() {
        protocolHandler.connect();
    }

    public void disconnect() {
        protocolHandler.disconnect();
    }

    public synchronized void login(String username) {
        NetworkListener listener = new NetworkListener() {
            public boolean message(String message) {
                String OkPattern = "^(OK)$";
                String ErrPattern = "^(ERR).+";
                String ErrDuplicatePattern = "^(ERR Duplicate name exists)$";

                System.out.println("message: " + message);

                if(message.matches(OkPattern)) {
                    System.out.println("Login was good");
                    protocolHandler.getReader().removeListener(this);
                    return true;
                }
                else if(message.matches(ErrDuplicatePattern)) {
                    System.out.println("Name is already in use");
                    protocolHandler.getReader().removeListener(this);
                    return true;
                }
                else if(message.matches(ErrPattern)) {
                    System.out.println("Not good");
                    protocolHandler.getReader().removeListener(this);
                    return true;
                }

                protocolHandler.getReader().removeListener(this);
                return false;
            }
        };

        protocolHandler.getReader().addListener(listener);
        protocolHandler.getWriter().send("login " + username);
    }

    public synchronized void getPlayerList() {
        NetworkListener listener = new NetworkListener() {
            public boolean message(String message) {
                String OkPattern = "^(OK)$";
                String PlayerlistPattern = "^SVR PLAYERLIST \\[.*\\]$";

                if(message.matches(OkPattern)) {
                    System.out.println("get playerlist was good");
                    return true;
                }
                else if(message.matches(PlayerlistPattern)) {
                    System.out.println(message);
                    protocolHandler.getReader().removeListener(this);
                    return true;
                }

                return false;
            }
        };

        protocolHandler.getReader().addListener(listener);
        protocolHandler.getWriter().send("get playerlist");
    }
}
