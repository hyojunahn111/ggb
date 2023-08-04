package com.example.ggb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.graphics.Color;

public class picture extends AppCompatActivity {

    private TextView incomeTextView, expenseTextView, resultTextView;
    private IncomeDBHelper indbHelper;
    private ExpenseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

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

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalIncome, "수입"));
        entries.add(new PieEntry((float) totalExpense, "지출"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setValueTextSize(12f);

        int[] colors = {Color.RED, Color.BLUE};
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        // 원형 그래프 설정
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.invalidate();

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
    }
}
