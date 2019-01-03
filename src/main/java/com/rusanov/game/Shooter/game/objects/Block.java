package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.graphics.Draw;
import org.lwjgl.opengl.GL11;

public class Block extends GameObject {
    public Block(int width, int height) {
        setType(GameObjectType.BLOCK);
        setWidth(width);
        setHeight(height);
        setColor(Constants.BLOCK_COLOR);
        setInvulnerable(true);
    }

    @Override
    public void render() {
        final int BORDER_WIDTH = 4;
        final float centerX = getX();
        final float centerY = getY();
        final int width = getWidth();
        final int height = getHeight();
        final float x0 = centerX - width / 2;
        final float y0 = centerY - height / 2;
        GL11.glColor3f(0.84f, 0.04f, 0.04f);
        Draw.rectangle(x0, y0, width, height, BORDER_WIDTH);

        GL11.glColor3ub((byte)getColor().getRed(), (byte)getColor().getGreen(), (byte)getColor().getBlue());
        Draw.filledRectangle(x0 + BORDER_WIDTH, y0 + BORDER_WIDTH,
                width - 2 * BORDER_WIDTH, height - 2 * BORDER_WIDTH);

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    @Override
    public void intersect(GameObject otherObject) {}
}
