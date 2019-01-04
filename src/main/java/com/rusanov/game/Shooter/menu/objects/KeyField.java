package com.rusanov.game.Shooter.menu.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.graphics.Draw;
import com.rusanov.game.Shooter.menu.Item;
import com.rusanov.game.Shooter.menu.MenuConstants;
import com.rusanov.game.Shooter.menu.MenuSizes;
import com.rusanov.game.Shooter.menu.Restart;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class KeyField extends MenuObject {
    private Item id;
    private boolean isActive = true;
    private float blackoutText;
    private boolean isPressed = false;
    private String name;
    private int textureFont;
    private Color pressedColor;
    private int keycode;
    private int fieldX;
    private int fieldY;

    public KeyField(Item id, int keycode, String name, int textureFont, int x, int y) {
        this.id = id;
        setState(isActive);
        this.keycode = keycode;
        this.textureFont = textureFont;
        this.name = name;
        this.x = x;
        this.y = y;
        fieldX = MenuSizes.KEY_FIELD_X;
        fieldY = y - MenuSizes.KEY_FIELD_BORDER_Y;
        width = MenuSizes.KEY_FIELD_WIDTH;
        height = Restart.TIMES_NEW_ROMAN.getHeight() + 2 * MenuSizes.KEY_FIELD_BORDER_Y;
    }

    private void setState(boolean isActive) {
        this.isActive = isActive;
        if (isActive) {
            color = MenuConstants.KEY_FIELD_COLOR;
            transparency = MenuConstants.BUTTON_TRANSPARENCY;
            blackoutText = MenuConstants.FIELD_TEXT_BLACKOUT_ACTIVE;
        } else {
            color = MenuConstants.BUTTON_PASSIVE_COLOR;
            transparency = MenuConstants.BUTTON_TRANSPARENCY_SELECTED;
            blackoutText = MenuConstants.FIELD_TEXT_BLACKOUT_PASSIVE;
        }
        pressedColor = color;
    }

    @Override
    public void render() {
        Draw.text(textureFont, Restart.TIMES_NEW_ROMAN, x, y, name, MenuConstants.TEXT_COLOR);

        GL11.glColor4ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)transparency);
        Draw.filledRectangle(fieldX + MenuSizes.BUTTON_BORDER_WIDTH / 2, fieldY + MenuSizes.BUTTON_BORDER_WIDTH / 2,
                width - MenuSizes.BUTTON_BORDER_WIDTH - MenuSizes.BUTTON_BORDER_WIDTH / 2,
                height - MenuSizes.BUTTON_BORDER_WIDTH - MenuSizes.BUTTON_BORDER_WIDTH / 2);
        GL11.glColor4ub((byte)color.darker().getRed(), (byte)color.darker().getGreen(), (byte)color.darker().getBlue(),
                (byte)transparency);
        Draw.rectangle(fieldX, fieldY, width, height, MenuSizes.BUTTON_BORDER_WIDTH);
        GL11.glColor4f(1, 1, 1, 1);

        Draw.text(textureFont, Restart.TIMES_NEW_ROMAN, fieldX + width / 2 - Restart.TIMES_NEW_ROMAN.getWidth(Keyboard.getKeyName(keycode)) / 2, y,
                Keyboard.getKeyName(keycode), MenuConstants.FIELD_TEXT_COLOR.darker(blackoutText));
    }

    @Override
    public void update() {
        setState(isActive);
        if (isActive) {
            int moutheX = Input.getMoutheX();
            int moutheY = Input.getMoutheY();
            if (moutheX >= fieldX && moutheX < fieldX + width &&
                    moutheY >= fieldY && moutheY < fieldY + height) {
                transparency = isPressed ? MenuConstants.BUTTON_TRANSPARENCY : MenuConstants.BUTTON_TRANSPARENCY_SELECTED;
                if (Input.isMouseButtonClicked(Constants.MOUSE_LEFT)) {
                    isPressed = true;
                }
            } else {
                transparency = MenuConstants.BUTTON_TRANSPARENCY;
            }
        }
        color = isPressed ? pressedColor.darker() : pressedColor;
    }

    @Override
    public String toString(){
        return name;
    }

    // GETTERS AND SETTERS
    public Item getId() {
        return id;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public int getKeycode() {
        return keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }
}
