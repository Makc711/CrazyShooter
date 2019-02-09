package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.game.objects.*;
import com.rusanov.game.Shooter.menu.MenuSizes;

import java.util.concurrent.ThreadLocalRandom;

class Level {
    private Game game;

    Level(Game game) {
        this.game = game;
        createLevelObjects();
    }

    private void createLevelObjects() {
        int blocksOnLevel = ThreadLocalRandom.current().
                nextInt(Constants.BLOCKS_ON_LEVEL_MIN, Constants.BLOCKS_ON_LEVEL_MAX + 1);
        for (int i = 0; i < blocksOnLevel; i++) {
            createBlock();
        }
        createPlayer();
        createEnemies();
        createBonus();
    }

    private void createBlock() {
        for (int i = 0; i < Constants.ATTEMPTS_TO_CREATE_BLOCK; i++) {
            int x = ThreadLocalRandom.current().
                    nextInt(Constants.BLOCK_SIZE_MAX / 2, MenuSizes.SCREEN_WIDTH - Constants.BLOCK_SIZE_MAX / 2 - 1);
            int y = ThreadLocalRandom.current().
                    nextInt(Constants.BLOCK_SIZE_MAX / 2, MenuSizes.SCREEN_HEIGHT - Constants.BLOCK_SIZE_MAX / 2 - 1);
            if (game.createObject(GameObjectType.BLOCK, x, y) != null) {
                break;
            }
        }
    }

    private void createPlayer() {
        Player player = (Player)createHuman(GameObjectType.PLAYER);
        player.setKeys(ControlItem.KEY_UP.getKeycode(), ControlItem.KEY_DOWN.getKeycode(),
                ControlItem.KEY_LEFT.getKeycode(), ControlItem.KEY_RIGHT.getKeycode());
        player.setSpeed(Constants.PLAYER_SPEED);
        player.setHealth(Constants.PLAYER_HEALTH);
        player.setRechargeTime(Constants.PLAYER_RECHARGE_TIME);
        player.setMoveTowardsGaze(game.getMenuConstructor().isMoveTowardsGaze());
        game.setPlayer(player);
    }

    private void createEnemies() {
        int level = game.getPlayerPoints();
        for (int i = 0; i < Constants.LEVEL_SETTINGS[level].getEnemiesOnLevel(); i++) {
            Enemy enemy = (Enemy)createHuman(GameObjectType.ENEMY);
            enemy.setSpeed(Constants.LEVEL_SETTINGS[level].getEnemySpeed());
            enemy.setHealth(Constants.LEVEL_SETTINGS[level].getEnemyHealth());
            enemy.setMaximumFireDistance(Constants.LEVEL_SETTINGS[level].getMaximumFireDistance());
            enemy.setTimeBetweenActions(Constants.LEVEL_SETTINGS[level].getAnalyzeTime());
            enemy.setRechargeTime(Constants.LEVEL_SETTINGS[level].getRechargeTime());
            game.addEnemy(enemy, i);
        }
    }

    private void createBonus() {
        int x;
        int y;
        do {
            x = ThreadLocalRandom.current().
                    nextInt(Constants.BONUS_SIZE / 2, MenuSizes.SCREEN_WIDTH - Constants.BONUS_SIZE / 2 + 1);
            y = ThreadLocalRandom.current().
                    nextInt(Constants.BONUS_SIZE / 2, MenuSizes.SCREEN_HEIGHT - Constants.BONUS_SIZE / 2 + 1);
        } while (game.createObject(GameObjectType.BONUS, x, y) == null);
    }

    private Human createHuman(GameObjectType objectType) {
        Human human;
        do {
            int x = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, MenuSizes.SCREEN_WIDTH - Constants.HUMAN_SIZE / 2 + 1);
            int y = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, MenuSizes.SCREEN_HEIGHT - Constants.HUMAN_SIZE / 2 + 1);
            human = (Human)game.createObject(objectType, x, y);
        } while (human == null);
        return human;
    }
}

class LevelSettings {
    private int enemiesOnLevel;
    private int enemySpeed;
    private int enemyHealth;
    private float rechargeTime;
    private float analyzeTime;
    private int maximumFireDistance;

    LevelSettings(int enemiesOnLevel, int enemySpeed, int enemyHealth, float rechargeTime, int maximumFireDistance) {
        this.enemiesOnLevel = enemiesOnLevel;
        this.enemySpeed = enemySpeed;
        this.enemyHealth = enemyHealth;
        this.rechargeTime = rechargeTime;
        this.analyzeTime = rechargeTime;
        this.maximumFireDistance = maximumFireDistance;
    }

    int getEnemiesOnLevel() {
        return enemiesOnLevel;
    }

    int getEnemySpeed() {
        return enemySpeed;
    }

    int getEnemyHealth() {
        return enemyHealth;
    }

    float getRechargeTime() {
        return rechargeTime;
    }

    float getAnalyzeTime() {
        return analyzeTime;
    }

    int getMaximumFireDistance() {
        return maximumFireDistance;
    }
}
