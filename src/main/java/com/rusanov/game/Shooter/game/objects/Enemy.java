package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.game.objects.LieAlgorithm.Point;
import com.rusanov.game.Shooter.graphics.GameTexture;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Human {
    private float timeBetweenActions;
    private float analyzeTime = Constants.ENEMY_START_WAIT;
    private int maximumFireDistance;

    public Enemy(GameTexture textureHuman) {
        super(GameObjectType.ENEMY, textureHuman, Constants.ENEMY_COLOR);
        setInvulnerable(true);
        setInvulnerableTime(Constants.ENEMY_START_WAIT);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Player player = (Player)getGame().getPlayer();

        if (analyzeTime > 0) {
            analyzeTime -= deltaTime;
            if (player != null && player.getTargetObject() == this) {
                moveTowardsGaze(Direction.DOWN_LEFT);
            }
        } else {
            analyzeTime = timeBetweenActions;
            analyze();
        }
    }

    private void analyze() {
        float currentAngle = getAngle();
        float angleToMove = currentAngle;
        float currentDistanceToPlayer = maximumFireDistance;
        float currentDistanceToBlock = 0;
        boolean isDefendSelf = false;
        for (int angle = 0; angle < 360; angle++) {
            setAngle(angle);
            TargetObject targetObject = distanceTo(GameObjectType.PLAYER);
            float distanceToPlayer = targetObject.getDistanceToHuman();
            if (distanceToPlayer >= 0 && distanceToPlayer < currentDistanceToPlayer) {
                currentDistanceToPlayer = distanceToPlayer;
                currentAngle = angle;
            }
            float distanceToBlock = targetObject.getDistanceToBlock();
            if (distanceToBlock > currentDistanceToBlock) {
                currentDistanceToBlock = distanceToBlock;
                angleToMove = angle;
            }
            float distanceToPlayerBullet = targetObject.getDistanceToHumanBullet();
            if (distanceToPlayerBullet > 0 && distanceToPlayerBullet < Constants.HUMAN_SIZE) {
                currentAngle = angle;
                isDefendSelf = true;
                break;
            }
        }
        setAngle(currentAngle);

        if (isDefendSelf || currentDistanceToPlayer < maximumFireDistance) {
            setXSpeed(0);
            setYSpeed(0);
            fire();
            return;
        }
        enemyMove(currentDistanceToBlock, angleToMove);
    }

    private void enemyMove(float currentDistanceToBlock, float angleToMove) {
        int startColumn = (int)(getX()) / Constants.TILE_SIZE;
        int startRow = (int)(getY()) / Constants.TILE_SIZE;
        Point start = new Point(startColumn, startRow);
        Point end = new Point(0, 0);
        GameObject player = getGame().getPlayer();
        if (player != null) {
            int endColumn = (int)(player.getX()) / Constants.TILE_SIZE;
            int endRow = (int)(player.getY()) / Constants.TILE_SIZE;
            end = new Point(endColumn, endRow);
        }
        Point[] way = getGame().getWayFinder().find(start, end);
        if (way != null && way.length >= Constants.TILES_IN_HUMAN_SIZE) {
            float dX = way[Constants.TILES_IN_HUMAN_SIZE / 2 + 1].getColumn() * Constants.TILE_SIZE + Constants.TILE_SIZE / 2 - getX();
            float dY = way[Constants.TILES_IN_HUMAN_SIZE / 2 + 1].getRow() * Constants.TILE_SIZE + Constants.TILE_SIZE / 2 - getY();
            int up = (dY >= 0) ? -1 : 1;
            float directionAngle = (float)(up * Math.toDegrees(Math.acos(dX / Math.sqrt(dX * dX + dY * dY))));
            setAngle(directionAngle);
            moveTowardsGaze(Direction.UP);
        } else {
            if (ThreadLocalRandom.current().nextInt(0, 3) == 0) {
                setAngle(ThreadLocalRandom.current().nextInt(0, 360));
                moveTowardsGaze(Direction.UP);
            } else {
                if (currentDistanceToBlock > 0) {
                    setAngle(angleToMove);
                    moveTowardsGaze(Direction.UP);
                } else {
                    setXSpeed(0);
                    setYSpeed(0);
                }
            }
        }
    }

    public void setMaximumFireDistance(int maximumFireDistance) {
        this.maximumFireDistance = maximumFireDistance;
    }

    public void setTimeBetweenActions(float timeBetweenActions) {
        this.timeBetweenActions = timeBetweenActions;
    }
}
