package org.kratos.framework.communication;

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

    public void getPlayerlist(CommandListener listener) {
        communication.command("getPlayerlist", listener);
    }

    public void getGamelist(CommandListener listener) {
        communication.command("getGamelist", listener);
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
}
