package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import me.luke.game.Dungeon;

public class DungeonLevelUpScreen implements Screen {
    final Dungeon game;
    final DungeonGameScreen gameScreen;
    OrthographicCamera camera;

    public DungeonLevelUpScreen(final Dungeon game, final DungeonGameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Choose a Weapon or Item", 1920f / 2f, 1080f / 2f);
        game.font.draw(game.batch, "Weapon 1", 1920f / 2f, 1080f / 2f - 15);
        game.font.draw(game.batch, "Weapon 2", 1920f / 2f, 1080f / 2f - 30);
        game.font.draw(game.batch, "Weapon 3", 1920f / 2f, 1080f / 2f - 45);
        game.batch.end();

        if (Gdx.input.isTouched()) {
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
