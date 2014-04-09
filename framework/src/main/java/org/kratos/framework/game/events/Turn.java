package org.kratos.framework.game.events;

/**
 * Created by FakeYou on 4/9/14.
 */
public class Turn {

    private String message;

    public Turn(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
