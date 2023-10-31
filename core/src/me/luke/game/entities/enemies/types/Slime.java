package me.luke.game.entities.enemies.types;

import me.luke.game.entities.enemies.Enemy;

public class Slime extends Enemy {
    public Slime(float x, float y, float width, float height, int speed, int droppedXp, int hp) {
        super(x, y, width, height, speed, droppedXp, hp, "newEnemyRight.png");
        setDmg(1);
    }
}
