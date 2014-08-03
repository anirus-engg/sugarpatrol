package com.udaan.sugarpatrol;

/**
 * Created on 6/22/14.
 */
public abstract class Bug {
    protected static float showDeadMultiplier = 6;
    protected float showDeadTick = 0;
    protected int x, y;
    protected int type;
    protected float tick;
    protected float tickTime = 0.0f;
    protected int state = -1;
    protected Assets assets;

    public Bug() {
        assets = Assets.getInstance();
    }

    public abstract void move();

    public boolean isSmashed(int smashX, int smashY) {
        boolean rv = false;
        if(state != getDEAD()) {
            int x = World.SUGAR_X;
            int y = World.SUGAR_Y;
            if (smashX > x && smashX < (x + assets.getSugar1().getRegionWidth()) && smashY > y && smashY < (y + assets.getSugar1().getRegionHeight()))
                rv = false;
            else {
                x = this.x + 5;
                y = this.y + 5;
                rv =  smashX > x && smashX < x + (getSIZE() - 10) && smashY > y && smashY < y + (getSIZE() - 10);
            }
        }
        return rv;
    }

    public void nextState() {
        if (state != getDEAD()) {
            state = ((state + 2) - ((state + 2) % 2));
        }
    }

    public boolean isDead() {
        return state == getDEAD();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }

    public float getTickTime() {
        return tickTime;
    }

    public void setTickTime(float tickTime) {
        this.tickTime = tickTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public abstract int getSIZE();

    public abstract int getDEAD();
}