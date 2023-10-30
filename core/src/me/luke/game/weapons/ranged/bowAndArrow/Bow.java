package me.luke.game.weapons.ranged.bowAndArrow;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.entities.*;
import me.luke.game.enums.Direction;
import me.luke.game.weapons.Weapon;

import java.util.Iterator;

public class Bow extends Weapon {
    private final Array<Arrow> arrows;
    private static long lastArrowTime;
    private static long lastIntervalTime;

    private int i = 0;

    public Bow() {
        super("Bow", "enemy.png");
        setMaxLvl(100);
        setBaseDmg(12f);
        setProjectileSpeed(600);
        setCooldown(800);
        setCritMulti(0);
        setCritChance(0);
        setAmount(1);
        setProjectileInterval(50);
        setPierce(1);
        setKnockback(10);
        arrows = new Array<>();
        lastArrowTime = TimeUtils.millis();
        lastIntervalTime = TimeUtils.millis();
    }

    public void action(Player player) {
        if(TimeUtils.timeSinceMillis(lastIntervalTime) > getProjectileInterval() && i < getAmount()) {
            spawnArrow(player);
            lastIntervalTime = TimeUtils.millis();
            i++;
        } else if(TimeUtils.timeSinceMillis(lastArrowTime) > getCooldown()){
            i = 0;
            lastArrowTime = TimeUtils.millis();
        }

        for (Iterator<Arrow> iter = arrows.iterator(); iter.hasNext(); ) {
            Arrow arrow = iter.next();
            arrow.move();
            if(!(arrow.y + 12 < 1080) || !(arrow.y + 12 > 0) || !(arrow.x + 12 < 1920) || !(arrow.x + 12 > 0)) {
                iter.remove();
            }
        }
    }

    private void spawnArrow(Player player) {
        Direction dir;
        if(player.getCurrentDirection() == Direction.TOPRIGHT || player.getCurrentDirection() == Direction.DOWNRIGHT)
            dir = Direction.RIGHT;
        else if(player.getCurrentDirection() == Direction.TOPLEFT || player.getCurrentDirection() == Direction.DOWNLEFT)
            dir = Direction.LEFT;
        else
            dir = player.getCurrentDirection();

        Arrow arrow = new Arrow(dir, getProjectileSpeed(), getPierce());
        arrow.setX(player.getSpawnPoint().getX());
        arrow.setY(player.getSpawnPoint().getY());
        arrow.setWidth(12);
        arrow.setHeight(12);
        arrows.add(arrow);
    }



    public Array<Arrow> getArrows() {
        return arrows;
    }
}
