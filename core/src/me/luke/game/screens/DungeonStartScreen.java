package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import me.luke.game.Drop;
import me.luke.game.Dungeon;

public class DungeonStartScreen implements Screen {

    final Dungeon game;
    final DungeonGameScreen gameScreen;

    private CharSequence str = "Willkommen!";
    private int screenWidth, screenHeight;
    OrthographicCamera camera;

    public DungeonStartScreen(final Dungeon game) {
        this.game = game;
        gameScreen = new DungeonGameScreen(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, str, screenWidth/2, screenHeight/2);
        game.batch.end();

        if (Gdx.input.justTouched()) { // wechseln Sie zum neuen Bildschirm, wenn der Benutzer auf den Bildschirm tippt
            game.setScreen(gameScreen);
            dispose();
        }
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

    @Override
    public void dispose() {

    }

}
