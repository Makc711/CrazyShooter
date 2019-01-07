package com.rusanov.game.Shooter.menu;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class FontGame {
    public static int TEXTURE_FONT;
    public static TrueTypeFont TIMES_NEW_ROMAN;

    public FontGame(){
        TEXTURE_FONT = GL11.glGenTextures() + 1;
        TIMES_NEW_ROMAN = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, MenuSizes.MENU_FONT_SIZE), true);
    }
}
