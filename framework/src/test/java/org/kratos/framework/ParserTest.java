package org.kratos.framework;

import junit.framework.TestCase;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.events.Challenge;
import org.kratos.framework.game.events.Match;
import org.kratos.framework.game.events.Move;
import org.kratos.framework.game.events.Win;

/**
 * Created by FakeYou on 4/13/14.
 */
public class ParserTest extends TestCase {
    private Parser parser;

    public void setUp() {
        parser = new Parser();
    }

    public void testParsePlayerlist() {
        String playerlistMessage = "SVR PLAYERLIST [\"test\", \"hoi\"]";

        String[] playerlist = parser.parsePlayerlist(playerlistMessage);

        assertEquals(2, playerlist.length);

        assertEquals("test", playerlist[0]);
        assertEquals("hoi", playerlist[1]);
    }

    public void testParseGamelist() {
        String gamelistMessage = "SVR GAMELIST [\"Ultra Guess Game\", \"Guess Game Deluxe\", \"Guess Game\"]";

        String[] gamelist = parser.parseGamelist(gamelistMessage);

        assertEquals(3, gamelist.length);

        assertEquals("Ultra Guess Game", gamelist[0]);
        assertEquals("Guess Game Deluxe", gamelist[1]);
        assertEquals("Guess Game", gamelist[2]);
    }

    public void testParseChallenge() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        assertEquals("test", challenge.getChallenger());
        assertEquals("Ultra Guess Game", challenge.getGame());
        assertEquals(0, challenge.getNumber());
    }

    public void testParseChallengeEvil() {
        String challengeMessage = "SVR GAME CHALLENGE {CHALLENGER: \"test\", GAMETYPE: \"Other Game\", GAMETYPE: \"Ultra Guess Game\", CHALLENGENUMBER: \"0\"}";

        Challenge challenge = parser.parseChallengeRequest(challengeMessage);

        assertEquals("test\", GAMETYPE: \"Other Game", challenge.getChallenger());
        assertEquals("Ultra Guess Game", challenge.getGame());
        assertEquals(0, challenge.getNumber());
    }

    public void testParseMove() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        assertEquals("test", move.getPlayer());
        assertEquals("Hoger", move.getDetails(), "Hoger");
        assertEquals("3", move.getMove());
    }

    public void testParseMoveEvil() {
        String moveMessage = "SVR GAME MOVE {PLAYER: \"test\", MOVE: \"4\", DETAILS: \"Hoger\", MOVE: \"3\"}";

        Move move = parser.parseMove(moveMessage);

        assertEquals("test\", MOVE: \"4", move.getPlayer());
        assertEquals("Hoger", move.getDetails());
        assertEquals("3", move.getMove());
    }

    public void testParseMatch() {
        String matchMessage = "SVR GAME MATCH {GAMETYPE: \"Ultra Guess Game\", PLAYERTOMOVE: \"hoi\", OPPONENT: \"hoi\"}";

        Match match = parser.parseMatch(matchMessage);

        assertEquals("Ultra Guess Game", match.getGametype());
        assertEquals("hoi", match.getPlayerToMove());
        assertEquals("hoi", match.getOpponent());
    }

    public void testParseMatchPlayerStart() {
        String matchMessage = "SVR GAME MATCH {GAMETYPE: \"Ultra Guess Game\", PLAYERTOMOVE: \"ikke\", OPPONENT: \"hoi\"}";

        Match match = parser.parseMatch(matchMessage);

        assertEquals("Ultra Guess Game", match.getGametype());
        assertEquals("ikke", match.getPlayerToMove());
        assertEquals("hoi", match.getOpponent());
    }

    public void testParseWin() {
        String winMessage = "SVR GAME WIN {COMMENT: \"Turn timelimit reached\", PLAYERONESCORE: \"2\", PLAYERTWOSCORE: \"13\"}";

        Win win = parser.parseWin(winMessage);

        assertEquals("Turn timelimit reached", win.getComment());
        assertEquals(2, win.getPlayerOneScore());
        assertEquals(13, win.getPlayerTwoScore());
    }
}
