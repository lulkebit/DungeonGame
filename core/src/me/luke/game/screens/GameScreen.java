package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Drop;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final Drop game;
    private final OrthographicCamera camera;
    private final Rectangle bucket;
    private final Array<Rectangle> raindrops;
    private long lastDropTime;
    private int dropsGathered;

    private Texture dropImg;
    private Texture bucketImg;

    public GameScreen(final Drop game) {
        this.game = game;

        dropImg = new Texture("drop.png");
        bucketImg = new Texture("bucket.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle();
        bucket.x = (float) 800 / 2 - (float) 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<>();
        spawnRaindrop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(bucketImg, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            game.batch.draw(dropImg, raindrop.x, raindrop.y);
        }
        game.batch.end();

        /*if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - (float) 64 / 2;
        }*/

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 350 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 350 * Gdx.graphics.getDeltaTime();

        if(bucket.x < 0)
            bucket.x = 0;
        if(bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0)
                iter.remove();

            if(raindrop.overlaps(bucket)) {
                // dropSound.play();
                dropsGathered++;
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        game.dispose();
        dropImg.dispose();
        bucketImg.dispose();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
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
