package com.eugene.findex.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.eugene.findex.GameActivityRequestHandler;
import com.eugene.findex.KoreanFindexGame;
import com.eugene.findex.utils.Constants;

public class DesktopLauncher implements GameActivityRequestHandler {

	private static DesktopLauncher application;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Constants.WORLD_WIDTH;
		config.height = (int)Constants.WORLD_HEIGHT;

		if (application == null) {
			application = new DesktopLauncher();
		}

		new LwjglApplication(new KoreanFindexGame(application), config);
	}

	@Override
	public void showInterstitialAds(boolean show) {

	}

	@Override
	public void showBannerAds(boolean show) {

	}
}
