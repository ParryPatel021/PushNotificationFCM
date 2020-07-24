package com.pushnotificationfcm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pushnotificationfcm.R;
import com.pushnotificationfcm.helpers.ConstantMethod;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AdView mAdView;
    private Button btnInterstitialAd, btnRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mAdView = findViewById(R.id.adView);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                ConstantMethod.showToast(MainActivity.this, "Ad Closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                ConstantMethod.showToast(MainActivity.this, "Ad Failed To Load");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                ConstantMethod.showToast(MainActivity.this, "Ad Opened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                ConstantMethod.showToast(MainActivity.this, "Ad Loaded");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                ConstantMethod.showToast(MainActivity.this, "Ad Clicked");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        btnInterstitialAd = findViewById(R.id.btnInterstitialAd);
        btnRewardedVideoAd = findViewById(R.id.btnRewardedVideoAd);

        btnInterstitialAd.setOnClickListener(this);
        btnRewardedVideoAd.setOnClickListener(this);
    }

    private Context getActivity() {
        return this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInterstitialAd:
                startActivity(new Intent(this, InterstitialAdActivity.class));
                break;
            case R.id.btnRewardedVideoAd:
                startActivity(new Intent(this, RewardedVideoAdActivity.class));
                break;
        }
    }
}
