package com.pushnotificationfcm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pushnotificationfcm.R;
import com.pushnotificationfcm.helpers.AppProgressDialog;
import com.pushnotificationfcm.helpers.ConstantMethod;
import com.pushnotificationfcm.helpers.Constants;
import com.pushnotificationfcm.ws.WebConstants;
import com.pushnotificationfcm.ws.helper.WebserviceResponse;
import com.pushnotificationfcm.ws.helper.WebserviceWrapper;
import com.pushnotificationfcm.ws.models.Attribute;
import com.pushnotificationfcm.ws.models.ResponseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {

//    private EditText edtUserName, edtEmail, edtPassword;
//    private Button btnSignUp;
//    private WebserviceWrapper webserviceWrapper;
//    private AppProgressDialog appProgressDialog;
    private SharedPreferences preferences;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mAdView=findViewById(R.id.adView);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                ConstantMethod.showToast(MainActivity.this,"Ad Closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                ConstantMethod.showToast(MainActivity.this,"Ad Failed To Load");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                ConstantMethod.showToast(MainActivity.this,"Ad Opened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                ConstantMethod.showToast(MainActivity.this,"Ad Loaded");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                ConstantMethod.showToast(MainActivity.this,"Ad Clicked");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

//        btnSignUp.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_interstitial_ad:
                startActivity(new Intent(this,InterstitialAdActivity.class));
                break;
            case R.id.menu_rewarded_video_ad:
                startActivity(new Intent(this,RewardedVideoAdActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
//        edtUserName = findViewById(R.id.edtUserName);
//        edtEmail = findViewById(R.id.edtEmail);
//        edtPassword = findViewById(R.id.edtPassword);
//        btnSignUp = findViewById(R.id.btnSignUp);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        webserviceWrapper = new WebserviceWrapper(getActivity(), this);
//        appProgressDialog = new AppProgressDialog();


    }

    private Context getActivity() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:

//                if (edtUserName.getText().toString().length() > 0 &&
//                        edtEmail.getText().toString().length() > 0 &&
//                        edtPassword.getText().toString().length() > 0) {
//                    callSignUp();
//                }

                break;
        }
    }

    private void callSignUp() {

//        showProgress(appProgressDialog);
//
//        Attribute attribute = new Attribute();
//        attribute.setUsername(edtUserName.getText().toString());
//        attribute.setEmail(edtEmail.getText().toString());
//        attribute.setPassword(edtPassword.getText().toString());
//        attribute.setDeviceToken(preferences.getString(Constants.FIREBASE_TOKEN,""));
//        Log.i(" Token",""+preferences.getString(Constants.FIREBASE_TOKEN,""));
//
//        webserviceWrapper.addOrCallRequest(WebConstants.SERVICE_REGISTRATION, Request.Method.POST, attribute, ResponseHandler.class,
//                this.getClass().getName(), null, WebserviceWrapper.WebserviceCallType.QUEUE);

    }

    @Override
    public void onResponse(String url, Object object) {
//        hideProgress(appProgressDialog);
        switch (url) {
            case WebConstants.SERVICE_REGISTRATION:
                ResponseHandler responseHandler = (ResponseHandler) object;
                switch (responseHandler.getStatus()) {
                    case SUCCESS:

                        ConstantMethod.showToast(this, responseHandler.getMessage());

                        break;
                    case FAILED:

                        ConstantMethod.showToast(this, responseHandler.getMessage());

                        break;
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(String url, Exception error, String errorMessage) {
//        hideProgress(appProgressDialog);
        ConstantMethod.showToast(this, errorMessage);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAdView!=null){
            mAdView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdView!=null){
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
//        webserviceWrapper.cancelRequestQueue(this.getClass().getName());
        if(mAdView != null){
            mAdView.destroy();
        }
        super.onDestroy();
    }

//    public void showProgress(AppProgressDialog appProgressDialog) {
//        if (appProgressDialog == null) {
//            appProgressDialog.setProgressColor(getResources().getColor(R.color.colorAccent));
//            appProgressDialog.setProgressText("Please Wait...");
//            appProgressDialog.show(getSupportFragmentManager(), "TAG");
//        }
//    }
//
//    public void hideProgress(AppProgressDialog appProgressDialog) {
//        if (!(appProgressDialog == null))
//            appProgressDialog.cancel();
//    }

}
