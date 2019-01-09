package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.menu.MenuConstants;
import com.rusanov.game.Shooter.menu.MenuSizes;

import java.io.*;

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
        String saveFileFullName = new File(MenuConstants.NAME_OF_SAVE_DIRECTORY +
                MenuConstants.NAME_OF_SAVE).getAbsolutePath();
        try (ObjectInputStream save = new ObjectInputStream(new FileInputStream(saveFileFullName))) {
            game = (Game)save.readObject();
            game.loadTextures();
            game.setTextures();
        } catch (Exception e) {
            e.printStackTrace();
            game.getMenuConstructor().setButtonLoadActive(false);
            File file = new File(saveFileFullName);
            System.out.println("Файл " + saveFileFullName + (file.delete() ? " удален" : " не обнаружен"));
        }
    }
}
