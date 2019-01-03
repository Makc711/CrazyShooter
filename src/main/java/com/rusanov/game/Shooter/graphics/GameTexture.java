package com.rusanov.game.Shooter.graphics;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GameTexture {
    private Texture texture;

    public GameTexture(String name) {
        try {
            texture = TextureLoader.getTexture(name.substring(name.lastIndexOf('.') + 1), ResourceLoader.getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(float x, float y, float width, float height, float CenterTx, float CanterTy, float rotate) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        GL11.glPushMatrix();

        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotate, 0, 0, -1);
        GL11.glTranslatef(-CenterTx, -CanterTy, 0);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);    GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);    GL11.glVertex2f(width, 0);
        GL11.glTexCoord2f(1, 1);    GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(0, 1);    GL11.glVertex2f(0, height);
        GL11.glEnd();

        GL11.glPopMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public int getTextureWidth() {
        return texture.getTextureWidth();
    }
}