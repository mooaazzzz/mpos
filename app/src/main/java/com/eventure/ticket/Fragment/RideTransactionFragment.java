package com.eventure.ticket.Fragment;

import static com.eventure.ticket.activities.GlobalState.showToast;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.eventure.ticket.R;
import com.eventure.ticket.models.GetRideDetailsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideTransactionFragment extends BaseFragment {

    private RideTransactionFragment logoutFragment;

    public RideTransactionFragment getInstance() {
        if (logoutFragment == null) {
            logoutFragment = new RideTransactionFragment();
        }
        return logoutFragment;
    }


    @Override
    public int getLayoutID() {
        return R.layout.layout_ride_transaction_fragment;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners() {

    }

}
