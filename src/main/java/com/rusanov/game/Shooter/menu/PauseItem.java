package com.rusanov.game.Shooter.menu;

public enum PauseItem implements Item {
    CONTINUE("CONTINUE"),
    SAVE("SAVE"),
    CANCEL("CANCEL");

    private final String name;

    PauseItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
