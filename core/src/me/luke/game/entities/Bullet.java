package me.luke.game.entities;

import com.badlogic.gdx.math.Rectangle;
import me.luke.game.enums.Direction;

public class Bullet extends Rectangle {
    private final Direction direction;
    private final int dmg;

    public Bullet(Direction direction, int dmg) {
        this.direction = direction;
        this.dmg = dmg;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getDmg() {
        return dmg;
    }
}
