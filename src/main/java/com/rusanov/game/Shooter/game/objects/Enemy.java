package com.rusanov.game.Shooter.game.objects;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.graphics.GameTexture;
import com.rusanov.game.Shooter.game.objects.LieAlgorithm.Point;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Human {
    private float analyzeTime = Constants.ENEMY_START_WAIT;

    public Enemy(GameTexture textureHuman) {
        super(GameObjectType.ENEMY, textureHuman, Constants.ENEMY_COLOR);
        setSpeed(Constants.ENEMY_SPEED);
        setInvulnerable(true);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Player player = (Player)getGame().getPlayer();

        if (analyzeTime > 0) {
            analyzeTime -= deltaTime;
            if (player != null && player.isKillEnemy()) {
                move(Direction.DOWN_LEFT);
            }
        } else {
            if (isInvulnerable()) {
                setInvulnerable(false);
            }
            if (player != null && player.isKillEnemy()) {
                player.protectEnemy();
            }
            analyzeTime = Constants.ENEMY_ANALYZE_TIME;
            analyze();
        }
    }

    private void analyze() {
        float currentAngle = getAngle();
        float angleToMove = currentAngle;
        float currentDistanceToPlayer = Constants.MAXIMUM_FIRE_DISTANCE;
        float currentDistanceToBlock = 0;
        boolean isDefendSelf = false;
        for (int angle = 0; angle < 360; angle++) {
            setAngle(angle);
            float[] distance = distanceTo(GameObjectType.PLAYER);
            float distanceToPlayer = distance[0];
            if (distanceToPlayer >= 0 && distanceToPlayer < currentDistanceToPlayer) {
                currentDistanceToPlayer = distanceToPlayer;
                currentAngle = angle;
            }
            float distanceToBlock = distance[1];
            if (distanceToBlock > currentDistanceToBlock) {
                currentDistanceToBlock = distanceToBlock;
                angleToMove = angle;
            }
            float distanceToPlayerBullet = distance[2];
            if (distanceToPlayerBullet > 0 && distanceToPlayerBullet < Constants.HUMAN_SIZE) {
                currentAngle = angle;
                isDefendSelf = true;
                break;
            }
        }
        setAngle(currentAngle);

        if (isDefendSelf || currentDistanceToPlayer < Constants.MAXIMUM_FIRE_DISTANCE) {
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
            move(Direction.UP);
        } else {
            if (ThreadLocalRandom.current().nextInt(0, 3) == 0) {
                setAngle(ThreadLocalRandom.current().nextInt(0, 360));
                move(Direction.UP);
            } else {
                if (currentDistanceToBlock > 0) {
                    setAngle(angleToMove);
                    move(Direction.UP);
                } else {
                    setXSpeed(0);
                    setYSpeed(0);
                }
            }
        }
    }
}
