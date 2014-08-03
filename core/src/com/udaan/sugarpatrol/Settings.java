package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created on 6/19/14.
 */
public class Settings {
    public final static int PATIO = 0;
    public final static int BRICK = 1;
    public final static int WOOD = 2;
    public final static int STEEL = 3;
    public final static int MARBLE = 4;
    public final static int CONCRETE = 5;
    public final static int ANGELIC = 0;
    public final static int HUMANE = 1;
    public final static int DEMONIC = 2;

    private final static String FILE = ".sugarpatrol";

    private static int[] highScoresA = new int[] {550, 250, 125, 60, 30};
    private static int[] highScoresH = new int[] {500, 250, 125, 60, 30};
    private static int[] highScoresD = new int[] {450, 250, 125, 60, 30};
    private static boolean soundEnabled = true;
    private static int goldCoins = 0;
    private static int heartCount = 3;
    private static int flashCount = 2;
    private static int freezeCount = 1;
    private static int antomCount = 1;
    private static int selectedBackground = 0;
    private static int selectedLevel = 1;
    private static boolean patioStatus = true;
    private static boolean brickStatus = false;
    private static boolean woodStatus = false;
    private static boolean steelStatus = false;
    private static boolean marbleStatus = false;
    private static boolean concreteStatus = false;

