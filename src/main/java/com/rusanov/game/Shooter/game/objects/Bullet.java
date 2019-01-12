package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.graphics.Draw;
import org.lwjgl.opengl.GL11;

public class Bullet extends GameObject {
    private GameObject ownerObject;

    public Bullet() {
        setType(GameObjectType.BULLET);
        setWidth(Constants.BULLET_SIZE);
        setHeight(Constants.BULLET_SIZE);
    }

    @Override
    public void render() {
        GL11.glColor3ub((byte)getColor().getRed(), (byte)getColor().getGreen(), (byte)getColor().getBlue());
        Draw.filledCircle(getX(), getY(), Constants.BULLET_SIZE, 16);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    @Override
    public void intersect(GameObject otherObject) {
        if (otherObject.getType() == GameObjectType.BLOCK) {
            int x00 = (int)getX() - getWidth() / 2;
            int y00 = (int)getY() - getHeight() / 2;
            int x01 = x00 + getWidth() - 1;
            int y01 = y00 + getHeight() - 1;

            int x10 = (int)otherObject.getX() - otherObject.getWidth() / 2;
            int y10 = (int)otherObject.getY() - otherObject.getHeight() / 2;
            int x11 = x10 + otherObject.getWidth() - 1;
            int y11 = y10 + otherObject.getHeight() - 1;

            if (x00 >= x11 || x01 <= x10) {
                setXSpeed(-getXSpeed());
            } else if (y00 >= y11 || y01 <= y10) {
                setYSpeed(-getYSpeed());
            } else {
                setYSpeed(-getYSpeed());
                setXSpeed(-getXSpeed());
            }
        } else {
            this.setHealth(0);
            if (ownerObject != null && otherObject.getType() != ownerObject.getType()) {
                otherObject.doDamage(Constants.DAMAGE_FROM_BULLET);
            }
        }
    }

    GameObject getOwnerObject() {
        return ownerObject;
    }

    void setOwnerObject(GameObject ownerObject) {
        this.ownerObject = ownerObject;
    }
}
