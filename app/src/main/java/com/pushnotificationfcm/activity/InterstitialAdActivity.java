package com.pushnotificationfcm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.pushnotificationfcm.R;
import com.pushnotificationfcm.helpers.ConstantMethod;

public class InterstitialAdActivity extends AppCompatActivity {

    private String TAG = InterstitialAdActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private Button btnSHowAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);

        btnSHowAdd=findViewById(R.id.btnSHowAdd);

        /** InterstitialAd Initialization */
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        /** Button Click event */
        btnSHowAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d(TAG, "The interstitial wasn't loaded yet.");
                }
            }
        });

        /** Events of AdMob */
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                ConstantMethod.showToast(InterstitialAdActivity.this,"Ad Loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                ConstantMethod.showToast(InterstitialAdActivity.this,"Ad Failed To Load");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                ConstantMethod.showToast(InterstitialAdActivity.this,"Ad Opened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                ConstantMethod.showToast(InterstitialAdActivity.this,"Ad Closed");

                // Load an Add after closed to show second time.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });



    }
}
