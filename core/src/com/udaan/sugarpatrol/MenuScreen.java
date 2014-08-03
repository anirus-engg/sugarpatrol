package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created on 6/21/14.
 */
public class MenuScreen implements Screen {

    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    public MenuScreen(SugarPatrolGame game) {
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
            if(ScreenFunctions.inBounds(touchPoint, 148, 600 - assets.getPlayButton().getRegionHeight(), assets.getPlayButton().getRegionWidth(), assets.getPlayButton().getRegionHeight())) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new GameScreen(game));
            }
            //touched backButton
            else if(ScreenFunctions.inBounds(touchPoint, 22, 600 - assets.getBackButton().getRegionHeight(), assets.getBackButton().getRegionWidth(), assets.getBackButton().getRegionHeight())) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new MainScreen(game));
            }

            //touched shopButton
            else if(ScreenFunctions.inBounds(touchPoint, 58, 348, 287, 60)) {
                assets.playSound(assets.getClick(), 1);
                //TODO - shop screen
            }

            //touched upgradesButton
            else if(ScreenFunctions.inBounds(touchPoint, 58, 269, 287, 60)) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new UpgradesPowerupScreen(game));
            }

            //touched SettingsButton
            else if(ScreenFunctions.inBounds(touchPoint, 58, 190, 287, 60)) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new SettingsScreen(game));
            }
        }
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getMenu(), 0, 0);
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
