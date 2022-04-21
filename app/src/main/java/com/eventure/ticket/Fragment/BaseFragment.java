package com.eventure.ticket.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.eventure.ticket.R;
import com.eventure.ticket.activities.Functions;
import com.eventure.ticket.retrofit.ApiPaths;
import com.eventure.ticket.retrofit.RetrofitInit;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseFragment extends Fragment {

    //region Variables
    public Dialog dialog;
    public Context context;
    public Activity activity;
    public View view;
    public int intScreenHeight, intScreenWidthCreateFragment;
    public float intScreenWidth;
    public ApiPaths apiPaths;
    private RetrofitInit retrofitInit = new RetrofitInit();
    public Functions functions;
    public ImageView ivFooter;
    //endregion

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(getLayoutID(), container, false);
        dialog = new Dialog(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        intScreenHeight = displayMetrics.heightPixels;
        intScreenWidth = displayMetrics.widthPixels / 1.2f;
        intScreenWidthCreateFragment = displayMetrics.widthPixels;
        apiPaths = retrofitInit.retrofitBuilder();
        functions = new Functions();
        initViews(view);
        initListeners();
        return view;

    }

    @LayoutRes
    public abstract int getLayoutID();

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    public abstract void initViews(View view);

    public abstract void initListeners();

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void toolbar(View view, String title, String image) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView ivSearch = toolbar.findViewById(R.id.iv_toolbar_image);

        if (!TextUtils.isEmpty(image)) {
            Picasso.get().load(image).placeholder(R.drawable.eee_dark).into(ivSearch);
        }
        tvTitle.setText(title);

    }

}
