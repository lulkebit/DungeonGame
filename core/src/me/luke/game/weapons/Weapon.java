package me.luke.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Weapon extends Rectangle {
    private final String name; // Name of the weapon.
    private int lvl; // Current level of the weapon.
    private int maxLvl; // The highest level the weapon can be upgraded to.
    private float baseDmg; // The damage dealt by a single projectile per hit.
    private float projectileSpeed; // The base projectile speed of the weapon.
    private float effectDuration; // The duration of the weapon's effect.
    private float cooldown; // The time required for the weapon to be used again. (millis)
    private float critMulti; // Damage multiplier to critical hits.
    private float critChance; // Chance to hit a crit.
    private float area; // The base area of the weapon.
    private int amount; // The amount of projectiles fired per use.
    private float projectileInterval; // The time required for an additional projectile to be fired between each Cooldown. (millis)
    private int pierce; // The number of enemies a single projectile can hit before being used up.
    private float knockback; // Determines the strength of the knockback effect on the enemy

    private final Texture texture;

    public Weapon(String name, String texturePath) {
        this.name = name;
        this.texture = new Texture(texturePath);

        setLvl(1);
    }

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(int maxLvl) {
        this.maxLvl = maxLvl;
    }

    public float getBaseDmg() {
        return baseDmg;
    }

    public void setBaseDmg(float baseDmg) {
        this.baseDmg = baseDmg;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public float getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(float effectDuration) {
        this.effectDuration = effectDuration;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getCritMulti() {
        return critMulti;
    }

    public void setCritMulti(float critMulti) {
        this.critMulti = critMulti;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getProjectileInterval() {
        return projectileInterval;
    }

    public void setProjectileInterval(float projectileInterval) {
        this.projectileInterval = projectileInterval;
    }

    public int getPierce() {
        return pierce;
    }

    public void setPierce(int pierce) {
        this.pierce = pierce;
    }

    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }

    public Texture getTexture() {
        return texture;
    }
}
