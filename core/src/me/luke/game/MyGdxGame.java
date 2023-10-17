package me.luke.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture dropImg;
	private Texture bucketImg;

	private Rectangle bucket;

	private Array<Rectangle> raindrops;
	private long lastDropTime;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		dropImg = new Texture("drop.png");
		bucketImg = new Texture("bucket.png");

		bucket = new Rectangle();
		bucket.x = (float) 800 / 2 - (float) 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<>();
		spawnRaindrop();
	}

	@Override
	public void render () { // V------ Main Gameloop ------V
		ScreenUtils.clear(0, 0, 0.2f, 1);
		// camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
			batch.draw(bucketImg, bucket.x, bucket.y);
			for(Rectangle raindrop: raindrops) {
				batch.draw(dropImg, raindrop.x, raindrop.y);
			}
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - (float) 64 / 2;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 350 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 350 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) {
				iter.remove();
				System.out.println("Bottom hit");
			}

			if(raindrop.overlaps(bucket)) {
				// dropSound.play();
				iter.remove();
			}
		}

	}
	
	@Override
	public void dispose () {
		dropImg.dispose();
		bucketImg.dispose();
		batch.dispose();
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
}
