package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.game.Game;
import com.rusanov.game.Shooter.menu.objects.*;
import com.rusanov.game.Shooter.menu.objects.Checkbox;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class VideoMenu implements Serializable {
    private int textureFont;
    private List<MenuObject> videoObjects = new ArrayList<>();
    private MenuButton buttonVideo;
    private boolean isSettingsChanged = false;
    private Checkbox checkboxFullscreen;
    private Checkbox checkboxWidescreen;
    private boolean isFullscreen = MenuSizes.FULLSCREEN;
    private int screenWidth = MenuSizes.SCREEN_WIDTH;
    private int screenHeight = MenuSizes.SCREEN_HEIGHT;
    private Game game;

    VideoMenu(MenuButton buttonVideo, Game game, int textureFont) {
        this.game = game;
        this.textureFont = textureFont;
        this.buttonVideo = buttonVideo;
        createVideoObjects();
    }

    private void createVideoObjects() {
        ControlMenu.createOptionsBackground(buttonVideo, videoObjects, textureFont);
        int bottomCheckBoxesY = createCheckBoxes();
        createFields(bottomCheckBoxesY);
    }

    private int createCheckBoxes() {
        int checkboxY = MenuSizes.MENU_OPTIONS_Y;
        checkboxFullscreen = new Checkbox("Fullscreen", textureFont, MenuSizes.MENU_OPTIONS_X, checkboxY);
        checkboxFullscreen.setSelected(Display.isFullscreen());
        videoObjects.add(checkboxFullscreen);
        checkboxY += MenuSizes.DISTANCE_BETWEEN_CHECKBOX_Y;
        checkboxWidescreen = new Checkbox("Widescreen", textureFont, MenuSizes.MENU_OPTIONS_X, checkboxY);
        videoObjects.add(checkboxWidescreen);
        checkboxY += MenuSizes.DISTANCE_BETWEEN_CHECKBOX_Y;
        return checkboxY;
    }

    private void createFields(int startY) {
        try {
            DisplayMode[] displayModes = Display.getAvailableDisplayModes();
            int fieldYDisplay = startY;
            int fieldYDisplayWidescreen = startY;
            for (DisplayMode current : displayModes) {
                float displayFormat = (float)current.getWidth() / current.getHeight();
                if ((current.getBitsPerPixel() == Constants.DISPLAY_BITS_PER_PIXEL_WIN ||
                        current.getBitsPerPixel() == Constants.DISPLAY_BITS_PER_PIXEL_MAC) &&
                        current.getFrequency() == Constants.FPS) {
                    String nameOfField = current.getWidth() + "x" + current.getHeight();
                    boolean isFieldSelected = false;
                    if (MenuSizes.SCREEN_WIDTH == current.getWidth() && MenuSizes.SCREEN_HEIGHT == current.getHeight()) {
                        isFieldSelected = true;
                    }
                    Field field = new Field(textureFont, isFieldSelected);
                    field.setName(nameOfField);
                    field.setScreenWidth(current.getWidth());
                    field.setScreenHeight(current.getHeight());
                    field.setX(MenuSizes.VIDEO_FIELD_X);
                    if (displayFormat > Constants.DISPLAY_WIDESCREEN) {
                        field.setY(fieldYDisplay);
                        fieldYDisplay += MenuSizes.VIDEO_FIELD_HEIGHT;
                        field.setWidescreen(true);
                        field.setVisible(false);
                    } else {
                        field.setY(fieldYDisplayWidescreen);
                        fieldYDisplayWidescreen += MenuSizes.VIDEO_FIELD_HEIGHT;
                        field.setWidescreen(false);
                        field.setVisible(true);
                    }
                    videoObjects.add(field);
                }
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    void render() {
        for (MenuObject menuObject : videoObjects) {
            menuObject.render();
        }
    }

    void update() {
        for (MenuObject menuObject : videoObjects) {
            menuObject.update();
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton) menuObject;
                if (button.getId() == null) {
                    button.setActive(isSettingsChanged);
                    if (isSettingsChanged && button.isPressed()) {
                        button.setPressed(false);
                        button.setActive(false);
                        saveSettings();
                        isSettingsChanged = false;
                    }
                }
            } else if (menuObject instanceof Field) {
                Field field = (Field)menuObject;
                if (field.isPressed()) {
                    field.setNotPressed();
                    changeSelectedField(field);
                    screenWidth = field.getScreenWidth();
                    screenHeight = field.getScreenHeight();
                    isSettingsChanged = true;
                }
            }
        }
        if (checkboxFullscreen.isPressed()) {
            checkboxFullscreen.setNotPressed();
            isFullscreen = checkboxFullscreen.isSelected();
            isSettingsChanged = true;
        }
        if (checkboxWidescreen.isPressed()) {
            checkboxWidescreen.setNotPressed();
            for (MenuObject menuObject : videoObjects) {
                if (menuObject instanceof Field) {
                    Field field = (Field)menuObject;
                    if (field.isWidescreen()) {
                        field.setVisible(checkboxWidescreen.isSelected());
                    } else {
                        field.setVisible(!checkboxWidescreen.isSelected());
                    }
                }
            }
        }
    }

    private void changeSelectedField(Field selectedField) {
        for (MenuObject menuObject : videoObjects) {
            if (menuObject instanceof Field && menuObject != selectedField) {
                Field field = (Field)menuObject;
                field.setSelected(false);
            }
        }
    }

    private void saveSettings() {
        try(ObjectOutputStream videoSettings = new ObjectOutputStream(new FileOutputStream(MenuConstants.NAME_OF_VIDEO_SETTINGS))) {
            videoSettings.writeBoolean(isFullscreen);
            videoSettings.writeInt(screenWidth);
            videoSettings.writeInt(screenHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.reload();
    }

    void resetChangedSettings() {
        if (isSettingsChanged) {
            checkboxFullscreen.setSelected(Display.isFullscreen());
            isFullscreen = MenuSizes.FULLSCREEN;
            screenWidth = MenuSizes.SCREEN_WIDTH;
            screenHeight = MenuSizes.SCREEN_HEIGHT;
            for (MenuObject menuObject : videoObjects) {
                if (menuObject instanceof Field) {
                    Field field = (Field)menuObject;
                    field.setSelected(MenuSizes.SCREEN_WIDTH == field.getScreenWidth() &&
                            MenuSizes.SCREEN_HEIGHT == field.getScreenHeight());
                }
            }
            isSettingsChanged = false;
        }
    }
}
