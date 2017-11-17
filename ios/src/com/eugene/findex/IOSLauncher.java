package com.eugene.findex;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate implements GameActivityRequestHandler {

    @Override
    protected IOSApplication createApplication() {

        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new KoreanFindexGame(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void showInterstitialAds(boolean show) {

    }

    @Override
    public void showBannerAds(boolean show) {

    }
}