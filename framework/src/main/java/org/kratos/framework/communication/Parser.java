package org.kratos.framework.communication;

import org.kratos.framework.game.Challenge;

/**
 * Created by FakeYou on 9-4-14.
 */
public class Parser {

    public Parser() {

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
