package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static me.luke.game.screens.DungeonGameScreen.hitPlayer;

public class Enemy extends Rectangle {
    private int speed;
    private int dmg = 2;

    public Enemy(float x, float y, float width, float height, int speed) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.speed = speed;
    }

    public void move(Rectangle player) {
        Vector2 distance = new Vector2(
                (player.getX() + player.getWidth() / 2) - this.getX(),
                (player.getY() + player.getHeight() / 2) - this.getY()
                );
        distance = distance.nor();
        if(!this.overlaps(player)) {
            this.x += distance.x * this.speed * Gdx.graphics.getDeltaTime();
            this.y += distance.y * this.speed * Gdx.graphics.getDeltaTime();
        } else {
            hitPlayer(dmg);
        }
    }

    public int getDmg() {
        return dmg;
    }
}
