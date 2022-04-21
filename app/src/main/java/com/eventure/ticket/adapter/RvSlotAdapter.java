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

import java.util.List;


public class RvSlotAdapter extends RecyclerView.Adapter<RvSlotAdapter.Holder> {

    List<String> textName;
    private int selectPosition = 0;
    Context context;
    CustomClickListener onClick;

    public RvSlotAdapter(List<String> textName, Context context, CustomClickListener onClick) {
        this.textName = textName;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_text, parent, false);
        return new Holder(view);
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
        holder.tvName.setText(textName.get(position));
    }

    @Override
    public int getItemCount() {
        return textName.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onClick.OnItemClickListener(getAdapterPosition());
                }
            });
        }
    }
}
