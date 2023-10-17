package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Drop;
import me.luke.game.Dungeon;
import me.luke.game.entities.Bullet;
import me.luke.game.enums.Direction;

import java.util.Iterator;

public class DungeonGameScreen implements Screen {
    private final Dungeon game;
    private final OrthographicCamera camera;
    private static long lastBulletTime;
    private static Direction currentDirection = Direction.RIGHT;

    private final Texture bgTexture;
    private final Texture playerTexture;
    private final Texture bulletTexture;

    private static Rectangle player;
    private static Array<Bullet> bullets;

    public DungeonGameScreen(final Dungeon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        bgTexture = new Texture("background.png");
        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet.png");

        player = new Rectangle();
        player.x = (float) 1920 / 2 - (float) 64 / 2;
        player.y = 20;
        player.width = 64;
        player.height = 64;

        bullets = new Array<>();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bgTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(playerTexture, player.x, player.y);
        for(Bullet bullet : bullets) {
            game.batch.draw(bulletTexture, bullet.x, bullet.y);
        }
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.y += 350 * Gdx.graphics.getDeltaTime();
            currentDirection = Direction.UP;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.y -= 350 * Gdx.graphics.getDeltaTime();
            currentDirection = Direction.DOWN;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.x -= 350 * Gdx.graphics.getDeltaTime();
            currentDirection = Direction.LEFT;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.x += 350 * Gdx.graphics.getDeltaTime();
            currentDirection = Direction.RIGHT;
        }

        if(TimeUtils.nanoTime() - lastBulletTime > 300000000)
            spawnBullet();

        for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
            Bullet bullet = iter.next();

            switch (bullet.getDirection()) {
                case UP:
                    bullet.y += 200 * Gdx.graphics.getDeltaTime();
                    break;

                case DOWN:
                    bullet.y -= 200 * Gdx.graphics.getDeltaTime();
                    break;

                case RIGHT:
                    bullet.x += 200 * Gdx.graphics.getDeltaTime();
                    break;

                case LEFT:
                    bullet.x -= 200 * Gdx.graphics.getDeltaTime();
                    break;
            }

            if(!(bullet.y + 12 < 1080) || !(bullet.y + 12 > 0) || !(bullet.x + 12 < 1920) || !(bullet.x + 12 > 0)) {
                iter.remove();
            }
        }
    }

    private static void spawnBullet() {
        Bullet bullet = new Bullet(currentDirection);
        bullet.x = player.x + player.getWidth() / 2;
        bullet.y = player.y + player.getHeight() / 2;
        bullet.width = 12;
        bullet.height = 12;
        bullets.add(bullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        game.dispose();
        playerTexture.dispose();
        bgTexture.dispose();
        bulletTexture.dispose();
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
