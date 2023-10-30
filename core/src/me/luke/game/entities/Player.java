package me.luke.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import me.luke.game.Dungeon;
import me.luke.game.enums.Direction;
import me.luke.game.manager.GameManager;
import me.luke.game.screens.DungeonGameScreen;
import me.luke.game.screens.DungeonLevelUpScreen;
import me.luke.game.screens.DungeonPauseScreen;
import me.luke.game.weapons.Weapon;
import me.luke.game.weapons.ranged.bowAndArrow.Bow;

public class Player extends Rectangle {
    private Direction currentDirection;
    private Direction previousDirection;

    private Circle pickupRange;
    private Rectangle spawnPoint;

    private int speed;
    private float hp;
    private float maxHP;
    private float healing;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int radius;

    private Array<Weapon> weapons;
    private Bow bow;

    public Player(float hp, float maxHP, float healing, int startLevel) {
        this.currentDirection = Direction.RIGHT;
        this.previousDirection = Direction.RIGHT;
        this.speed = 350;
        this.hp = hp;
        this.maxHP = maxHP;
        this.healing = healing;
        this.level = startLevel;
        this.xp = 0;
        this.xpToNextLevel = 100;
        this.radius = 100;

        pickupRange = new Circle(getX(), getY(), getRadius());
        spawnPoint = new Rectangle();
        spawnPoint.setWidth(1);
        spawnPoint.setHeight(1);
        spawnPoint.setX(getX() + getWidth());
        spawnPoint.setY(getY() + getHeight() / 2);

        weapons = new Array<>();
        bow = new Bow();
        weapons.add(bow);
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void levelUp(final Dungeon game, DungeonGameScreen gameScreen, GameManager gameManager) {
        setXp(getXp() - getXpToNextLevel());
        setLevel(getLevel() + 1);
        setXpToNextLevel(getXpToNextLevel() + 2);

        if(getHp() < getMaxHP())
            setHp(getHp() + 4);
        else if(getHp() > getMaxHP() - 4)
            setHp(getMaxHP());

        game.setScreen(new DungeonLevelUpScreen(game, gameScreen, gameManager));
    }

    // TODO Fix Movement when using arrow keys and WASD at the same time
    public void playerMovement() {
        pickupRange.setX(getX() + getWidth() / 2);
        pickupRange.setY(getY() + getHeight() / 2);

        if((Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) ||
                (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        ) {
            setPreviousDirection(Direction.RIGHT);
            setCurrentDirection(Direction.TOPRIGHT);
            setX(getX() + getSpeed() * Gdx.graphics.getDeltaTime());
            setY(getY() + getSpeed() * Gdx.graphics.getDeltaTime());
        } else if((Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) ||
                (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
        ) {
            setPreviousDirection(Direction.LEFT);
            setCurrentDirection(Direction.TOPLEFT);
            setX(getX() - getSpeed() * Gdx.graphics.getDeltaTime());
            setY(getY() + getSpeed() * Gdx.graphics.getDeltaTime());
        } else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D) ||
                (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        ) {
            setPreviousDirection(Direction.RIGHT);
            setCurrentDirection(Direction.DOWNRIGHT);
            setX(getX() + getSpeed() * Gdx.graphics.getDeltaTime());
            setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
        } else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A) ||
                (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
        ) {
            setPreviousDirection(Direction.LEFT);
            setCurrentDirection(Direction.DOWNLEFT);
            setX(getX() - getSpeed() * Gdx.graphics.getDeltaTime());
            setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                setPreviousDirection(getCurrentDirection());
                setCurrentDirection(Direction.LEFT);
                setX(getX() - getSpeed() * Gdx.graphics.getDeltaTime());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                setPreviousDirection(getCurrentDirection());
                setCurrentDirection(Direction.RIGHT);
                setX(getX() + getSpeed() * Gdx.graphics.getDeltaTime());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                setCurrentDirection(Direction.UP);
                setY(getY() + getSpeed() * Gdx.graphics.getDeltaTime());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                setCurrentDirection(Direction.DOWN);
                setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
            }
        }

        if(getX() < 0)
            setX(0);
        if(getX() > 1920 - getWidth())
            setX(1920 - getWidth());
        if(getY() < 0)
            setY(0);
        if(getY() > 1080 - getHeight())
            setY(1080 - getHeight());
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(Direction previousDirection) {
        this.previousDirection = previousDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getHealing() {
        return healing;
    }

    public void setHealing(float healing) {
        this.healing = healing;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getPickupRange() {
        return pickupRange;
    }

    public Rectangle getSpawnPoint() {
        return spawnPoint;
    }

    public Array<Weapon> getWeapons() {
        return weapons;
    }

    public Bow getBow() {
        return bow;
    }
}
