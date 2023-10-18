package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Rectangle {
    private int speed;

    public Enemy(float x, float y, float width, float height, int speed) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.speed = speed;
    }

    public void move(Rectangle player) {
        Vector2 distance = new Vector2(player.getX() - this.getX(), player.getY() - this.getY());
        distance = distance.nor();
        this.x += distance.x * this.speed * Gdx.graphics.getDeltaTime();
        this.y += distance.y * this.speed * Gdx.graphics.getDeltaTime();
    }
}
