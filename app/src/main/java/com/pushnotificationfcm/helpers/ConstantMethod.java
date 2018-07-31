package com.pushnotificationfcm.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.regex.Pattern;


public class ConstantMethod {

    // All Fonts
    public static final String FontMontserratRegular = "Montserrat-Regular.otf";
    public static final String FontMontserratLight = "Montserrat-Light.otf";


    public static final String PREFS_NAME = "PushNotificationPreference";

    // City Pattern
    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|35[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|35[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|35[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|35[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,6})$");

    public final static Pattern USERNAME_PATTERN = Pattern
            .compile("^[[A-Za-z0-9]+[A-Za-z0-9_]]{3,20}$");


    /* At least one upper case english letter
     At least one digit
     At least one special character
     Minimum 8 in length
     Maximum 50 in length*/
    public final static Pattern PASSWORD_PATTERN = Pattern
            .compile("^[[A-Za-z0-9]+[A-Za-z0-9_]+(?=.*?[#?!/':;,\\\"\\\"@$%^&*-])]{8,50}$");


    // check City
    public static boolean checkPattern(Pattern pattern, String text) {
        return pattern.matcher(text).matches();
    }


    // Set Custom Fonts
    public static Typeface setCustomFont(Context contex, String fontType) {
        return Typeface.createFromAsset(contex.getAssets(),
                fontType);
    }


//    public static int getDeviceHeight(Context context) {
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        int height = metrics.heightPixels;
//        if (context instanceof BaseActivity) {
//            Rect rectgle = new Rect();
//            Window window = ((BaseActivity) context).getWindow();
//            window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
//            int StatusBarHeight = rectgle.top;
//            height = height - StatusBarHeight;
//        }
//        return height;
//    }


    public static int getDeviceWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;

    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        return Math.round(px
                / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static int dpToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int spToPx(Context context, float spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }


    public static String validateString(String value) {
        if (value == null)
            return "";
        else if (value.equalsIgnoreCase("null"))
            return "";
        else if (value.equalsIgnoreCase(""))
            return "";
        else
            return value;
    }

    public static String validateStringWithDefaultVal(String value, String defaultVal) {
        if (value == null)
            return defaultVal;
        else if (value.equalsIgnoreCase("null"))
            return defaultVal;
        else if (value.equalsIgnoreCase(""))
            return defaultVal;
        else
            return value;
    }

    public static String CapitaliseFirstLetter(String value) {
        String str = validateString(value);
        if (!str.equalsIgnoreCase("")) {
            return str.substring(0, 1) + str.substring(1, str.length()).toLowerCase();
        } else {
            return "";
        }
    }

    public static double validateDouble(String value) {
        if (value == null)
            return 0;
        else if (value.equalsIgnoreCase("null"))
            return 0;
        else if (value.equalsIgnoreCase(""))
            return 0;
        else
            return Double.parseDouble(value);
    }

    public static long validateLong(String value) {
        if (value == null)
            return 0;
        else if (value.equalsIgnoreCase("null"))
            return 0;
        else if (value.equalsIgnoreCase(""))
            return 0;
        else
            return Long.parseLong(value);
    }

    public static int validateInteger(String value) {
        if (value == null)
            return 0;
        else if (value.equalsIgnoreCase("null"))
            return 0;
        else if (value.equalsIgnoreCase(""))
            return 0;
        else
            return Integer.parseInt(value);
    }

    public static float validateFloat(String value) {
        if (value == null)
            return 0;
        else if (value.equalsIgnoreCase("null"))
            return 0;
        else if (value.equalsIgnoreCase(""))
            return 0;
        else
            return Float.parseFloat(value);
    }

    public static boolean validateBooelan(String value) {
        return (value != null &&
                !value.equalsIgnoreCase("null") &&
                !value.equalsIgnoreCase("") &&
                Boolean.parseBoolean(value));
    }


    public static String validateNatural(String value) {
        if (value == null)
            return "";
        else if (value.equalsIgnoreCase("null"))
            return "";
        else if (value.equalsIgnoreCase(""))
            return "";
        else {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return decimalFormat.format(Double.parseDouble(value));
        }
    }

    public static String validateHours(long value) {
        return value > 9 ? String.valueOf(value) : "0" + value;
    }

    public static String validateNaturalFromDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }


//    public static void showToast(View view, String text) {
//        Snackbar snackbar;
//        snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
//        View snackBarView = snackbar.getView();
//        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//        snackbar.show();
//    }
//    public static void showToast(View view, String text, int bgColor, int textFontColor) {
//
//        Snackbar snackbar;
//        snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(bgColor);
//        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(textFontColor);
//        snackbar.show();
//    }

    public static void showToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hide keyboard on activity launch
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View cur_focus = activity.getCurrentFocus();
        if (cur_focus != null) {
            inputMethodManager.hideSoftInputFromWindow(cur_focus.getWindowToken(), 0);
        }
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    // Store String to Preference
    static public boolean setPreference(Context c, String value, String key) {
        if (value != null) {
            try {
                SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(key, value);
                return editor.commit();
            } catch (Exception e) {
                return false;

            }
        } else
            return false;
    }

    static public String getPreference(Context c, String key) {
        if (c != null) {
            SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            return settings.getString(key, "");
        }
        return "";
    }


    // Store Boolean to Preference
    static public boolean setBooleanPreference(Context c, Boolean value, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();

    }

    static public Boolean getBooleanPreferenceWithDefaultVal(Context c, String key, boolean defaultVal) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultVal);
    }

    public static void clearWholePreference(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    /**
     * To check for the connectivity.
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void alertOffline(Context context) {
        Toast.makeText(context,"You are Offline",Toast.LENGTH_SHORT).show();
    }

//    public static void setCornerRadius(View view, int resId) {
//        view.setBackgroundResource(R.drawable.bg_common_corner_radius);
//        GradientDrawable drawable = (GradientDrawable) view.getBackground();
//        drawable.setColor(resId);
//    }

//    public static void animateBounceViewOnClick(Context context, View view) {
//        Animation animationBounce = AnimationUtils.loadAnimation(context, R.anim.bounce_button);
//        animationBounce.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(animationBounce);
//
//    }
}

