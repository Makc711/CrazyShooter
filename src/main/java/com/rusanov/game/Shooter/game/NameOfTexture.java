package com.rusanov.game.Shooter.game;

public enum NameOfTexture {
    TEXTURE_HUMAN1(0, "Textures/Human1.png"),
    TEXTURE_HUMAN2(1, "Textures/Human2.png"),
    TEXTURE_WOODEN_BOX(2, "Textures/WoodenBox.png"),
    TEXTURE_HEART(3, "Textures/Heart.png"),
    TEXTURE_SHIELD(4, "Textures/Shield.png"),
    TEXTURE_STAR(5, "Textures/Star.png"),
    TEXTURE_SPEED(6, "Textures/Speed.png");

    private int code;
    private final String name;

    NameOfTexture(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }
}
