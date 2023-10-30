package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Spawner {
    private static Array<Enemy> enemies;
    private long lastSpawnTime;
    private final long timeBetweenSpawns;
    private long timeNow;
    private long nextWaveSpawn;
    private long wave;
    private int maxEnemies;
    private int enemyCount;

    public Spawner() {
        enemies = new Array<>();
        timeBetweenSpawns = 90000000000L;
        timeNow = TimeUtils.nanoTime();
        nextWaveSpawn = TimeUtils.nanoTime() + timeBetweenSpawns;
        wave = 1;
        maxEnemies = 15;
        enemyCount = 0;
    }

    public void spawnerLoop(Player player) {
        if(TimeUtils.nanoTime() - lastSpawnTime + (wave * 1000000L)> 1000000000L)
            spawnEnemy();

        for (Enemy enemy : enemies) {
            enemy.move(player);
        }

        timeNow = TimeUtils.nanoTime();

        if(timeNow >= nextWaveSpawn) {
            nextWaveSpawn += timeBetweenSpawns;
            wave++;
            maxEnemies++;
            spawnBoss();
        }
    }

    public void spawnEnemy() {
        if(enemyCount < maxEnemies) {
            Enemy enemy = new Enemy(MathUtils.random(1,2)==1?0f:1920f, MathUtils.random(0, 1080-64), 24f, 31f, 80, 10 + (int) wave);
            enemies.add(enemy);
            enemyCount++;
            lastSpawnTime = TimeUtils.nanoTime();
        }
    }

    public void spawnBoss() {
        Boss boss = new Boss(MathUtils.random(1,2)==1?0f:1920f, MathUtils.random(0, 1080-64), 62, 64, 60, 100 + (int) wave * 2);
        enemies.add(boss);
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public long getLastSpawnTime() {
        return lastSpawnTime;
    }

    public long getWave() {
        return wave;
    }

    public long getNextWaveSpawn() {
        return nextWaveSpawn;
    }

    public long getTimeBetweenSpawns() {
        return timeBetweenSpawns;
    }

    public long getTimeNow() {
        return timeNow;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getMaxEnemies() {
        return maxEnemies;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }
}
