package org.kratos.framework.game;

/**
 * Created by FakeYou on 4/6/14.
 */
public class Challenge {

    private String challenger;
    private String game;
    private int number;
    private boolean accepted;

    public Challenge(String challenger, String game, int number) {
        this.challenger = challenger;
        this.game = game;
        this.number = number;

        this.accepted = false;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
