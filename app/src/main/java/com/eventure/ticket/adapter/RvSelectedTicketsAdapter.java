package com.eventure.ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eventure.ticket.R;
import com.eventure.ticket.models.NumberOnTicketAndTotalAmount;
import com.eventure.ticket.models.loginModel.LoginData;

import java.util.List;


public class RvSelectedTicketsAdapter extends RecyclerView.Adapter<RvSelectedTicketsAdapter.Holder> {

    List<LoginData> numberOfSelectedTickets;
    Context context;
    NumberOfTickets numberOfTickets;
    List<NumberOnTicketAndTotalAmount> numberOfRideOnEveryTicket;
    private int activityPosition;

    public RvSelectedTicketsAdapter(List<LoginData> numberOfSelectedTickets, List<NumberOnTicketAndTotalAmount> numberOfRideOnEveryTicket, Context context, NumberOfTickets numberOfTickets, int activityPosition) {
        this.numberOfSelectedTickets = numberOfSelectedTickets;
        this.context = context;
        this.numberOfTickets = numberOfTickets;
        this.numberOfRideOnEveryTicket = numberOfRideOnEveryTicket;
        this.activityPosition = activityPosition;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_tickets, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        Log.e("numberOfRideOnEvery12321", numberOfRideOnEveryTicket.get(position).getNumberOfTicket() + "");
        holder.tvCount.setText(String.valueOf(numberOfRideOnEveryTicket.get(position).getNumberOfTicket()));
        holder.tvRideName.setText(numberOfSelectedTickets.get(position).getRideName() + " ( " + numberOfSelectedTickets.get(position).getDurationName() + " )");
        holder.tvRideCount.setText("1" + " x " + numberOfSelectedTickets.get(position).getRidePrice());
    }

    public void removeItem(int position) {
        numberOfSelectedTickets.remove(position);
        numberOfRideOnEveryTicket.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return numberOfSelectedTickets.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView ivAdd, ivMinus;
        TextView tvCount, tvRideCount, tvRideName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvRideName = itemView.findViewById(R.id.tv_ride_name);
            tvRideCount = itemView.findViewById(R.id.tv_ride_count);
            ivAdd.setOnClickListener(v -> {
                numberOfTickets.addNumberOfTickets(tvCount, tvRideCount, getAdapterPosition());
            });
            ivMinus.setOnClickListener(v -> {
                numberOfTickets.minusNumberOfTickets(tvCount, tvRideCount, getAdapterPosition());
            });
        }
    }

    public interface NumberOfTickets {
        void addNumberOfTickets(TextView text, TextView tvRideCount, int position);

        void minusNumberOfTickets(TextView text, TextView tvRideCount, int position);
    }
}
