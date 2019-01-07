package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.menu.MenuConstants;
import com.rusanov.game.Shooter.menu.MenuSizes;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main {
    private static Game game;

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {
        boolean isGameReset;
        do {
            new MenuSizes();
            game = new Game();
            game.setupSystem();
            game.initialize();
            while (game.loop());
            game.shutdown();

            isGameReset = game.isReload();
        } while (isGameReset);
    }

    public static void loadGame(){
        try(ObjectInputStream save = new ObjectInputStream(new FileInputStream(MenuConstants.NAME_OF_SAVE))) {
            game = (Game)save.readObject();
            game.loadTextures();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
