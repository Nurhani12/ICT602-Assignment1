package com.example.assignment1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

public class DetailActivity extends AppCompatActivity {

    private TextView detailMonth, detailUnits, detailCharges, detailRebate, detailFinalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);  // Make sure XML file name is activity_detail.xml
        getWindow().setStatusBarColor(Color.parseColor("#020d22"));

        detailMonth = findViewById(R.id.detailMonth);
        detailUnits = findViewById(R.id.detailUnits);
        detailCharges = findViewById(R.id.detailCharges);
        detailRebate = findViewById(R.id.detailRebate);
        detailFinalCost = findViewById(R.id.detailFinalCost);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Get data from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String month = extras.getString("month");
            int units = extras.getInt("units");
            double totalCharges = extras.getDouble("totalCharges");
            int rebate = extras.getInt("rebate");
            double finalCost = extras.getDouble("finalCost");

            detailMonth.setText("Month: " + month);
            detailUnits.setText("Units: " + units);
            detailCharges.setText(String.format("Total Charges: RM %.2f", totalCharges));
            detailRebate.setText("Rebate: " + rebate + "%");
            detailFinalCost.setText(String.format("Final Cost: RM %.2f", finalCost));
        }
    }
}
