package me.luke.game.weapons.ranged.bowAndArrow;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import me.luke.game.entities.*;
import me.luke.game.enums.Direction;
import me.luke.game.weapons.Weapon;

import java.util.Iterator;

public class Bow extends Weapon {
    private Array<Arrow> arrows;
    private static long lastArrowTime;

    public Bow(Player player) {
        super("Bow", "enemy.png");
        setMaxLvl(8);
        setBaseDmg(10f);
        setProjectileSpeed(400);
        setCooldown(300);
        setCritMulti(0);
        setCritChance(0);
        setAmount(1);
        setProjectileInterval(80);
        setPierce(1);
        setKnockback(10);
        arrows = new Array<>();
        lastArrowTime = TimeUtils.millis();
    }

    public void action(Player player) {
        spawnArrow(player);

        for (Iterator<Arrow> iter = arrows.iterator(); iter.hasNext(); ) {
            Arrow arrow = iter.next();
            arrow.move();
            if(!(arrow.y + 12 < 1080) || !(arrow.y + 12 > 0) || !(arrow.x + 12 < 1920) || !(arrow.x + 12 > 0)) {
                iter.remove();
            }
        }
    }

    private void spawnArrow(Player player) {
        if(TimeUtils.millis() - lastArrowTime < getCooldown())
            return;

        Direction dir;
        if(player.getCurrentDirection() == Direction.TOPRIGHT || player.getCurrentDirection() == Direction.DOWNRIGHT)
            dir = Direction.RIGHT;
        else if(player.getCurrentDirection() == Direction.TOPLEFT || player.getCurrentDirection() == Direction.DOWNLEFT)
            dir = Direction.LEFT;
        else
            dir = player.getCurrentDirection();

        Arrow arrow = new Arrow(dir, getProjectileSpeed());
        arrow.setX(player.getSpawnPoint().getX());
        arrow.setY(player.getSpawnPoint().getY());
        arrow.setWidth(12);
        arrow.setHeight(12);
        arrows.add(arrow);
        lastArrowTime = TimeUtils.millis();
    }



    public Array<Arrow> getArrows() {
        return arrows;
    }
}
