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
    private List<MenuObject> videoObjects = new ArrayList<>();
    private Button buttonVideo;
    private boolean isSettingsChanged = false;
    private boolean isFullscreen = MenuSizes.FULLSCREEN;
    private boolean isWidescreen = MenuSizes.WIDESCREEN;
    private int screenWidth = MenuSizes.SCREEN_WIDTH;
    private int screenHeight = MenuSizes.SCREEN_HEIGHT;
    private Game game;

    VideoMenu(Button buttonVideo, Game game) {
        this.game = game;
        this.buttonVideo = buttonVideo;
        createVideoObjects();
    }

    private void createVideoObjects() {
        ControlMenu.createOptionsBackground(buttonVideo, videoObjects);
        int bottomCheckBoxesY = createCheckBoxes();
        createFields(bottomCheckBoxesY);
    }

    private int createCheckBoxes() {
        int checkboxY = MenuSizes.MENU_OPTIONS_Y;
        Checkbox checkboxFullscreen = new Checkbox(MenuConstants.NAME_OF_CHECKBOX_FULLSCREEN,
                MenuSizes.MENU_OPTIONS_X, checkboxY);
        checkboxFullscreen.setSelected(isFullscreen);
        videoObjects.add(checkboxFullscreen);
        checkboxY += MenuSizes.DISTANCE_BETWEEN_CHECKBOX_Y;
        Checkbox checkboxWidescreen = new Checkbox(MenuConstants.NAME_OF_CHECKBOX_WIDESCREEN,
                MenuSizes.MENU_OPTIONS_X, checkboxY);
        checkboxWidescreen.setSelected(isWidescreen);
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
                    TextField textField = new TextField(isFieldSelected);
                    textField.setName(nameOfField);
                    textField.setScreenWidth(current.getWidth());
                    textField.setScreenHeight(current.getHeight());
                    textField.setX(MenuSizes.VIDEO_FIELD_X);
                    if (displayFormat > Constants.DISPLAY_WIDESCREEN) {
                        textField.setY(fieldYDisplay);
                        fieldYDisplay += MenuSizes.VIDEO_FIELD_HEIGHT;
                        textField.setWidescreen(true);
                        textField.setVisible(false);
                    } else {
                        textField.setY(fieldYDisplayWidescreen);
                        fieldYDisplayWidescreen += MenuSizes.VIDEO_FIELD_HEIGHT;
                        textField.setWidescreen(false);
                        textField.setVisible(true);
                    }
                    videoObjects.add(textField);
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
            if (menuObject instanceof Button) {
                Button button = (Button) menuObject;
                if (button.getId() == null) {
                    button.setActive(isSettingsChanged);
                    if (isSettingsChanged && button.isPressed()) {
                        button.setPressed(false);
                        button.setActive(false);
                        saveSettings();
                        isSettingsChanged = false;
                    }
                }
            } else if (menuObject instanceof TextField) {
                TextField textField = (TextField)menuObject;
                if (textField.isWidescreen()) {
                    textField.setVisible(isWidescreen);
                } else {
                    textField.setVisible(!isWidescreen);
                }
                if (textField.isPressed()) {
                    textField.setNotPressed();
                    changeSelectedField(textField);
                    screenWidth = textField.getScreenWidth();
                    screenHeight = textField.getScreenHeight();
                    isSettingsChanged = true;
                }
            } else if (menuObject instanceof Checkbox) {
                Checkbox checkbox = (Checkbox)menuObject;
                if (checkbox.isPressed()) {
                    checkbox.setNotPressed();
                    if (checkbox.toString().equals(MenuConstants.NAME_OF_CHECKBOX_FULLSCREEN)) {
                        isFullscreen = checkbox.isSelected();
                        isSettingsChanged = true;
                    } else if (checkbox.toString().equals(MenuConstants.NAME_OF_CHECKBOX_WIDESCREEN)) {
                        isWidescreen = checkbox.isSelected();
                    }
                }
            }
        }
    }

    private void changeSelectedField(TextField selectedTextField) {
        for (MenuObject menuObject : videoObjects) {
            if (menuObject instanceof TextField && menuObject != selectedTextField) {
                TextField textField = (TextField)menuObject;
                textField.setSelected(false);
            }
        }
    }

    private void saveSettings() {
        File videoSettingsFolder = new File(MenuConstants.NAME_OF_OPTIONS_DIRECTORY);
        if (!videoSettingsFolder.exists()) {
            if (videoSettingsFolder.mkdirs()) {
                System.out.println("Папка: " + videoSettingsFolder + " создана!");
            }
        }
        String videoSettingsFullName = new File(MenuConstants.NAME_OF_OPTIONS_DIRECTORY +
                MenuConstants.NAME_OF_VIDEO_SETTINGS).getAbsolutePath();
        try(ObjectOutputStream videoSettings = new ObjectOutputStream(new FileOutputStream(videoSettingsFullName))) {
            videoSettings.writeBoolean(isFullscreen);
            videoSettings.writeBoolean(isWidescreen);
            videoSettings.writeInt(screenWidth);
            videoSettings.writeInt(screenHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.reload();
    }

    void resetChangedSettings() {
        if (isSettingsChanged) {
            isFullscreen = MenuSizes.FULLSCREEN;
            isWidescreen = MenuSizes.WIDESCREEN;
            screenWidth = MenuSizes.SCREEN_WIDTH;
            screenHeight = MenuSizes.SCREEN_HEIGHT;
            for (MenuObject menuObject : videoObjects) {
                if (menuObject instanceof TextField) {
                    TextField textField = (TextField)menuObject;
                    textField.setSelected(MenuSizes.SCREEN_WIDTH == textField.getScreenWidth() &&
                            MenuSizes.SCREEN_HEIGHT == textField.getScreenHeight());
                }
            }
            isSettingsChanged = false;
        }
    }
}
