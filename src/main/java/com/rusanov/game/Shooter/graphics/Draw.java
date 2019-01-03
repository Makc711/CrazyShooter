package com.rusanov.game.Shooter.graphics;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Draw {
    public static void rectangle(float x, float y, int width, int height, int borderWidth) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x + borderWidth / 2, y + borderWidth / 2, 0.0F);
        GL11.glLineWidth(borderWidth);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2i(0, 0);
        GL11.glVertex2i(width - borderWidth, 0);
        GL11.glVertex2i(width - borderWidth, height - borderWidth);
        GL11.glVertex2i(0, height - borderWidth);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void filledRectangle(float x, float y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2i(0, 0);
        GL11.glVertex2i(width, 0);
        GL11.glVertex2i(width, height);
        GL11.glVertex2i(0, height);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void filledRectangle(float x, float y, int width, int height, Color colorLeft, Color colorRight, byte transparency) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4ub((byte)colorLeft.getRed(), (byte)colorLeft.getGreen(), (byte)colorLeft.getBlue(), transparency);
        GL11.glVertex2i(0, 0);
        GL11.glColor4ub((byte)colorRight.getRed(), (byte)colorRight.getGreen(), (byte)colorRight.getBlue(), transparency);
        GL11.glVertex2i(width, 0);
        GL11.glVertex2i(width, height);
        GL11.glColor4ub((byte)colorLeft.getRed(), (byte)colorLeft.getGreen(), (byte)colorLeft.getBlue(), transparency);
        GL11.glVertex2i(0, height);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void filledCircle(float centerX, float centerY, int diameter, int segments) {
        int angle = 360 / segments;
        float radius = (float)diameter / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef(centerX, centerY, 0.0F);
        GL11.glLineWidth(2);
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i <= 360; i += angle){
            float x = (float)Math.cos(Math.toRadians(i)) * radius;
            float y = (float)Math.sin(Math.toRadians(i)) * radius;
            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void text(int textureFont, TrueTypeFont font, int x, int y, String text, org.newdawn.slick.Color color) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureFont);
        font.drawString(x, y, text, color);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
