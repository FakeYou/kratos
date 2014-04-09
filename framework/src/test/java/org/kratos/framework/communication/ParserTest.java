package org.kratos.framework.communication;

import junit.framework.TestCase;
import org.junit.Test;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Move;

/**
 * Created by FakeYou on 4/9/14.
 */
public class ParserTest extends TestCase {
    private Parser parser;

    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void testParsePlayerlist() {
        String playerlistMessage = "SVR PLAYERLIST [\"test\", \"hoi\"]";

        String[] playerlist = parser.parsePlayerlist(playerlistMessage);

        assertEquals(playerlist.length, 2);

        assertEquals(playerlist[0], "test");
        assertEquals(playerlist[1], "hoi");
    }

    @Test
    public void testParseGamelist() {
        String gamelistMessage = "SVR GAMELIST [\"Ultra Guess Game\", \"Guess Game Deluxe\", \"Guess Game\"]";

        String[] gamelist = parser.parseGamelist(gamelistMessage);

        assertEquals(gamelist.length, 3);

        assertEquals(gamelist[0], "Ultra Guess Game");
        assertEquals(gamelist[1], "Guess Game Deluxe");
        assertEquals(gamelist[2], "Guess Game");
    }

    @Test
    public void testParseChallenge() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        assertEquals(challenge.getChallenger(), "test");
        assertEquals(challenge.getGame(), "Ultra Guess Game");
        assertEquals(challenge.getNumber(), 0);
    }

    @Test
    public void testParseChallengeEvil() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Other Game\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        assertEquals(challenge.getChallenger(), "test\", GAMETYPE: \"Other Game");
        assertEquals(challenge.getGame(), "Ultra Guess Game");
        assertEquals(challenge.getNumber(), 0);
    }

    @Test
    public void testParseMove() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        assertEquals(move.getPlayer(), "test");
        assertEquals(move.getDetails(), "Hoger");
        assertEquals(move.getMove(), "3");
    }

    @Test
    public void testParseMoveEvil() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", MOVE: \"4\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        assertEquals(move.getPlayer(), "test\", MOVE: \"4");
        assertEquals(move.getDetails(), "Hoger");
        assertEquals(move.getMove(), "3");
    }
}
