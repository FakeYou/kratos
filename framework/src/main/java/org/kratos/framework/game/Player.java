package org.kratos.framework.game;

/**
 * Created by FakeYou on 4/8/14.
 */
public class Player {
    private String username;
    private Boolean human;
    
    public Player(String username, Boolean human) {
        this.username = username;
        this.human = human;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isHuman() {
        return human;
    }

    public void setHuman(Boolean human) {
        this.human = human;
    }
}
