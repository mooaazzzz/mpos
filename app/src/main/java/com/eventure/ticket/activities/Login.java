package com.eventure.ticket.activities;

import static com.eventure.ticket.activities.GlobalState.showToast;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.eventure.ticket.R;
import com.eventure.ticket.models.loginModel.LoginModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends BaseActivity {


    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etUsername.setText("");
        etPassword.setText("");
    }

    public void login(View view) {
        validation();
    }

    private void validation() {
        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();

        if (TextUtils.isEmpty(strUsername)) {
            etUsername.setError(getString(R.string.error_enter_full_name));
            return;
        }
        if (strPassword.isEmpty()) {
            etPassword.setError(getString(R.string.error_please_enter_password));
            return;
        }

        loginUser(strUsername, strPassword);
    }

    private void loginUser(String strEmail, String strPassword) {
        functions.showProgressLoader(dialog);
        Call<LoginModel> tokenCall = bApiPaths.login(strEmail, strPassword);
        tokenCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                functions.dismissProgressLoader(dialog);
                if (response.isSuccessful()) {
                    GlobalState.getInstance().dataList = response.body();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                    Log.e("getDurationName", response.body().getLogin().get(0).getDurationName());
                }else {
                    showToast(getString(R.string.error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                functions.dismissProgressLoader(dialog);
                Log.e("getLocalizedMessage", t.getLocalizedMessage());
                showToast(getString(R.string.network_error));
            }
        });
    }
}