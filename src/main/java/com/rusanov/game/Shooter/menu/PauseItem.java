package com.rusanov.game.Shooter.menu;

public enum PauseItem implements Item {
    CONTINUE("CONTINUE"),
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
