package com.example.ggb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseContract.ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                    ExpenseContract.ExpenseEntry.COLUMN_ITEM + " TEXT," +
                    ExpenseContract.ExpenseEntry.COLUMN_EXPENSE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExpenseContract.ExpenseEntry.TABLE_NAME;

    public ExpenseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public int getTotalExpense() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(" + ExpenseContract.ExpenseEntry.COLUMN_EXPENSE + ") FROM " +
                ExpenseContract.ExpenseEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        int totalExpense = 0;

        if (cursor.moveToFirst()) {
            totalExpense = cursor.getInt(0);
        }

        cursor.close();
        return totalExpense;
    }

}