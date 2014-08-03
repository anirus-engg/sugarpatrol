package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created on 7/5/14.
 */
public class SettingsScreen implements Screen {
    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;
    private int[] highScores;

    public SettingsScreen(SugarPatrolGame game) {
        this.game = game;
        camera = new OrthographicCamera(400, 600);
        camera.position.set(400 / 2, 600 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            //touched playButton
            if (ScreenFunctions.inBounds(touchPoint, 148, 600 - assets.getPlayButton().getRegionHeight(), assets.getPlayButton().getRegionWidth(), assets.getPlayButton().getRegionHeight())) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new GameScreen(game));
            }
            //touched backButton
            else if (ScreenFunctions.inBounds(touchPoint, 22, 600 - assets.getBackButton().getRegionHeight(), assets.getBackButton().getRegionWidth(), assets.getBackButton().getRegionHeight())) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new MenuScreen(game));
            }
            //touched SoundCheckBox
            else if(ScreenFunctions.inBounds(touchPoint, 227, 455, 27, 27)) {
                Settings.setSoundEnabled(!Settings.isSoundEnabled());
                assets.playSound(assets.getClick(), 1);
            }
            //touched angelicOption
            else if(ScreenFunctions.inBounds(touchPoint, 227, 370, 20, 20)) {
                assets.playSound(assets.getClick(), 1);
                Settings.setSelectedLevel(Settings.ANGELIC);
            }
            //touched humaneOption
            else if(ScreenFunctions.inBounds(touchPoint, 227, 345, 20, 20)) {
                assets.playSound(assets.getClick(), 1);
                Settings.setSelectedLevel(Settings.HUMANE);
            }
            //touched demonicOption
            else if(ScreenFunctions.inBounds(touchPoint, 227, 320, 20, 20)) {
                assets.playSound(assets.getClick(), 1);
                Settings.setSelectedLevel(Settings.DEMONIC);
            }
        }
        highScores = Settings.getHighScores();
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getMenuSettings(), 0, 0);
        game.batch.draw(assets.getCheckbox(Settings.isSoundEnabled()), 227, 455);
        game.batch.draw(assets.getOption(Settings.getSelectedLevel() == Settings.ANGELIC), 227, 370, 20, 20);
        game.batch.draw(assets.getOption(Settings.getSelectedLevel() == Settings.HUMANE), 227, 345, 20, 20);
        game.batch.draw(assets.getOption(Settings.getSelectedLevel() == Settings.DEMONIC), 227, 320, 20, 20);
        ScreenFunctions.drawNumbers(game, "" + highScores[0], 200, 203, assets.getNumbersMedium());
        ScreenFunctions.drawNumbers(game, "" + highScores[1], 200, 176, assets.getNumbersMedium());
        ScreenFunctions.drawNumbers(game, "" + highScores[2], 200, 150, assets.getNumbersMedium());
        ScreenFunctions.drawNumbers(game, "" + highScores[3], 200, 123, assets.getNumbersMedium());
        ScreenFunctions.drawNumbers(game, "" + highScores[4], 200, 95, assets.getNumbersMedium());
        game.batch.draw(assets.getArsenal(), 0, 600 - assets.getArsenal().getHeight());
        game.batch.draw(assets.getPlayButton(), 148, 600 - assets.getPlayButton().getRegionHeight());
        game.batch.draw(assets.getBackButton(), 22, 600 - assets.getBackButton().getRegionHeight());
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
