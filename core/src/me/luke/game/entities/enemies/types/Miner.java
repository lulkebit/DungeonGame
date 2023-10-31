package me.luke.game.entities.enemies.types;

import me.luke.game.entities.enemies.Enemy;

public class Miner extends Enemy {
    public Miner(float x, float y, float width, float height, int speed, int droppedXp, int hp, String texturePath) {
        super(x, y, width, height, speed, droppedXp, hp, texturePath);
    }
}
