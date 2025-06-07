package com.example.assignment1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ElectricityBill.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "bills";
    public static final String COL_ID = "id";
    public static final String COL_MONTH = "month";
    public static final String COL_UNITS = "units";
    public static final String COL_REBATE = "rebate";
    public static final String COL_TOTAL = "totalCharges";
    public static final String COL_FINAL = "finalCost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MONTH + " TEXT NOT NULL, " +
                COL_UNITS + " INTEGER NOT NULL, " +
                COL_REBATE + " INTEGER NOT NULL, " +
                COL_TOTAL + " REAL NOT NULL, " +
                COL_FINAL + " REAL NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //  Insert full bill details into the database
    public void insertBill(String month, int units, double totalCharges, int rebate, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MONTH, month);
        values.put(COL_UNITS, units);
        values.put(COL_TOTAL, totalCharges);
        values.put(COL_REBATE, rebate);
        values.put(COL_FINAL, finalCost);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //  Returns a list of summary strings for ListView (e.g. "May 2025 - RM 123.45")
    public List<String> getBillSummaryList() {
        List<String> summaryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_MONTH + ", " + COL_FINAL + " FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndexOrThrow(COL_MONTH));
                double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FINAL));
                summaryList.add(month + " - RM " + String.format(Locale.getDefault(), "%.2f", finalCost));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return summaryList;
    }

    //  Get list of IDs to associate with summary list items
    public ArrayList<Integer> getBillIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_ID + " FROM " + TABLE_NAME + " ORDER BY " + COL_ID, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                ids.add(id);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ids;
    }

    //  Fetch full Bill object by ID (used for detail screen)
    public Bill getBillById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Bill bill = null;

        if (cursor != null && cursor.moveToFirst()) {
            String month = cursor.getString(cursor.getColumnIndexOrThrow(COL_MONTH));
            int units = cursor.getInt(cursor.getColumnIndexOrThrow(COL_UNITS));
            double totalCharges = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL));
            int rebate = cursor.getInt(cursor.getColumnIndexOrThrow(COL_REBATE));
            double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FINAL));
            bill = new Bill(month, units, totalCharges, rebate, finalCost);
            cursor.close();
        }

        db.close();
        return bill;
    }
}
