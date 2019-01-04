package com.rusanov.game.Shooter.game;

import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Constants {
    static final String SCREEN_NAME = "Crazy Shooter";
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;
    public static boolean FULLSCREEN = false;
    public static final int FPS = 60;
    public static final int DISPLAY_BITS_PER_PIXEL_WIN = 32;
    public static final int DISPLAY_BITS_PER_PIXEL_MAC = 24;
    public static final float DISPLAY_WIDESCREEN = 1.4f;
    static final int FONT_SIZE = 30;

    static final int KEY_UP = Keyboard.KEY_W;
    static final int KEY_DOWN = Keyboard.KEY_S;
    static final int KEY_LEFT = Keyboard.KEY_A;
    static final int KEY_RIGHT = Keyboard.KEY_D;
    public static final int MOUSE_LEFT = 0;

    static final int MAX_SCORE = 10;
    static final int SCORE_POSITION_X = 15;
    static final int SCORE_POSITION_Y = 5;

    static final int BLOCK_SIZE_MIN = 50;
    static final int BLOCK_SIZE_MAX = 150;
    static final int BLOCKS_ON_LEVEL_MIN = 5;
    static final int BLOCKS_ON_LEVEL_MAX = 18;
    static final int ATTEMPTS_TO_CREATE_BLOCK = 100;
    public static final Color BLOCK_COLOR = Color.ORANGE;

    public static final int HUMAN_SIZE = 60;
    public static final int HUMAN_TEXTURE_SIZE = HUMAN_SIZE * 256 / 200;
    public static final int HUMAN_GUN_SIZE = 56;
    public static final float HUMAN_FIRE_COOLDOWN_TIME = 1.5f;
    public static final int TILES_IN_HUMAN_SIZE = 3;
    public static final int TILE_SIZE = HUMAN_SIZE / TILES_IN_HUMAN_SIZE;

    public static final Color PLAYER_COLOR = Color.GREEN;
    public static final int PLAYER_SPEED = 100;

    public static final Color ENEMY_COLOR = Color.RED;
    public static final int ENEMY_SPEED = 100;
    public static final float ENEMY_ANALYZE_TIME = 1.5f;
    public static final int ENEMY_START_WAIT = 3;
    public static final int MAXIMUM_FIRE_DISTANCE = 10_000;

    public static final int BULLET_SIZE = 8;
    public static final int BULLET_SPEED = 300;
    public static final int DAMAGE_FROM_BULLET = 1;
}

