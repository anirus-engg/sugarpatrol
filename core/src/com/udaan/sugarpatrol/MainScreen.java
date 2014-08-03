package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created on 6/19/14.
 */
public class MainScreen implements Screen {
    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    public MainScreen(SugarPatrolGame game) {
        this.game = game;

        camera = new OrthographicCamera(400, 600);
        camera.position.set(400 / 2, 600 / 2, 0);
        touchPoint = new Vector3();

        assets = Assets.getInstance();
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            //touched anywhere except top bar
            if(ScreenFunctions.inBounds(touchPoint, 0, 0, 400, 600 - 50)) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new GameScreen(game));
            }
            //touched menuButton
            else if(ScreenFunctions.inBounds(touchPoint, 148, 600 - assets.getMenuButton().getRegionHeight(), assets.getMenuButton().getRegionWidth(), assets.getMenuButton().getRegionHeight())) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getBackgroundMask(), 0, 0);
        game.batch.draw(assets.getArsenal(), 0, 600 - assets.getArsenal().getHeight());
        game.batch.draw(assets.getMenuButton(), 148, 600 - assets.getMenuButton().getRegionHeight());
        game.batch.draw(assets.getLogo(), 0, 530 - assets.getLogo().getHeight());
        game.batch.draw(assets.getTapText(), 40, 130);
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
    public void pause () {
        Settings.save();
    }

    @Override
    public void resume() {
        update();
        draw();
    }

    @Override
    public void dispose() {

    }
}
