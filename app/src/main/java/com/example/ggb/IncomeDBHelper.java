package com.example.ggb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomeDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "income.db";
    private static final String TABLE_NAME = "income_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INCOME = "income";

    public IncomeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INCOME + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveIncome(String income) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null); // 기존 데이터 삭제
        ContentValues values = new ContentValues();
        values.put(COLUMN_INCOME, income);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String getSavedIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        String savedIncome = "";
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            savedIncome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME));
        }
        cursor.close();
        db.close();
        return savedIncome;
    }
}
