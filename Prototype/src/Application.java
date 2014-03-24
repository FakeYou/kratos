import network.Interpreter;
import network.telnet.TelnetHandler;
import org.apache.commons.net.telnet.TelnetClient;

import java.util.UUID;

/**
 * Created by FakeYou on 3/22/14.
 */
public class Application {
    private Interpreter interpreter;

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        String host = "localhost";
        int port = 7789;

        String username = UUID.randomUUID().toString();

        try {
            interpreter = new Interpreter(Interpreter.Protocol.TELNET, host, port);
            interpreter.login(username);
            interpreter.getPlayerList();
            interpreter.getPlayerList();
            interpreter.getPlayerList();
            interpreter.getPlayerList();
            interpreter.getPlayerList();
            interpreter.getPlayerList();
            interpreter.connect();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        while(true) {

        }
    }
}
