package com.rusanov.game.Shooter.menu;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MenuSizes {
    public static boolean FULLSCREEN;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    static int MENU_FONT_SIZE;

    public static int BUTTON_WIDTH;
    public static int BUTTON_HEIGHT;
    static int BUTTON_OPTIONS_BORDER_X;
    static int BUTTON_OPTIONS_BORDER_Y;
    static int DISTANCE_BETWEEN_BUTTONS_Y;
    public static int BUTTON_MENU_X;
    public static int BUTTON_BORDER_WIDTH;
    static int MENU_OPTIONS_BACKGROUND_X;
    static int MENU_OPTIONS_BACKGROUND_Y;
    static int MENU_OPTIONS_BACKGROUND_WIDTH;
    static int MENU_OPTIONS_X;
    static int MENU_OPTIONS_Y;

    public static int CHECKBOX_SIZE;
    public static int CHECKBOX_DIAMETER_ACTIVE;
    public static int CHECKBOX_DIAMETER_SELECTED;
    static int DISTANCE_BETWEEN_CHECKBOX_Y;

    public static int KEY_FIELD_X;
    public static int KEY_FIELD_WIDTH;
    public static int KEY_FIELD_BORDER_Y;

    public static int VIDEO_FIELD_WIDTH;
    public static int VIDEO_FIELD_HEIGHT;
    static int VIDEO_FIELD_X;

    public MenuSizes() {
        try(ObjectInputStream videoSettings = new ObjectInputStream(new FileInputStream(MenuConstants.NAME_OF_VIDEO_SETTINGS))) {
            FULLSCREEN = videoSettings.readBoolean();
            SCREEN_WIDTH = videoSettings.readInt();
            SCREEN_HEIGHT = videoSettings.readInt();
        } catch(Exception e) {
            FULLSCREEN = false;
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }

        double scaleX = (double) SCREEN_WIDTH / MenuConstants.STANDARD_SCREEN_WIDTH;
        double scaleY = (double) SCREEN_HEIGHT / MenuConstants.STANDARD_SCREEN_HEIGHT;

        MENU_FONT_SIZE = (int)(MenuConstants.MENU_FONT_SIZE * scaleY);

        BUTTON_WIDTH = (int)(MenuConstants.BUTTON_WIDTH * scaleX);
        BUTTON_HEIGHT = (int)(MenuConstants.BUTTON_HEIGHT * scaleY);
        BUTTON_OPTIONS_BORDER_X = (int)(MenuConstants.BUTTON_OPTIONS_BORDER_X * scaleX);
        BUTTON_OPTIONS_BORDER_Y = (int)(MenuConstants.BUTTON_OPTIONS_BORDER_Y * scaleY);
        DISTANCE_BETWEEN_BUTTONS_Y = (int)(BUTTON_HEIGHT + MenuConstants.DISTANCE_BETWEEN_BUTTONS_Y * scaleY);
        BUTTON_MENU_X = SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
        BUTTON_BORDER_WIDTH = (int)(MenuConstants.BUTTON_BORDER_WIDTH * scaleY);
        MENU_OPTIONS_BACKGROUND_X = 2 * BUTTON_OPTIONS_BORDER_X + BUTTON_WIDTH;
        MENU_OPTIONS_BACKGROUND_Y = BUTTON_OPTIONS_BORDER_Y;
        MENU_OPTIONS_BACKGROUND_WIDTH = SCREEN_WIDTH - MENU_OPTIONS_BACKGROUND_X - BUTTON_OPTIONS_BORDER_X;
        MENU_OPTIONS_X = MENU_OPTIONS_BACKGROUND_X + BUTTON_OPTIONS_BORDER_X / 2;
        MENU_OPTIONS_Y = MENU_OPTIONS_BACKGROUND_Y + BUTTON_OPTIONS_BORDER_Y / 2;

        CHECKBOX_SIZE = MENU_FONT_SIZE;
        CHECKBOX_DIAMETER_ACTIVE = (int)(CHECKBOX_SIZE - MenuConstants.CHECKBOX_BORDER_ACTIVE * scaleY);
        CHECKBOX_DIAMETER_SELECTED = (int)(CHECKBOX_SIZE - MenuConstants.CHECKBOX_BORDER_SELECTED * scaleY);
        DISTANCE_BETWEEN_CHECKBOX_Y = (int)(CHECKBOX_SIZE + MenuConstants.DISTANCE_BETWEEN_BUTTONS_Y * scaleY);

        KEY_FIELD_X = (int)(MENU_OPTIONS_BACKGROUND_X + MenuConstants.KEY_FIELD_X * scaleX);
        KEY_FIELD_WIDTH = (int)(MenuConstants.KEY_FIELD_WIDTH * scaleX);
        KEY_FIELD_BORDER_Y = (int)(MenuConstants.KEY_FIELD_BORDER_Y * scaleY);

        VIDEO_FIELD_WIDTH = (int)(MenuConstants.VIDEO_FIELD_WIDTH * scaleX);
        VIDEO_FIELD_HEIGHT = (int)(MenuConstants.VIDEO_FIELD_HEIGHT * scaleY);
        VIDEO_FIELD_X = MENU_OPTIONS_BACKGROUND_X + MENU_OPTIONS_BACKGROUND_WIDTH / 2 - VIDEO_FIELD_WIDTH / 2;
    }
}
