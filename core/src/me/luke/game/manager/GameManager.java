package me.luke.game.manager;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.Dungeon;
import me.luke.game.entities.*;
import me.luke.game.entities.enemies.Enemy;
import me.luke.game.entities.enemies.Spawner;
import me.luke.game.entities.enemies.bosses.SlimeKing;
import me.luke.game.entities.enemies.types.Slime;
import me.luke.game.weapons.ranged.bowAndArrow.Arrow;
import me.luke.game.weapons.ranged.bowAndArrow.Bow;

import java.util.Iterator;
import java.util.Random;

public class GameManager {
    final Dungeon game;

    private final Player player;
    private final Spawner spawner;

    private static long lastTimeHit;
    private static final long hitCD = 100000000;

    private final Array<XpOrb> xpOrbs;
    private final Array<Chest> chests;

    private final Bow bow;
    private final Random randObj;

    public GameManager(final Dungeon game) {
        this.game = game;

        player = new Player(100f, 100f, 0.2f, 0);
        player.setWidth(50);
        player.setHeight(64);
        player.setX(1920f / 2f - player.getWidth() / 2f);
        player.setY(1080f / 2f - player.getHeight() / 2f);

        lastTimeHit = TimeUtils.nanoTime();

        xpOrbs = new Array<>();
        chests = new Array<>();
        spawner = new Spawner();

        bow = player.getBow();
        randObj = new Random();
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
    }

    private void hitCheck(Arrow arrow) {
        for (Iterator<Enemy> iter = spawner.getEnemies().iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();

            if(arrow.overlaps(enemy)) {
                if(arrow.getPierceCount() <= 1)
                    killarrow(arrow);
                else
                    arrow.setPierceCount(arrow.getPierceCount() - 1);


                enemy.setHp((int) (randObj.nextFloat() <= bow.getCritChance() ? enemy.getHp() - bow.getBaseDmg() * bow.getCritMulti() : enemy.getHp() - bow.getBaseDmg()));

                if(enemy.getHp() <= 0) {
                    if(enemy.getClass() == SlimeKing.class) {
                        chests.add(new Chest(enemy.getX(), enemy.getY(), 18, 14));
                    } else if(enemy.getClass() == Slime.class) {
                        xpOrbs.add(new XpOrb(enemy.getX(), enemy.getY(), enemy.getDroppedXp()));
                        spawner.setEnemyCount(spawner.getEnemyCount() - 1);
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
