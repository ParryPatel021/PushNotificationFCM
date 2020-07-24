package com.pushnotificationfcm.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.pushnotificationfcm.R;
import com.pushnotificationfcm.helpers.AppProgressDialog;
import com.pushnotificationfcm.helpers.ConstantMethod;
import com.pushnotificationfcm.helpers.Constants;
import com.pushnotificationfcm.ws.WebConstants;
import com.pushnotificationfcm.ws.helper.WebserviceResponse;
import com.pushnotificationfcm.ws.helper.WebserviceWrapper;
import com.pushnotificationfcm.ws.models.Attribute;
import com.pushnotificationfcm.ws.models.ResponseHandler;

public class PushNotificationActivity extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {

    private EditText edtUserName, edtEmail, edtPassword;
    private Button btnSignUp;
    private WebserviceWrapper webserviceWrapper;
    private AppProgressDialog appProgressDialog;
    private SharedPreferences preferences;

    private Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        initViews();
    }

    private void initViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        webserviceWrapper = new WebserviceWrapper(getActivity(), this);
        appProgressDialog = new AppProgressDialog();

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:

                if (edtUserName.getText().toString().length() > 0 &&
                        edtEmail.getText().toString().length() > 0 &&
                        edtPassword.getText().toString().length() > 0) {
                    callSignUp();
                }

                break;
        }
    }

    private void callSignUp() {

        showProgress(appProgressDialog);

        Attribute attribute = new Attribute();
        attribute.setUsername(edtUserName.getText().toString());
        attribute.setEmail(edtEmail.getText().toString());
        attribute.setPassword(edtPassword.getText().toString());
        attribute.setDeviceToken(preferences.getString(Constants.FIREBASE_TOKEN, ""));
        Log.i(" Token", "" + preferences.getString(Constants.FIREBASE_TOKEN, ""));

        webserviceWrapper.addOrCallRequest(WebConstants.SERVICE_REGISTRATION, Request.Method.POST, attribute, ResponseHandler.class,
                this.getClass().getName(), null, WebserviceWrapper.WebserviceCallType.QUEUE);

    }

    @Override
    public void onResponse(String url, Object object) {
        hideProgress(appProgressDialog);
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
        hideProgress(appProgressDialog);
        ConstantMethod.showToast(this, errorMessage);
    }

    public void showProgress(AppProgressDialog appProgressDialog) {
        if (appProgressDialog == null) {
            appProgressDialog.setProgressColor(getResources().getColor(R.color.colorAccent));
            appProgressDialog.setProgressText("Please Wait...");
            appProgressDialog.show(getSupportFragmentManager(), "TAG");
        }
    }

    public void hideProgress(AppProgressDialog appProgressDialog) {
        if (!(appProgressDialog == null))
            appProgressDialog.cancel();
    }

}