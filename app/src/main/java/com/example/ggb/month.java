package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.widget.ListView;

public class month extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MonthAdapter adapter;

    ArrayList<MonthData> MonthList;
    private TextView incomeTextView, expenseTextView, resultTextView;
    private IncomeDBHelper indbHelper;
    private ExpenseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        incomeTextView = findViewById(R.id.incomeTextView);
        indbHelper = new IncomeDBHelper(this);

        expenseTextView = findViewById(R.id.expenseTextView);
        dbHelper = new ExpenseDBHelper(this);

        resultTextView = findViewById(R.id.resultTextView);

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

        this.InitializeMovieData();
        ListView listMonth = (ListView)findViewById(R.id.listMonth);
        final MonthAdapter myAdapter = new MonthAdapter(this,MonthList);
        listMonth.setAdapter(myAdapter);

    }
    public void InitializeMovieData()
    {
        MonthList = new ArrayList<MonthData>();

        MonthList.add(new MonthData("1월", "수입: 0", "지출: 0", "합계: 0" ));
        MonthList.add(new MonthData("2월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("3월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("4월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("5월", "수입: 0", "지출: 0", "합계: 0" ));
        MonthList.add(new MonthData("6월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("7월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("8월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("9월", "수입: 0", "지출: 0", "합계: 0" ));
        MonthList.add(new MonthData("10월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("11월","수입: 0","지출: 0","합계: 0"));
        MonthList.add(new MonthData("12월","수입: 0","지출: 0","합계: 0"));
    }
}