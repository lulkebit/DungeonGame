package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import me.luke.game.Dungeon;
import me.luke.game.manager.GameManager;

import java.util.Random;

public class ChestScreen implements Screen {
    final Dungeon game;
    final GameScreen gameScreen;
    OrthographicCamera camera;
    GameManager gameManager;
    private final int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    private String upgrade;

    public ChestScreen(final Dungeon game, final GameScreen gameScreen, GameManager gameManager) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.gameManager = gameManager;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        randomUpgrade();
    }

    private static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0.2f, 1);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "- CHEST -", 1920f / 2f, 1080f / 2f);
        game.font.draw(game.batch, "Bow...", 920f / 2f, 1080f / 2f - 15);
        game.font.draw(game.batch, upgrade, 920f / 2f, 1080f / 2f - 30);
        game.batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(gameScreen);
            dispose();
        }
    }

    private void randomUpgrade() {
        if (gameManager.getPlayer().getBow().getLvl() < gameManager.getPlayer().getBow().getMaxLvl()) {
            switch (getRandom(array)) {
                case 0:
                    upgrade = "CritMulti";
                    gameManager.getPlayer().getBow().setCritMulti(gameManager.getPlayer().getBow().getCritMulti() + 0.2f);
                    break;

                case 1:
                    upgrade = "CritChance";
                    gameManager.getPlayer().getBow().setCritChance(gameManager.getPlayer().getBow().getCritChance() + 0.02f);
                    break;

                case 2:
                    upgrade = "Amount";
                    gameManager.getPlayer().getBow().setAmount(gameManager.getPlayer().getBow().getAmount() + 1);
                    break;

                case 3:
                    upgrade = "Interval";
                    gameManager.getPlayer().getBow().setProjectileInterval(gameManager.getPlayer().getBow().getProjectileInterval() - 5f);
                    break;

                case 4:
                    upgrade = "Pierce";
                    gameManager.getPlayer().getBow().setPierce(gameManager.getPlayer().getBow().getPierce() + 1);
                    break;

                case 5:
                    upgrade = "Knockback";
                    gameManager.getPlayer().getBow().setKnockback(gameManager.getPlayer().getBow().getKnockback() + 8);
                    break;

                case 6:
                    upgrade = "Cooldown";
                    gameManager.getPlayer().getBow().setCooldown(gameManager.getPlayer().getBow().getCooldown() - 10);
                    break;

                case 7:
                    upgrade = "BaseDmg";
                    gameManager.getPlayer().getBow().setBaseDmg(gameManager.getPlayer().getBow().getBaseDmg() + 6);
                    break;
            }
            gameManager.getPlayer().getBow().setLvl(gameManager.getPlayer().getBow().getLvl() + 1);
        } else {
            upgrade = "MaxLevelReached";
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
