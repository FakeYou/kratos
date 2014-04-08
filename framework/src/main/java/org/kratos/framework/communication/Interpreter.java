package org.kratos.framework.communication;

import org.kratos.framework.game.Challenge;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Interpreter {
    private Communication communication;

    public Interpreter(Communication communication) {
        this.communication = communication;
    }

    public void connect(String host, int port, CommandListener listener) {
        communication.getCommunicationListener("connect").addListener(listener);
        communication.connect(host, port);
    }

    public void disconnect() {
        communication.disconnect();
    }

    public void login(String username, CommandListener listener) {
        communication.command("login", listener, username);
    }

    public void logout(CommandListener listener) {
        communication.command("logout", listener);
    }

    public void challenge(String challengee, String game, CommandListener listener) {
        communication.command("challenge", listener, challengee, game);
    }

    public void getPlayerlist(CommandListener listener) {
        communication.command("getPlayerlist", listener);
    }

    public void getGamelist(CommandListener listener) {
        communication.command("getGamelist", listener);
    }

    public void subscribe(String game, CommandListener listener) {
        communication.command("subscribe", listener, game);
    }

    public void get(String argument, CommandListener listener) {
        communication.command("get", listener, argument);
    }

    public String[] parsePlayerlist(String playerlist) {
        String beginPattern = "^(SVR PLAYERLIST \\[)";
        String endPattern = "\\]$";
        String delimiterPattern = ", ";

        playerlist = playerlist.replaceAll(beginPattern, "").replaceAll(endPattern, "");

        String[] list = playerlist.split(delimiterPattern);

        for(int i = 0; i < list.length; i++) {
            list[i] = list[i].replaceAll("^\"", "").replaceAll("\"$", "");
        }

        return list;
    }

    public String[] parseGamelist(String gamelist) {
        String beginPattern = "^(SVR GAMELIST \\[)";
        String endPattern = "\\]$";
        String delimiterPattern = ", ";

        gamelist = gamelist.replaceAll(beginPattern, "").replaceAll(endPattern, "");

        String[] list = gamelist.split(delimiterPattern);

        for(int i = 0; i < list.length; i++) {
            list[i] = list[i].replaceAll("^\"", "").replaceAll("\"$", "");
        }

        return list;
    }

    public Challenge parseChallengeRequest(String request) {
        String username = request;
        String usernameBeginPattern = "^(SVR GAME CHALLENGE \\{CHALLENGER: \")";
        String usernameEndPattern = "(\", GAMETYPE: \")[a-zA-Z ]+(\", CHALLENGENUMBER: \")[0-9]+(\"\\})$";

        String gametype;
        String gametypeBeginPattern = "^(\\{GAMETYPE: \")";
        String gametypeEndPattern = "(\", CHALLENGENUMBER: \")[0-9]+(\"\\})$";

        String challengenumber;
        String challengenumberBeginPattern = "^(\\{GAMETYPE: \")[a-zA-Z ]+(\", CHALLENGENUMBER: \")";
        String challengenumberEndPattern = "(\"\\})$";

        username = username.replaceAll(usernameBeginPattern, "").replaceAll(usernameEndPattern, "");

        request = request.replaceAll(usernameBeginPattern, "").replaceFirst(username, "").replaceFirst("\", ", "");
        request = "{" + request;

        gametype = request.replaceAll(gametypeBeginPattern, "").replaceAll(gametypeEndPattern, "");
        challengenumber = request.replaceAll(challengenumberBeginPattern, "").replaceAll(challengenumberEndPattern, "");

        System.out.println(username + ", " + gametype + ", " + challengenumber);

        return new Challenge(username, gametype, Integer.parseInt(challengenumber));
    }
}
