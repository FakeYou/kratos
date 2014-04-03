package org.kratos.lobby;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;

/**
 * Created by FakeYou on 3-4-14.
 */
public class Lobby {
    public static void main(String[] args) {
        Kratos kratos = new Kratos();

        CommandListener listener = new CommandListener() {
            @Override
            public void trigger(Boolean success, String response) {
                System.out.println(response);
            }
        };

        kratos.getInterpreter().get("playerlist", listener);
    }
}
