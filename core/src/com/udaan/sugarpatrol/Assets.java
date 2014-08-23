package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created on 6/19/14.
 */
public class Assets {
    private static Assets instance;

    private static Texture arsenal;
    private static Texture backgroundWood1;
    private static Texture backgroundWood2;
    private static Texture backgroundConcrete;
    private static Texture backgroundMarble;
    private static Texture backgroundSteel;
    private static Texture backgroundBrick;
    private static Texture backgroundMask;
    private static Texture logo;
    private static TextureRegion backButton;
    private static TextureRegion menuButton;
    private static TextureRegion playButton;
    private static Texture tapText;
    private static TextureRegion sugar1;
    private static TextureRegion sugar2;
    private static TextureRegion sugar3;
    private static Texture menu;
    private static Texture menuUpgradesBackground;
    private static Texture menuUpgradesPowerup;
    private static Texture menuSettings;
    private static Texture lines;
    private static Texture blackAnts;
    private static Texture bigBlackAnts;
    private static Texture redAnts;
    private static Texture numbers;
    private static Texture numbersMedium;
    private static Texture numbersSmall;
    private static TextureRegion goldCoin;
    private static TextureRegion flash;
    private static TextureRegion freeze;
    private static TextureRegion antom;
    private static TextureRegion disabledFlash;
    private static TextureRegion disabledFreeze;
    private static TextureRegion disabledAntom;
    private static TextureRegion snow1;
    private static TextureRegion snow2;
    private static Texture pause;
    private static Texture gameover;
    private static Texture selection;
    private static Texture confirm;
    private static Texture insufficient;
    private static TextureRegion checked;
    private static TextureRegion unchecked;
    private static TextureRegion selected;
    private static TextureRegion unselected;

    private Music backgroundMusic;
    private Sound click;

    private Assets() {
    }

    public static Assets getInstance() {
        if (instance == null)
            instance = new Assets();
        return instance;
    }

    private static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public void load() {
        arsenal = loadTexture("images/arsenal.png");
        backgroundWood1 = loadTexture("images/background_wood_1.png");
        backgroundWood2 = loadTexture("images/background_wood_2.png");
        backgroundConcrete = loadTexture("images/background_concrete.png");
        backgroundMarble = loadTexture("images/background_marble.png");
        backgroundSteel = loadTexture("images/background_steel.png");
        backgroundBrick = loadTexture("images/background_brick.png");
        backgroundMask = loadTexture("images/background_mask.png");
        logo = loadTexture("images/logo.png");
        Texture buttons = loadTexture("images/buttons.png");
        menuButton = new TextureRegion(buttons, 0, 0, 104, 48);
        backButton = new TextureRegion(buttons, 104, 0, 104, 48);
        playButton = new TextureRegion(buttons, 208, 0, 104, 48);
        tapText = loadTexture("images/tap_text.png");
        Texture sugar = loadTexture("images/sugar.png");
        sugar3 = new TextureRegion(sugar, 0, 0, 49, 106);
        sugar2 = new TextureRegion(sugar, 49, 0, 49, 106);
        sugar1 = new TextureRegion(sugar, 98, 0, 49, 106);
        menu = loadTexture("images/main_menu.png");
//        menuStore = loadTexture("images/menu_store.png");
        menuUpgradesBackground = loadTexture("images/menu_upgrades_background.png");
        menuUpgradesPowerup = loadTexture("images/menu_upgrades_powerup.png");
        menuSettings = loadTexture("images/menu_settings.png");
        lines = loadTexture("images/lines.png");
        blackAnts = loadTexture("images/black_ants.png");
        bigBlackAnts = loadTexture("images/big_black_ants.png");
        redAnts = loadTexture("images/red_ants.png");
        numbers = loadTexture("images/numbers.png");
        numbersMedium = loadTexture("images/numbers_medium.png");
        numbersSmall = loadTexture("images/numbers_small.png");
        Texture items = loadTexture("images/items.png");
        goldCoin = new TextureRegion(items, 0, 0, 38, 38);
        flash = new TextureRegion(items, 38, 0, 38, 38);
        freeze = new TextureRegion(items, 76, 0, 38, 38);
        antom = new TextureRegion(items, 114, 0, 38, 38);
        disabledFlash = new TextureRegion(items, 152, 0, 38, 38);
        disabledFreeze = new TextureRegion(items, 190, 0, 38, 38);
        disabledAntom = new TextureRegion(items, 228, 0, 38, 38);
        Texture snow = loadTexture("images/snow.png");
        snow1 = new TextureRegion(snow, 0, 0, 400, 600);
        snow2 = new TextureRegion(snow, 400, 0, 400, 600);
        pause = loadTexture("images/pause.png");
        gameover = loadTexture("images/gameover.png");
        selection = loadTexture("images/selection.png");
        confirm = loadTexture("images/confirm.png");
        insufficient = loadTexture("images/insufficient.png");
        Texture checkbox = loadTexture("images/checkbox.png");
        unchecked = new TextureRegion(checkbox, 0, 0, 27, 27);
        checked = new TextureRegion(checkbox, 27, 0, 27, 27);
        Texture option = loadTexture("images/option.png");
        unselected = new TextureRegion(option, 0, 0, 27, 27);
        selected = new TextureRegion(option, 27, 0, 27, 27);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/march.wav"));

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
    }

