package com.pushnotificationfcm.base;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.pushnotificationfcm.R;

public class PushWithFCM extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, getString(R.string.ADMOB_TEST_ID));

    }
}
