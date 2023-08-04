package com.example.ggb;

import android.provider.BaseColumns;

public class ExpenseContract {
    private ExpenseContract() {}

    public static class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_EXPENSE = "expense";
    }
}
