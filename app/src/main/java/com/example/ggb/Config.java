package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Config extends AppCompatActivity {

    private TextView incomeTextView, expenseTextView, resultTextView;
    private IncomeDBHelper indbHelper;
    private ExpenseDBHelper dbHelper;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private EditText thresholdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

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

        if (totalExpense > 100000) {
            setAlarm(); // 알림 설정 메서드 호출
        }

        Button conbt = findViewById(R.id.conbt);
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


        // 이하 버튼 및 리스너들은 동일하게 유지합니다.

        thresholdEditText = findViewById(R.id.conTxt);

        Button confirmButton = findViewById(R.id.conSelBt);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thresholdStr = thresholdEditText.getText().toString();
                if (!thresholdStr.isEmpty()) {
                    double threshold = Double.parseDouble(thresholdStr);
                    setAlarm(threshold);
                }
            }
        });

        Button deleteButton = findViewById(R.id.conRemBt);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAlarm();
            }
        });
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiverIntent = new Intent(this, AlarmReceiver.class);
        alarmReceiverIntent.putExtra("message", "총 지출이 100,000원 이상입니다!");
        alarmIntent = PendingIntent.getBroadcast(this, 0, alarmReceiverIntent, PendingIntent.FLAG_IMMUTABLE);

        // 알림 시간 설정 (예시로 현재 시간 + 5초로 설정)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        // 알림 등록
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        Toast.makeText(this, "알림이 설정되었습니다!", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(double threshold) {
        if (threshold > 100000) {
            setAlarm(); // 총 지출이 100,000원 이상일 때만 알림 설정
        } else {
            removeAlarm(); // 그 외의 경우에는 알림 제거
        }
    }

    private void removeAlarm() {
        if (alarmManager != null && alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
            Toast.makeText(this, "알림이 제거되었습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}
