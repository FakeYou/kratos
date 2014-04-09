package org.kratos.framework.game.events;

/**
 * Created by FakeYou on 4/9/14.
 */
public class Match {

    private String gametype;
    private String playerToMove;
    private String opponent;

    public Match(String gametype, String playerToMove, String opponent) {
        this.gametype = gametype;
        this.playerToMove = playerToMove;
        this.opponent = opponent;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getPlayerToMove() {
        return playerToMove;
    }

    public void setPlayerToMove(String playerToMove) {
        this.playerToMove = playerToMove;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
