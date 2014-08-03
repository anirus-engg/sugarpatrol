package com.udaan.sugarpatrol;

/**
 * Created on 6/22/14.
 */
public class RedAnt extends Bug {
    public static final int SIZE = 56;

    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
    public static final int TYPE_5 = 4;
    public static final int TYPE_6 = 5;
    public static final int TYPE_7 = 6;
    public static final int TYPE_8 = 7;
    public static final int TYPE_9 = 8;
    public static final int TYPE_10 = 9;
    public static final int TYPE_11 = 10;
    public static final int TYPE_12 = 11;

    public static final int ALIVE_0 = 0;
    public static final int ALIVE_1 = 1;
    public static final int ALIVE_2 = 2;
    public static final int ALIVE_3 = 3;
    public static final int DEAD = 4;
    public static final int FADE = 5;
    public static final int CLEAR = 6;
    public static final int GOT_SUGAR = 7;

    public RedAnt(int type, float tick) {
        super();
        switch (type) {
            case TYPE_1:
                x = 176;
                y = 66;
                break;
            case TYPE_2:
                x = 176;
                y = 500;
                break;
            case TYPE_3:
                x = 0;
                y = 66;
                break;
            case TYPE_4:
                x = 0;
                y = 490;
                break;
            case TYPE_5:
                x = 350;
                y = 66;
                break;
            case TYPE_6:
                x = 350;
                y = 490;
                break;
            case TYPE_7:
                x = 0;
                y = 150;
                break;
            case TYPE_8:
                x = 0;
                y = 400;
                break;
            case TYPE_9:
                x = 350;
                y = 150;
                break;
            case TYPE_10:
                x = 350;
                y = 400;
                break;
            case TYPE_11:
                x = 0;
                y = 275;
                break;
            case TYPE_12:
                x = 350;
                y = 275;
                break;
            default:
                x = 400;
                y = 0;
                break;
        }
        this.type = type;
        this.tick = tick;
        state = ALIVE_0;
    }

    @Override
    public void move() {
        switch (state) {
            case ALIVE_0:
                state = ALIVE_1;
                break;
            case ALIVE_1:
                state = ALIVE_0;
                break;
            case ALIVE_2:
                state = ALIVE_3;
                break;
            case ALIVE_3:
                state = ALIVE_2;
                break;
            default:
                showDeadTick++;
                if (showDeadTick >= showDeadMultiplier) {
                    showDeadTick = 0;
                    if (state == DEAD) state = FADE;
                    if (state == FADE) state = CLEAR;
                }
                return;
        }

        int upperX = World.SUGAR_X;
        int lowerX = World.SUGAR_X + assets.getSugar1().getRegionWidth() - SIZE;
        int upperY = World.SUGAR_Y;
        int lowerY = World.SUGAR_Y + assets.getSugar1().getRegionHeight() - SIZE;

        switch (type) {
            case TYPE_1:
                if (y < upperY) {
                    y += 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_2:
                if (y > lowerY) {
                    y -= 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_3:
                if (x < lowerX && y < upperY) {
                    x += 2;
                    y += 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_4:
                if (x < lowerX && y > lowerY) {
                    x += 2;
                    y -= 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_5:
                if (x > upperX && y < upperY) {
                    x -= 2;
                    y += 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_6:
                if (x > upperX && y > lowerY) {
                    x -= 2;
                    y -= 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_7:
                if (x < lowerX && y < upperY) {
                    x += 2;
                    y += 1;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_8:
                if (x < lowerX && y > lowerY) {
                    x += 2;
                    y -= 1;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_9:
                if (x > upperX && y < upperY) {
                    x -= 2;
                    y += 1;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_10:
                if (x > upperX && y > lowerY) {
                    x -= 2;
                    y -= 1;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_11:
                if (x < lowerX) {
                    x += 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            case TYPE_12:
                if (x > upperX) {
                    x -= 2;
                } else {
                    state = GOT_SUGAR;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSIZE() {
        return SIZE;
    }

    @Override
    public int getDEAD() {
        return DEAD;
    }
}
