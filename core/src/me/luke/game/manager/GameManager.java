package me.luke.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Dungeon;
import me.luke.game.entities.*;
import me.luke.game.screens.DungeonGameOverScreen;
import me.luke.game.screens.DungeonPauseScreen;
import me.luke.game.weapons.ranged.bowAndArrow.Arrow;
import me.luke.game.weapons.ranged.bowAndArrow.Bow;

import java.util.Iterator;

public class GameManager {
    final Dungeon game;

    private final Player player;
    private final Spawner spawner;

    private static long lastTimeHit;
    private static final long hitCD = 100000000;

    private Array<XpOrb> xpOrbs;
    private Array<Chest> chests;

    private Bow bow;

    public GameManager(final Dungeon game) {
        this.game = game;

        player = new Player(100f, 100f, 0.2f, 0);
        player.setWidth(60);
        player.setHeight(60);
        player.setX(1920f / 2f - player.getWidth() / 2f);
        player.setY(1080f / 2f - player.getHeight() / 2f);

        lastTimeHit = TimeUtils.nanoTime();

        xpOrbs = new Array<>();
        chests = new Array<>();
        spawner = new Spawner();

        bow = player.getBow();
    }

    public void main() {
        bow.action(player);

        playerOrbCheck();
        hitPlayerCheck();

        for(Arrow arrow : bow.getArrows()) {
            hitCheck(arrow);
        }
    }

    private void playerOrbCheck() {
        for (Iterator<XpOrb> iter = xpOrbs.iterator(); iter.hasNext(); ) {
            XpOrb xpOrb = iter.next();

            if(xpOrb.overlaps(player.getPickupRange())) {
                //xpOrb.moveToPlayer(player);
                iter.remove();
                player.addXp(xpOrb.getValue());
            }
        }

        for(Iterator<Chest> iter = chests.iterator(); iter.hasNext();) {
            Chest chest = iter.next();

            if(chest.overlaps(player)) {
                // Chest Screen
                iter.remove();
            }
        }
    }

    private void hitCheck(Arrow arrow) {
        for (Iterator<Enemy> iter = spawner.getEnemies().iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();

            if(arrow.overlaps(enemy)) {
                killarrow(arrow);
                enemy.setHp((int) (enemy.getHp() - bow.getBaseDmg()));

                if(enemy.getHp() <= 0) {
                    if(enemy.getClass() == Boss.class) {
                        chests.add(new Chest(enemy.getX(), enemy.getY(), 18, 14));
                    } else if(enemy.getClass() == Enemy.class) {
                        xpOrbs.add(new XpOrb(enemy.getX(), enemy.getY(), enemy.getDroppedXp()));
                    }
                    iter.remove();
                } else {
                    enemy.hit(player);
                }
            }
        }
    }

    private void killarrow(Arrow arrowToKill) {
        for(int i = 0; i < bow.getArrows().size; i++) {
            if(bow.getArrows().get(i) == arrowToKill)
                bow.getArrows().removeIndex(i);
        }
    }

    public void hitPlayerCheck() {
        for(Enemy enemy : spawner.getEnemies()) {
            if(enemy.overlaps(player)) {
                if(TimeUtils.nanoTime() - lastTimeHit > hitCD) {
                    lastTimeHit = TimeUtils.nanoTime();
                    player.setHp(player.getHp() - enemy.getDmg());
                }
            }
        }
    }

    public final Player getPlayer() {
        return player;
    }

    public Array<XpOrb> getXpOrbs() {
        return xpOrbs;
    }

    public Array<Chest> getChests() {
        return chests;
    }

    public Spawner getSpawner() {
        return spawner;
    }
}
