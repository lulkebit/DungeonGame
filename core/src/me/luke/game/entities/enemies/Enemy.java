package me.luke.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.luke.game.entities.Player;

public class Enemy extends Rectangle { // TODO: Disponse Texture to fix memory leak
    private int speed;
    private int dmg;
    private int droppedXp;
    private int hp;
    private final Texture texture;

    public Enemy(float x, float y, float width, float height, int speed, int droppedXp, int hp, String texturePath) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setSpeed(speed);
        setDroppedXp(droppedXp);
        setHp(hp);
        this.texture = new Texture(texturePath);
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
        this.x -= distance.x * (this.speed * player.getBow().getKnockback()) * Gdx.graphics.getDeltaTime();
        this.y -= distance.y * (this.speed * player.getBow().getKnockback()) * Gdx.graphics.getDeltaTime();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getDroppedXp() {
        return droppedXp;
    }

    public void setDroppedXp(int droppedXp) {
        this.droppedXp = droppedXp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Texture getTexture() {
        return texture;
    }
}
