package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.ControlItem;
import com.rusanov.game.Shooter.menu.objects.Background;
import com.rusanov.game.Shooter.menu.objects.KeyField;
import com.rusanov.game.Shooter.menu.objects.MenuButton;
import com.rusanov.game.Shooter.menu.objects.MenuObject;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ControlMenu {
    private TrueTypeFont font;
    private int textureFont;
    private List<MenuObject> controlObjects = new ArrayList<>();
    private MenuButton buttonControl;
    private boolean isSettingsChanged = false;

    ControlMenu(MenuButton buttonControl, TrueTypeFont font, int textureFont) {
        this.font = font;
        this.textureFont = textureFont;
        this.buttonControl = buttonControl;
        createControlObjects();
    }

    private void createControlObjects() {
        createOptionsBackground(buttonControl, controlObjects, font, textureFont);
        int keyFieldStartY = MenuSizes.MENU_OPTIONS_Y;
        try(FileInputStream controlItems = new FileInputStream(MenuConstants.NAME_OF_CONTROL_SETTINGS)) {
            for (ControlItem controlItem : ControlItem.values()) {
                controlItem.setKeycode(controlItems.read());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ControlItem.values().length; i++) {
            KeyField keyField = new KeyField(ControlItem.values()[i], ControlItem.values()[i].getKeycode(),
                    ControlItem.values()[i].toString(), font, textureFont,
                    MenuSizes.MENU_OPTIONS_X, keyFieldStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            controlObjects.add(keyField);
        }
    }

    void render() {
        for (MenuObject menuObject : controlObjects) {
            menuObject.render();
        }
    }

    void update() {
        for (MenuObject menuObject : controlObjects) {
            menuObject.update();
            if (menuObject instanceof MenuButton) {
                MenuButton button = (MenuButton)menuObject;
                if (button.getId() == null) {
                    button.setActive(isSettingsChanged);
                    if (isSettingsChanged && button.isPressed()) {
                        button.setPressed(false);
                        button.setActive(false);
                        saveSettings(true);
                        isSettingsChanged = false;
                    }
                }
            } else if (menuObject instanceof KeyField) {
                KeyField keyField = (KeyField)menuObject;
                if (keyField.isPressed()) {
                    changePressedField(keyField);
                    int keycode = Input.getEventKeycode();
                    if (keycode != Keyboard.KEY_NONE && keycode != Keyboard.KEY_ESCAPE) {
                        if (keycode != keyField.getKeycode()) {
                            keyField.setKeycode(keycode);
                            isSettingsChanged = true;
                        }
                        keyField.setPressed(false);
                        setAllFieldsActive();
                    }
                }
            }
        }
    }

    private void changePressedField(KeyField pressedField) {
        for (MenuObject menuObject : controlObjects) {
            if (menuObject instanceof KeyField) {
                KeyField keyField = (KeyField)menuObject;
                keyField.setActive(false);
                keyField.setPressed(false);
            }
        }
        pressedField.setActive(true);
        pressedField.setPressed(true);
    }

    private void setAllFieldsActive() {
        for (MenuObject menuObject : controlObjects) {
            if (menuObject instanceof KeyField) {
                KeyField keyField = (KeyField) menuObject;
                keyField.setActive(true);
                keyField.setPressed(false);
            }
        }
    }

    private void saveSettings(boolean isSave) {
        for (MenuObject menuObject : controlObjects) {
            if (menuObject instanceof KeyField) {
                KeyField keyField = (KeyField)menuObject;
                if (isSave) {
                    ((ControlItem)keyField.getId()).setKeycode(keyField.getKeycode());
                } else {
                    keyField.setKeycode(((ControlItem)keyField.getId()).getKeycode());
                }
            }
        }
        if (isSave) {
            try(FileOutputStream controlItems = new FileOutputStream(MenuConstants.NAME_OF_CONTROL_SETTINGS)) {
                for (ControlItem controlItem : ControlItem.values()) {
                    controlItems.write(controlItem.getKeycode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void resetChangedSettings() {
        if (isSettingsChanged) {
            saveSettings(false);
            isSettingsChanged = false;
        }
        setAllFieldsActive();
    }

    static void createOptionsBackground(MenuButton buttonParent, List<MenuObject> menuObjects, TrueTypeFont font, int textureFont) {
        int buttonBackgroundX = MenuSizes.BUTTON_OPTIONS_BORDER_X + MenuSizes.BUTTON_WIDTH;
        int buttonBackgroundY = buttonParent.getY();
        menuObjects.add(new Background(buttonBackgroundX, buttonBackgroundY, MenuSizes.BUTTON_OPTIONS_BORDER_X,
                MenuSizes.BUTTON_HEIGHT, buttonParent.getColor()));
        int backgroundX = MenuSizes.MENU_OPTIONS_BACKGROUND_X;
        int backgroundY = MenuSizes.MENU_OPTIONS_BACKGROUND_Y;
        int backgroundWidth = MenuSizes.MENU_OPTIONS_BACKGROUND_WIDTH;
        int backgroundHeight = Constants.SCREEN_HEIGHT - 2 * MenuSizes.BUTTON_OPTIONS_BORDER_Y;
        menuObjects.add(new Background(backgroundX, backgroundY, backgroundWidth, backgroundHeight));
        MenuButton button = new MenuButton(false, "SAVE", font, textureFont,
                backgroundX + backgroundWidth / 2 - MenuSizes.BUTTON_WIDTH / 2,
                backgroundY + backgroundHeight - MenuSizes.BUTTON_OPTIONS_BORDER_Y / 2 - MenuSizes.BUTTON_HEIGHT);
        menuObjects.add(button);
    }
}