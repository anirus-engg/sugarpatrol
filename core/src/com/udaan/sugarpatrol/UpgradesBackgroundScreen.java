package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created on 7/5/14.
 */
public class UpgradesBackgroundScreen implements Screen {
    private static final int BRICK_COINS = 50;
    private static final int WOOD_COINS = 75;
    private static final int STEEL_COINS = 100;
    private static final int MARBLE_COINS = 125;
    private static final int CONCRETE_COINS = 150;

    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    private boolean showConfirm = false;
    private boolean showInsufficient = false;
    private boolean patioConfirm = false;
    private boolean brickConfirm = false;
    private boolean woodConfirm = false;
    private boolean steelConfirm = false;
    private boolean marbleConfirm = false;
    private boolean concreteConfirm = false;

    public UpgradesBackgroundScreen(SugarPatrolGame game) {
        this.game = game;
        camera = new OrthographicCamera(400, 600);
        camera.position.set(400 / 2, 600 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(showInsufficient) {
                if(Screens.inBounds(touchPoint, 127, 197, 149, 46)) {
                    assets.playSound(assets.getClick(), 1);
                    showInsufficient = false;
                }
            }
            else if(showConfirm) {
                if(Screens.inBounds(touchPoint, 31, 216, 150, 47)) {
                    assets.playSound(assets.getClick(), 1);
                    if(patioConfirm) {
                        patioConfirm = false;
                        showConfirm = false;
                        Settings.setPatioStatus(true);
                    }
                    else if(brickConfirm) {
                        brickConfirm = false;
                        showConfirm = false;
                        Settings.setGoldCoins(Settings.getGoldCoins() - BRICK_COINS);
                        Settings.setBrickStatus(true);
                    }
                    else if(woodConfirm) {
                        woodConfirm = false;
                        showConfirm = false;
                        Settings.setGoldCoins(Settings.getGoldCoins() - WOOD_COINS);
                        Settings.setWoodStatus(true);
                    }
                    else if(steelConfirm) {
                        steelConfirm = false;
                        showConfirm = false;
                        Settings.setGoldCoins(Settings.getGoldCoins() - STEEL_COINS);
                        Settings.setSteelStatus(true);
                    }
                    else if(marbleConfirm) {
                        marbleConfirm = false;
                        showConfirm = false;
                        Settings.setGoldCoins(Settings.getGoldCoins() - MARBLE_COINS);
                        Settings.setMarbleStatus(true);
                    }
                    else if(concreteConfirm) {
                        concreteConfirm = false;
                        showConfirm = false;
                        Settings.setGoldCoins(Settings.getGoldCoins() - CONCRETE_COINS);
                        Settings.setConcreteStatus(true);
                    }
                }
                else if(Screens.inBounds(touchPoint, 219, 216, 150, 47)) {
                    assets.playSound(assets.getClick(), 1);
                    showConfirm = false;
                    patioConfirm = brickConfirm = woodConfirm = steelConfirm = marbleConfirm = concreteConfirm = false;
                }
            }
            else {
                //touched patioButton
                if (Screens.inBounds(touchPoint, 30, 309, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getPatioStatus() != 2)
                        Settings.setSelectedBackground(Settings.PATIO);
                    else {
                        showConfirm = true;
                        patioConfirm = true;
                    }
                }
                //touched brickButton
                else if (Screens.inBounds(touchPoint, 150, 309, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getBrickStatus() != 2)
                        Settings.setSelectedBackground(Settings.BRICK);
                    else if(Settings.checkGoldCoins(BRICK_COINS)) {
                        showConfirm = true;
                        brickConfirm = true;
                    }
                    else
                        showInsufficient = true;
                }
                //touched woodButton
                else if (Screens.inBounds(touchPoint, 270, 309, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getWoodStatus() != 2)
                        Settings.setSelectedBackground(Settings.WOOD);
                    else if(Settings.checkGoldCoins(WOOD_COINS)) {
                        showConfirm = true;
                        woodConfirm = true;
                    }
                    else
                        showInsufficient = true;
                }
                //touched steelButton
                else if (Screens.inBounds(touchPoint, 30, 116, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getSteelStatus() != 2)
                        Settings.setSelectedBackground(Settings.STEEL);
                    else if(Settings.checkGoldCoins(STEEL_COINS)) {
                        showConfirm = true;
                        steelConfirm = true;
                    }
                    else
                        showInsufficient = true;
                }
                //touched marbleButton
                else if (Screens.inBounds(touchPoint, 150, 116, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getMarbleStatus() != 2)
                        Settings.setSelectedBackground(Settings.MARBLE);
                    else if(Settings.checkGoldCoins(MARBLE_COINS)) {
                        showConfirm = true;
                        marbleConfirm = true;
                    }
                    else
                        showInsufficient = true;
                }
                //touched concreteButton
                else if (Screens.inBounds(touchPoint, 270, 116, 100, 100)) {
                    assets.playSound(assets.getClick(), 1);
                    if (Settings.getConcreteStatus() != 2)
                        Settings.setSelectedBackground(Settings.CONCRETE);
                    else if(Settings.checkGoldCoins(CONCRETE_COINS)) {
                        showConfirm = true;
                        concreteConfirm = true;
                    }
                    else
                        showInsufficient = true;
                }
                //touched playButton
                else if (Screens.inBounds(touchPoint, 148, 600 - assets.getPlayButton().getRegionHeight(), assets.getPlayButton().getRegionWidth(), assets.getPlayButton().getRegionHeight())) {
                    assets.playSound(assets.getClick(), 1);
                    game.setScreen(new GameScreen(game));
                }
                //touched backButton
                else if (Screens.inBounds(touchPoint, 22, 600 - assets.getBackButton().getRegionHeight(), assets.getBackButton().getRegionWidth(), assets.getBackButton().getRegionHeight())) {
                    assets.playSound(assets.getClick(), 1);
                    game.setScreen(new MenuScreen(game));
                }
                //touch back
                else if (Screens.inBounds(touchPoint, 15, 455, 51, 51)) {
                    assets.playSound(assets.getClick(), 1);
                    game.setScreen(new UpgradesPowerupScreen(game));
                }
            }
        }
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getMenuUpgradesBackground(), 0, 0);
        game.batch.draw(assets.getArsenal(), 0, 600 - assets.getArsenal().getHeight());
        game.batch.draw(assets.getPlayButton(), 148, 600 - assets.getPlayButton().getRegionHeight());
        game.batch.draw(assets.getBackButton(), 22, 600 - assets.getBackButton().getRegionHeight());
        game.batch.draw(assets.getGoldCoin(), 348, 555);
        Screens.drawNumbers(game, "" + Settings.getGoldCoins(), 348 - (("" + Settings.getGoldCoins()).length() * World.NUMBERS_WIDTH), 555, assets.getNumbers());

