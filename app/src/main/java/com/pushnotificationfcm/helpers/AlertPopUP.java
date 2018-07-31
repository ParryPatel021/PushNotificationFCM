package com.pushnotificationfcm.helpers;

import android.app.Dialog;


public class AlertPopUP {


    private static Dialog dlg;


    /****
     * Custom alerts
     ***/


//    public static void showAlertCustom(final Context context,
//                                       String title, String message,
//                                       String positiveBtnName, final String negativeBtnName,
//                                       boolean cancelOutSideTouch, View.OnClickListener onClickListener) {
//
//        dismissDialog();
//
//        dlg = new Dialog(context, R.style.InvitationDialog);
//        dlg.setContentView(R.layout.alert_dialog_custom);
//        dlg.setCanceledOnTouchOutside(cancelOutSideTouch);
//
//        Rect displayRectangle = new Rect();
//        dlg.getWindow().getDecorView()
//                .getWindowVisibleDisplayFrame(displayRectangle);
//        dlg.getWindow().setLayout((int) (displayRectangle.width() * 0.8f),
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        Typeface fontRegular = ConstantMethod.setCustomFont(context,
//                ConstantMethod.FontMontserratRegular);
//        Typeface fontLight = ConstantMethod.setCustomFont(context,
//                ConstantMethod.FontMontserratLight);
//
//        Button btnPositive = (Button) dlg.findViewById(R.id.btnPositive);
//        Button btnNegative = (Button) dlg.findViewById(R.id.btnNegative);
//
//        TextView textTitle = (TextView) dlg.findViewById(R.id.textTitle);
//        TextView textSubMessage = (TextView) dlg.findViewById(R.id.textSubMessage);
//        btnPositive.setTypeface(fontRegular);
//        btnNegative.setTypeface(fontRegular);
//        textTitle.setTypeface(fontRegular);
//        textSubMessage.setTypeface(fontLight);
//
//        RelativeLayout layoutMain = (RelativeLayout) dlg.findViewById(R.id.layoutMain);
//
//        btnPositive.setText(positiveBtnName);
//        if (negativeBtnName != null)
//            btnNegative.setText(negativeBtnName);
//        else
//            btnNegative.setVisibility(View.GONE);
//
//        if (title != null && title.length() > 0)
//            textTitle.setText(title);
//        else
//            textTitle.setText(context.getString(R.string.app_name));
//
//        if (message != null)
//            textSubMessage.setText(message);
//        else
//            textSubMessage.setVisibility(View.GONE);
//
//        if (onClickListener == null) {
//            btnPositive.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismissDialog();
//                }
//            });
//        } else {
//            btnPositive.setOnClickListener(onClickListener);
//            btnNegative.setOnClickListener(onClickListener);
//        }
//
//        btnPositive.setTag(DialogInterface.BUTTON_POSITIVE);
//        btnNegative.setTag(DialogInterface.BUTTON_NEGATIVE);
//
//        ConstantMethod.setCornerRadius(layoutMain, Color.WHITE);
//
//        if (!((Activity) context).isFinishing()) {
//            dlg.show();
//        }
//    }
//
//
//    public static void dismissDialog() {
//        if (dlg != null && dlg.isShowing()) {
//            dlg.dismiss();
//            dlg = null;
//        }
//    }


}
