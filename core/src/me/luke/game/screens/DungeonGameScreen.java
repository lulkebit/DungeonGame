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
import me.luke.game.Dungeon;
import me.luke.game.entities.Bullet;
import me.luke.game.enums.Direction;

import java.util.Iterator;

public class DungeonGameScreen implements Screen {
    private final Dungeon game;
    private final OrthographicCamera camera;
    private static Direction currentDirection = Direction.RIGHT;
    private static Direction previousDirection = Direction.RIGHT;

    private static long lastBulletTime;
    private static final int bulletSpeed = 400;
    private static final int playerSpeed = 350;

    private final Texture bgTexture;
    private final Texture playerRightTexture;
    private final Texture playerLeftTexture;
    private final Texture bulletRightTexture;
    private final Texture bulletLeftTexture;
    private final Texture bulletUpTexture;
    private final Texture bulletDownTexture;

    private static Rectangle player;
    private static Array<Bullet> bullets;

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

        if(previousDirection == Direction.RIGHT || currentDirection == Direction.RIGHT || currentDirection == Direction.TOPRIGHT || currentDirection == Direction.DOWNRIGHT) {
            game.batch.draw(playerRightTexture, player.x, player.y);
        } else if (previousDirection == Direction.LEFT || currentDirection == Direction.LEFT || currentDirection == Direction.TOPLEFT || currentDirection == Direction.DOWNLEFT) {
            game.batch.draw(playerLeftTexture, player.x, player.y);
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
        game.batch.end();

        gameLoop();
    }

    private static void gameLoop() {
        playerMovement();

        if(TimeUtils.nanoTime() - lastBulletTime > 300000000)
            spawnBullet();

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
        }
    }

    private static void spawnBullet() {
        Direction dir;
        if(currentDirection == Direction.TOPRIGHT || currentDirection == Direction.DOWNRIGHT)
            dir = Direction.RIGHT;
        else if(currentDirection == Direction.TOPLEFT || currentDirection == Direction.DOWNLEFT)
            dir = Direction.LEFT;
        else
            dir = currentDirection;

        Bullet bullet = new Bullet(dir);
        bullet.x = player.x + player.getWidth() / 2;
        bullet.y = player.y + player.getHeight() / 2;
        bullet.width = 12;
        bullet.height = 12;
        bullets.add(bullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

    // TODO Fix Movement when using arrow keys and WASD at the same time
    private static void playerMovement() {
        if((Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) ||
                (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        ) {
            previousDirection = Direction.RIGHT;
            currentDirection = Direction.TOPRIGHT;
            player.x += playerSpeed * Gdx.graphics.getDeltaTime();
            player.y += playerSpeed * Gdx.graphics.getDeltaTime();
        } else if((Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) ||
                (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
        ) {
            previousDirection = Direction.LEFT;
            currentDirection = Direction.TOPLEFT;
            player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
            player.y += playerSpeed * Gdx.graphics.getDeltaTime();
        } else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D) ||
                (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        ) {
            previousDirection = Direction.RIGHT;
            currentDirection = Direction.DOWNRIGHT;
            player.x += playerSpeed * Gdx.graphics.getDeltaTime();
            player.y -= playerSpeed * Gdx.graphics.getDeltaTime();
        } else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A) ||
                (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
        ) {
            previousDirection = Direction.LEFT;
            currentDirection = Direction.DOWNLEFT;
            player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
            player.y -= playerSpeed * Gdx.graphics.getDeltaTime();
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                previousDirection = currentDirection;
                currentDirection = Direction.LEFT;
                player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                previousDirection = currentDirection;
                currentDirection = Direction.RIGHT;
                player.x += playerSpeed * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                currentDirection = Direction.UP;
                player.y += playerSpeed * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                currentDirection = Direction.DOWN;
                player.y -= playerSpeed * Gdx.graphics.getDeltaTime();
            }
        }

        if(player.x < 0)
            player.x = 0;
        if(player.x > 1920 - 64)
            player.x = 1920 - 64;
        if(player.y < 0)
            player.y = 0;
        if(player.y > 1080 - 64)
            player.y = 1080 - 64;
    }

    @Override
    public void dispose() {
        game.dispose();
        playerRightTexture.dispose();
        playerLeftTexture.dispose();
        bgTexture.dispose();
        bulletRightTexture.dispose();
        bulletLeftTexture.dispose();
        bulletUpTexture.dispose();
        bulletDownTexture.dispose();
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
