package com.eventure.ticket.Fragment;

import static com.eventure.ticket.activities.GlobalState.showToast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eventure.ticket.R;
import com.eventure.ticket.activities.GlobalState;
import com.eventure.ticket.adapter.RvPaymentAdapter;
import com.eventure.ticket.adapter.RvSelectedTicketsAdapter;
import com.eventure.ticket.adapter.RvSlotAdapter;
import com.eventure.ticket.interfaces.CustomClickListener;
import com.eventure.ticket.models.DataModel;
import com.eventure.ticket.models.NumberOnTicketAndTotalAmount;
import com.eventure.ticket.models.Ticketinfo;
import com.eventure.ticket.models.loginModel.Age;
import com.eventure.ticket.models.loginModel.Gender;
import com.eventure.ticket.models.loginModel.LoginData;
import com.eventure.ticket.models.loginModel.Natioanlity;
import com.eventure.ticket.models.loginModel.PaySouce;
import com.eventure.ticket.utils.SunmiPrintHelper;
import com.eventure.ticket.utils.SwipeToDeleteCallback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends BaseFragment implements CustomClickListener, AdapterView.OnItemSelectedListener, RvPaymentAdapter.OnClickListener, RvSelectedTicketsAdapter.NumberOfTickets {

    private MainFragment mainFragment;
    //    NumberPicker number_picker;
    EditText etPhone, etName, etEmail;
    List<String> durationName = new ArrayList<>();
    Spinner spinnerAge, spinnerGender, spinnerNationality;
    AutoCompleteTextView spinnerMiniGolf;
    RecyclerView rvSlots, rvPayment, rvSelectTickets;
    RvSlotAdapter rvSlotAdapter;
    RvPaymentAdapter rvPaymentAdapter;
    LinearLayout ll_ticket_value;
    String activity = "", slot = "", StrPhone = "", StrName = "", StrEmail = "info@bookingqube.com", paymentType = "", strAge = "", strGender = "", strNationality = "";
    int numberOfTicket = 1, totalPaid = 0;
    private int position = 0;
    private int activityPosition = 0;
    List<DataModel> dataResponse = new ArrayList<>();
    Button btnPrint;
    //    ImageView ivAdd, ivMinus;
    List<LoginData> loginData = new ArrayList<>();
    List<String> rideName = new ArrayList<>();
    TextView tvTicketing, tv_swipe_left;
    private int visibility = 1;
    List<NumberOnTicketAndTotalAmount> numberOfRideOnEveryTicket = new ArrayList<>();
    List<LoginData> selectedTickets = new ArrayList<>();
    RvSelectedTicketsAdapter rvSelectedTicketsAdapter;

    LoginData removeItemCheck = new LoginData();
    LoginData selectedLoginData = new LoginData();

    SwipeToDeleteCallback swipeToDeleteCallback;

    public MainFragment getInstance() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        return mainFragment;
    }

    @Override
    public int getLayoutID() {
        return R.layout.layout_main_fragment;
    }

    @Override
    public void initViews(View view) {
        ivFooter = view.findViewById(R.id.iv_logo_booket);
//        ivAdd = view.findViewById(R.id.iv_add);
        tv_swipe_left = view.findViewById(R.id.tv_swipe_left);
        tvTicketing = view.findViewById(R.id.tv_ticketing);
        btnPrint = view.findViewById(R.id.btn_print);
        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        rvSelectTickets = view.findViewById(R.id.rv_selected_ticket);
        rvSelectTickets.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rvSlots = view.findViewById(R.id.rv_slots);
        rvSlots.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvPayment = view.findViewById(R.id.rv_payment);
        rvPayment.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        number_picker = view.findViewById(R.id.number_picker);
        ll_ticket_value = view.findViewById(R.id.ll_ticket_value);
        spinnerMiniGolf = view.findViewById(R.id.spinner_mini_golf);
        spinnerMiniGolf.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
//        spinnerMiniGolf.setOnItemSelectedListener(this);
        spinnerAge = view.findViewById(R.id.spinner_age);
        spinnerAge.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerGender = view.findViewById(R.id.spinner_gender);
        spinnerGender.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerNationality = view.findViewById(R.id.spinner_nationality);
        spinnerNationality.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        sendData(view);
    }

    @Override
    public void initListeners() {
        setSpinnerAdapter();
//        slotAdapter();
        paymentAdapter();
        etTextListener(etName, 1);
        etTextListener(etPhone, 2);
        etTextListener(etEmail, 3);
        setAgeSpinnerAdapter();
        setGenderSpinnerAdapter();
        setNationalitySpinnerAdapter();
        setRvSelectedTicketsAdapter();
        enableSwipeToDeleteAndUndo();
        data();
        spinnerActivity();
        btnPrint.setOnClickListener(v -> {
            validation();
        });
        spinnerMiniGolf.setOnClickListener(v -> {
            spinnerMiniGolf.showDropDown();
        });
    }

    private void sendData(View view) {
        toolbar(view, GlobalState.getInstance().dataList.getPagesModel().get(0).getHeaderTitle(), GlobalState.getInstance().dataList.getPagesModel().get(0).getHeaderImage());
        if (!TextUtils.isEmpty(GlobalState.getInstance().dataList.getPagesModel().get(0).getFooterImage())) {
            Picasso.get().load(GlobalState.getInstance().dataList.getPagesModel().get(0).getFooterImage()).placeholder(R.drawable.ic_logo_new).into(ivFooter);
        }
        tvTicketing.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getFooterTitle());

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsAge().equals(visibility)) {
            spinnerAge.setVisibility(View.VISIBLE);
        } else {
            spinnerAge.setVisibility(View.GONE);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsGender().equals(visibility)) {
            spinnerGender.setVisibility(View.VISIBLE);
        } else {
            spinnerGender.setVisibility(View.GONE);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsNationality().equals(visibility)) {
            spinnerNationality.setVisibility(View.VISIBLE);
        } else {
            spinnerNationality.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsEmail().equals(visibility)) {
            StrEmail = "";
            etEmail.setHint(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeEmail());
            etEmail.setVisibility(View.VISIBLE);
        } else {
            etEmail.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsName().equals(visibility)) {
            etName.setHint(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeName());
            etName.setVisibility(View.VISIBLE);
        } else {
            etName.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsPhone().equals(visibility)) {
            etPhone.setHint(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomePhone());
            etPhone.setVisibility(View.VISIBLE);
        } else {
            etPhone.setVisibility(View.GONE);
        }
    }


    private void etTextListener(EditText et, int name) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.e("namename123", name + "  ");
                if (name == 1) {
                    StrName = editable.toString();
                } else if (name == 2) {
                    StrPhone = editable.toString();
                } else if (name == 3) {
                    if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsEmail().equals(visibility)) {
                        StrEmail = editable.toString();
                    }
                }
                data();
            }
        });
    }

    private void setNumberPicker() {
//        number_picker.setMaxValue(10);
//        number_picker.setMinValue(1);
//        number_picker.setOnValueChangedListener((numberPicker, i, i1) -> {
//            numberOfTicket = numberPicker.getValue();
//            totalPaid = Integer.parseInt(selectedLoginData.getRidePrice()) * numberOfTicket;
//            data();
//        });
    }


    private void slotAdapter() {
        durationName.clear();
        for (LoginData duration : GlobalState.getInstance().dataList.getLogin()) {
            if (duration.getRideName().equalsIgnoreCase(activity)) {
                durationName.add(duration.getDurationName());
            }
        }
        if (!durationName.isEmpty()) {
            slot = durationName.get(0);
//            totalPaid = Integer.parseInt(loginData.get(activityPosition).getRidePrice()) * numberOfTicket;
        }
        removeItemCheck = loginData.get(activityPosition);
        selectedLoginData = loginData.get(activityPosition);
        rvSlotAdapter = new RvSlotAdapter(durationName, context, this);
        rvSlots.setAdapter(rvSlotAdapter);
    }

    private void paymentAdapter() {
        List<PaySouce> tempPaymentSource = GlobalState.getInstance().dataList.getPaySouce();
        paymentType = tempPaymentSource.get(0).getName();
        rvPaymentAdapter = new RvPaymentAdapter(tempPaymentSource, context, this);
        rvPayment.setAdapter(rvPaymentAdapter);
    }

    private void data() {
        ll_ticket_value.setVisibility(View.VISIBLE);
        ll_ticket_value.removeAllViews();
//        textView("Activity: " + activity);
//        textView("Slot: " + slot);
//
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsAge().equals(visibility)) {
            textView(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeAge() + ": " + strAge);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsGender().equals(visibility)) {
            textView(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeGender() + ": " + strGender);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsNationality().equals(visibility)) {
            textView(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeNationality() + ": " + strNationality);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsEmail().equals(visibility)) {
            textView(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeEmail() + ": " + StrEmail);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsName().equals(visibility)) {
            textView(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeName() + ": " + StrName);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsPhone().equals(visibility)) {
            textView("Phone: " + StrPhone);
        }

        textView("Payment type: " + paymentType);
        textView("Number of tickets: " + numberOfTicket);
        textView("Total: " + totalPaid);

    }

    private void setSpinnerAdapter() {
        rideName.clear();
        loginData.clear();
        for (int i = 0; i < GlobalState.getInstance().dataList.getLogin().size(); i++) {
            if (!rideName.contains(GlobalState.getInstance().dataList.getLogin().get(i).getRideName())) {
                rideName.add(GlobalState.getInstance().dataList.getLogin().get(i).getRideName());
                loginData.add(GlobalState.getInstance().dataList.getLogin().get(i));
            }
        }
        activity = rideName.get(0);
        data();

        ArrayAdapter<String> arrayAdapterFabricName  = new ArrayAdapter<>(context, R.layout.layout_spinner_activity_text, rideName);
        spinnerMiniGolf.setAdapter(arrayAdapterFabricName);

    }


    private void spinnerActivity() {
        spinnerMiniGolf.setOnItemClickListener((adapterView, view, position, l) -> {
            String selection = (String)adapterView.getItemAtPosition(position);
            for (int act = 0; act < loginData.size(); act++) {
                if (loginData.get(act).getRideName().equalsIgnoreCase(selection)) {
                    activity = rideName.get(act);
                    activityPosition = act;
                    slotAdapter();
                    addNewItem(act);
                }
            }
            dismissKeyboard(spinnerMiniGolf);
        });

    }

    private void dismissKeyboard(EditText editText) {
        InputMethodManager imm = null;
        try {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /////////////////// Selected activity from spinner //////////////
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        showToast(rideName.get(position));

    }

    private void addNewItem(int position) {
        if (!selectedTickets.contains(loginData.get(position))) {
            if (tv_swipe_left.getVisibility() == View.GONE) {
                tv_swipe_left.setVisibility(View.VISIBLE);
            }
            selectedTickets.add(loginData.get(position));
            numberOfRideOnEveryTicket.add(new NumberOnTicketAndTotalAmount(1, Integer.parseInt(loginData.get(position).getRidePrice()) * 1, loginData.get(position).getId()));
            rvSelectedTicketsAdapter.notifyDataSetChanged();
        }
        getTotalAmountAndTicket();
        data();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e("Log.e", "Log.e");
    }

    private void textView(String text) {
        TextView textView = new TextView(context);
        textView.setId(View.generateViewId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 15;
        TextViewCompat.setTextAppearance(textView, R.style.style_regular);
        textView.setText(text);
        textView.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
        ll_ticket_value.addView(textView);
    }

    private void validation() {
//        Log.e("selectedTickets", String.valueOf(selectedTickets.size()));
        if (TextUtils.isEmpty(slot)) {
            showToast("Please select slot");
            return;
        }
        if (numberOfTicket == 0) {
            showToast("Please select number of ticket");
            return;
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsEmail().equals(visibility)) {
            if (TextUtils.isEmpty(StrEmail)) {
                etEmail.setError("Email cannot be empty");
                return;
            }
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsPhone().equals(visibility)) {
            if (TextUtils.isEmpty(StrPhone)) {
                etPhone.setError("Phone cannot be empty");
                return;
            }
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsName().equals(visibility)) {
            if (TextUtils.isEmpty(StrName)) {
                etName.setError("Name cannot be empty");
                return;
            }
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsAge().equals(visibility)) {
            if (TextUtils.isEmpty(strAge)) {
                showToast("Please select age");
                return;
            }
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsGender().equals(visibility)) {
            if (TextUtils.isEmpty(strGender)) {
                showToast("Please select gender");
                return;
            }
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsNationality().equals(visibility)) {
            if (TextUtils.isEmpty(strNationality)) {
                showToast("Please select nationality");
                return;
            }
        }


        int durationId = loginData.get(activityPosition).getId();
        int loginId = loginData.get(position).getLoginId();
//        Log.e("durationId", String.valueOf(durationId));
//        Log.e("loginId", String.valueOf(loginId));
//        Log.e("slot", String.valueOf(slot));
//        Log.e("numberOfTicket", String.valueOf(numberOfTicket));
//        Log.e("StrName", String.valueOf(StrName));
//        Log.e("strGender", String.valueOf(strGender));
//        Log.e("activity", String.valueOf(activity));
//        Log.e("activityPosition", String.valueOf(activityPosition));
//        Log.e("Email", String.valueOf(StrEmail));

        if (numberOfRideOnEveryTicket.isEmpty()) {
            showToast("Please select activity");
            return;
        }

        List<Ticketinfo> ticketinfos = new ArrayList<>();

        for (NumberOnTicketAndTotalAmount dataCartItem : numberOfRideOnEveryTicket) {
            Ticketinfo dataCardObj = new Ticketinfo();
            dataCardObj.setDurationSlotID(String.valueOf(dataCartItem.getSlotId()));
            dataCardObj.setTickets(String.valueOf(dataCartItem.getNumberOfTicket()));
            dataCardObj.setAmount(String.valueOf(dataCartItem.getTotalAmount()));
            ticketinfos.add(dataCardObj);
        }

        Gson gson = new Gson();
        JsonArray ticketInfo = gson.toJsonTree(ticketinfos).getAsJsonArray();

        JsonObject createOrder = generateCreateOrder(loginId, paymentType, StrPhone, StrName, StrEmail, strGender, strAge, strNationality, String.valueOf(numberOfTicket), String.valueOf(totalPaid), ticketInfo);
        Log.e("createOrder", createOrder.toString());
        postTransaction(createOrder);
//
    }

    private JsonObject generateCreateOrder(int Logid, String PaymentSource, String CustomerPhone,
                                           String CustomerName, String CustomerEmail, String Gender,
                                           String Age, String Nationality, String TotalTickets, String TotalAmount, JsonArray Ticketinfo) {
        JsonObject transactionObject = new JsonObject();
        transactionObject.addProperty("LoginId", Logid);
        transactionObject.addProperty("PaymentSource", PaymentSource);
        transactionObject.addProperty("CustomerPhone", CustomerPhone);
        transactionObject.addProperty("CustomerName", CustomerName);
        transactionObject.addProperty("CustomerEmail", CustomerEmail);
        transactionObject.addProperty("Gender", Gender);
        transactionObject.addProperty("Age", Age);
        transactionObject.addProperty("Nationality", Nationality);
        transactionObject.add("Ticketinfo", Ticketinfo);
        transactionObject.addProperty("TotalTickets", TotalTickets);
        transactionObject.addProperty("TotalPaid", TotalAmount);

        JsonObject jsonObjectParent = new JsonObject();
        jsonObjectParent.add("Transaction", transactionObject);

        return jsonObjectParent;

    }

//    private void postTransaction(JsonObject createOrder) {
//        functions.showProgressLoader(dialog);
//        Call<PostTransaction> loginModelCall = apiPaths.postTransaction(createOrder);
//        loginModelCall.enqueue(new Callback<PostTransaction>() {
//            @Override
//            public void onResponse(@NotNull Call<PostTransaction> call, @NotNull Response<PostTransaction> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.e("DataModelDataModel", response.code() + "");
//                    dataResponse = response.body().get(0);
//
//                    SunmiPrintHelper.getInstance().printExample(context, dataResponse, "Merchant copy");
//                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                        SunmiPrintHelper.getInstance().printExample(context, dataResponse, "Customer copy");
//                        functions.dismissProgressLoader(dialog);
//                        clearData();
//                    }, 5000);
//
//
//                } else {
//                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<PostTransaction> call, @NotNull Throwable t) {
//                functions.dismissProgressLoader(dialog);
//                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void postTransaction(JsonObject createOrder) {
        functions.showProgressLoader(dialog);
        Call<List<DataModel>> loginModelCall = apiPaths.postTransaction(createOrder.toString());
        loginModelCall.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DataModel>> call, @NotNull Response<List<DataModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("DataModelDataModel", response.code() + "");
                    dataResponse = response.body();

                    SunmiPrintHelper.getInstance().printExample(context, dataResponse, "Merchant copy");
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        SunmiPrintHelper.getInstance().printExample(context, dataResponse, "Customer copy");
                        functions.dismissProgressLoader(dialog);
                        clearData();
                    }, 6000);


                } else {
                    functions.dismissProgressLoader(dialog);
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<DataModel>> call, @NotNull Throwable t) {
                functions.dismissProgressLoader(dialog);
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnItemClickListener(int position) {
        rvSlotAdapter.selectedPosition(position);
        rvSlotAdapter.notifyDataSetChanged();
        this.position = position;
        slot = durationName.get(position);
        selectedLoginData = GlobalState.getInstance().dataList.getLogin().get(position);
        removeItemCheck = GlobalState.getInstance().dataList.getLogin().get(position);
        if (!selectedTickets.contains(selectedLoginData)) {
            if (tv_swipe_left.getVisibility() == View.GONE) {
                tv_swipe_left.setVisibility(View.VISIBLE);
            }

            selectedTickets.add(selectedLoginData);
            numberOfRideOnEveryTicket.add(new NumberOnTicketAndTotalAmount(1, Integer.parseInt(selectedLoginData.getRidePrice()) * 1, selectedLoginData.getId()));
            rvSelectedTicketsAdapter.notifyDataSetChanged();
        }
        getTotalAmountAndTicket();
//        totalPaid = Integer.parseInt(GlobalState.getInstance().dataList.getLogin().get(position).getRidePrice()) * numberOfTicket;
        data();
    }

    @Override
    public void onClickListener(int position) {
        rvPaymentAdapter.selectedPosition(position);
        rvPaymentAdapter.notifyDataSetChanged();
        paymentType = GlobalState.getInstance().dataList.getPaySouce().get(position).getName();

        data();
    }


    private void setAgeSpinnerAdapter() {
        List<String> age = new ArrayList<>();
        age.add(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeAge());
        for (Age tempAge : GlobalState.getInstance().dataList.getAge()) {
            age.add(tempAge.getAge());
        }
        ArrayAdapter<String> spinnerAgeArrayAdapter = new ArrayAdapter<String>(
                context, R.layout.layout_spinner_text, age) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
                return view;
            }
        };

        spinnerAge.setAdapter(spinnerAgeArrayAdapter);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    strAge = GlobalState.getInstance().dataList.getAge().get(position - 1).getAge();
                    data();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGenderSpinnerAdapter() {
        List<String> gender = new ArrayList<>();
        gender.add(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeGender());
        for (Gender tempGender : GlobalState.getInstance().dataList.getGender()) {
            gender.add(tempGender.getGender());
        }
        ArrayAdapter<String> spinnerGenderArrayAdapter = new ArrayAdapter<String>(
                context, R.layout.layout_spinner_text, gender) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
                return view;
            }
        };

        spinnerGender.setAdapter(spinnerGenderArrayAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    strGender = GlobalState.getInstance().dataList.getGender().get(position - 1).getGender();
                    data();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setNationalitySpinnerAdapter() {
        List<String> nationality = new ArrayList<>();
        nationality.add(GlobalState.getInstance().dataList.getPagesModel().get(0).getHomeNationality());
        for (Natioanlity tempGender : GlobalState.getInstance().dataList.getNatioanlity()) {
            nationality.add(tempGender.getNationality());
        }
        ArrayAdapter<String> spinnerNationalityArrayAdapter = new ArrayAdapter<String>(
                context, R.layout.layout_spinner_text, nationality) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
                return view;
            }
        };

        spinnerNationality.setAdapter(spinnerNationalityArrayAdapter);
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    strNationality = GlobalState.getInstance().dataList.getNatioanlity().get(position - 1).getNationality();
                    data();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void addNumberOfTickets(TextView text, TextView tvRideCount, int position) {
//        number_picker.setValue(number_picker.getValue() + 1);
//        int tempNumberOfTickets = Integer.parseInt(text.getText().toString());
        int tempNumberOfTickets = numberOfRideOnEveryTicket.get(position).getNumberOfTicket();

//        Log.e("tempNumberOfTickets", String.valueOf(tempNumberOfTickets));
        if (tempNumberOfTickets < 10) {
            int value = tempNumberOfTickets + 1;
//            totalPaid = Integer.parseInt(selectedTickets.get(position).getRidePrice()) * value;
            numberOfRideOnEveryTicket.set(position, new NumberOnTicketAndTotalAmount(value, Integer.parseInt(selectedTickets.get(position).getRidePrice()) * value, selectedTickets.get(position).getId()));
            text.setText(String.valueOf(numberOfRideOnEveryTicket.get(position).getNumberOfTicket()));

            tvRideCount.setText("1 x " + selectedTickets.get(position).getRidePrice());
        }
        getTotalAmountAndTicket();
    }

    @Override
    public void minusNumberOfTickets(TextView text, TextView tvRideCount, int position) {
        int tempNumberOfTickets = numberOfRideOnEveryTicket.get(position).getNumberOfTicket();
//        Log.e("tempNumberOfTickets", String.valueOf(tempNumberOfTickets));
        if (tempNumberOfTickets > 1) {
            int value = tempNumberOfTickets - 1;

            numberOfRideOnEveryTicket.set(position, new NumberOnTicketAndTotalAmount(value, Integer.parseInt(selectedTickets.get(position).getRidePrice()) * value, selectedTickets.get(position).getId()));
            text.setText(String.valueOf(numberOfRideOnEveryTicket.get(position).getNumberOfTicket()));

            tvRideCount.setText("1 x " + selectedTickets.get(position).getRidePrice());
        }
        getTotalAmountAndTicket();
    }

    private void getTotalAmountAndTicket() {
        int tempTotal = 0;
        int tempTickets = 0;
        for (int i = 0; i < numberOfRideOnEveryTicket.size(); i++) {
            tempTotal = tempTotal + numberOfRideOnEveryTicket.get(i).getTotalAmount();
            tempTickets = tempTickets + numberOfRideOnEveryTicket.get(i).getNumberOfTicket();
        }
        totalPaid = tempTotal;
        numberOfTicket = tempTickets;
        data();
    }

    private void setRvSelectedTicketsAdapter() {
        rvSelectedTicketsAdapter = new RvSelectedTicketsAdapter(selectedTickets, numberOfRideOnEveryTicket, context, this, activityPosition);
        rvSelectTickets.setAdapter(rvSelectedTicketsAdapter);
    }

    private void enableSwipeToDeleteAndUndo() {
        swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
//                Log.e("position", String.valueOf(selectedTickets.size()));
//                selectedTickets.remove(position);

                if (selectedTickets.get(position).equals(removeItemCheck)) {
                    showToast("Cannot delete the selected item.");
                    rvSelectedTicketsAdapter.removeItem(position);
                    selectedTickets.add(removeItemCheck);
                    numberOfRideOnEveryTicket.add(new NumberOnTicketAndTotalAmount(1, Integer.parseInt(removeItemCheck.getRidePrice()) * 1, removeItemCheck.getId()));
                    rvSelectedTicketsAdapter.notifyDataSetChanged();
                } else {
                    rvSelectedTicketsAdapter.removeItem(position);
                }
                getTotalAmountAndTicket();
                data();
//                getTotalAmountAndTicket();
                Log.e("position123", String.valueOf(selectedTickets.size()));
                if (selectedTickets.size() == 0) {
                    tv_swipe_left.setVisibility(View.GONE);
                }
//                numberOfRideOnEveryTicket.set(position,1);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvSelectTickets);
    }


    private void clearData() {
        StrPhone = "";
        StrEmail = "info@bookingqube.com";
        StrName = "";
        strAge = "";
        strGender = "";
        strNationality = "";
        numberOfRideOnEveryTicket.clear();
        selectedTickets.clear();
        setSpinnerAdapter();
        paymentType = GlobalState.getInstance().dataList.getPaySouce().get(0).getName();
        numberOfTicket = 1;
        totalPaid = 0;
        position = 0;
        activityPosition = 0;
        etName.setText("");
        etPhone.setText("");
        etEmail.setText("");

        setAgeSpinnerAdapter();
        setGenderSpinnerAdapter();
        setNationalitySpinnerAdapter();
        activity = rideName.get(0);
//        if (!durationName.isEmpty()) {
//            slot = durationName.get(0);
//            totalPaid = Integer.parseInt(loginData.get(activityPosition).getRidePrice()) * numberOfTicket;
//        }


        rvPaymentAdapter.selectedPosition(0);
        rvPaymentAdapter.notifyDataSetChanged();

        rvSlotAdapter.selectedPosition(position);
        rvSlotAdapter.notifyDataSetChanged();
        data();
    }
}
