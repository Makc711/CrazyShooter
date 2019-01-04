package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Game;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private Game game;
    private GameObjectType type = GameObjectType.NONE;
    private float x;
    private float y;
    private int width;
    private int height;
    private int health = 1;
    private Color color;
    private boolean invulnerable = false;
    private float xSpeed = 0;
    private float ySpeed = 0;

    public abstract void render();

    public void update(float deltaTime) {
        int oldIntX = (int)x;
        int oldIntY = (int)y;

        float newX = x + xSpeed * deltaTime;
        float newY = y + ySpeed * deltaTime;

        int newIntX = (int)newX;
        int newIntY = (int)newY;

        if (oldIntX != newIntX) {
            game.moveObjectTo(this, newX, y);
        } else {
            x = newX;
        }

        if (oldIntY != newIntY) {
            game.moveObjectTo(this, x, newY);
        } else {
            y = newY;
        }
    }

    public abstract void intersect(GameObject otherObject);

    @SuppressWarnings("all")
    void doDamage(int damage) {
        if (invulnerable) {
            return;
        }

        if (health > damage) {
            health -= damage;
        } else {
            health = 0;
        }
    }

    //==== SETTERS AND GETTERS ====
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        if (this.game == null) {
            this.game = game;
        }
    }

    public GameObjectType getType() {
        return type;
    }

    void setType(GameObjectType type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    Color getColor() {
        return color;
    }

    void setColor(Color color) {
        this.color = color;
    }

    boolean isInvulnerable() {
        return invulnerable;
    }

    void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    float getXSpeed() {
        return xSpeed;
    }

    void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    float getYSpeed() {
        return ySpeed;
    }

    void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    //=============================
}
