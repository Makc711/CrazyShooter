package com.rusanov.game.Shooter.menu.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.graphics.Draw;
import com.rusanov.game.Shooter.menu.MenuConstants;
import com.rusanov.game.Shooter.menu.MenuSizes;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

public class Field extends MenuObject {
    private boolean isVisible;
    private boolean isSelected;
    private boolean isPressed = false;
    private float blackoutText;
    private String name;
    private TrueTypeFont font;
    private int textureFont;
    private boolean isWidescreen;
    private int screenWidth;
    private int screenHeight;

    public Field(TrueTypeFont font, int textureFont, boolean isSelected) {
        setState(isSelected);
        this.font = font;
        this.textureFont = textureFont;
        width = MenuSizes.VIDEO_FIELD_WIDTH;
        height = MenuSizes.VIDEO_FIELD_HEIGHT;
    }

    private void setState(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            color = MenuConstants.VIDEO_FIELD_COLOR;
            transparency = MenuConstants.VIDEO_FIELD_TRANSPARENCY_SELECTED;
            blackoutText = MenuConstants.VIDEO_FIELD_TEXT_SELECTED;
        } else {
            color = MenuConstants.VIDEO_FIELD_COLOR;
            transparency = MenuConstants.VIDEO_FIELD_TRANSPARENCY;
            blackoutText = MenuConstants.VIDEO_FIELD_TEXT;
        }
    }

    @Override
    public void render() {
        if (isVisible) {
            GL11.glColor4ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)transparency);
            Draw.filledRectangle(x, y, width, height);
            GL11.glColor4f(1, 1, 1, 1);

            Draw.text(textureFont, font, x + width / 2 - font.getWidth(name) / 2, y + height / 2 - font.getHeight() / 2,
                    name, MenuConstants.FIELD_TEXT_COLOR.darker(blackoutText));
        }
    }

    @Override
    public void update() {
        if (isVisible) {
            setState(isSelected);
            if (!isSelected) {
                int moutheX = Input.getMoutheX();
                int moutheY = Input.getMoutheY();
                if (moutheX >= x && moutheX < x + width && moutheY >= y && moutheY < y + height) {
                    transparency = MenuConstants.VIDEO_FIELD_TRANSPARENCY_CHOSE;
                    blackoutText = MenuConstants.VIDEO_FIELD_TEXT_SELECTED;
                    if (Input.isMouseButtonClicked(Constants.MOUSE_LEFT)) {
                        isSelected = true;
                        isPressed = true;
                    }
                } else {
                    transparency = MenuConstants.VIDEO_FIELD_TRANSPARENCY;
                    blackoutText = MenuConstants.VIDEO_FIELD_TEXT;
                }
            }
        }
    }

    // GETTERS AND SETTERS
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setNotPressed() {
        isPressed = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWidescreen() {
        return isWidescreen;
    }

    public void setWidescreen(boolean widescreen) {
        isWidescreen = widescreen;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