    public void playSound(Sound sound, float volume) {
        if(Settings.isSoundEnabled()) sound.play(volume);
    }

    public void playMusic(Music music, float volume, boolean looping) {
        if(Settings.isSoundEnabled()) {
            music.setLooping(looping);
            music.setVolume(volume);
            music.play();
        }
    }

    public Texture getArsenal() {
        return arsenal;
    }

    public Texture getLogo() {
        return logo;
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public Sound getClick() {
        return click;
    }

    public Texture getBackground() {
        switch(Settings.getSelectedBackground()) {
            case Settings.PATIO:
                return backgroundWood1;
            case Settings.BRICK:
                return backgroundBrick;
            case Settings.WOOD:
                return backgroundWood2;
            case Settings.STEEL:
                return backgroundSteel;
            case Settings.MARBLE:
                return backgroundMarble;
            case Settings.CONCRETE:
                return backgroundConcrete;
            default:
                return null;
        }
    }

    public TextureRegion getBackButton() {
        return backButton;
    }

    public TextureRegion getMenuButton() {
        return menuButton;
    }

    public TextureRegion getPlayButton() {
        return playButton;
    }

    public Texture getTapText() {
        return tapText;
    }

    public Texture getBackgroundMask() {
        return backgroundMask;
    }

    public TextureRegion getSugar1() {
        return sugar1;
    }

    public TextureRegion getSugar2() {
        return sugar2;
    }

    public TextureRegion getSugar3() {
        return sugar3;
    }

    public Texture getMenu() {
        return menu;
    }

    public Texture getLines() {
        return lines;
    }

    public Texture getBlackAnts() {
        return blackAnts;
    }

    public Texture getBigBlackAnts() {
        return bigBlackAnts;
    }

    public Texture getRedAnts() {
        return redAnts;
    }

    public Texture getNumbers() {
        return numbers;
    }

    public Texture getNumbersMedium() {
        return numbersMedium;
    }

    public Texture getNumbersSmall() {
        return numbersSmall;
    }

    public TextureRegion getGoldCoin() {
        return goldCoin;
    }

    public TextureRegion getFlash() {
        return flash;
    }

    public TextureRegion getFreeze() {
        return freeze;
    }

    public TextureRegion getAntom() {
        return antom;
    }

    public TextureRegion getDisabledFlash() {
        return disabledFlash;
    }

    public TextureRegion getDisabledFreeze() {
        return disabledFreeze;
    }

    public TextureRegion getDisabledAntom() {
        return disabledAntom;
    }

    public TextureRegion getSnow(boolean flag) {
        return flag ? snow1 : snow2;
    }

    public Texture getPause() {
        return pause;
    }

    public Texture getGameover() {
        return gameover;
    }

    public Texture getMenuUpgradesBackground() {
        return menuUpgradesBackground;
    }

    public Texture getMenuUpgradesPowerup() {
        return menuUpgradesPowerup;
    }

    public Texture getMenuSettings() {
        return menuSettings;
    }

    public Texture getSelection() {
        return selection;
    }

    public Texture getConfirm() {
        return confirm;
    }

    public Texture getInsufficient() {
        return insufficient;
    }

    public TextureRegion getCheckbox(boolean flag) {
        return flag ? checked : unchecked;
    }

    public TextureRegion getOption(boolean flag) {
        return flag ? selected : unselected;
    }
}
