package me.luke.game.entities;

public class Boss extends Enemy{
    public Boss(float x, float y, float width, float height, int speed, int hp) {
        super(x, y, width, height, speed, hp);
        this.setDroppedXp(100);
        this.setDmg(10);
    }
}
