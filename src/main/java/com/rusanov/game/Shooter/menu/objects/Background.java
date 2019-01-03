package com.rusanov.game.Shooter.menu.objects;

import com.rusanov.game.Shooter.graphics.Draw;
import com.rusanov.game.Shooter.menu.MenuConstants;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Background extends MenuObject {
    private Color colorLeft = null;

    public Background(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = MenuConstants.BACKGROUND_COLOR;
        transparency = MenuConstants.BACKGROUND_TRANSPARENCY;
    }

    public Background(int x, int y, int width, int height, Color colorLeft) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = MenuConstants.BACKGROUND_COLOR;
        transparency = MenuConstants.BACKGROUND_TRANSPARENCY;
        this.colorLeft = colorLeft;
    }

    @Override
    public void render() {
        if (colorLeft == null) {
            GL11.glColor4ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)transparency);
            Draw.filledRectangle(x, y, width, height);
            GL11.glColor4f(1, 1, 1, 1);
        } else {
            Draw.filledRectangle(x, y, width, height, colorLeft, color, (byte)transparency);
        }
    }

    @Override
    public void update() {

    }
}
