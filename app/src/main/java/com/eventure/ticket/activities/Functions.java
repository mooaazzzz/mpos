package com.eventure.ticket.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.eventure.ticket.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Functions {

    private final String dateFormat = "dd/MM/yyyy";

    private final String dateTimeFormat = "M/dd/yyyy hh:mm:ss a";
    private final String timeFormat = "hh:mm a";
    private int recyclerViewLastItemPosition = -1;
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_FOR_MESSAGE = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public void fullScreenActivity(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void goToClass(Context context, Class aClass) {
        Intent intent = new Intent(context, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void showProgressLoader(Dialog dialog) {
        dialog.setContentView(R.layout.layout_loader);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!dialog.isShowing()) dialog.show();
    }

    public void dismissProgressLoader(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public Date convertStringIntoDate(String strDate, String dateFormat) {
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat(dateFormat);
        Date tempDate = null;
        try {
            tempDate = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    public String convertIntoLocalTime(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FOR_MESSAGE, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }

    public Date convertStringIntoTime(String strDate) {
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat(timeFormat);
        Date tempDate = null;
        try {
            tempDate = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

}
