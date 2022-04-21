package com.eventure.ticket.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.eventure.ticket.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<String> activityNames;
    LayoutInflater inflter;


    public CustomAdapter(Context applicationContext,  List<String> activityNames) {
        this.context = applicationContext;
        this.activityNames = activityNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return activityNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, viewGroup,false);

        TextView names = (TextView) view.findViewById(R.id.textView);



        names.setText(activityNames.get(i));
        return view;
    }

}