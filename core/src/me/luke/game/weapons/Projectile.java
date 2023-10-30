package me.luke.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import me.luke.game.enums.Direction;

public class Projectile extends Rectangle {
    private final Direction direction;
    private final Texture textureUp;
    private final Texture textureDown;
    private final Texture textureLeft;
    private final Texture textureRight;
    private float speed;
    private int pierceCount;

    public Projectile(Direction direction, String textureUpPath, String textureDownPath, String textureLeftPath, String textureRightPath, float speed, int pierceCount) {
        this.direction = direction;
        this.textureUp = new Texture(textureUpPath);
        this.textureDown = new Texture(textureDownPath);
        this.textureLeft = new Texture(textureLeftPath);
        this.textureRight = new Texture(textureRightPath);

        setSpeed(speed);
        setPierceCount(pierceCount);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getTextureUp() {
        return textureUp;
    }

    public Texture getTextureDown() {
        return textureDown;
    }

    public Texture getTextureLeft() {
        return textureLeft;
    }

    public Texture getTextureRight() {
        return textureRight;
    }

    public int getPierceCount() {
        return pierceCount;
    }

    public void setPierceCount(int pierceCount) {
        this.pierceCount = pierceCount;
    }
}
