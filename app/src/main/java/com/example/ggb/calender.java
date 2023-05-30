package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class calender extends AppCompatActivity {

    private GridView calendarGridView;
    private ArrayList<Date> dates;
    private CalendarAdapter calendarAdapter;
    private SimpleDateFormat monthYearFormat;
    private ArrayList<String> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

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

        calendarGridView = findViewById(R.id.calendarGridView);
        TextView monthYearTextView = findViewById(R.id.monthYearTextView);

        dates = new ArrayList<>();
        monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        expenseList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        generateCalendar(calendar);

        calendarAdapter = new CalendarAdapter(this, dates, calendar);
        calendarAdapter.setExpenseList(expenseList);
        calendarGridView.setAdapter(calendarAdapter);

        monthYearTextView.setText(monthYearFormat.format(calendar.getTime()));
    }

    private void generateCalendar(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        dates.clear();
        for (int i = 0; i < 42; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    // 예산 항목을 설정하는 메서드
    public void setExpenseList(ArrayList<String> expenseList) {
        this.expenseList = expenseList;
        if (calendarAdapter != null) {
            calendarAdapter.setExpenseList(expenseList);
            calendarAdapter.notifyDataSetChanged();
        }
    }
}