package com.udaan.sugarpatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by anirus on 10/23/14.
 */
public class ComingSoonScreen implements Screen {
    private SugarPatrolGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    public ComingSoonScreen(SugarPatrolGame game) {
        this.game = game;
        camera = new OrthographicCamera(320, 480);
        camera.position.set(320 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
    }

    public void update() {
        if(Gdx.input.justTouched()) {
            game.setScreen(new MainScreen(game));
        }
    }

    public void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
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
