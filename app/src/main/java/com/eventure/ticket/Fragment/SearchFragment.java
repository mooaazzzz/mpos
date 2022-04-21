package com.eventure.ticket.Fragment;

import static com.eventure.ticket.activities.GlobalState.showToast;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eventure.ticket.R;
import com.eventure.ticket.activities.GlobalState;
import com.eventure.ticket.adapter.RvSearchAdapter;
import com.eventure.ticket.adapter.RvSlotAdapter;
import com.eventure.ticket.interfaces.CustomClickListener;
import com.eventure.ticket.models.GetRideDetailsModel;
import com.eventure.ticket.models.Search;
import com.eventure.ticket.models.loginModel.LoginData;
import com.eventure.ticket.utils.SunmiPrintHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends BaseFragment implements RvSearchAdapter.PrintSingle {

    private SearchFragment logoutFragment;
    Button btnSearch, btnPrint;
    EditText etSearchedText;
    RecyclerView rvSearchedData;
    RvSearchAdapter searchAdapter;
    List<Search> searchList = new ArrayList<>();


    public SearchFragment getInstance() {
        if (logoutFragment == null) {
            logoutFragment = new SearchFragment();
        }
        return logoutFragment;
    }


    @Override
    public int getLayoutID() {
        return R.layout.layout_search_fragment;
    }

    @Override
    public void initViews(View view) {

        btnSearch = view.findViewById(R.id.btn_search);
        btnPrint = view.findViewById(R.id.btn_print);
        rvSearchedData = view.findViewById(R.id.rv_searched_data);
        rvSearchedData.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        etSearchedText = view.findViewById(R.id.et_search);
    }

    @Override
    public void initListeners() {
//        slotAdapter();
        btnSearch.setOnClickListener(v -> {
            validation();
        });
        btnPrint.setOnClickListener(v -> {
            if (searchList.isEmpty()) {
                showToast("Noting to print");
                return;
            }
            SunmiPrintHelper.getInstance().printSearch(searchList, "Report");
//            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                SunmiPrintHelper.getInstance().printDayClosing(searchList, "Customer copy");
//                functions.dismissProgressLoader(dialog);
////                        clearData();
//            }, 5000);
        });
    }




    private void validation() {
        String strSearch = etSearchedText.getText().toString();

        if (TextUtils.isEmpty(strSearch)) {
            etSearchedText.setError("Field cannot be empty");
            return;
        }
        search(strSearch);
    }

    private void setAdaptor() {
        searchAdapter = new RvSearchAdapter(searchList, context, this);
        rvSearchedData.setAdapter(searchAdapter);
    }

    private void search(String search) {
        functions.showProgressLoader(dialog);
        Call<List<Search>> tokenCall = apiPaths.search(search);
        tokenCall.enqueue(new Callback<List<Search>>() {
            @Override
            public void onResponse(@NonNull Call<List<Search>> call, @NonNull Response<List<Search>> response) {
                functions.dismissProgressLoader(dialog);
                Log.e("response", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    searchList = response.body();
                    setAdaptor();
                } else {
                    showToast(getString(R.string.error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Search>> call, @NonNull Throwable t) {
                functions.dismissProgressLoader(dialog);
                showToast(getString(R.string.network_error));
                Log.e("getLocalizedMessage", t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void printSingle(int position) {
        showToast("Print report");
        SunmiPrintHelper.getInstance().printSearchSingle(searchList, "Report", position);
    }
}
