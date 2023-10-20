package me.luke.game.entities;

import com.badlogic.gdx.math.Rectangle;
import me.luke.game.enums.Direction;

public class Player extends Rectangle {
    private Direction currentDirection;
    private Direction previousDirection;

    private int speed;
    private float hp;
    private float maxHP;
    private float healing;

    public Player(float hp, float maxHP, float healing) {
        this.currentDirection = Direction.RIGHT;
        this.previousDirection = Direction.RIGHT;
        this.speed = 350;
        this.hp = hp;
        this.maxHP = maxHP;
        this.healing = healing;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(Direction previousDirection) {
        this.previousDirection = previousDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getHealing() {
        return healing;
    }

    public void setHealing(float healing) {
        this.healing = healing;
    }
}
