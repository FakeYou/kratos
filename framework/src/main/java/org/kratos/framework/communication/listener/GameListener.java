package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;

/**
 * Created by FakeYou on 4/8/14.
 */
public class GameListener extends AbstractListener {

    public GameListener() {
        super();
    }

    @Override
    public resolved trigger(String message) {
        String GameMovePattern = "^(SVR GAME MOVE \\{PLAYER: \").+(\", DETAILS: \").+(\", MOVE: \").+(\"\\})$";
        String GameLossPattern = "^(SVR GAME LOSS \\{COMMENT: \").+(\", PLAYERONESCORE: \").+(\", PLAYERTWOSCORE: \").+(\"\\})$";
        String GameWinPattern = "^(SVR GAME WIN \\{COMMENT: \").+(\", PLAYERONESCORE: \").+(\", PLAYERTWOSCORE: \").+(\"\\})$";
        String GameDrawPattern = "^(SVR GAME DRAW \\{COMMENT: \").+(\", PLAYERONESCORE: \").+(\", PLAYERTWOSCORE: \").+(\"\\})$";
        String GameMatchPattern = "^(SVR GAME MATCH \\{GAMETYPE: \").+(\", PLAYERTOMOVE: \").+(\", OPPONENT: \").+(\"\\})$";
        String GameYourTurnPattern = "^(SVR GAME YOURTURN \\{TURNMESSAGE: \").+(\"\\})$";

        if(message.matches(GameMovePattern)) {
            informListeners(Communication.status.GAME_MOVE, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(GameLossPattern)) {
            informListeners(Communication.status.GAME_LOSS, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(GameWinPattern)) {
            informListeners(Communication.status.GAME_WIN, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(GameDrawPattern)) {
            informListeners(Communication.status.GAME_DRAW, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(GameMatchPattern)) {
            informListeners(Communication.status.GAME_MATCH, message);
            return resolved.COMPLETE;
        }
        else if(message.matches(GameYourTurnPattern)) {
            informListeners(Communication.status.GAME_YOUR_TURN, message);
            return resolved.COMPLETE;
        }

        return resolved.INCOMPLETE;
    }
}
