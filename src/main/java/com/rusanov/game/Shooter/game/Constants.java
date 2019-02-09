package com.rusanov.game.Shooter.game;

import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Constants {
    static final String SCREEN_NAME = "Crazy Shooter";

    public static final int FPS = 60;
    public static final int DISPLAY_BITS_PER_PIXEL_WIN = 32;
    public static final int DISPLAY_BITS_PER_PIXEL_MAC = 24;
    public static final float DISPLAY_WIDESCREEN = 1.4f;

    static final int KEY_UP = Keyboard.KEY_UP;
    static final int KEY_DOWN = Keyboard.KEY_DOWN;
    static final int KEY_LEFT = Keyboard.KEY_LEFT;
    static final int KEY_RIGHT = Keyboard.KEY_RIGHT;
    public static final int MOUSE_LEFT = 0;

    static final int SCORE_POSITION_X = 15;
    static final int SCORE_POSITION_Y = 5;

    static final int BLOCK_SIZE_MIN = 50;
    static final int BLOCK_SIZE_MAX = 150;
    static final int BLOCKS_ON_LEVEL_MIN = 5;
    static final int BLOCKS_ON_LEVEL_MAX = 18;
    static final int ATTEMPTS_TO_CREATE_BLOCK = 100;
    public static final Color BLOCK_COLOR = Color.ORANGE;

    public static final int HUMAN_SIZE = 60;
    public static final int HUMAN_GUN_SIZE = 56;
    public static final int HUMAN_TEXTURE_SIZE = HUMAN_SIZE * 256 / (256 - HUMAN_GUN_SIZE);
    public static final int TILES_IN_HUMAN_SIZE = 3;
    public static final int TILE_SIZE = HUMAN_SIZE / TILES_IN_HUMAN_SIZE;
    public static final int MAXIMUM_FIRE_DISTANCE = 10_000;

    public static final Color PLAYER_COLOR = Color.GREEN;
    static final int PLAYER_SPEED = 100;
    static final int PLAYER_HEALTH = 2;
    static final float PLAYER_RECHARGE_TIME = 1.5f;

    public static final Color ENEMY_COLOR = Color.RED;
    public static final int ENEMY_START_WAIT = 3;
    static final int MAX_ENEMIES_ON_LEVEL = 2;

    static final LevelSettings[] LEVEL_SETTINGS = {
        new LevelSettings(1, 100, 1, 1.5f, 10_000),
        new LevelSettings(1, 100, 2, 1.5f, 10_000),
        new LevelSettings(1, 200, 2, 1.0f, 400),
        new LevelSettings(2, 100, 1, 1.5f, 10_000),
        new LevelSettings(2, 100, 2, 1.5f, 400),
        new LevelSettings(2, 200, 2, 1.2f, 600),
        new LevelSettings(2, 200, 2, 1.5f, 800),
        new LevelSettings(2, 200, 2, 1.4f, 1_000),
        new LevelSettings(2, 200, 2, 1.2f, 1_000),
        new LevelSettings(2, 200, 2, 1.0f, 10_000)
    };
    static final int MAX_SCORE = LEVEL_SETTINGS.length;

    public static final int BULLET_SIZE = 8;
    public static final int BULLET_SPEED = 300;
    public static final int DAMAGE_FROM_BULLET = 1;

    public static final int BONUS_SIZE = 50;
    public static final int BONUS_HEALTH = 2;
    public static final int BONUS_GIVE_HEALTH = 1;
    public static final int BONUS_GIVE_INVULNERABLE_TIME = 5;
    public static final float BONUS_GIVE_RECHARGE_TIME = 0.5f;
    public static final int BONUS_GIVE_FAST_SPEED = 200;

    static final String TEXTURE_CURSOR_HAND = "Textures/CursorHand.png";
    static final String TEXTURE_CURSOR_AIM = "Textures/CursorAim.png";

    public static final float MATH_45_ANGLE = (float)Math.sqrt(2) / 2;
}