package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.Game;
import com.rusanov.game.Shooter.menu.objects.Background;
import com.rusanov.game.Shooter.menu.objects.MenuButton;
import com.rusanov.game.Shooter.menu.objects.MenuObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PauseMenu {
    private Game game;
    private TrueTypeFont timesNewRoman;
    private int textureFont;
    private List<MenuObject> pauseObjects = new ArrayList<>();

    public PauseMenu(Game game) {
        this.game = game;
        Font awtFont = new Font("Times New Roman", Font.BOLD, MenuSizes.MENU_FONT_SIZE);
        textureFont = GL11.glGenTextures() + 1;
        timesNewRoman = new TrueTypeFont(awtFont, true);
        createPauseObjects();
    }

    private void createPauseObjects() {
        pauseObjects.add(new Background(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        int buttonStartY = Constants.SCREEN_HEIGHT / 2 - PauseItem.values().length *
                MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y / 2;
        for (int i = 0; i < PauseItem.values().length; i++) {
            MenuButton button = new MenuButton(PauseItem.values()[i], true);
            button.setY(buttonStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            button.setName(PauseItem.values()[i].toString());
            button.setFont(timesNewRoman);
            button.setTextureFont(textureFont);
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
                        case CANCEL:
                            game.setGameState(Game.GameState.MENU);
                            break;
                        default:
                            System.err.println("Error: Unknown pause menu state \"" + item + "\"!");
                    }
                }
            }
        }
    }
}
