package com.rusanov.game.Shooter.menu.objects;

import com.rusanov.game.Shooter.menu.MenuSizes;

import java.awt.*;
import java.io.Serializable;

public abstract class MenuObject implements Serializable {
    int x = MenuSizes.BUTTON_MENU_X;
    int y = 0;
    int width = MenuSizes.BUTTON_WIDTH;
    int height = MenuSizes.BUTTON_HEIGHT;
    Color color;
    byte transparency;

    public abstract void render();

    public abstract void update();

    // GETTERS AND SETTERS
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }
}
