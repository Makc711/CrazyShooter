package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.menu.Item;

public enum ControlItem implements Item {
    KEY_UP("KEY UP: ", Constants.KEY_UP),
    KEY_DOWN("KEY DOWN: ", Constants.KEY_DOWN),
    KEY_LEFT("KEY LEFT: ", Constants.KEY_LEFT),
    KEY_RIGHT("KEY RIGHT: ", Constants.KEY_RIGHT);

    private final String name;
    private int keycode;

    ControlItem(String name, int keycode) {
        this.name = name;
        this.keycode = keycode;
    }

    public int getKeycode() {
        return keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    @Override
    public String toString() {
        return name;
    }
}
