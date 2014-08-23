package com.udaan.sugarpatrol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SugarPatrolGame extends Game {
    private OrthographicCamera camera;
    protected SpriteBatch batch;
    protected IActivityRequestHandler myRequestHandler;

    public SugarPatrolGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }


    @Override
	public void create () {
		batch = new SpriteBatch();
		Settings.load();
        setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
