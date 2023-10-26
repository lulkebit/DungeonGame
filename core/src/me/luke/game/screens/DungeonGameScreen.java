package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Dungeon;
import me.luke.game.entities.*;
import me.luke.game.enums.Direction;

import java.util.Iterator;

public class DungeonGameScreen implements Screen {
    private final Dungeon game;
    private final OrthographicCamera camera;

    private static long lastBulletTime;
    private static final int bulletSpeed = 400;

    private static long lastHealTime;
    private static long lastTimeHit;
    private static final long hitCD = 100000000;
    private static long timeAlive = 0;
    private static final long startTime = TimeUtils.millis();

    private final Texture bgTexture;
    private final Texture playerRightTexture;
    private final Texture playerLeftTexture;
    private final Texture bulletRightTexture;
    private final Texture bulletLeftTexture;
    private final Texture bulletUpTexture;
    private final Texture bulletDownTexture;
    private final Texture enemyTexture;
    private final Texture xpTexture;
    private final Texture bossTexture;

    private static Player player;
    private static Rectangle spawnPoint;
    private static Array<Bullet> bullets;
    private static Array<XpOrb> xpOrbs;
    private static Spawner spawner;
    private static Slider slider;

    public DungeonGameScreen(final Dungeon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        bgTexture = new Texture("background.png");
        playerRightTexture = new Texture("playerRight.png");
        playerLeftTexture = new Texture("playerLeft.png");
        bulletRightTexture = new Texture("bulletRight.png");
        bulletLeftTexture = new Texture("bulletLeft.png");
        bulletUpTexture = new Texture("bulletUp.png");
        bulletDownTexture = new Texture("bulletDown.png");
        enemyTexture = new Texture("enemy.png");
        xpTexture = new Texture("xp.png");
        bossTexture = new Texture("boss.png");

        player = new Player(100f, 100f, 0.2f, 0);
        player.setWidth(60);
        player.setHeight(60);
        player.setX(1920f / 2f - player.getWidth() / 2f);
        player.setY(1080f / 2f - player.getHeight() / 2f);

        lastHealTime = TimeUtils.nanoTime();

        spawnPoint = new Rectangle();
        spawnPoint.setWidth(1);
        spawnPoint.setHeight(1);
        spawnPoint.setX(player.getX() + player.getWidth());
        spawnPoint.setY(player.getY() + player.getHeight() / 2);


        lastTimeHit = TimeUtils.nanoTime();

        bullets = new Array<>();
        spawner = new Spawner();
        xpOrbs = new Array<>();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        timeAlive = (TimeUtils.millis() - startTime) / 1000;
        long minutes = (timeAlive % 3600) / 60;
        long seconds = timeAlive % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);


        game.batch.begin();
        game.batch.draw(bgTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if(player.getPreviousDirection() == Direction.RIGHT || player.getCurrentDirection() == Direction.RIGHT || player.getCurrentDirection() == Direction.TOPRIGHT || player.getCurrentDirection() == Direction.DOWNRIGHT) {
            game.batch.draw(playerRightTexture, player.x, player.y);
            spawnPoint.setX(player.getX() + player.getWidth());
            spawnPoint.setY(player.getY() + player.getHeight() / 2);
        } else if (player.getPreviousDirection() == Direction.LEFT || player.getCurrentDirection() == Direction.LEFT || player.getCurrentDirection() == Direction.TOPLEFT || player.getCurrentDirection() == Direction.DOWNLEFT) {
            game.batch.draw(playerLeftTexture, player.x, player.y);
            spawnPoint.setX(player.getX());
            spawnPoint.setY(player.getY() + player.getHeight() / 2);
        }

        if(player.getCurrentDirection() == Direction.DOWN) {
            spawnPoint.setX(player.getX() + player.getWidth() / 2);
            spawnPoint.setY(player.getY());
        } else if(player.getCurrentDirection() == Direction.UP) {
            spawnPoint.setX(player.getX() + player.getWidth() / 2);
            spawnPoint.setY(player.getY() + player.getHeight());
        }


        for(Bullet bullet : bullets) {
            switch (bullet.getDirection()) {
                case UP:
                    game.batch.draw(bulletUpTexture, bullet.x, bullet.y);
                    break;

                case DOWN:
                    game.batch.draw(bulletDownTexture, bullet.x, bullet.y);
                    break;

                case RIGHT:
                    game.batch.draw(bulletRightTexture, bullet.x, bullet.y);
                    break;

                case LEFT:
                    game.batch.draw(bulletLeftTexture, bullet.x, bullet.y);
                    break;
            }
        }

        for(XpOrb xpOrb : xpOrbs) {
            game.batch.draw(xpTexture, xpOrb.x, xpOrb.y);
        }

        for(Enemy enemy : spawner.getEnemies()) {
            if(enemy.getClass() == Enemy.class)
                game.batch.draw(enemyTexture, enemy.getX(), enemy.getY());
            else if(enemy.getClass() == Boss.class)
                game.batch.draw(bossTexture, enemy.getX(), enemy.getY());
        }

        game.font.draw(game.batch, "HP: " + (int) player.getHp(), player.getX(), player.getY() + player.getHeight() + 20);
        game.font.draw(game.batch, timeString, 1920f / 2f, 1080 - 20);
        game.font.draw(game.batch, "Level " + player.getLevel(), 1920f / 2f, 1080 - 35);
        game.font.draw(game.batch, player.getXp() + " XP", 1920f / 2f, 1080 - 50);

        // ----------------------- DEBUG ----------------------- //
        int offset = 400;
        game.font.draw(game.batch, "Alive: " + timeString, 0, 400 + offset);
        game.font.draw(game.batch, "Delta: " + Gdx.graphics.getDeltaTime(), 0, 380 + offset);
        game.font.draw(game.batch, "HP: " + (int) player.getHp(), 0, 360 + offset);
        game.font.draw(game.batch, "E-HP: " + player.getHp(), 0, 340 + offset);
        game.font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 320 + offset);
        game.font.draw(game.batch, "XP: " + player.getXp(), 0, 300 + offset);
        game.font.draw(game.batch, "XPToNextLvL: " + player.getXpToNextLevel(), 0, 280 + offset);
        game.font.draw(game.batch, "Level: " + player.getLevel(), 0, 260 + offset);
        game.font.draw(game.batch, "Wave: " + spawner.getWave(), 0, 240 + offset);
        game.font.draw(game.batch, "NextWaveSpawn: " + spawner.getNextWaveSpawn(), 0, 220 + offset);
        game.font.draw(game.batch, "TimeNow: " + spawner.getTimeNow(), 0, 200 + offset);
        game.font.draw(game.batch, "TimeBetweenSpawns: " + spawner.getTimeBetweenSpawns(), 0, 180 + offset);
        // ----------------------- DEBUG ----------------------- //
        game.batch.end();

