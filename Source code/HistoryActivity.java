package com.example.assignment1;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.Bill;
import com.example.assignment1.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> billList;
    ArrayAdapter<String> adapter;
    DatabaseHelper dbHelper;
    ArrayList<Integer> billIds; // store ids to fetch details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getWindow().setStatusBarColor(Color.parseColor("#020d22"));

        listView = findViewById(R.id.historyListView);
        dbHelper = new DatabaseHelper(this);

        //  Get bill summaries and bill IDs
        billList = new ArrayList<>(dbHelper.getBillSummaryList());  // Example: "Jan - RM 100.00"
        billIds = new ArrayList<>(dbHelper.getBillIds());

        // If billList is null or empty, initialize it safely
        if (billList == null) {
            billList = new ArrayList<>();
        }

        //  Set adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billList);
        listView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        //  Handle item click
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < billIds.size()) {
                int selectedBillId = billIds.get(position);
                Bill selectedBill = dbHelper.getBillById(selectedBillId);

                if (selectedBill != null) {
                    Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                    intent.putExtra("month", selectedBill.getMonth());
                    intent.putExtra("units", selectedBill.getUnits());
                    intent.putExtra("totalCharges", selectedBill.getTotalCharges());
                    intent.putExtra("rebate", selectedBill.getRebate());
                    intent.putExtra("finalCost", selectedBill.getFinalCost());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Bill details not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
