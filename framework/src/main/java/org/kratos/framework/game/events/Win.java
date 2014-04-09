package org.kratos.framework.game.events;

/**
 * Created by FakeYou on 4/9/14.
 */
public class Win {

    private String comment;
    private int playerOneScore;
    private int playerTwoScore;

    public Win(String comment, int playerOneScore, int playerTwoScore) {
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
