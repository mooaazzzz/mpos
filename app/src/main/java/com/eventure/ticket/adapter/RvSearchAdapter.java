package com.eventure.ticket.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eventure.ticket.R;
import com.eventure.ticket.models.Search;
import com.eventure.ticket.models.loginModel.PaySouce;

import java.util.List;


public class RvSearchAdapter extends RecyclerView.Adapter<RvSearchAdapter.Holder> {

    List<Search> textName;
    Context context;
    PrintSingle printSingle;

    public RvSearchAdapter(List<Search> textName, Context context, PrintSingle printSingle) {
        this.textName = textName;
        this.context = context;
        this.printSingle = printSingle;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.ll_ticket_value.removeAllViews();
        textView("RideName: " + textName.get(position).getRideName(), holder.ll_ticket_value);
        textView("Duration: " + textName.get(position).getDuration(), holder.ll_ticket_value);
        textView("Barcode: " + textName.get(position).getBarcode(), holder.ll_ticket_value);
        textView("Total Paid: " + textName.get(position).getTotalPaid(), holder.ll_ticket_value);
        textView("Payment Source: " + textName.get(position).getPaymentSource(), holder.ll_ticket_value);
        textView("Customer Name: " + textName.get(position).getCustomerName(), holder.ll_ticket_value);
        textView("Customer Email: " + textName.get(position).getCustomerEmail(), holder.ll_ticket_value);
        textView("Customer Phone: " + textName.get(position).getCustomerPhone(), holder.ll_ticket_value);
        textView("Logged By: " + textName.get(position).getLoggedBy(), holder.ll_ticket_value);
        textView("Transaction Time: " + textName.get(position).getTransactionTime(), holder.ll_ticket_value);
        textView("No of Tickets: " + textName.get(position).getNoOfTickets(), holder.ll_ticket_value);
        textView("Rest Msg: " + textName.get(position).getRestMsg(), holder.ll_ticket_value);
        textView("Ret Val: " + textName.get(position).getRetVal(), holder.ll_ticket_value);

    }

    @Override
    public int getItemCount() {
        return textName.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout ll_ticket_value;
        TextView tvPrint;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvPrint = itemView.findViewById(R.id.tv_print);
            ll_ticket_value = itemView.findViewById(R.id.ll_ticket_value);
            tvPrint.setOnClickListener(v -> {
                printSingle.printSingle(getAdapterPosition());
            });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    private void textView(String text, LinearLayout layout) {
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
        layout.addView(textView);
    }

    public interface PrintSingle {
        void printSingle(int position);
    }
}
