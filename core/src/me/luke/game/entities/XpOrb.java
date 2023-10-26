package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class XpOrb extends Circle {
    private int value;

    public XpOrb(float x, float y, int xpValue) {
        this.x = x;
        this.y = y;
        this.value = xpValue;
    }

    public void moveToPlayer(Player player) {
        while(this.getX() != player.getX() + player.getWidth() / 2 && this.getY() != player.getY() + player.getHeight() / 2) {
            Vector2 distance = new Vector2(
                    (player.getX() + player.getWidth() / 2) - this.getX(),
                    (player.getY() + player.getHeight() / 2) - this.getY()
            );
            distance = distance.nor();
            this.setX(this.getX() + distance.x * 100 * Gdx.graphics.getDeltaTime());
            this.setY(this.getY() + distance.y * 100 * Gdx.graphics.getDeltaTime());
        }
    }

    public int getValue() {
        return value;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
