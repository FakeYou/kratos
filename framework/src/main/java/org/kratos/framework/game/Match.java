package org.kratos.framework.game;

import nl.hanze.t23i.gamemodule.extern.AbstractGameModule;
import nl.hanze.t23i.gamemodule.game.GuessGame;
import org.kratos.framework.Kratos;

/**
 * Created by FakeYou on 4/8/14.
 */
public class Match {
    private AbstractGameModule gameModule;

    public Match(Kratos kratos, String gameType, Player player, Player opponent) {
        if(gameType == "Guess Game") {
            gameModule = new GuessGame(player.getUsername(), opponent.getUsername());
        }
    }

    public void start() {
        gameModule.start();
    }
}
