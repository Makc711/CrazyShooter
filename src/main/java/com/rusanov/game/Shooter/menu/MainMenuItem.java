package com.rusanov.game.Shooter.menu;

public enum MainMenuItem implements Item {
    NEW_GAME("NEW GAME"),
    LOAD("LOAD"),
    OPTIONS("OPTIONS"),
    AUTHORS("AUTHORS"),
    EXIT("EXIT");

    private final String name;

    MainMenuItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}