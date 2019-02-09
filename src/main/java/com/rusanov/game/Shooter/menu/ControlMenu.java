package com.rusanov.game.Shooter.menu;

import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.ControlItem;
import com.rusanov.game.Shooter.menu.objects.*;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ControlMenu implements Serializable {
    private List<MenuObject> controlObjects = new ArrayList<>();
    private MenuButton buttonControl;
    private boolean isSettingsChanged = false;
    private boolean isMoveTowardsGaze = true;

    ControlMenu(MenuButton buttonControl) {
        this.buttonControl = buttonControl;
        createControlObjects();
    }

    private void createControlObjects() {
        createOptionsBackground(buttonControl, controlObjects);
        int keyFieldStartY = MenuSizes.MENU_OPTIONS_Y;
        String controlSettingsFullName = new File(MenuConstants.NAME_OF_OPTIONS_DIRECTORY +
                MenuConstants.NAME_OF_CONTROL_SETTINGS).getAbsolutePath();
        try(FileInputStream controlItems = new FileInputStream(controlSettingsFullName)) {
            for (ControlItem controlItem : ControlItem.values()) {
                controlItem.setKeycode(controlItems.read());
            }
            isMoveTowardsGaze = controlItems.read() > 0;
        } catch(Exception e) {
            isMoveTowardsGaze = true;
            e.printStackTrace();
        }
        int i = 0;
        for ( ; i < ControlItem.values().length; i++) {
            KeyField keyField = new KeyField(ControlItem.values()[i], ControlItem.values()[i].getKeycode(),
                    ControlItem.values()[i].toString(),
                    MenuSizes.MENU_OPTIONS_X, keyFieldStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
            controlObjects.add(keyField);
        }
        Checkbox moveTowardsGaze = new Checkbox(MenuConstants.NAME_OF_CHECKBOX_MOVE_TG,
                MenuSizes.MENU_OPTIONS_X, keyFieldStartY + i * MenuSizes.DISTANCE_BETWEEN_BUTTONS_Y);
        moveTowardsGaze.setSelected(isMoveTowardsGaze);
        controlObjects.add(moveTowardsGaze);
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
            } else if (menuObject instanceof Checkbox) {
                Checkbox checkbox = (Checkbox)menuObject;
                if (checkbox.isPressed()) {
                    checkbox.setNotPressed();
                    if (checkbox.toString().equals(MenuConstants.NAME_OF_CHECKBOX_MOVE_TG)) {
                        isMoveTowardsGaze = checkbox.isSelected();
                        isSettingsChanged = true;
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
            File controlSettingsFolder = new File(MenuConstants.NAME_OF_OPTIONS_DIRECTORY);
            if (!controlSettingsFolder.exists()) {
                if (controlSettingsFolder.mkdirs()) {
                    System.out.println("Папка: " + controlSettingsFolder + " создана!");
                }
            }
            String controlSettingsFullName = new File(MenuConstants.NAME_OF_OPTIONS_DIRECTORY +
                    MenuConstants.NAME_OF_CONTROL_SETTINGS).getAbsolutePath();
            try(FileOutputStream outputStream = new FileOutputStream(controlSettingsFullName)) {
                for (ControlItem controlItem : ControlItem.values()) {
                    outputStream.write(controlItem.getKeycode());
                }
                outputStream.write(isMoveTowardsGaze ? 1 : 0);
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

    static void createOptionsBackground(MenuButton buttonParent, List<MenuObject> menuObjects) {
        int buttonBackgroundX = MenuSizes.BUTTON_OPTIONS_BORDER_X + MenuSizes.BUTTON_WIDTH;
        int buttonBackgroundY = buttonParent.getY();
        menuObjects.add(new Background(buttonBackgroundX, buttonBackgroundY, MenuSizes.BUTTON_OPTIONS_BORDER_X,
                MenuSizes.BUTTON_HEIGHT, buttonParent.getColor()));
        int backgroundX = MenuSizes.MENU_OPTIONS_BACKGROUND_X;
        int backgroundY = MenuSizes.MENU_OPTIONS_BACKGROUND_Y;
        int backgroundWidth = MenuSizes.MENU_OPTIONS_BACKGROUND_WIDTH;
        int backgroundHeight = MenuSizes.SCREEN_HEIGHT - 2 * MenuSizes.BUTTON_OPTIONS_BORDER_Y;
        menuObjects.add(new Background(backgroundX, backgroundY, backgroundWidth, backgroundHeight));
        MenuButton button = new MenuButton(false, "SAVE",
                backgroundX + backgroundWidth / 2 - MenuSizes.BUTTON_WIDTH / 2,
                backgroundY + backgroundHeight - MenuSizes.BUTTON_OPTIONS_BORDER_Y / 2 - MenuSizes.BUTTON_HEIGHT);
        menuObjects.add(button);
    }

    boolean isMoveTowardsGaze() {
        return isMoveTowardsGaze;
    }
}