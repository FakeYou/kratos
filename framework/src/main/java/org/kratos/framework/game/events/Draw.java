package org.kratos.framework.game.events;

/**
 * Created by FakeYou on 4/10/14.
 */
public class Draw {
    private String comment;
    private int playerOneScore;
    private int playerTwoScore;

    public Draw(String comment, int playerOneScore, int playerTwoScore) {
        this.comment = comment;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }
}
