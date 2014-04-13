package org.kratos.framework;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Match;
import org.kratos.framework.game.events.Move;
import org.kratos.framework.game.events.Win;

/**
 * Created by FakeYou on 4/13/14.
 */
public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void testParsePlayerlist() {
        String playerlistMessage = "SVR PLAYERLIST [\"test\", \"hoi\"]";

        String[] playerlist = parser.parsePlayerlist(playerlistMessage);

        Assert.assertEquals(2, playerlist.length);

        Assert.assertEquals("test", playerlist[0]);
        Assert.assertEquals("hoi", playerlist[1]);
    }

    @Test
    public void testParseGamelist() {
        String gamelistMessage = "SVR GAMELIST [\"Ultra Guess Game\", \"Guess Game Deluxe\", \"Guess Game\"]";

        String[] gamelist = parser.parseGamelist(gamelistMessage);

        Assert.assertEquals(3, gamelist.length);

        Assert.assertEquals("Ultra Guess Game", gamelist[0]);
        Assert.assertEquals("Guess Game Deluxe", gamelist[1]);
        Assert.assertEquals("Guess Game", gamelist[2]);
    }

    @Test
    public void testParseChallenge() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        Assert.assertEquals("test", challenge.getChallenger());
        Assert.assertEquals("Ultra Guess Game", challenge.getGame());
        Assert.assertEquals(0, challenge.getNumber());
    }

    @Test
    public void testParseChallengeEvil() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Other Game\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        Assert.assertEquals("test\", GAMETYPE: \"Other Game", challenge.getChallenger());
        Assert.assertEquals("Ultra Guess Game", challenge.getGame());
        Assert.assertEquals(0, challenge.getNumber());
    }

    @Test
    public void testParseMove() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        Assert.assertEquals("test", move.getPlayer());
        Assert.assertEquals("Hoger", move.getDetails(), "Hoger");
        Assert.assertEquals("3", move.getMove());
    }

    @Test
    public void testParseMoveEvil() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", MOVE: \"4\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        Assert.assertEquals("test\", MOVE: \"4", move.getPlayer());
        Assert.assertEquals("Hoger", move.getDetails());
        Assert.assertEquals("3", move.getMove());
    }

    @Test
    public void testParseMatch() {
        String matchMessage = "SVR GAME MATCH {GAMETYPE: \"Ultra Guess Game\", PLAYERTOMOVE: \"hoi\", OPPONENT: \"hoi\"}";

        Match match = parser.parseMatch(matchMessage);

        Assert.assertEquals("Ultra Guess Game", match.getGametype());
        Assert.assertEquals("hoi", match.getPlayerToMove());
        Assert.assertEquals("hoi", match.getOpponent());
    }

    @Test
    public void testParseMatchPlayerStart() {
        String matchMessage = "SVR GAME MATCH {GAMETYPE: \"Ultra Guess Game\", PLAYERTOMOVE: \"ikke\", OPPONENT: \"hoi\"}";

        Match match = parser.parseMatch(matchMessage);

        Assert.assertEquals("Ultra Guess Game", match.getGametype());
        Assert.assertEquals("ikke", match.getPlayerToMove());
        Assert.assertEquals("hoi", match.getOpponent());
    }

    @Test
    public void testParseWin() {
        String winMessage = "SVR GAME WIN {COMMENT: \"Turn timelimit reached\", PLAYERONESCORE: \"2\", PLAYERTWOSCORE: \"13\"}";

        Win win = parser.parseWin(winMessage);

        Assert.assertEquals("Turn timelimit reached", win.getComment());
        Assert.assertEquals(2, win.getPlayerOneScore());
        Assert.assertEquals(13, win.getPlayerTwoScore());
    }
}
