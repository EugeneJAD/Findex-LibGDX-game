package com.eugene.findex;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.eugene.findex.screens.MainScreen;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.ScreenManager;

public class KoreanFindexGame extends Game {

    private GameActivityRequestHandler mRequestHandler;

	public KoreanFindexGame(GameActivityRequestHandler gameActivityRequestHandler){
		mRequestHandler = gameActivityRequestHandler;
	}

	@Override
	public void create() {

		AssetManager am = new AssetManager();
		Assets.instance.init(am);

        //Initialize handler to control showing aps
		ScreenManager.getInstance().setHandler(mRequestHandler);

		setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
		ScreenManager.getInstance().dispose();
	}

	public void setIsInterstitialAdReady(boolean isInterstitialAdReady) {
		ScreenManager.getInstance().setInterstitialAdReady(isInterstitialAdReady);
	}

}
