package me.luke.game.entities;

import com.badlogic.gdx.math.Rectangle;
import me.luke.game.enums.Direction;

public class Bullet extends Rectangle {
    private final Direction direction;

    public Bullet(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