        gameLoop();
    }

    private void gameLoop() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(new DungeonPauseScreen(game, this));

        if(player.getHp() <= 0) {
            game.setScreen(new DungeonGameOverScreen(game));
            dispose();
        }

        if(player.getXp() >= player.getXpToNextLevel())
            player.levelUp(game, this);

        player.playerMovement();
        spawner.spawnerLoop(player);

        if(TimeUtils.nanoTime() - lastBulletTime > 300000000)
            spawnBullet();
        if(TimeUtils.nanoTime() - lastHealTime > 1000000000 && player.getHp() < player.getMaxHP()) {
            lastHealTime = TimeUtils.nanoTime();
            player.setHp(player.getHp() + player.getHealing());
        }

        for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
            Bullet bullet = iter.next();

            switch (bullet.getDirection()) {
                case UP:
                    bullet.y += bulletSpeed * Gdx.graphics.getDeltaTime();
                    break;

                case DOWN:
                    bullet.y -= bulletSpeed * Gdx.graphics.getDeltaTime();
                    break;

                case RIGHT:
                    bullet.x += bulletSpeed * Gdx.graphics.getDeltaTime();
                    break;

                case LEFT:
                    bullet.x -= bulletSpeed * Gdx.graphics.getDeltaTime();
                    break;
            }

            if(!(bullet.y + 12 < 1080) || !(bullet.y + 12 > 0) || !(bullet.x + 12 < 1920) || !(bullet.x + 12 > 0)) {
                iter.remove();
            }
            hitCheck(spawner.getEnemies(), bullet);
        }

        playerOrbCheck(xpOrbs, player);
    }

    private static void hitCheck(Array<Enemy> enemies, Bullet bullet) {
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();

            if(bullet.overlaps(enemy)) {
                killBullet(bullet);
                enemy.setHp(enemy.getHp() - bullet.getDmg());

                if(enemy.getHp() <= 0) {
                    xpOrbs.add(new XpOrb(enemy.getX(), enemy.getY(), enemy.getDroppedXp()));
                    iter.remove();
                } else {
                    enemy.hit(player);
                }
            }
        }
    }

    private static void killBullet(Bullet bulletToKill) {
        for(int i = 0; i < bullets.size; i++) {
            if(bullets.get(i) == bulletToKill)
                bullets.removeIndex(i);
        }
    }

    private static void playerOrbCheck(Array<XpOrb> xpOrbs, Player player) {
        for (Iterator<XpOrb> iter = xpOrbs.iterator(); iter.hasNext(); ) {
            XpOrb xpOrb = iter.next();

            if(xpOrb.overlaps(player.getPickupRange())) {
                //xpOrb.moveToPlayer(player);
                iter.remove();
                player.addXp(xpOrb.getValue());
            }
        }
    }

    public static void hitPlayer(int dmg) {
        if(TimeUtils.nanoTime() - lastTimeHit > hitCD) {
            lastTimeHit = TimeUtils.nanoTime();
            player.setHp(player.getHp() - dmg);
        }
    }

    private static void spawnBullet() {
        Direction dir;
        if(player.getCurrentDirection() == Direction.TOPRIGHT || player.getCurrentDirection() == Direction.DOWNRIGHT)
            dir = Direction.RIGHT;
        else if(player.getCurrentDirection() == Direction.TOPLEFT || player.getCurrentDirection() == Direction.DOWNLEFT)
            dir = Direction.LEFT;
        else
            dir = player.getCurrentDirection();

        Bullet bullet = new Bullet(dir, 10);
        bullet.setX(spawnPoint.getX());
        bullet.setY(spawnPoint.getY());
        bullet.setWidth(12);
        bullet.setHeight(12);
        bullets.add(bullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        playerRightTexture.dispose();
        playerLeftTexture.dispose();
        bgTexture.dispose();
        bulletRightTexture.dispose();
        bulletLeftTexture.dispose();
        bulletUpTexture.dispose();
        bulletDownTexture.dispose();
        enemyTexture.dispose();
        xpTexture.dispose();
        bossTexture.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
