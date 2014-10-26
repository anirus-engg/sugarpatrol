package com.udaan.sugarpatrol.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.udaan.sugarpatrol.IActivityRequestHandler;
import com.udaan.sugarpatrol.SugarPatrolGame;

public class DesktopLauncher implements IActivityRequestHandler{

    private static DesktopLauncher application;

    public static void main (String[] arg) {
        if(application == null)
            application = new DesktopLauncher();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Sugar Patrol";
        config.width = 400;
        config.height = 600;

        new LwjglApplication(new SugarPatrolGame(application), config);
    }

    @Override
    public void showAds(boolean show) {

    }

    @Override
    public boolean showShop() {
        return false;
    }
}
