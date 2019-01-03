package com.rusanov.game.Shooter.menu;

public enum OptionsItem implements Item {
    CONTROL("CONTROL"),
    VIDEO("VIDEO"),
    CANCEL("CANCEL");

    private final String name;

    OptionsItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
