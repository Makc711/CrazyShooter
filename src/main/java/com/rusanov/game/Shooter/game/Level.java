package com.rusanov.game.Shooter.game;

import com.rusanov.game.Shooter.game.objects.GameObject;
import com.rusanov.game.Shooter.game.objects.GameObjectType;
import com.rusanov.game.Shooter.game.objects.Player;

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
        createEnemy();
    }

    private void createBlock() {
        for (int i = 0; i < Constants.ATTEMPTS_TO_CREATE_BLOCK; i++) {
            int x = ThreadLocalRandom.current().
                    nextInt(Constants.BLOCK_SIZE_MAX / 2, Constants.SCREEN_WIDTH - Constants.BLOCK_SIZE_MAX / 2 - 1);
            int y = ThreadLocalRandom.current().
                    nextInt(Constants.BLOCK_SIZE_MAX / 2, Constants.SCREEN_HEIGHT - Constants.BLOCK_SIZE_MAX / 2 - 1);
            if (game.createObject(GameObjectType.BLOCK, x, y) != null) {
                break;
            }
        }
    }

    private void createPlayer() {
        int x, y;
        Player player;
        do {
            x = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, Constants.SCREEN_WIDTH - Constants.HUMAN_SIZE / 2);
            y = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, Constants.SCREEN_HEIGHT - Constants.HUMAN_SIZE / 2);
            player = (Player)game.createObject(GameObjectType.PLAYER, x, y);
        } while (player == null);
        player.setKeys(ControlItem.KEY_UP.getKeycode(), ControlItem.KEY_DOWN.getKeycode(),
                ControlItem.KEY_LEFT.getKeycode(), ControlItem.KEY_RIGHT.getKeycode());
        game.setPlayer(player);
    }

    private void createEnemy() {
        int x, y;
        GameObject enemy;
        do {
            x = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, Constants.SCREEN_WIDTH - Constants.HUMAN_SIZE / 2);
            y = ThreadLocalRandom.current().
                    nextInt(Constants.HUMAN_SIZE / 2, Constants.SCREEN_HEIGHT - Constants.HUMAN_SIZE / 2);
            enemy = game.createObject(GameObjectType.ENEMY, x, y);
        } while (enemy == null);
        game.setEnemy(enemy);
    }
}
