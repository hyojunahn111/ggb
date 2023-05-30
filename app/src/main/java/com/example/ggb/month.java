package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class month extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MonthAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // 한 달의 요일 배열 가져오기
        String[] dayOfWeeks = new DateFormatSymbols().getShortWeekdays();

        // 현재 달의 첫 번째 날짜와 마지막 날짜를 가져옴
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<String> dateList = new ArrayList<>();

        // 요일 추가
        for (int i = 1; i <= 7; i++) {
            dateList.add(dayOfWeeks[i]);
        }

        // 공백 추가
        for (int i = 1; i < firstDayOfMonth; i++) {
            dateList.add("");
        }

        // 날짜 추가
        for (int i = 1; i <= lastDayOfMonth; i++) {
            dateList.add(String.valueOf(i));
        }

        adapter = new MonthAdapter(dateList);
        recyclerView.setAdapter(adapter);
    }
}