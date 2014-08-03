package com.udaan.sugarpatrol.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.udaan.sugarpatrol.IActivityRequestHandler;
import com.udaan.sugarpatrol.SugarPatrolGame;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler{

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(400, 600);
    }

    @Override
    public ApplicationListener getApplicationListener () {
        return new SugarPatrolGame(this);
    }

    @Override
    public void showAds(boolean show) {

    }
}