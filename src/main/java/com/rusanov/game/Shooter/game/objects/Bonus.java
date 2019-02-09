package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.game.NameOfTexture;
import com.rusanov.game.Shooter.graphics.GameTexture;

import java.util.concurrent.ThreadLocalRandom;

public class Bonus extends GameObject {
    private transient GameTexture texture;
    private int bonusId;

    public Bonus(GameTexture texture) {
        setType(GameObjectType.BONUS);
        setWidth(Constants.BONUS_SIZE);
        setHeight(Constants.BONUS_SIZE);
        setHealth(Constants.BONUS_HEALTH);
        bonusId = ThreadLocalRandom.current().
                nextInt(NameOfTexture.TEXTURE_HEART.getCode(), NameOfTexture.TEXTURE_SPEED.getCode() + 1);
        this.texture = texture;
    }

    @Override
    public void render() {
        texture.draw(getX(), getY(), Constants.BONUS_SIZE, Constants.BONUS_SIZE,
                Constants.BONUS_SIZE / 2, Constants.BONUS_SIZE / 2, 0);
    }

    @Override
    public void update(float deltaTime) {
        setTexture();
        super.update(deltaTime);
    }

    @Override
    public void intersect(GameObject otherObject) { }

    void giveImprovement(Human human) {
        if (bonusId == NameOfTexture.TEXTURE_HEART.getCode()) {
            giveLife(human);
        } else if (bonusId == NameOfTexture.TEXTURE_SHIELD.getCode()) {
            giveInvulnerability(human);
        } else if (bonusId == NameOfTexture.TEXTURE_STAR.getCode()) {
            giveRateOfFire(human);
        } else if (bonusId == NameOfTexture.TEXTURE_SPEED.getCode()) {
            giveFastSpeed(human);
        } else {
            System.out.println("Error: non-existent bonusId: " + bonusId + "!");
        }
    }

    private void giveLife(Human human) {
        human.setHealth(human.getHealth() + Constants.BONUS_GIVE_HEALTH);
    }

    private void giveInvulnerability(Human human) {
        human.setInvulnerable(true);
        human.setInvulnerableTime(Constants.BONUS_GIVE_INVULNERABLE_TIME);
    }

    private void giveRateOfFire(Human human) {
        human.setRechargeTime(Constants.BONUS_GIVE_RECHARGE_TIME);
    }

    private void giveFastSpeed(Human human) {
        human.setSpeed(Constants.BONUS_GIVE_FAST_SPEED);
    }

    public void setTexture() {
        texture = getGame().getTextures()[(getHealth() == Constants.BONUS_HEALTH)
                ? NameOfTexture.TEXTURE_WOODEN_BOX.getCode()
                : bonusId];
    }
}
