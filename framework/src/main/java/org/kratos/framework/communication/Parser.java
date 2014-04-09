package org.kratos.framework.communication;

import org.apache.commons.lang3.StringUtils;
import org.kratos.framework.game.events.*;

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

    public Match parseMatch(String message) {
        // SVR GAME MATCH {GAMETYPE: "Ultra Guess Game", PLAYERTOMOVE: "hoi", OPPONENT: "hoi"}

        String gametype;
        String gametypeBeginPattern = "^(SVR GAME MATCH \\{GAMETYPE: \")";
        String gametypeEndPattern = "(\", PLAYERTOMOVE: \").+(\", OPPONENT: \").+(\"\\})$";

        String playerToMove;
        String playerToMoveBeginPattern = "^(SVR GAME MATCH \\{GAMETYPE: \").+(\", PLAYERTOMOVE: \")";
        String playerToMoveEndPattern = "(\", OPPONENT: \").+(\"\\})$";

        String opponent;
        String opponentBeginPattern = "^(SVR GAME MATCH \\{GAMETYPE: \").+(\", PLAYERTOMOVE: \").+(\", OPPONENT: \")";
        String opponentEndPattern = "(\"\\})$";


        gametype = message.replaceAll(gametypeBeginPattern, "").replaceAll(gametypeEndPattern, "");
        opponent = message.replaceAll(opponentBeginPattern, "").replaceAll(opponentEndPattern, "");

        if(StringUtils.countMatches(message, opponent) == 2) {
            playerToMove = opponent;
        }
        else {
            playerToMove = message.replaceAll(playerToMoveBeginPattern, "").replaceAll(playerToMoveEndPattern, "");
        }


        return new Match(gametype, playerToMove, opponent);
    }

    public Win parseWin(String message) {
        // SVR GAME WIN {COMMENT: "Turn timelimit reached", PLAYERONESCORE: "0", PLAYERTWOSCORE: "0"}

        String comment;
        String commentBeginPattern = "^(SVR GAME WIN \\{COMMENT: \")";
        String commentEndPattern = "(\", PLAYERONESCORE: \")[0-9]+(\", PLAYERTWOSCORE: \")[0-9]+(\"\\})$";

        String playerOneScore;
        String playerOneScoreBeginPattern = "^(SVR GAME WIN \\{COMMENT: \").+(\", PLAYERONESCORE: \")";
        String playerOneScoreEndPattern = "(\", PLAYERTWOSCORE: \")[0-9]+(\"\\})$";

        String playerTwoScore;
        String playerTwoScoreBeginPattern = "^(SVR GAME WIN \\{COMMENT: \").+(\", PLAYERONESCORE: \")[0-9]+(\", PLAYERTWOSCORE: \")";
        String playerTwoScoreEndPattern = "(\"\\})$";

        comment = message.replaceAll(commentBeginPattern, "").replaceAll(commentEndPattern, "");
        playerOneScore = message.replaceAll(playerOneScoreBeginPattern, "").replaceAll(playerOneScoreEndPattern, "");
        playerTwoScore = message.replaceAll(playerTwoScoreBeginPattern, "").replaceAll(playerTwoScoreEndPattern, "");

        return new Win(comment, Integer.parseInt(playerOneScore), Integer.parseInt(playerTwoScore));
    }

    public Loss parseLoss(String message) {
        // SVR GAME LOSS {COMMENT: "Turn timelimit reached", PLAYERONESCORE: "0", PLAYERTWOSCORE: "0"}

        String comment;
        String commentBeginPattern = "^(SVR GAME LOSS \\{COMMENT: \")";
        String commentEndPattern = "(\", PLAYERONESCORE: \")[0-9]+(\", PLAYERTWOSCORE: \")[0-9]+(\"\\})$";

        String playerOneScore;
        String playerOneScoreBeginPattern = "^(SVR GAME LOSS \\{COMMENT: \").+(\", PLAYERONESCORE: \")";
        String playerOneScoreEndPattern = "(\", PLAYERTWOSCORE: \")[0-9]+(\"\\})$";

        String playerTwoScore;
        String playerTwoScoreBeginPattern = "^(SVR GAME LOSS \\{COMMENT: \").+(\", PLAYERONESCORE: \")[0-9]+(\", PLAYERTWOSCORE: \")";
        String playerTwoScoreEndPattern = "(\"\\})$";

        comment = message.replaceAll(commentBeginPattern, "").replaceAll(commentEndPattern, "");
        playerOneScore = message.replaceAll(playerOneScoreBeginPattern, "").replaceAll(playerOneScoreEndPattern, "");
        playerTwoScore = message.replaceAll(playerTwoScoreBeginPattern, "").replaceAll(playerTwoScoreEndPattern, "");

        return new Loss(comment, Integer.parseInt(playerOneScore), Integer.parseInt(playerTwoScore));
    }
}