        game.batch.draw(assets.getSelection(), 30, 309, Settings.getPatioStatus() * 100, 0, 100, 100);
        game.batch.draw(assets.getSelection(), 150, 309, Settings.getBrickStatus() * 100, 0, 100, 100);
        game.batch.draw(assets.getSelection(), 270, 309, Settings.getWoodStatus() * 100, 0, 100, 100);
        game.batch.draw(assets.getSelection(), 30, 116, Settings.getSteelStatus() * 100, 0, 100, 100);
        game.batch.draw(assets.getSelection(), 150, 116, Settings.getMarbleStatus() * 100, 0, 100, 100);
        game.batch.draw(assets.getSelection(), 270, 116, Settings.getConcreteStatus() * 100, 0, 100, 100);

        if(Settings.getBrickStatus() == 2) {
            game.batch.draw(assets.getGoldCoin(), 172, 349, 22, 22);
            Screens.drawNumbers(game, "" + BRICK_COINS, 194, 346, assets.getNumbersMedium());
        }

        if(Settings.getWoodStatus() == 2) {
            game.batch.draw(assets.getGoldCoin(), 283, 349, 22, 22);
            Screens.drawNumbers(game, "" + WOOD_COINS, 305, 346, assets.getNumbersMedium());
        }

        if(Settings.getSteelStatus() == 2) {
            game.batch.draw(assets.getGoldCoin(), 42, 156, 22, 22);
            Screens.drawNumbers(game, "" + STEEL_COINS, 64, 153, assets.getNumbersMedium());
        }

        if(Settings.getMarbleStatus() == 2) {
            game.batch.draw(assets.getGoldCoin(), 162, 156, 22, 22);
            Screens.drawNumbers(game, "" + MARBLE_COINS, 184, 153, assets.getNumbersMedium());
        }

        if(Settings.getConcreteStatus() == 2) {
            game.batch.draw(assets.getGoldCoin(), 282, 156, 22, 22);
            Screens.drawNumbers(game, "" + CONCRETE_COINS, 304, 153, assets.getNumbersMedium());
        }

        if(showConfirm)
            game.batch.draw(assets.getConfirm(), 0, 200);
        if(showInsufficient)
            game.batch.draw(assets.getInsufficient(), 25, 178);
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game.myRequestHandler.showAds(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
