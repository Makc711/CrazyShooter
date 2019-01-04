package com.rusanov.game.Shooter.menu;

public enum PauseItem implements Item {
    CONTINUE("CONTINUE"),
    CANCEL("CANCEL"),
    SAVE("SAVE");

    private final String name;

    PauseItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
