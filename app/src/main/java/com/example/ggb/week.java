package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class week extends AppCompatActivity {

    private ListView listView;
    private TextView dateTextView, dayTextView;
    private EditText itemEditText, expenseEditText;
    private ArrayList<String> expenseList;
    private ArrayAdapter<String> adapter;
    private ExpenseDBHelper dbHelper;
    private TextView expenseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        expenseTextView = findViewById(R.id.expenseTextView);
        dbHelper = new ExpenseDBHelper(this);

        int totalExpense = dbHelper.getTotalExpense();
        expenseTextView.setText("총 지출 : " + totalExpense);

        Button conbt = (Button) findViewById(R.id.conbt);
        conbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), config.class);
                startActivity(intent);
            }
        });

        Button weekbtn = (Button) findViewById(R.id.weekbt);
        weekbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), week.class);
                startActivity(intent);
            }
        });

        Button calbtn = (Button) findViewById(R.id.calbt);
        calbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });

        Button monthbtn = (Button) findViewById(R.id.monthbt);
        monthbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), month.class);
                startActivity(intent);
            }
        });

        Button picbtn = (Button) findViewById(R.id.picbt);
        picbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), picture.class);
                startActivity(intent);
            }
        });

        dbHelper = new ExpenseDBHelper(this);

        listView = findViewById(R.id.listView);
        dateTextView = findViewById(R.id.dateTextView);
        dayTextView = findViewById(R.id.dayTextView);
        itemEditText = findViewById(R.id.itemEditText);
        expenseEditText = findViewById(R.id.expenseEditText);

        expenseList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 리스트 아이템 클릭 시 동작 (필요한 경우 구현)
            }
        });

        setCurrentDate();

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = itemEditText.getText().toString();
                String expense = expenseEditText.getText().toString();
                String entry = item + ": " + expense;
                expenseList.add(entry);
                adapter.notifyDataSetChanged();
                itemEditText.setText("");
                expenseEditText.setText("");

                saveExpense(item, expense);
            }
        });

        loadExpenses();

        adapter.notifyDataSetChanged();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteExpense(i);
                return true;
            }
        });
    }

    private void setCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        String formattedDate = dateFormat.format(currentDate);
        String formattedDay = dayFormat.format(currentDate);

        dateTextView.setText(formattedDate);
        dayTextView.setText(formattedDay);
    }

    private void saveExpense(String item, String expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_ITEM, item);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE, expense);

        db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);
    }

    private void loadExpenses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ExpenseContract.ExpenseEntry.COLUMN_ITEM,
                ExpenseContract.ExpenseEntry.COLUMN_EXPENSE
        };

        Cursor cursor = db.query(
                ExpenseContract.ExpenseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        expenseList.clear();
        if (cursor.moveToFirst()) {
            do {
                String item = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_ITEM));
                String expense = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE));
                String entry = item + ": " + expense;
                expenseList.add(entry);
            } while (cursor.moveToNext());
        }

        cursor.close();

    }

    private void deleteExpense(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String item = expenseList.get(position).split(":")[0].trim();
        String expense = expenseList.get(position).split(":")[1].trim();

        String selection = ExpenseContract.ExpenseEntry.COLUMN_ITEM + " = ? AND " +
                ExpenseContract.ExpenseEntry.COLUMN_EXPENSE + " = ?";
        String[] selectionArgs = {item, expense};

        db.delete(ExpenseContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);

        expenseList.remove(position);
        adapter.notifyDataSetChanged();
    }
}