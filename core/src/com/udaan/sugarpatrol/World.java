package com.udaan.sugarpatrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created on 6/22/14.
 */
public class World {
    public static final int SUGAR_X = 175;
    public static final int SUGAR_Y = 247;
    public static final int NUMBERS_WIDTH = 26;

    private static final int NEW_BLACK_ANT_LOCATIONS = 12;
    private static final int NEW_BIG_BLACK_ANT_LOCATIONS = 10;
    private static final int NEW_RED_ANT_LOCATIONS = 12;

    private static final float FLASH_TIME = 10.0f;
    private static final float FLASH_SHOW_TIME = 0.1f;
    private static final float FREEZE_TIME = 10.0f;
    private static final float SNOW_TIME = 1.0f;
    private static final float NUKE_TIME = 0.5f;
    private static final float NUKE_SHOW_TIME = 0.1f;

    private static float NEW_BLACK_ANT_TIME;
    private static float NEW_BIG_BLACK_ANT_TIME;
    private static float NEW_RED_ANT_TIME;

    private List<BlackAnt> blackAnts = new ArrayList<BlackAnt>();
    private float newBlackAntTick = 0.0f;
    private List<BigBlackAnt> bigBlackAnts = new ArrayList<BigBlackAnt>();
    private float newBigBlackAntTick = 0.0f;
    private List<RedAnt> redAnts = new ArrayList<RedAnt>();
    private float newRedAntTick = 0.0f;

    private WorldState worldState = WorldState.Normal;
    private float flashTick = 0.0f;
    private float freezeTick = 0.0f;
    private float nukeTick = 0.0f;
    private boolean selectedFlash = false;
    private boolean selectedFreeze = false;
    private boolean selectedAntom = false;
    private float snowTick = 0.0f;
    private boolean snowFlag = true;
    private boolean showFlashed = false;
    private boolean showFrozen = false;
    private boolean showNuked = false;

    private Random generator = new Random();
    private int sugarLeft = 3;
    private int[] ignorePosQ = new int[7];
    private int ignorePosQfront = 0;
    private int ignorePosQback = 6;
    private int score;
    private int coins;
    private boolean gameOver = false;
    private boolean isBugSmashed = false;

    protected enum WorldState {
        Normal,
        Flashed,
        Frozen,
        Nuked
    }

    public World() {
        switch(Settings.getSelectedLevel()) {
            case 0:
                NEW_BLACK_ANT_TIME = 1.60f;
                NEW_BIG_BLACK_ANT_TIME = 16.0f;
                NEW_RED_ANT_TIME = 4.0f;
                break;
            case 1:
                NEW_BLACK_ANT_TIME = 0.80f;
                NEW_BIG_BLACK_ANT_TIME = 8.0f;
                NEW_RED_ANT_TIME = 2.0f;
                break;
            case 2:
                NEW_BLACK_ANT_TIME = 0.40f;
                NEW_BIG_BLACK_ANT_TIME = 4.0f;
                NEW_RED_ANT_TIME = 1.0f;
                break;
            default:
                NEW_BLACK_ANT_TIME = 0f;
                NEW_BIG_BLACK_ANT_TIME = 0f;
                NEW_RED_ANT_TIME = 0f;
                break;
        }

        newBlackAnt();
        Arrays.fill(ignorePosQ, NEW_BLACK_ANT_LOCATIONS + 1);
    }

    public void resetWorld() {
        blackAnts = new ArrayList<BlackAnt>();
        redAnts = new ArrayList<RedAnt>();
        bigBlackAnts = new ArrayList<BigBlackAnt>();
        gameOver = false;
        sugarLeft = 3;
    }

    private void newBlackAnt() {
        BlackAnt blackAnt = new BlackAnt(randomPosition(NEW_BLACK_ANT_LOCATIONS), 0.10f);
        blackAnts.add(blackAnt);
    }

    private void newBigBlackAnt() {
        BigBlackAnt bigBlackAnt = new BigBlackAnt(randomPosition(NEW_BIG_BLACK_ANT_LOCATIONS), 0.10f);
        bigBlackAnts.add(bigBlackAnt);
    }

    private void newRedAnt() {
        RedAnt redAnt = new RedAnt(randomPosition(NEW_RED_ANT_LOCATIONS), 0.10f);
        redAnts.add(redAnt);
    }

