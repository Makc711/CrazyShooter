package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.Game;
import com.rusanov.game.Shooter.menu.objects.MenuButton;
import com.rusanov.game.Shooter.menu.objects.MenuObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuConstructor {
    private Game game;
    private TrueTypeFont timesNewRoman;
    private int textureFont;
    private List<MenuObject> mainMenuObjects = new ArrayList<>();
    private List<MenuObject> optionsObjects = new ArrayList<>();
    private MenuState menuState = MenuState.MAIN_MENU;
    private ControlMenu controlMenu;
    private VideoMenu videoMenu;

    private enum MenuState {
        MAIN_MENU, OPTIONS, AUTHORS
    }

    public MenuConstructor(Game game) {
        this.game = game;
        textureFont = GL11.glGenTextures() + 1;
        timesNewRoman = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, MenuSizes.MENU_FONT_SIZE), true);
        createMenuObjects();
    }

    private void createMenuObjects() {
        int buttonStartY = MenuSizes.SCREEN_HEIGHT / 2 - MainMenuItem.values().length *
                MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y / 2;
        for (int i = 0; i < MainMenuItem.values().length; i++) {
            MenuButton button = new MenuButton(MainMenuItem.values()[i], true, MainMenuItem.values()[i].toString(),
                    timesNewRoman, textureFont, buttonStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            mainMenuObjects.add(button);
        }
        buttonStartY = MenuSizes.SCREEN_HEIGHT / 2 - OptionsItem.values().length *
                MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y / 2;
        for (int i = 0; i < OptionsItem.values().length; i++) {
            MenuButton button = new MenuButton(OptionsItem.values()[i], true, OptionsItem.values()[i].toString(),
                    timesNewRoman, textureFont, buttonStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            button.setX(MenuSizes.BUTTON_OPTIONS_BORDER_X);
            optionsObjects.add(button);
            if (OptionsItem.values()[i] == OptionsItem.CONTROL) {
                button.setPressed(true);
                controlMenu = new ControlMenu(button, timesNewRoman, textureFont);
            } else if (OptionsItem.values()[i] == OptionsItem.VIDEO) {
                videoMenu = new VideoMenu(button, game, timesNewRoman, textureFont);
            }
        }
    }

    public void render() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        switch (menuState) {
            case MAIN_MENU:
                renderMainMenu();
                break;
            case OPTIONS:
                renderOptions();
                break;
            case AUTHORS:
                renderAuthors();
                break;
            default:
                System.err.println("Error: Unknown menu state \"" + menuState + "\"!");
        }
    }

    private void renderMainMenu() {
        for (MenuObject menuObject : mainMenuObjects) {
            menuObject.render();
        }
    }

    private void renderOptions() {
        for (MenuObject menuObject : optionsObjects) {
            menuObject.render();
        }
    }

    private void renderAuthors() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureFont);
        String title = "Authors:";
        int titleX = MenuSizes.SCREEN_WIDTH / 2 - timesNewRoman.getWidth(title);
        int titleY = MenuSizes.SCREEN_HEIGHT / 2 - timesNewRoman.getHeight();
        String author = "Name: Maxim Rusanov";
        String mail = "e-mail: makc93@mail.ru";
        timesNewRoman.drawString(titleX, titleY, title, org.newdawn.slick.Color.black);
        timesNewRoman.drawString(titleX, titleY + timesNewRoman.getHeight(), author, org.newdawn.slick.Color.black);
        timesNewRoman.drawString(titleX, titleY + 2 * timesNewRoman.getHeight(), mail, org.newdawn.slick.Color.black);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void update() {
        switch (menuState) {
            case MAIN_MENU:
                updateMainMenu();
                break;
            case OPTIONS:
                updateOptions();
                break;
            case AUTHORS:
                updateAuthors();
                break;
            default:
                System.err.println("Error: Unknown menu state \"" + menuState + "\"!");
        }
    }

    private void updateMainMenu() {
        if (Input.isKeyClicked(Keyboard.KEY_ESCAPE)) {
            game.exit();
        }
        for (MenuObject menuObject : mainMenuObjects) {
            menuObject.update();
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton)menuObject;
                if (button.isPressed()) {
                    button.setPressed(false);
                    MainMenuItem item = (MainMenuItem)button.getId();
                    switch (item) {
                        case NEW_GAME:
                            game.initialize();
                            game.setGameState(Game.GameState.PLAY);
                            break;
                        case OPTIONS:
                            menuState = MenuState.OPTIONS;
                            break;
                        case AUTHORS:
                            menuState = MenuState.AUTHORS;
                            break;
                        case EXIT:
                            game.exit();
                            break;
                        default:
                            System.err.println("Error: Unknown main menu state \"" + item + "\"!");
                    }
                }
            }
        }
    }

    private void updateOptions() {
        if (Input.isKeyClicked(Keyboard.KEY_ESCAPE)) {
            resetChangedSettings();
            menuState = MenuState.MAIN_MENU;
        }
        for (MenuObject menuObject : optionsObjects) {
            menuObject.update();
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton) menuObject;
                if (button.isPressed()) {
                    button.setPressed(false);
                    OptionsItem item = (OptionsItem)button.getId();
                    switch (item) {
                        case CONTROL:
                            controlMenu.render();
                            controlMenu.update();
                            changePressedButton(button, optionsObjects);
                            break;
                        case VIDEO:
                            videoMenu.render();
                            videoMenu.update();
                            changePressedButton(button, optionsObjects);
                            break;
                        case CANCEL:
                            resetChangedSettings();
                            menuState = MenuState.MAIN_MENU;
                            break;
                        default:
                            System.err.println("Error: Unknown main menu state \"" + item + "\"!");
                    }
                }
            }
        }
    }

    private void updateAuthors() {
        if (Input.isKeyClicked(Keyboard.KEY_ESCAPE) || Input.isMouseButtonClicked(Constants.MOUSE_LEFT)) {
            menuState = MenuState.MAIN_MENU;
        }
    }

    private void changePressedButton(MenuButton pressedButton, List<MenuObject> menuObjects) {
        for (MenuObject menuObject : menuObjects) {
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton) menuObject;
                button.setPressed(false);
            }
        }
        pressedButton.setPressed(true);
    }

    private void resetChangedSettings() {
        controlMenu.resetChangedSettings();
        videoMenu.resetChangedSettings();
    }
}
