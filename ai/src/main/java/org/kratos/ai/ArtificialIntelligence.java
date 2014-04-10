package org.kratos.ai;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import org.kratos.ai.utils.Arguments;
import org.kratos.framework.Kratos;
import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.events.Match;

/**
 * Created by FakeYou on 4/10/14.
 */
public class ArtificialIntelligence {
    private Arguments arguments;
    private Kratos kratos;
    private Communication communication;
    private Interpreter interpreter;
    private Parser parser;

    private Boolean standAlone;

    public ArtificialIntelligence(Kratos kratos, Boolean standAlone) {
        this.kratos = kratos;
        this.standAlone = standAlone;

    }

    public void start() {
        kratos.setUsername(getUsername());

        if(standAlone) {
            communication = kratos.getCommunication();
            interpreter = kratos.getInterpreter();
            parser = kratos.getParser();

            interpreter.connect(getHost(), getPort(), new CommandListener() {
                @Override
                public void trigger(Communication.status status, String response) {
                    if(status == Communication.status.OK) {
                        interpreter.login(getUsername(), null);
                    }
                }
            });

            kratos.getCommunication().getCommunicationListener("game").addListener(new CommandListener() {
                @Override
                public void trigger(Communication.status status, String response) {
                    if(status == Communication.status.GAME_MATCH) {
                        Match match = parser.parseMatch(response);

                        System.out.println("[ArtificialIntelligence/start] starting AI for game: " + match.getGametype());

                        if(match.getGametype().equals("Tic Tac Toe")) {
                            new TicTacToeAI(kratos, match);
                        }
                    }
                }
            });
        }
        else {

        }
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    public int getPort() {
        Integer port = arguments.getPort();

        if(port != null) {
            return port;
        }
        else {
            return kratos.getSetting("port").getAsInt();
        }
    }

    public String getHost() {
        String host = arguments.getHost();

        if(host != null) {
            return host;
        }
        else {
            return kratos.getSetting("host").getAsString();
        }
    }

    public String getUsername() {
        String username = arguments.getUsername();

        if(username != null) {
            return username;
        }

        return "Bot";
    }

    public static void main(String[] args) {
        ArtificialIntelligence artificialIntelligence = new ArtificialIntelligence(new Kratos(), true);

        try {
            Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.LONG_OR_COMPACT);
            artificialIntelligence.setArguments(arguments);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        artificialIntelligence.start();
    }
}