    private void clearBugs() {
        int len = blackAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            int state = blackAnts.get(j).getState();
            if (state == BlackAnt.CLEAR || state == BlackAnt.GOT_SUGAR) {
                if (state == BlackAnt.GOT_SUGAR) sugarLeft--;
                blackAnts.remove(j);
            }
        }

        len = bigBlackAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            int state = bigBlackAnts.get(j).getState();
            if (state == BigBlackAnt.CLEAR || state == BigBlackAnt.GOT_SUGAR) {
                if (state == BigBlackAnt.GOT_SUGAR) sugarLeft--;
                bigBlackAnts.remove(j);
            }
        }

        len = redAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            int state = redAnts.get(j).getState();
            if (state == RedAnt.CLEAR || state == RedAnt.GOT_SUGAR) {
                if (state == RedAnt.GOT_SUGAR) sugarLeft--;
                redAnts.remove(j);
            }
        }
    }

    private void moveBugs(float deltatime) {
        int len = blackAnts.size();
        BlackAnt blackAnt;
        for (int i = 0; i < len; i++) {
            blackAnt = blackAnts.get(i);
            blackAnt.setTickTime(blackAnt.getTickTime() + deltatime);

            if (blackAnt.getTickTime() >= blackAnt.getTick()) {
                blackAnt.setTickTime(0.0f);
                blackAnt.move();
            }
        }

        len = bigBlackAnts.size();
        BigBlackAnt bigBlackAnt;
        for (int i = 0; i < len; i++) {
            bigBlackAnt = bigBlackAnts.get(i);
            bigBlackAnt.setTickTime((bigBlackAnt.getTickTime() + deltatime));

            if (bigBlackAnt.getTickTime() >= bigBlackAnt.getTick()) {
                bigBlackAnt.setTickTime(0.0f);
                bigBlackAnt.move();

                if (bigBlackAnt.isCoin()) {
                    float showCoinTick = bigBlackAnt.getShowCoinTick();
                    bigBlackAnt.setShowCoinTick(showCoinTick + deltatime);
                    if (showCoinTick > bigBlackAnt.getShowCoinTime())
                        bigBlackAnt.setState(BigBlackAnt.CLEAR);
                }
            }
        }

        len = redAnts.size();
        RedAnt redAnt;
        for (int i = 0; i < len; i++) {
            redAnt = redAnts.get(i);
            redAnt.setTickTime(redAnt.getTickTime() + deltatime);

            if (redAnt.getTickTime() >= redAnt.getTick()) {
                redAnt.setTickTime(0.0f);
                redAnt.move();
            }
        }
    }

    private void killAll() {
        int len = blackAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            blackAnts.get(j).setState(BlackAnt.DEAD);
        }

        len = bigBlackAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            bigBlackAnts.get(j).setState(BigBlackAnt.DEAD);
        }

        len = redAnts.size();
        for (int j = len - 1; j >= 0; j--) {
            redAnts.get(j).setState(RedAnt.DEAD);
        }
    }
    private int randomPosition(int seed) {
        int newPos;
        do {
            newPos = generator.nextInt(seed);
        } while (!isValidPos(newPos));

        return newPos;
    }

    private boolean isValidPos(int position) {
        for (int i = 0; i < ignorePosQ.length; i++) {
            if (ignorePosQ[i] == position) return false;
        }
        ignorePosQ[ignorePosQfront] = position;
        ignorePosQfront = (ignorePosQfront + 1) % ignorePosQ.length;
        ignorePosQback = (ignorePosQback + 1) % ignorePosQ.length;

        return true;
    }

    private void newAnt(float deltaTime) {
        newBlackAntTick += deltaTime;
        newBigBlackAntTick += deltaTime;
        newRedAntTick += deltaTime;

        if (newBlackAntTick > NEW_BLACK_ANT_TIME) {
            newBlackAntTick = 0.0f;
            newBlackAnt();
        }

        if (newBigBlackAntTick > NEW_BIG_BLACK_ANT_TIME) {
            newBigBlackAntTick = 0.0f;
            newBigBlackAnt();
        }

        if (newRedAntTick > NEW_RED_ANT_TIME) {
            newRedAntTick = 0.0f;
            newRedAnt();
        }
    }

    public boolean isShowFlashed() {
        return showFlashed;
    }

    public void setShowFlashed(boolean showFlashed) {
        this.showFlashed = showFlashed;
    }

    public boolean isShowFrozen() {
        return showFrozen;
    }

    public void setShowFrozen(boolean showFrozen) {
        this.showFrozen = showFrozen;
    }

    public boolean isShowNuked() {
        return showNuked;
    }

    public void setShowNuked(boolean showNuked) {
        this.showNuked = showNuked;
    }

    public void setWorldState(WorldState state) {
        worldState = state;
    }

    public boolean checkBugs(int x, int y) {
        isBugSmashed = false;
        int len = blackAnts.size();
        BlackAnt blackAnt;
        for (int i = 0; i < len; i++) {
            blackAnt = blackAnts.get(i);
            if (blackAnt.isSmashed(x, y)) {
                score++;
                isBugSmashed = true;
                blackAnt.nextState();
            }
        }

        len = bigBlackAnts.size();
        BigBlackAnt bigBlackAnt;
        for (int i = 0; i < len; i++) {
            bigBlackAnt = bigBlackAnts.get(i);
            if (bigBlackAnt.isSmashed(x, y)) {
                isBugSmashed = true;
                if (bigBlackAnt.isCoin()) {
                    coins++;
                    bigBlackAnt.setState(BigBlackAnt.CLEAR);
                } else if (bigBlackAnt.isDead())
                    bigBlackAnt.setState(BigBlackAnt.COIN);
                else {
                    bigBlackAnt.nextState();
                }
            }
        }

        len = redAnts.size();
        RedAnt redAnt;
        for (int i = 0; i < len; i++) {
            redAnt = redAnts.get(i);
            if (redAnt.isSmashed(x, y)) {
                score++;
                isBugSmashed = true;
                redAnt.nextState();
            }
        }
        return isBugSmashed;
    }

    public void update(float deltaTime) {
        if (sugarLeft <= 0) {
            gameOver = true;
            return;
        }

        clearBugs();

        if(worldState == WorldState.Normal) {
            newAnt(deltaTime);

            synchronized (this) {
                moveBugs(deltaTime);
            }

            showFlashed = false;
            showFrozen = false;
            showNuked = false;
        }
        else if(worldState == WorldState.Flashed) {
            flashTick += deltaTime;
            newAnt(deltaTime);
            moveBugs(deltaTime / 2);

            if(flashTick >= FLASH_SHOW_TIME)
                showFlashed = false;
            else
                showFlashed = true;

            if(flashTick >= FLASH_TIME) {
                flashTick = 0.0f;
                worldState = WorldState.Normal;
            }
        }
        else if(worldState == WorldState.Frozen) {
            freezeTick += deltaTime;
            snowTick += deltaTime;

            if(snowTick >= SNOW_TIME) {
                snowTick = 0.0f;
                snowFlag = !snowFlag;
            }

            if(freezeTick >= FREEZE_TIME) {
                freezeTick = 0.0f;
                worldState = WorldState.Normal;
            }
            showFrozen = true;
        }
        else if(worldState == WorldState.Nuked) {
            nukeTick += deltaTime;
            killAll();

            if(nukeTick >= NUKE_SHOW_TIME)
                showNuked = false;
            else
                showNuked = true;

            if(nukeTick >= NUKE_TIME) {
                nukeTick = 0.0f;
                worldState = WorldState.Flashed;
            }
        }
    }

    public List<BlackAnt> getBlackAnts() {
        return blackAnts;
    }

    public List<BigBlackAnt> getBigBlackAnts() {
        return bigBlackAnts;
    }

    public List<RedAnt> getRedAnts() {
        return redAnts;
    }

    public int getSugarLeft() {
        return sugarLeft;
    }

    public void setSugarLeft(int sugarLeft) {
        this.sugarLeft = sugarLeft;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isBugSmashed() {
        return isBugSmashed;
    }

    public void setBugSmashed(boolean isBugSmashed) {
        this.isBugSmashed = isBugSmashed;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setSelectedFlash(boolean selectedFlash) {
        this.selectedFlash = selectedFlash;
    }

    public void setSelectedFreeze(boolean selectedFreeze) {
        this.selectedFreeze = selectedFreeze;
    }

    public void setSelectedAntom(boolean selectedAntom) {
        this.selectedAntom = selectedAntom;
    }

    public boolean isSelectedAntom() {
        return selectedAntom;
    }

    public boolean isSelectedFreeze() {
        return selectedFreeze;
    }

    public boolean isSelectedFlash() {
        return selectedFlash;
    }

    public boolean isSnowFlag() {
        return snowFlag;
    }

    public void setSnowFlag(boolean snowFlag) {
        this.snowFlag = snowFlag;
    }
}
