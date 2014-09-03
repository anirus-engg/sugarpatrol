package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

/**
 * Created on 6/21/14.
 */
public class GameScreen implements Screen {

    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;
    private GameState gameState = GameState.Running;
    private World world;
    private int score = 0;
    private int coins = 0;
    private int heartRequired = 1;

    enum GameState {
        Running,
        Paused,
        GameOver
    }

    public GameScreen(SugarPatrolGame game) {
        this.game = game;

        camera = new OrthographicCamera(400, 600);
        camera.position.set(400 / 2, 600 / 2, 0);
        touchPoint = new Vector3();

        assets = Assets.getInstance();

        world = new World();

        assets.playMusic(assets.getBackgroundMusic(), 1, true);
    }

    private void saveScores() {
        Settings.setGoldCoins(Settings.getGoldCoins() + coins);
        Settings.addScore(score);
        Settings.save();
    }

    private void updateRunning(float deltaTime) {

        world.update(deltaTime);

        if (score != world.getScore()) score = world.getScore();

        if (coins != world.getCoins()) coins = world.getCoins();

        if (world.isGameOver()) {
            gameState = GameState.GameOver;
            return;
        }

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            world.checkBugs((int)touchPoint.x, (int)touchPoint.y);
            if (Settings.getFlashCount() > 0 && Screens.inBounds(touchPoint, 133, 555, 38, 38))
                world.setSelectedFlash(!world.isSelectedFlash());
            else if(Settings.getFreezeCount() > 0 && Screens.inBounds(touchPoint, 181, 555, 38, 38))
                world.setSelectedFreeze(!world.isSelectedFreeze());
            else if(Settings.getAntomCount() > 0 && Screens.inBounds(touchPoint, 229, 555, 38, 38))
                world.setSelectedAntom(!world.isSelectedAntom());
            else if(world.isSelectedFlash()) {
                Settings.setFlashCount(Settings.getFlashCount() - 1);
                world.setWorldState(World.WorldState.Flashed);
                world.setSelectedFlash(false);
            }
            else if(world.isSelectedFreeze()) {
                Settings.setFreezeCount(Settings.getFreezeCount() - 1);
                world.setWorldState(World.WorldState.Frozen);
                world.setSelectedFreeze(false);
            }
            else if(world.isSelectedAntom()) {
                Settings.setAntomCount(Settings.getAntomCount() - 1);
                world.setWorldState(World.WorldState.Nuked);
                world.setSelectedAntom(false);
            }

        }
    }

    private void updatePaused() {
        if (assets.getBackgroundMusic().isPlaying())
            assets.getBackgroundMusic().stop();

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (Screens.inBounds(touchPoint, 10, 10, 350, 550)) {
                assets.playSound(assets.getClick(), 1);
                assets.playMusic(assets.getBackgroundMusic(), 1, true);
                gameState = GameState.Running;
            }
        }
    }

    private void updateGameOver() {
        if(assets.getBackgroundMusic().isPlaying())
            assets.getBackgroundMusic().stop();

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (Screens.inBounds(touchPoint, 78, 201, 245, 50)) {
                saveScores();
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new MainScreen(game));
            }
            else if (Screens.inBounds(touchPoint, 78, 271, 245, 50)) {
                assets.playSound(assets.getClick(), 1);
                if(Settings.checkHearts(heartRequired)) {
                    Settings.setHeartCount(Settings.getHeartCount() - heartRequired);
                    heartRequired *= 2;
                    gameState = GameState.Running;
                    world.resetWorld();
                    assets.playSound(assets.getClick(), 1);
                    assets.playMusic(assets.getBackgroundMusic(), 1, true);
                }
            }
        }
    }

    private void draw() {
        List<BlackAnt> blackAnts = world.getBlackAnts();
        List<BigBlackAnt> bigBlackAnts = world.getBigBlackAnts();
        List<RedAnt> redAnts = world.getRedAnts();

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getLines(), 0, 0);

        int len = blackAnts.size();
        for (int i = 0; i < len; i++) {
            BlackAnt blackAnt = blackAnts.get(i);
            game.batch.draw(assets.getBlackAnts(), blackAnt.getX(), blackAnt.getY(),
                    blackAnt.getState() * BlackAnt.SIZE, blackAnt.getType() * BlackAnt.SIZE, BlackAnt.SIZE, BlackAnt.SIZE);
        }

        len = bigBlackAnts.size();
        for (int i = 0; i < len; i++) {
            BigBlackAnt bigBlackAnt = bigBlackAnts.get(i);
            if (bigBlackAnt.isCoin()) {
                game.batch.draw(assets.getBigBlackAnts(), bigBlackAnt.getX(), bigBlackAnt.getY(),
                        BigBlackAnt.COIN * BigBlackAnt.SIZE, bigBlackAnt.getType() * BigBlackAnt.SIZE, BigBlackAnt.SIZE, BigBlackAnt.SIZE);
            } else
                game.batch.draw(assets.getBigBlackAnts(), bigBlackAnt.getX(), bigBlackAnt.getY(),
                        bigBlackAnt.getState() * BigBlackAnt.SIZE, bigBlackAnt.getType() * BigBlackAnt.SIZE, BigBlackAnt.SIZE, BigBlackAnt.SIZE);
        }

        len = redAnts.size();
        for (int i = 0; i < len; i++) {
            RedAnt redAnt = redAnts.get(i);
            game.batch.draw(assets.getRedAnts(), redAnt.getX(), redAnt.getY(),
                    redAnt.getState() * RedAnt.SIZE, redAnt.getType() * RedAnt.SIZE, RedAnt.SIZE, RedAnt.SIZE);
        }

        switch (world.getSugarLeft()) {
            case 1:
                game.batch.draw(assets.getSugar1(), World.SUGAR_X, World.SUGAR_Y);
                break;
            case 2:
                game.batch.draw(assets.getSugar2(), World.SUGAR_X, World.SUGAR_Y);
                break;
            case 3:
                game.batch.draw(assets.getSugar3(), World.SUGAR_X, World.SUGAR_Y);
                break;
        }

        if(world.isShowFlashed())
            game.batch.draw(assets.getFlashed(), 0, 0);
        if(world.isShowFrozen())
            game.batch.draw(assets.getSnow(world.isSnowFlag()), 0, 0);
        if(world.isShowNuked())
            game.batch.draw(assets.getNuked(), 0, 0);

        game.batch.draw(assets.getArsenal(), 0, 600 - assets.getArsenal().getHeight());
        game.batch.draw(assets.getGoldCoin(), 5, 555);

        if(Settings.getFlashCount() > 0 && !world.isSelectedFreeze() && !world.isSelectedAntom())
            game.batch.draw(assets.getFlash(), 133, 555);
        else
            game.batch.draw(assets.getDisabledFlash(), 133, 555);

        if(Settings.getFreezeCount() > 0 && !world.isSelectedFlash() && !world.isSelectedAntom())
            game.batch.draw(assets.getFreeze(), 181, 555);
        else
            game.batch.draw(assets.getDisabledFreeze(), 181, 555);

        if(Settings.getAntomCount() > 0 && !world.isSelectedFreeze() && !world.isSelectedFlash())
            game.batch.draw(assets.getAntom(), 229, 555);
        else
            game.batch.draw(assets.getDisabledAntom(), 229, 555);

        game.batch.draw(assets.getNumbersSmall(), 152, 550, Settings.getFlashCount() * 19, 0, 19, 19);
        game.batch.draw(assets.getNumbersSmall(), 200, 550, Settings.getFreezeCount() * 19, 0, 19, 19);
        game.batch.draw(assets.getNumbersSmall(), 248, 550, Settings.getAntomCount() * 19, 0, 19, 19);

        Screens.drawNumbers(game, "" + score, 390 - ("" + score).length() * World.NUMBERS_WIDTH, 555, assets.getNumbers());
        Screens.drawNumbers(game, "" + coins, 47, 555, assets.getNumbers());

        if(gameState == GameState.GameOver) {
            game.batch.draw(assets.getGameover(), 0, 173);
            Screens.drawNumbers(game, "" + heartRequired, 256, 282, assets.getNumbersMedium());
        }

        if(gameState == GameState.Paused) {
            game.batch.draw(assets.getPause(), 0, 173);
        }
        game.batch.end();
    }

    private void update(float deltaTime) {
        if(gameState == GameState.Running)
            updateRunning(deltaTime);
        else if(gameState == GameState.Paused)
            updatePaused();
        else if(gameState == GameState.GameOver)
            updateGameOver();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game.myRequestHandler.showAds(true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        Settings.save();
        gameState = GameState.Paused;
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