    public static void load () {
        try {
            FileHandle filehandle = Gdx.files.external(FILE);

            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
            goldCoins = Integer.parseInt(strings[1]);
            heartCount = Integer.parseInt(strings[2]);
            flashCount = Integer.parseInt(strings[3]);
            freezeCount = Integer.parseInt(strings[4]);
            antomCount = Integer.parseInt(strings[5]);
            selectedBackground = Integer.parseInt(strings[6]);
            selectedLevel = Integer.parseInt(strings[7]);
            patioStatus = Boolean.parseBoolean(strings[8]);
            brickStatus = Boolean.parseBoolean(strings[9]);
            woodStatus = Boolean.parseBoolean(strings[10]);
            steelStatus = Boolean.parseBoolean(strings[11]);
            marbleStatus = Boolean.parseBoolean(strings[12]);
            concreteStatus = Boolean.parseBoolean(strings[13]);
            for (int i = 0; i < 5; i++) {
                highScoresA[i] = Integer.parseInt(strings[i+14]);
            }
            for (int i = 0; i < 5; i++) {
                highScoresH[i] = Integer.parseInt(strings[i+19]);
            }
            for (int i = 0; i < 5; i++) {
                highScoresD[i] = Integer.parseInt(strings[i+24]);
            }


        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save () {
        try {
            FileHandle filehandle = Gdx.files.external(FILE);

            filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
            filehandle.writeString(Integer.toString(goldCoins)+"\n", true);
            filehandle.writeString(Integer.toString(heartCount)+"\n", true);
            filehandle.writeString(Integer.toString(flashCount)+"\n", true);
            filehandle.writeString(Integer.toString(freezeCount)+"\n", true);
            filehandle.writeString(Integer.toString(antomCount)+"\n", true);
            filehandle.writeString(Integer.toString(selectedBackground)+"\n", true);
            filehandle.writeString(Integer.toString(selectedLevel)+"\n", true);
            filehandle.writeString(Boolean.toString(patioStatus)+"\n", true);
            filehandle.writeString(Boolean.toString(brickStatus)+"\n", true);
            filehandle.writeString(Boolean.toString(woodStatus)+"\n", true);
            filehandle.writeString(Boolean.toString(steelStatus)+"\n", true);
            filehandle.writeString(Boolean.toString(marbleStatus)+"\n", true);
            filehandle.writeString(Boolean.toString(concreteStatus)+"\n", true);
            for (int i = 0; i < 5; i++) {
                filehandle.writeString(Integer.toString(highScoresA[i])+"\n", true);
            }
            for (int i = 0; i < 5; i++) {
                filehandle.writeString(Integer.toString(highScoresH[i])+"\n", true);
            }
            for (int i = 0; i < 5; i++) {
                filehandle.writeString(Integer.toString(highScoresD[i])+"\n", true);
            }
        } catch (Throwable e) {
        }
    }

    public static void addScore (int score) {
        switch(selectedLevel) {
            case 0:
                highScoresA = addScore(score, highScoresA);
                break;
            case 1:
                highScoresH = addScore(score, highScoresH);
                break;
            case 2:
                highScoresD = addScore(score, highScoresD);
                break;
        }
    }

    private static int[] addScore(int score, int[] highScore) {
        for (int i = 0; i < 5; i++) {
            if (highScore[i] < score) {
                for (int j = 4; j > i; j--)
                    highScore[j] = highScore[j - 1];
                highScore[i] = score;
                break;
            }
        }
        return highScore;
    }

    public static int getPatioStatus() {
        if(patioStatus)
            return getSelectedBackground() == PATIO ? 1 : 0;
        else
            return 2;
    }

    public static int getBrickStatus() {
        if(brickStatus)
            return getSelectedBackground() == BRICK ? 1 : 0;
        else
            return 2;
    }

    public static int getWoodStatus() {
        if(woodStatus)
            return getSelectedBackground() == WOOD ? 1 : 0;
        else
            return 2;
    }

    public static int getSteelStatus() {
        if(steelStatus)
            return getSelectedBackground() == STEEL ? 1 : 0;
        else
            return 2;
    }

    public static int getMarbleStatus() {
        if(marbleStatus)
            return getSelectedBackground() == MARBLE ? 1 : 0;
        return 2;
    }

    public static int getConcreteStatus() {
        if(concreteStatus)
            return getSelectedBackground() == CONCRETE ? 1 : 0;
        else
            return 2;
    }

    public static void setPatioStatus(boolean patioStatus) {
        Settings.patioStatus = patioStatus;
    }

    public static void setBrickStatus(boolean brickStatus) {
        Settings.brickStatus = brickStatus;
    }

    public static void setWoodStatus(boolean woodStatus) {
        Settings.woodStatus = woodStatus;
    }

    public static void setSteelStatus(boolean steelStatus) {
        Settings.steelStatus = steelStatus;
    }

    public static void setMarbleStatus(boolean marbleStatus) {
        Settings.marbleStatus = marbleStatus;
    }

    public static void setConcreteStatus(boolean concreteStatus) {
        Settings.concreteStatus = concreteStatus;
    }

    public static boolean isSoundEnabled() {
        return soundEnabled;
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        Settings.soundEnabled = soundEnabled;
    }

    public static int getGoldCoins() {
        return goldCoins;
    }

    public static void setGoldCoins(int goldCoins) {
        Settings.goldCoins = goldCoins;
    }

    public static boolean checkGoldCoins(int goldCoins) {
        return Settings.goldCoins >= goldCoins;
    }

    public static boolean checkHearts(int hearts) {
        return Settings.heartCount >= hearts;
    }

    public static int[] getHighScores() {
        switch(selectedLevel) {
            case 0:
                return highScoresA;
            case 1:
                return highScoresH;
            case 2:
                return highScoresD;
            default:
                return null;
        }
    }

    public static int getFlashCount() {
        return flashCount;
    }

    public static void setFlashCount(int flashCount) {
        Settings.flashCount = flashCount;
    }

    public static int getFreezeCount() {
        return freezeCount;
    }

    public static void setFreezeCount(int freezeCount) {
        Settings.freezeCount = freezeCount;
    }

    public static int getAntomCount() {
        return antomCount;
    }

    public static void setAntomCount(int antomCount) {
        Settings.antomCount = antomCount;
    }

    public static int getSelectedBackground() {
        return selectedBackground;
    }

    public static void setSelectedBackground(int selectedBackground) {
        Settings.selectedBackground = selectedBackground;
    }

    public static int getHeartCount() {
        return heartCount;
    }

    public static void setHeartCount(int heartCount) {
        Settings.heartCount = heartCount;
    }

    public static int getSelectedLevel() {
        return selectedLevel;
    }

    public static void setSelectedLevel(int selectedLevel) {
        Settings.selectedLevel = selectedLevel;
    }
}
