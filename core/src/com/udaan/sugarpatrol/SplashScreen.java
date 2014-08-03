package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created on 8/1/14.
 */
public class SplashScreen implements Screen {
    private final static float DISPLAY_TIME = 3.0f;

    private float displayTimeTick = 0.0f;
    private SugarPatrolGame game;
    private Texture splash;

    public SplashScreen(SugarPatrolGame game) {
        this.game = game;
        splash = new Texture(Gdx.files.internal("images/splash.png"));
    }

    private void update(float deltaTime) {
        displayTimeTick += deltaTime;
        if(displayTimeTick >= DISPLAY_TIME) {
            game.setScreen(new MainScreen(game));
        }
    }

    private void draw() {
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
