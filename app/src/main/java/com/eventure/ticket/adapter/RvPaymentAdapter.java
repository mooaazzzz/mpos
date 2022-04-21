package com.eventure.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eventure.ticket.R;
import com.eventure.ticket.interfaces.CustomClickListener;
import com.eventure.ticket.models.loginModel.PaySouce;

import java.util.List;


public class RvPaymentAdapter extends RecyclerView.Adapter<RvPaymentAdapter.Holder> {

    List<PaySouce> textName;
    private int selectPosition = 0;
    Context context;
    private OnClickListener onClickListener;

    public RvPaymentAdapter(List<PaySouce> textName, Context context, OnClickListener onClickListener) {
        this.textName = textName;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_text, parent, false);
        return new Holder(view,onClickListener);
    }

    public void selectedPosition(int position) {
        selectPosition = position;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (selectPosition == position) {
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.tvName.setBackgroundColor(ContextCompat.getColor(context, R.color.orange_color));
        } else {
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tvName.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        }
        holder.tvName.setText(textName.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return textName.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        OnClickListener onClickListener;

        public Holder(@NonNull View itemView,OnClickListener onClickListener) {
            super(itemView);

            this.onClickListener = onClickListener;
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClickListener(getAdapterPosition());
        }
    }

    public  interface OnClickListener{
        void onClickListener(int position);
    }
}
