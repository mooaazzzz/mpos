package com.eventure.ticket.Fragment;

import static com.eventure.ticket.activities.GlobalState.showToast;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.eventure.ticket.R;
import com.eventure.ticket.activities.GlobalState;
import com.eventure.ticket.adapter.CustomAdapter;
import com.eventure.ticket.models.DayClosing;
import com.eventure.ticket.models.GetRideDetailsModel;
import com.eventure.ticket.models.loginModel.LoginData;
import com.eventure.ticket.utils.SunmiPrintHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayClosingFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    private DayClosingFragment logoutFragment;
    Spinner spinnerMiniGolf;
    List<GetRideDetailsModel> getRideDetailsModels = new ArrayList<>();
    EditText etPettyCash, etCashAmount, etCardAmount, etDebitAmount;
    int rideId = -1;
    Button btnUpdateInfo;
    SwitchCompat switchReprint;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout llParent, llReprint;
    View shimmerEffect;
    String selected_date = "";
    List<LoginData> loginData = new ArrayList<>();
    List<String> rideName = new ArrayList<>();
    CustomAdapter customAdapter;
    TextView tvTodayDate, tvTicketing, tvPettyTitle, tvCashTitle, tvDebitTitle, tvCreditTitle, tvLocation;
    private int visibility = 1;
    public final static int SELECT_IMAGE = 1, SELECT_CAMERA = 123;
    CheckBox cbCashDeposit;
    String stringBase64 = "";

    public DayClosingFragment getInstance() {
        if (logoutFragment == null) {
            logoutFragment = new DayClosingFragment();
        }
        return logoutFragment;
    }


    @Override
    public int getLayoutID() {
        return R.layout.layout_day_closing_fragment;
    }

    @Override
    public void initViews(View view) {
        cbCashDeposit = view.findViewById(R.id.cb_cash_deposit);
        bottomSheetDialog = new BottomSheetDialog(context);
        tvLocation = view.findViewById(R.id.tv_location);
        ivFooter = view.findViewById(R.id.iv_logo_booket);
        tvPettyTitle = view.findViewById(R.id.tv_petty_title);
        tvCashTitle = view.findViewById(R.id.tv_today_cash_amount_title);
        tvDebitTitle = view.findViewById(R.id.tv_total_debit_amount);
        tvCreditTitle = view.findViewById(R.id.tv_total_card_amount_cash);
        tvTicketing = view.findViewById(R.id.tv_ticketing);
        llReprint = view.findViewById(R.id.ll_reprint);
        llParent = view.findViewById(R.id.ll_parent);
        shimmerEffect = view.findViewById(R.id.shimmer_view_container);
        tvTodayDate = view.findViewById(R.id.tv_today_date);
        switchReprint = view.findViewById(R.id.switch_reprint);
        btnUpdateInfo = view.findViewById(R.id.btn_update_info);
        etPettyCash = view.findViewById(R.id.et_petty_cash);
        etCashAmount = view.findViewById(R.id.et_cash_amount);
        etCardAmount = view.findViewById(R.id.et_card_amount);
        etDebitAmount = view.findViewById(R.id.et_debit_amount);
        spinnerMiniGolf = view.findViewById(R.id.spinner_mini_golf);
        spinnerMiniGolf.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerMiniGolf.setOnItemSelectedListener(this);
        sendData(view);

    }

    private void clearData() {
        selected_date = "";
        stringBase64 = "";
        etCashAmount.setText("");
        etCardAmount.setText("");
        etDebitAmount.setText("");
        tvTodayDate.setText(getString(R.string.date));
        cbCashDeposit.setChecked(false);
        setSpinnerAdapter();

    }

    @Override
    public void initListeners() {
        btnUpdateInfo.setOnClickListener(v -> {
            validation();
        });
        tvTodayDate.setOnClickListener(v -> {
            datePicker(tvTodayDate);
        });
        switchReprint.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                btnUpdateInfo.setText(getString(R.string.reprint));
                llReprint.setVisibility(View.GONE);
            } else {
                btnUpdateInfo.setText(getString(R.string.update_info));
                llReprint.setVisibility(View.VISIBLE);
            }
        });
        checkBox();
    }

    private void sendData(View view) {
        tvLocation.setText("Location : " + GlobalState.getInstance().dataList.getLogin().get(0).getLocation());
        toolbar(view, GlobalState.getInstance().dataList.getPagesModel().get(0).getdHeaderTitle(), GlobalState.getInstance().dataList.getPagesModel().get(0).getdHeaderImage());
        if (!TextUtils.isEmpty(GlobalState.getInstance().dataList.getPagesModel().get(0).getdFooterImage())) {
            Picasso.get().load(GlobalState.getInstance().dataList.getPagesModel().get(0).getdFooterImage()).placeholder(R.drawable.ic_logo_new).into(ivFooter);
        }
        tvTicketing.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getdFooterTitle());
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsReprint().equals(visibility)) {
            switchReprint.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingReprint());
            switchReprint.setVisibility(View.VISIBLE);
        } else {
            switchReprint.setVisibility(View.GONE);
        }
        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsPettCash().equals(visibility)) {
            tvPettyTitle.setVisibility(View.VISIBLE);
            etPettyCash.setVisibility(View.VISIBLE);
            tvPettyTitle.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingPettCash());
        } else {
            tvPettyTitle.setVisibility(View.GONE);
            etPettyCash.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsDebitCard().equals(visibility)) {
            tvDebitTitle.setVisibility(View.VISIBLE);
            etDebitAmount.setVisibility(View.VISIBLE);
            tvDebitTitle.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingDebitCard());
        } else {
            tvDebitTitle.setVisibility(View.GONE);
            etDebitAmount.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsCreditCard().equals(visibility)) {
            tvCreditTitle.setVisibility(View.VISIBLE);
            etCardAmount.setVisibility(View.VISIBLE);
            tvCreditTitle.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingCreditCard());
        } else {
            tvCreditTitle.setVisibility(View.GONE);
            etCardAmount.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsCardAmount().equals(visibility)) {
            tvCashTitle.setVisibility(View.VISIBLE);
            etCashAmount.setVisibility(View.VISIBLE);
            tvCashTitle.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingCardAmount());
        } else {
            tvCashTitle.setVisibility(View.GONE);
            etCashAmount.setVisibility(View.GONE);
        }

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsDate().equals(visibility)) {
            tvTodayDate.setVisibility(View.VISIBLE);
            tvTodayDate.setText(GlobalState.getInstance().dataList.getPagesModel().get(0).getDayClosingDate());
        } else {
            tvTodayDate.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

//        if (spinnerRideNameArrayAdapter == null) {
//            getRideDetail();
//        }
        if (customAdapter == null) {
            setSpinnerAdapter();
        }
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

        customAdapter = new CustomAdapter(context, rideName);
        spinnerMiniGolf.setAdapter(customAdapter);

    }

    private void getRideDetail() {
        Call<List<GetRideDetailsModel>> tokenCall = apiPaths.getRideDetail();
        tokenCall.enqueue(new Callback<List<GetRideDetailsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetRideDetailsModel>> call, @NonNull Response<List<GetRideDetailsModel>> response) {
                shimmerEffect.setVisibility(View.GONE);
                llParent.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    getRideDetailsModels = response.body();
//                    setAgeSpinnerAdapter();
                } else {
                    showToast(getString(R.string.error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetRideDetailsModel>> call, @NonNull Throwable t) {
//                shimmerEffect.setVisibility(View.GONE);
//                llParent.setVisibility(View.VISIBLE);
                showToast(getString(R.string.network_error));
                Log.e("getLocalizedMessage", t.getLocalizedMessage());
            }
        });
    }

    private void validation() {
        String strPettyCash = etPettyCash.getText().toString();
        String strCashAmount = etCashAmount.getText().toString();
        String strCardAmount = etCardAmount.getText().toString();
        String strDebitAmount = etDebitAmount.getText().toString();

        if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsDate().equals(visibility)) {
            if (TextUtils.isEmpty(selected_date)) {
                showToast("Please select date");
                return;
            }
        }
        if (!cbCashDeposit.isChecked()) {
            showToast("Please add cash deposit image");
            return;
        }
        int loginId = GlobalState.getInstance().dataList.getLogin().get(0).getLoginId();
        String location = GlobalState.getInstance().dataList.getLogin().get(0).getLocation();
        if (!switchReprint.isChecked()) {

            if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsPettCash().equals(visibility)) {
                if (TextUtils.isEmpty(strPettyCash)) {
                    etPettyCash.setError("Petty cash cannot be empty");
                    return;
                }
            }
            if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsCardAmount().equals(visibility)) {
                if (TextUtils.isEmpty(strCashAmount)) {
                    etCashAmount.setError("Cash amount cannot be empty");
                    return;
                }
            }

            if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsCreditCard().equals(visibility)) {
                if (TextUtils.isEmpty(strCardAmount)) {
                    etCardAmount.setError("Card amount cannot be empty");
                    return;
                }
            }
            if (GlobalState.getInstance().dataList.getPagesModel().get(0).getIsDebitCard().equals(visibility)) {
                if (TextUtils.isEmpty(strDebitAmount)) {
                    etDebitAmount.setError("Debit amount cannot be empty");
                    return;
                }
            }
            Log.e("location", location + " location");

            dayClosing(loginId, Integer.parseInt(strPettyCash), Integer.parseInt(strCashAmount), Integer.parseInt(strCardAmount), Integer.parseInt(strDebitAmount), 0, "Closing report", location, stringBase64);
        } else {
            Log.e("location", location + " location");
            dayClosing(loginId, 0, 0, 0, 0, 1, "Closing report reprint", location, stringBase64);
        }

    }

    MaterialDatePicker.Builder<Long> builder;

    private void datePicker(TextView tv) {
        builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");
        builder.setTheme(R.style.DatePickerStyle);
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
//        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
//        constraintsBuilder.setValidator(dateValidator);
        builder.setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Long> picker = builder.build();

        picker.show(getChildFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(selection -> {
            selected_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selection);
            tv.setText(selected_date);
        });
        tv.setError(null);
    }

    private void dayClosing(int loginId, int strPettyCash, int strCashAmount, int strCardAmount, int strDebitAmount, int reprint, String closingText, String location, String stringBase64) {
        functions.showProgressLoader(dialog);
        Call<List<DayClosing>> loginModelCall = apiPaths.dayClosing(0, loginId, strPettyCash, strCashAmount, strCardAmount, strDebitAmount, selected_date, reprint, location, "data:image/png;base64," + stringBase64);
        loginModelCall.enqueue(new Callback<List<DayClosing>>() {
            @Override
            public void onResponse(@NotNull Call<List<DayClosing>> call, @NotNull Response<List<DayClosing>> response) {
                functions.dismissProgressLoader(dialog);
                Log.e("DataModelDataModel", response.code() + "");
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().get(0).getRetMsg().equalsIgnoreCase("Success")) {
                        showToast(response.body().get(0).getRetMsg());
                        SunmiPrintHelper.getInstance().printDayClosing(response.body(), closingText);
                    } else {
                        showToast(response.body().get(0).getRetMsg());
                    }
                    clearData();
                } else {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<DayClosing>> call, @NotNull Throwable t) {
                functions.dismissProgressLoader(dialog);
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rideId = loginData.get(i).getRideId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void checkBox() {
        cbCashDeposit.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    Log.e("bbbbbbbbb", String.valueOf(isChecked));
                    if (isChecked) {
                        bottomSheetDialog();
                    }
                }
        );

    }

    public void openGalley() {
        Intent intent_upload = new Intent();
        intent_upload.setType("image/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, SELECT_IMAGE);
    }

    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, SELECT_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_IMAGE && data != null) {
            Uri uriImage = data.getData();
            if (uriImage != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBase64 = convertBitmapToBase64(bitmap);


                longLog("imageString", stringBase64);
            }
        }
        if (requestCode == SELECT_CAMERA && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            stringBase64 = convertBitmapToBase64(imageBitmap);
            longLog("imageString", stringBase64);
        }

        if (!stringBase64.isEmpty()) {
            if (bottomSheetDialog != null) {
                bottomSheetDialog.dismiss();
            }
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 65, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        //Use your Base64 String as you wish
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void bottomSheetDialog() {
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_open_camera);

        TextView tvOpenCamera = bottomSheetDialog.findViewById(R.id.tv_camera);
        TextView tvGallery = bottomSheetDialog.findViewById(R.id.tv_gallery);

        tvOpenCamera.setOnClickListener(v -> {
            openCamera();
        });

        tvGallery.setOnClickListener(v -> {
            openGalley();
        });
        bottomSheetDialog.setOnDismissListener(dialogInterface -> {
            if (stringBase64.isEmpty()) {
                cbCashDeposit.setChecked(false);
            }
        });

        bottomSheetDialog.show();
    }

    public void longLog(String tag, String message) {
        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + 4000);
                Log.e(tag, message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }

}
