package com.pushnotificationfcm.helpers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pushnotificationfcm.R;


public class AppProgressDialog extends android.support.v4.app.DialogFragment {
    private static final int DELAY_MILLISECOND = 150;
    private static final int SHOW_MIN_MILLISECOND = 100;

    private ProgressBar mProgressBar;
    private boolean startedShowing;
    private long mStartMillisecond;
    private long mStopMillisecond;
    private int progressColor= Color.WHITE;
    private String progressText=null;

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }


    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    // default constructor. Needed so rotation doesn't crash
    public AppProgressDialog() {
        super();
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.app_dialog_progress, null));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressBar = getDialog().findViewById(R.id.progress);
        TextView textMessage = getDialog().findViewById(R.id.textMessage);

        mProgressBar.getIndeterminateDrawable().setColorFilter(
                getProgressColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        textMessage.setTextColor(getProgressColor());

        if(progressText!=null && progressText.length()>0){
            textMessage.setText(progressText);
            textMessage.setVisibility(View.VISIBLE);
        }
        else{
            textMessage.setVisibility(View.GONE);
        }

        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void show(final android.support.v4.app.FragmentManager fm, final String tag) {
        mStartMillisecond = System.currentTimeMillis();
        startedShowing = false;
        mStopMillisecond = Long.MAX_VALUE;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mStopMillisecond > System.currentTimeMillis())
                    showDialogAfterDelay(fm, tag);
            }
        }, DELAY_MILLISECOND);
    }

    private void showDialogAfterDelay(android.support.v4.app.FragmentManager fm, String tag) {
        startedShowing = true;
        super.show(fm, tag);

    }

    public void cancel() {
        mStopMillisecond = System.currentTimeMillis();

        if (startedShowing) {
            if (mProgressBar != null) {
                cancelWhenShowing();
            } else {
                cancelWhenNotShowing();
            }
        }
    }

    private void cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + SHOW_MIN_MILLISECOND) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                    setProgressColor(Color.WHITE);
                    setProgressText(null);

                }
            }, SHOW_MIN_MILLISECOND);
        } else {
            dismissAllowingStateLoss();
        }
    }

    private void cancelWhenNotShowing() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAllowingStateLoss();
                setProgressColor(Color.WHITE);
                setProgressText(null);
            }
        }, DELAY_MILLISECOND);
    }
}
