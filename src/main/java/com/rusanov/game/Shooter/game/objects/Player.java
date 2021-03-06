package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.Input;
import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.graphics.GameTexture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Player extends Human {
    private int keyUp;
    private int keyDown;
    private int keyLeft;
    private int keyRight;
    private GameObject targetObject;
    private boolean isMoveTowardsGaze;

    public Player(GameTexture textureHuman) {
        super(GameObjectType.PLAYER, textureHuman, Constants.PLAYER_COLOR);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        double deltaX = Input.getMoutheX() - getX();
        double deltaY = Input.getMoutheY() - getY();
        float angle = (float)(Math.toDegrees(Math.atan2(-deltaY, deltaX)));
        setAngle(angle);

        movePlayer();

        if (Mouse.isButtonDown(Constants.MOUSE_LEFT)) {
            if (fire()) {
                targetObject = distanceTo(GameObjectType.ENEMY).getTargetObject();
            }
        }
    }

    private void movePlayer() {
        if (!Keyboard.isKeyDown(keyRight) && Keyboard.isKeyDown(keyUp) &&
                !Keyboard.isKeyDown(keyLeft) && !Keyboard.isKeyDown(keyDown)) {
            move(Direction.UP);
        } else if (!Keyboard.isKeyDown(keyRight) && !Keyboard.isKeyDown(keyUp) &&
                !Keyboard.isKeyDown(keyLeft) && Keyboard.isKeyDown(keyDown)) {
            move(Direction.DOWN);
        } else if (!Keyboard.isKeyDown(keyRight) && !Keyboard.isKeyDown(keyUp) &&
                Keyboard.isKeyDown(keyLeft) && !Keyboard.isKeyDown(keyDown)) {
            move(Direction.LEFT);
        } else if (Keyboard.isKeyDown(keyRight) && !Keyboard.isKeyDown(keyUp) &&
                !Keyboard.isKeyDown(keyLeft) && !Keyboard.isKeyDown(keyDown)) {
            move(Direction.RIGHT);
        } else if (!Keyboard.isKeyDown(keyRight) && Keyboard.isKeyDown(keyUp) &&
                Keyboard.isKeyDown(keyLeft) && !Keyboard.isKeyDown(keyDown)) {
            move(Direction.UP_LEFT);
        } else if (Keyboard.isKeyDown(keyRight) && Keyboard.isKeyDown(keyUp) &&
                !Keyboard.isKeyDown(keyLeft) && !Keyboard.isKeyDown(keyDown)) {
            move(Direction.UP_RIGHT);
        } else if (!Keyboard.isKeyDown(keyRight) && !Keyboard.isKeyDown(keyUp) &&
                Keyboard.isKeyDown(keyLeft) && Keyboard.isKeyDown(keyDown)) {
            move(Direction.DOWN_LEFT);
        } else if (Keyboard.isKeyDown(keyRight) && !Keyboard.isKeyDown(keyUp) &&
                !Keyboard.isKeyDown(keyLeft) && Keyboard.isKeyDown(keyDown)) {
            move(Direction.DOWN_RIGHT);
        } else {
            setXSpeed(0);
            setYSpeed(0);
        }
    }

    private void move(Direction direction) {
        if (isMoveTowardsGaze) {
            moveTowardsGaze(direction);
        } else {
            moveAlongAxes(direction);
        }
    }

    public void setKeys(int keyUp, int keyDown, int keyLeft, int keyRight) {
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
    }

    GameObject getTargetObject() {
        return targetObject;
    }

    public void setMoveTowardsGaze(boolean moveTowardsGaze) {
        isMoveTowardsGaze = moveTowardsGaze;
    }
}
