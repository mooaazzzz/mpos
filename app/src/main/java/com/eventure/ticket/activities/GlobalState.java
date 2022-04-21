package com.eventure.ticket.activities;

import android.app.Application;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.eventure.ticket.models.loginModel.LoginModel;
import com.eventure.ticket.utils.SunmiPrintHelper;

import java.util.ArrayList;
import java.util.List;

public class GlobalState extends Application {
    private static GlobalState globalState;

    public static GlobalState getInstance() {
        if (globalState == null) {
            globalState = new GlobalState();
        }
        return globalState;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        globalState = this;
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    public static void showToast(String message) {
        Toast.makeText(globalState, message, Toast.LENGTH_LONG).show();
    }

    public LoginModel dataList = new LoginModel();

    public void clear() {
        dataList = new LoginModel();
    }
}
