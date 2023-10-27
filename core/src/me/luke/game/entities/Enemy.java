package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Rectangle {
    private int speed;
    private int dmg = 2;
    private int droppedXp;
    private int hp;

    public Enemy(float x, float y, float width, float height, int speed, int hp) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.speed = speed;
        this.droppedXp = 10;
        this.setHp(hp);
    }

    public void move(Player player) {
        Vector2 distance = new Vector2(
                (player.getX() + player.getWidth() / 2) - this.getX(),
                (player.getY() + player.getHeight() / 2) - this.getY()
                );
        distance = distance.nor();
        if(!this.overlaps(player)) {
            this.x += distance.x * this.speed * Gdx.graphics.getDeltaTime();
            this.y += distance.y * this.speed * Gdx.graphics.getDeltaTime();
        }
    }

    public void hit(Player player) {
        Vector2 distance = new Vector2(
                (player.getX() + player.getWidth() / 2) - this.getX(),
                (player.getY() + player.getHeight() / 2) - this.getY()
        );
        distance = distance.nor();
        this.x -= distance.x * (this.speed * 10) * Gdx.graphics.getDeltaTime();
        this.y -= distance.y * (this.speed * 10) * Gdx.graphics.getDeltaTime();
    }

    public int getDmg() {
        return dmg;
    }

    public int getDroppedXp() {
        return droppedXp;
    }

    public void setDroppedXp(int droppedXp) {
        this.droppedXp = droppedXp;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
