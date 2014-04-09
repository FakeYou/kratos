package org.kratos.framework.communication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Move;

/**
 * Created by FakeYou on 4/9/14.
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

        Assert.assertEquals(playerlist.length, 2);

        Assert.assertEquals(playerlist[0], "test");
        Assert.assertEquals(playerlist[1], "hoi");
    }

    @Test
    public void testParseGamelist() {
        String gamelistMessage = "SVR GAMELIST [\"Ultra Guess Game\", \"Guess Game Deluxe\", \"Guess Game\"]";

        String[] gamelist = parser.parseGamelist(gamelistMessage);

        Assert.assertEquals(gamelist.length, 3);

        Assert.assertEquals(gamelist[0], "Ultra Guess Game");
        Assert.assertEquals(gamelist[1], "Guess Game Deluxe");
        Assert.assertEquals(gamelist[2], "Guess Game");
    }

    @Test
    public void testParseChallenge() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        Assert.assertEquals(challenge.getChallenger(), "test");
        Assert.assertEquals(challenge.getGame(), "Ultra Guess Game");
        Assert.assertEquals(challenge.getNumber(), 0);
    }

    @Test
    public void testParseChallengeEvil() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Other Game\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        Assert.assertEquals(challenge.getChallenger(), "test\", GAMETYPE: \"Other Game");
        Assert.assertEquals(challenge.getGame(), "Ultra Guess Game");
        Assert.assertEquals(challenge.getNumber(), 0);
    }

    @Test
    public void testParseMove() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        Assert.assertEquals(move.getPlayer(), "test");
        Assert.assertEquals(move.getDetails(), "Hoger");
        Assert.assertEquals(move.getMove(), "3");
    }

    @Test
    public void testParseMoveEvil() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", MOVE: \"4\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        Assert.assertEquals(move.getPlayer(), "test\", MOVE: \"4");
        Assert.assertEquals(move.getDetails(), "Hoger");
        Assert.assertEquals(move.getMove(), "3");
    }
}
