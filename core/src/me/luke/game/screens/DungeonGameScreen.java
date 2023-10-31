package me.luke.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Dungeon;
import me.luke.game.entities.*;
import me.luke.game.entities.enemies.Enemy;
import me.luke.game.entities.enemies.Spawner;
import me.luke.game.entities.enemies.bosses.SlimeKing;
import me.luke.game.entities.enemies.types.Slime;
import me.luke.game.enums.Direction;
import me.luke.game.manager.GameManager;
import me.luke.game.weapons.ranged.bowAndArrow.Arrow;
import me.luke.game.weapons.ranged.bowAndArrow.Bow;

import java.util.Iterator;

public class DungeonGameScreen implements Screen {
    private final Dungeon game;
    private final OrthographicCamera camera;
    private static long lastHealTime;

    private static final long startTime = TimeUtils.millis();

    private final Texture bgTexture;
    private final Texture playerRightTexture;
    private final Texture playerLeftTexture;
    private final Texture xpTexture;
    private final Texture chestTexture;

    private final GameManager gameManager;
    private final Player player;
    private final Spawner spawner;
    private final Array<XpOrb> xpOrbs;
    private final Array<Chest> chests;

    public DungeonGameScreen(final Dungeon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        bgTexture = new Texture("background.png");
        playerRightTexture = new Texture("playerRight.png");
        playerLeftTexture = new Texture("playerLeft.png");
        xpTexture = new Texture("xp.png");
        chestTexture = new Texture("chest.png");


        lastHealTime = TimeUtils.nanoTime();


        gameManager = new GameManager(game);
        player = gameManager.getPlayer();
        spawner = gameManager.getSpawner();
        xpOrbs = gameManager.getXpOrbs();
        chests = gameManager.getChests();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        long timeAlive = (TimeUtils.millis() - startTime) / 1000;
        long minutes = (timeAlive % 3600) / 60;
        long seconds = timeAlive % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);


        game.batch.begin();
        game.batch.draw(bgTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if(player.getPreviousDirection() == Direction.RIGHT || player.getCurrentDirection() == Direction.RIGHT || player.getCurrentDirection() == Direction.TOPRIGHT || player.getCurrentDirection() == Direction.DOWNRIGHT) {
            game.batch.draw(playerRightTexture, player.getX(), player.getY());
            player.getSpawnPoint().setX(player.getX() + player.getWidth());
            player.getSpawnPoint().setY(player.getY() + player.getHeight() / 2);
        } else if (player.getPreviousDirection() == Direction.LEFT || player.getCurrentDirection() == Direction.LEFT || player.getCurrentDirection() == Direction.TOPLEFT || player.getCurrentDirection() == Direction.DOWNLEFT) {
            game.batch.draw(playerLeftTexture, player.getX(), player.getY());
            player.getSpawnPoint().setX(player.getX());
            player.getSpawnPoint().setY(player.getY() + player.getHeight() / 2);
        }

        if(player.getCurrentDirection() == Direction.DOWN) {
            player.getSpawnPoint().setX(player.getX() + player.getWidth() / 2);
            player.getSpawnPoint().setY(player.getY());
        } else if(player.getCurrentDirection() == Direction.UP) {
            player.getSpawnPoint().setX(player.getX() + player.getWidth() / 2);
            player.getSpawnPoint().setY(player.getY() + player.getHeight());
        }

        Bow bow = player.getBow();

        for(Arrow arrow : bow.getArrows()) {
            switch (arrow.getDirection()) {
                case UP:
                    game.batch.draw(arrow.getTextureUp(), arrow.x, arrow.y);
                    break;

                case DOWN:
                    game.batch.draw(arrow.getTextureDown(), arrow.x, arrow.y);
                    break;

                case LEFT:
                    game.batch.draw(arrow.getTextureLeft(), arrow.x, arrow.y);
                    break;

                case RIGHT:
                    game.batch.draw(arrow.getTextureRight(), arrow.x, arrow.y);
                    break;
            }
        }

        for(XpOrb xpOrb : xpOrbs) {
            game.batch.draw(xpTexture, xpOrb.getX(), xpOrb.getY());
        }

        for(Chest chest : chests) {
            game.batch.draw(chestTexture, chest.getX(), chest.getY());
        }

        for(Enemy enemy : spawner.getEnemies()) {
            if(enemy.getClass() == Slime.class)
                game.batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
            else if(enemy.getClass() == SlimeKing.class)
                game.batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
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
        game.font.draw(game.batch, "Amount: " + bow.getAmount(), 0, 160 + offset);
        game.font.draw(game.batch, "EnemyCount: " + spawner.getEnemyCount(), 0, 140 + offset);
        game.font.draw(game.batch, "MaxEnemyCount: " + spawner.getMaxEnemies(), 0, 120 + offset);
        // ----------------------- DEBUG ----------------------- //
        game.batch.end();

        gameLoop();
        gameManager.main();
    }

    private void gameLoop() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(new DungeonPauseScreen(game, this));

        if(player.getHp() <= 0) {
            game.setScreen(new DungeonGameOverScreen(game));
            dispose();
        }

        if(player.getXp() >= player.getXpToNextLevel())
            player.levelUp(game, this, gameManager);

        player.playerMovement();
        spawner.spawnerLoop(player);

        if(TimeUtils.nanoTime() - lastHealTime > 1000000000 && player.getHp() < player.getMaxHP()) {
            lastHealTime = TimeUtils.nanoTime();
            player.setHp(player.getHp() + player.getHealing());
        }

        for(Iterator<Chest> iter = chests.iterator(); iter.hasNext();) {
            Chest chest = iter.next();

            if(chest.overlaps(player)) {
                game.setScreen(new DungeonChestScreen(game, this, gameManager));
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        playerRightTexture.dispose();
        playerLeftTexture.dispose();
        bgTexture.dispose();
        xpTexture.dispose();
        chestTexture.dispose();
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
