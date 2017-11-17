package com.eugene.findex;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements GameActivityRequestHandler {

	private static final String TEST_ADMOB_APP_ID = "ca-app-pub-5753313989863665~1341993418";
	private InterstitialAd mInterstitialAd;
	protected AdView adView;

	private final static int SHOW_INTERSTITIAL_AD = 1001;
	private final static int SHOW_BANNER_AD = 1002;
	private final static int HIDE_BANNER_AD = 1003;

	private KoreanFindexGame game;
	private int backPressedCount;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_INTERSTITIAL_AD:
					if(mInterstitialAd.isLoaded()) {
						mInterstitialAd.show();
					}
					break;
				case SHOW_BANNER_AD:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_BANNER_AD:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		backPressedCount = 0;

		MobileAds.initialize(this, TEST_ADMOB_APP_ID);

		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-5753313989863665/7728450120");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());

		mInterstitialAd.setAdListener(new AdListener() {

			@Override
			public void onAdFailedToLoad(int errorCode) {
				if(game!=null)
					game.setIsInterstitialAdReady(false);
				mInterstitialAd.loadAd(new AdRequest.Builder().build());
			}
			@Override
			public void onAdClosed() {
				if(game!=null)
					game.setIsInterstitialAdReady(false);
				mInterstitialAd.loadAd(new AdRequest.Builder().build());
			}

			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				if(game!=null)
					game.setIsInterstitialAdReady(true);
			}

		});


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		RelativeLayout layout = new RelativeLayout(this);

		game = new KoreanFindexGame(this);
		View gameView = initializeForView(game, config);

		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-5753313989863665/6826431993");

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		layout.addView(gameView);

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		layout.addView(adView, adParams);

		setContentView(layout);

	}

	@Override
	public void showInterstitialAds(boolean show) {
		if(show)
			handler.sendEmptyMessage(SHOW_INTERSTITIAL_AD);
	}

	@Override
	public void showBannerAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_BANNER_AD : HIDE_BANNER_AD);
	}

	@Override
	public void onBackPressed() {

		if (backPressedCount<1) {
			Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
			backPressedCount++;
		} else if(backPressedCount==1)
			super.onBackPressed();
	}
}
