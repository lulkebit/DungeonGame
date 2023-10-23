package me.luke.game.entities;

import com.badlogic.gdx.math.Rectangle;

public class XpOrb extends Rectangle {
    private int value;

    public XpOrb(float x, float y, int xpValue) {
        this.x = x;
        this.y = y;
        this.value = xpValue;
    }

    public int getValue() {
        return value;
    }
}
