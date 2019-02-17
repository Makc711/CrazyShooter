package com.rusanov.game.Shooter.menu;

import java.awt.*;

public class MenuConstants {
    static final int STANDARD_SCREEN_WIDTH = 800;
    static final int STANDARD_SCREEN_HEIGHT = 600;

    static final int MENU_FONT_SIZE = 24;

    static final int BUTTON_WIDTH = 200;
    static final int BUTTON_HEIGHT = 60;
    static final int BUTTON_OPTIONS_BORDER_X = 50;
    static final int BUTTON_OPTIONS_BORDER_Y = 50;
    static final int DISTANCE_BETWEEN_BUTTONS_Y = 20;
    static final int BUTTON_BORDER_WIDTH = 4;
    public static final Color BUTTON_ACTIVE_COLOR = Color.GREEN;
    public static final Color BUTTON_PASSIVE_COLOR = Color.RED;
    public static final org.newdawn.slick.Color TEXT_COLOR = org.newdawn.slick.Color.black;
    public static final org.newdawn.slick.Color FIELD_TEXT_COLOR = org.newdawn.slick.Color.lightGray;
    public static final org.newdawn.slick.Color BUTTON_TEXT_COLOR = org.newdawn.slick.Color.blue;
    public static final float FIELD_TEXT_BLACKOUT_ACTIVE = 0.8f;
    public static final float FIELD_TEXT_BLACKOUT_PASSIVE = 0.2f;
    public static final float BUTTON_TEXT_BLACKOUT_ACTIVE = 0.2f;
    public static final float BUTTON_TEXT_BLACKOUT_PASSIVE = 0.8f;
    public static final byte BUTTON_TRANSPARENCY = (byte)255;
    public static final byte BUTTON_TRANSPARENCY_SELECTED = (byte)150;
    public static final Color BACKGROUND_COLOR = Color.CYAN;
    public static final byte BACKGROUND_TRANSPARENCY = (byte)150;

    static final int KEY_FIELD_X = 180;
    static final int KEY_FIELD_WIDTH = 160;
    static final int KEY_FIELD_BORDER_Y = 10;
    public static final Color KEY_FIELD_COLOR = Color.ORANGE;

    static final int VIDEO_FIELD_WIDTH = 200;
    static final int VIDEO_FIELD_HEIGHT = 30;
    public static final Color VIDEO_FIELD_COLOR = Color.BLUE.brighter();
    public static final byte VIDEO_FIELD_TRANSPARENCY = (byte)130;
    public static final byte VIDEO_FIELD_TRANSPARENCY_CHOSE = (byte)100;
    public static final byte VIDEO_FIELD_TRANSPARENCY_SELECTED = (byte)255;
    public static final float VIDEO_FIELD_TEXT_SELECTED = 1.0f;
    public static final float VIDEO_FIELD_TEXT = 0.6f;

    static final int CHECKBOX_BORDER_ACTIVE = 4;
    static final int CHECKBOX_BORDER_SELECTED = 12;

    static final String NAME_OF_OPTIONS_DIRECTORY = "Data\\Options\\";
    static final String NAME_OF_CONTROL_SETTINGS = "Control.dat";
    static final String NAME_OF_VIDEO_SETTINGS = "Video.dat";
    public static final String NAME_OF_SAVE_DIRECTORY = "Data\\Saves\\";
    public static final String NAME_OF_SAVE = "Save.dat";

    static final String NAME_OF_CHECKBOX_FULLSCREEN = "Fullscreen";
    static final String NAME_OF_CHECKBOX_WIDESCREEN = "Widescreen";
    static final String NAME_OF_CHECKBOX_MOVE_TG = "Move towards gaze";
}
