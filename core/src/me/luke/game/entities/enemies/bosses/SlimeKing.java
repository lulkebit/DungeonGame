package me.luke.game.entities.enemies.bosses;

import me.luke.game.entities.enemies.Enemy;

public class SlimeKing extends Enemy {
    public SlimeKing(float x, float y, float width, float height, int speed, int droppedXp, int hp) {
        super(x, y, width, height, speed, droppedXp, hp, "boss.png");
        setDmg(10);
    }
}
