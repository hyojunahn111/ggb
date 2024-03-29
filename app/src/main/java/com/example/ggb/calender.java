// calender.java

package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.text.DecimalFormat;
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
    private TextView incomeTextView, expenseTextView, resultTextView;
    private IncomeDBHelper indbHelper;
    private ExpenseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        incomeTextView = findViewById(R.id.incomeTextView);
        indbHelper = new IncomeDBHelper(this);

        expenseTextView = findViewById(R.id.expenseTextView);
        dbHelper = new ExpenseDBHelper(this);

        resultTextView = findViewById(R.id.totalTextView);

        int totalExpense = dbHelper.getTotalExpense();
        expenseTextView.setText("총 지출 : " + totalExpense);

        String savedIncome = indbHelper.getSavedIncome();
        double totalIncome = 0.0;
        try {
            totalIncome = Double.parseDouble(savedIncome);
        } catch (NumberFormatException e) {
            // savedIncome 값이 숫자 형태의 문자열이 아닌 경우 처리할 내용 작성
        }
        DecimalFormat incomeFormat = new DecimalFormat("####");
        incomeTextView.setText("총 수입 : " + incomeFormat.format(totalIncome));

        double totalResult = totalIncome - totalExpense;
        DecimalFormat resultFormat = new DecimalFormat("####");
        resultTextView.setText("총 합계 : " + resultFormat.format(totalResult));

        Button conbt = (Button) findViewById(R.id.conbt);
        conbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Config.class);
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
        TextView incomeTextView = findViewById(R.id.incomeTextView);
        TextView expenseTextView = findViewById(R.id.expenseTextView);
        TextView totalTextView = findViewById(R.id.totalTextView);

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

        // 수정: week.java에서 전달된 DB 정보를 받아온다.
        Intent intent = getIntent();
        ArrayList<String> dbData = intent.getStringArrayListExtra("dbData");
        if (dbData != null && dbData.size() >= 3) {
            incomeTextView.setText(dbData.get(0));
            expenseTextView.setText(dbData.get(1));
            totalTextView.setText(dbData.get(2));

            expenseList.clear(); // 기존 데이터를 모두 지우고
            expenseList.addAll(dbData.subList(3, dbData.size())); // 실제 DB 데이터를 추가
        }
    }

    private void generateCalendar(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);

        expenseList.clear(); // 기존 데이터를 모두 지우고

        int expenseIndex = 0; // 지출 데이터 인덱스
        while (dates.size() < 42) {
            dates.add(calendar.getTime());

            // 수정: 지출 정보를 expenseList에 추가한다.
            String expense = "0"; // 기본값으로 "0"을 설정
            if (expenseIndex < monthDays && expenseIndex < expenseList.size()) {
                expense = expenseList.get(expenseIndex); // 실제 DB 데이터로 대체
            }
            expenseList.add(expense);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            expenseIndex++;
        }

    }
}
