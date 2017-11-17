package com.eugene.findex.utils;

import com.badlogic.gdx.Screen;
import com.eugene.findex.GameActivityRequestHandler;

import java.util.HashMap;

/**
 * Created by Администратор on 02.10.2017.
 */

public class ScreenManager {

    public static final String TAG = ScreenManager.class.getSimpleName();

    private static ScreenManager instance;
    private HashMap<String,Screen> screens;
    private Constants.AppState appState;
    private GameActivityRequestHandler handler;
    private boolean isInterstitialAdReady;

    private ScreenManager(){
        screens = new HashMap<String, Screen>();
        appState = Constants.AppState.INITIAL;
        isInterstitialAdReady = false;
    }

    public static synchronized ScreenManager getInstance(){
        if(instance==null){
            instance = new ScreenManager();
        }
        return instance;
    }

    public Screen getScreen(String screenName){
        return screens.get(screenName);
    }

    public void addScreen(String screenName, Screen screen){

        if(screens.get(screenName)!=null)
            screens.get(screenName).dispose();

        screens.put(screenName,screen);

        if(screenName.equals(Constants.GAME_FIELD))
            appState = Constants.AppState.PLAYING;

    }

    public void dispose(){

        appState = Constants.AppState.INITIAL;
        if(screens.size()>0) {
            for (String key : screens.keySet()) {
                screens.get(key).dispose();
            }
            screens.clear();
        }
    }

    public Constants.AppState getAppState() {
        return appState;
    }

    public void setAppState(Constants.AppState appState) {
        this.appState = appState;
    }

    public void setHandler(GameActivityRequestHandler handler) {
        this.handler = handler;
    }
    public GameActivityRequestHandler getHandler() {
        return handler;
    }

    public void setInterstitialAdReady(boolean interstitialAdReady) {
        this.isInterstitialAdReady = interstitialAdReady;
    }

    public boolean isInterstitialAdReady() {
        return isInterstitialAdReady;
    }
}
