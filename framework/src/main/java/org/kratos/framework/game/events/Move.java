package org.kratos.framework.game.events;

/**
 * Created by FakeYou on 4/9/14.
 */
public class Move {

    private String player;
    private String details;
    private String move;

    public Move(String player, String details, String move) {
        this.player = player;
        this.details = details;
        this.move = move;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
