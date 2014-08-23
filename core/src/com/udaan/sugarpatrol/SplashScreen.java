package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created on 8/1/14.
 */
public class SplashScreen implements Screen {
    private final static float DISPLAY_TIME = 4.0f;

    private float displayTimeTick = 0.0f;
    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Texture splash;

    public SplashScreen(SugarPatrolGame game) {
        this.game = game;
        splash = new Texture(Gdx.files.internal("images/splash.png"));
        camera = new OrthographicCamera(400, 600);
        camera.position.set(400 / 2, 600 / 2, 0);
    }

    private void update(float deltaTime) {
        displayTimeTick += deltaTime;
        if(displayTimeTick >= DISPLAY_TIME) {
            game.setScreen(new MainScreen(game));
        }
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(splash, 0, 0);
        game.batch.end();
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
        game.myRequestHandler.showAds(false);
        Assets.getInstance().load();
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
