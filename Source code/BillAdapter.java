package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private List<Bill> billList = new ArrayList<>();
    private Context context;

    public BillAdapter(Context context) {
        this.context = context;
    }

    public void setBillList(List<Bill> bills) {
        this.billList = bills;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.monthText.setText("Month: " + bill.getMonth());
        holder.costText.setText("Final Cost: RM " + String.format("%.2f", bill.getFinalCost()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("month", bill.getMonth());
            intent.putExtra("units", bill.getUnits());
            intent.putExtra("rebate", bill.getRebate());
            intent.putExtra("totalCharges", bill.getTotalCharges());
            intent.putExtra("finalCost", bill.getFinalCost());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView monthText, costText;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(android.R.id.text1);
            costText = itemView.findViewById(android.R.id.text2);
        }
    }
}
