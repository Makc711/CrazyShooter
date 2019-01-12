package com.rusanov.game.Shooter.game.objects;

class TargetObject {
    private GameObject targetObject;
    private float distanceToHuman;
    private float distanceToBlock;
    private float distanceToHumanBullet;

    TargetObject(GameObject targetObject, float distanceToHuman, float distanceToBlock, float distanceToHumanBullet) {
        this.targetObject = targetObject;
        this.distanceToHuman = distanceToHuman;
        this.distanceToBlock = distanceToBlock;
        this.distanceToHumanBullet = distanceToHumanBullet;
    }

    GameObject getTargetObject() {
        return targetObject;
    }

    float getDistanceToHuman() {
        return distanceToHuman;
    }

    float getDistanceToBlock() {
        return distanceToBlock;
    }

    float getDistanceToHumanBullet() {
        return distanceToHumanBullet;
    }
}
