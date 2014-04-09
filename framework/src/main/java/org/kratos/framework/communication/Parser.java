package org.kratos.framework.communication;

import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Move;

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

        return new Challenge(username, gametype, Integer.parseInt(challengenumber));
    }

    public Move parseMove(String message) {
        // SVR GAME MOVE {PLAYER: "test", DETAILS: "Hoger", MOVE: "3"}

        String username = message;
        String usernameBeginPattern = "^(SVR GAME MOVE \\{PLAYER: \")";
        String usernameEndPattern = "(\", DETAILS: \").+(\", MOVE: \").+(\"\\})";

        String details;
        String detailsBeginPattern = "^(\\{DETAILS: \")";
        String detailsEndPattern = "(\", MOVE: \").+(\"\\})";

        String move;
        String moveBeginPattern = "^(\\{DETAILS: \").+(\", MOVE: \")";
        String moveEndPattern = "(\"\\})$";

        username = username.replaceAll(usernameBeginPattern, "").replaceAll(usernameEndPattern, "");

        message = message.replaceAll(usernameBeginPattern, "").replaceFirst(username, "").replaceFirst("\", ", "");
        message = "{" + message;

        details = message.replaceAll(detailsBeginPattern, "").replaceAll(detailsEndPattern, "");
        move = message.replaceAll(moveBeginPattern, "").replaceAll(moveEndPattern, "");

        return new Move(username, details, move);
    }
}
