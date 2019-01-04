package com.rusanov.game.Shooter.menu.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.menu.Item;
import com.rusanov.game.Shooter.graphics.Draw;
import com.rusanov.game.Shooter.menu.MenuConstants;
import com.rusanov.game.Shooter.menu.MenuSizes;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class MenuButton extends MenuObject {
    private Item id = null;
    private boolean isActive;
    private boolean isPressed = false;
    private String name;
    private TrueTypeFont font;
    private int textureFont;
    private float blackoutText;
    private Color pressedColor;

    public MenuButton(boolean isActive, String name, TrueTypeFont font, int textureFont, int x, int y) {
        setState(isActive);
        this.name = name;
        this.font = font;
        this.textureFont = textureFont;
        this.x = x;
        this.y = y;
    }

    public MenuButton(Item id, boolean isActive, String name, TrueTypeFont font, int textureFont, int y) {
        this.id = id;
        setState(isActive);
        this.name = name;
        this.font = font;
        this.textureFont = textureFont;
        this.y = y;
    }

    private void setState(boolean isActive) {
        this.isActive = isActive;
        if (isActive) {
            color = MenuConstants.BUTTON_ACTIVE_COLOR;
            transparency = MenuConstants.BUTTON_TRANSPARENCY;
            blackoutText = MenuConstants.BUTTON_TEXT_BLACKOUT_ACTIVE;
        } else {
            color = MenuConstants.BUTTON_PASSIVE_COLOR;
            transparency = MenuConstants.BUTTON_TRANSPARENCY_SELECTED;
            blackoutText = MenuConstants.BUTTON_TEXT_BLACKOUT_PASSIVE;
        }
        pressedColor = color;
    }

    @Override
    public void render() {
        GL11.glColor4ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)transparency);
        Draw.filledRectangle(x + MenuSizes.BUTTON_BORDER_WIDTH / 2, y + MenuSizes.BUTTON_BORDER_WIDTH / 2,
                width - MenuSizes.BUTTON_BORDER_WIDTH - MenuSizes.BUTTON_BORDER_WIDTH / 2,
                height - MenuSizes.BUTTON_BORDER_WIDTH - MenuSizes.BUTTON_BORDER_WIDTH / 2);
        GL11.glColor3ub((byte)color.darker().getRed(), (byte)color.darker().getGreen(), (byte)color.darker().getBlue());
        Draw.rectangle(x, y, width, height, MenuSizes.BUTTON_BORDER_WIDTH);
        GL11.glColor4f(1, 1, 1, 1);

        Draw.text(textureFont, font, x + width / 2 - font.getWidth(name) / 2, y + height / 2 - font.getHeight() / 2,
                name, MenuConstants.BUTTON_TEXT_COLOR.darker(blackoutText));
    }

    @Override
    public void update() {
        setState(isActive);
        if (isActive) {
            int moutheX = Input.getMoutheX();
            int moutheY = Input.getMoutheY();
            if (moutheX >= x && moutheX < x + width &&
                    moutheY >= y && moutheY < y + height) {
                transparency = MenuConstants.BUTTON_TRANSPARENCY_SELECTED;
                if (Input.isMouseButtonClicked(Constants.MOUSE_LEFT)) {
                    isPressed = true;
                }
            } else {
                transparency = MenuConstants.BUTTON_TRANSPARENCY;
            }
        }
        color = isPressed ? pressedColor.darker() : pressedColor;
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
}
