package me.luke.game.weapons.ranged.bowAndArrow;

import com.badlogic.gdx.Gdx;
import me.luke.game.enums.Direction;
import me.luke.game.weapons.Projectile;

public class Arrow extends Projectile {
    public Arrow(Direction direction, float speed, int pierceCount) { // TODO: Disponse these textures (Memory Leaking)
        super(direction, "bulletUp.png", "bulletDown.png", "bulletLeft.png", "bulletRight.png", speed, pierceCount);
    }

    public void move() {
        switch (getDirection()) {
            case UP:
                setY(getY() + getSpeed() * Gdx.graphics.getDeltaTime());
                break;

            case DOWN:
                setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
                break;

            case RIGHT:
                setX(getX() + getSpeed() * Gdx.graphics.getDeltaTime());
                break;

            case LEFT:
                setX(getX() - getSpeed() * Gdx.graphics.getDeltaTime());
                break;
        }
    }
}
