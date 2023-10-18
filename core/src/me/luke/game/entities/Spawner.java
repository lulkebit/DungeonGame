package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Spawner {
    private static Array<Enemy> enemies;
    private static long lastSpawnTime;

    public Spawner() {
        enemies = new Array<>();
    }

    public void spawnerLoop(Rectangle player) {
        for (Enemy enemy : enemies) {
            enemy.move(player);
        }
    }

    public static void spawnEnemy() {
        Enemy enemy = new Enemy(MathUtils.random(1,2)==1?0f:1920f, MathUtils.random(0, 1080-64), 64f, 64f, 150);
        enemies.add(enemy);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public static long getLastSpawnTime() {
        return lastSpawnTime;
    }
}
