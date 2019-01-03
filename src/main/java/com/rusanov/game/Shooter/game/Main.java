package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.menu.MenuSizes;

public class Main {
    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {
        boolean isGameReset;
        do {
            new MenuSizes();
            Game game = new Game();
            game.setupSystem();
            game.initialize();
            while (game.loop());
            game.shutdown();

            isGameReset = game.isReload();
        } while (isGameReset);
    }
}
