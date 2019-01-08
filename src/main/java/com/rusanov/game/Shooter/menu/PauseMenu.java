package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.Game;
import com.rusanov.game.Shooter.game.Main;
import com.rusanov.game.Shooter.menu.objects.Background;
import com.rusanov.game.Shooter.menu.objects.MenuButton;
import com.rusanov.game.Shooter.menu.objects.MenuObject;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PauseMenu implements Serializable {
    private Game game;
    private List<MenuObject> pauseObjects = new ArrayList<>();

    public PauseMenu(Game game) {
        this.game = game;
        createPauseObjects();
    }

    private void createPauseObjects() {
        pauseObjects.add(new Background(0, 0, MenuSizes.SCREEN_WIDTH, MenuSizes.SCREEN_HEIGHT));
        int buttonStartY = MenuSizes.SCREEN_HEIGHT / 2 - PauseItem.values().length *
                MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y / 2;
        for (int i = 0; i < PauseItem.values().length; i++) {
            MenuButton button = new MenuButton(PauseItem.values()[i], true, PauseItem.values()[i].toString(),
                    buttonStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            pauseObjects.add(button);
        }
    }

    public void render() {
        for (MenuObject menuObject : pauseObjects) {
            menuObject.render();
        }
    }

    public void update() {
        if (Input.isKeyClicked(Keyboard.KEY_ESCAPE)) {
            game.setGameState(Game.GameState.PLAY);
        }
        for (MenuObject menuObject : pauseObjects) {
            menuObject.update();
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton) menuObject;
                if (button.isPressed()) {
                    button.setPressed(false);
                    PauseItem item = (PauseItem) button.getId();
                    switch (item) {
                        case CONTINUE:
                            game.setGameState(Game.GameState.PLAY);
                            break;
                        case SAVE:
                            saveGame();
                            break;
                        case LOAD:
                            Main.loadGame();
                            break;
                        case CANCEL:
                            game.setGameState(Game.GameState.MENU);
                            game.resetGameProgress();
                            break;
                        default:
                            System.err.println("Error: Unknown pause menu state \"" + item + "\"!");
                    }
                }
            }
        }
    }

    private void saveGame() {
        File saveFolder = new File(MenuConstants.NAME_OF_SAVE_DIRECTORY);
        if (!saveFolder.exists()) {
            if (saveFolder.mkdirs()) {
                System.out.println("Папка: " + saveFolder + " создана!");
            }
        }
        String saveFileFullName = new File(MenuConstants.NAME_OF_SAVE_DIRECTORY +
                MenuConstants.NAME_OF_SAVE).getAbsolutePath();
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(saveFileFullName))) {
            game.getMenuConstructor().setButtonLoadActive(true);
            save.writeObject(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<MenuObject> getPauseObjects() {
        return pauseObjects;
    }
}
