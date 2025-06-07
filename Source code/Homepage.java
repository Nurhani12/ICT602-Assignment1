package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.graphics.Color;

public class Homepage extends AppCompatActivity {
    private Spinner monthSpinner;
    private EditText unitsInput;
    private SeekBar rebateSeekBar;
    private TextView rebateValueText, totalChargesText, finalCostText;
    private Button calculateButton, viewHistoryButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().setStatusBarColor(Color.parseColor("#020d22"));

        monthSpinner = findViewById(R.id.monthSpinner);
        unitsInput = findViewById(R.id.unitsInput);
        rebateSeekBar = findViewById(R.id.rebateSeekBar);
        rebateValueText = findViewById(R.id.rebateValueText);
        totalChargesText = findViewById(R.id.totalChargesText);
        finalCostText = findViewById(R.id.finalCostText);
        calculateButton = findViewById(R.id.calculateButton);

        dbHelper = new DatabaseHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        rebateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rebateValueText.setText("Rebate: " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        calculateButton.setOnClickListener(v -> calculateAndSave());

        Button btnViewHistory = findViewById(R.id.viewHistoryButton);
        btnViewHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void calculateAndSave() {
        String month = monthSpinner.getSelectedItem().toString();
        String unitsStr = unitsInput.getText().toString();
        int rebate = rebateSeekBar.getProgress();

        if (unitsStr.isEmpty()) {
            unitsInput.setError("Enter units used");
            return;
        }

        int units = Integer.parseInt(unitsStr);
        double totalCharges = calculateTotalCharges(units);
        double finalCost = totalCharges * (1 - rebate / 100.0);

        totalChargesText.setText(String.format(Locale.getDefault(), "Total Charges: RM %.2f", totalCharges));
        finalCostText.setText(String.format(Locale.getDefault(), "Final Cost: RM %.2f", finalCost));

        dbHelper.insertBill(month, units, totalCharges, rebate, finalCost);
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
    }

    private double calculateTotalCharges(int units) {
        double total = 0.0;

        if (units <= 200) {
            total = units * 0.218;
        } else if (units <= 300) {
            total = 200 * 0.218 + (units - 200) * 0.334;
        } else if (units <= 600) {
            total = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        } else if (units <= 900) {
            total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        } else {
            total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + 300 * 0.546 + (units - 900) * 0.546;
        }

        return total;
    }

    private double calculateFinalCost(double totalCharges, int rebatePercent) {
        return totalCharges - (totalCharges * rebatePercent / 100.0);
    }
}