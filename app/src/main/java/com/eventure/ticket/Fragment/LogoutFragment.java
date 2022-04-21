package com.eventure.ticket.Fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.eventure.ticket.R;
import com.eventure.ticket.activities.GlobalState;
import com.eventure.ticket.activities.Login;
import com.eventure.ticket.activities.Splash;

public class LogoutFragment extends BaseFragment {

    final int SPLASH_DELAY = 2000;
    CardView cvLogout;
    private LogoutFragment logoutFragment;

    public LogoutFragment getInstance() {
        if (logoutFragment == null) {
            logoutFragment = new LogoutFragment();
        }
        return logoutFragment;
    }


    @Override
    public int getLayoutID() {
        return R.layout.layout_logout_fragment;
    }

    @Override
    public void initViews(View view) {
        cvLogout = view.findViewById(R.id.cv_logout);
    }

    @Override
    public void initListeners() {
        cvLogout.setOnClickListener(v -> {

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        splashScreenTimer();
    }

    private void splashScreenTimer() {
        functions.showProgressLoader(dialog);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            GlobalState.getInstance().clear();
            functions.dismissProgressLoader(dialog);
            startActivity(new Intent(context, Login.class));
            activity.finish();
        }, SPLASH_DELAY);
    }
}
