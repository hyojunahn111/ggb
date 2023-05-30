package com.example.ggb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView expenseTextView;
    private TextView incomeTextView;
    private ExpenseDBHelper exdbHelper;
    private EditText incomeEditText;
    private IncomeDBHelper indbHelper;
    private TextView resultTextView;

    private SQLiteDatabase db; // SQLite 데이터베이스 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseTextView = findViewById(R.id.expenseTextView);
        incomeTextView = findViewById(R.id.incomeTextView);
        incomeEditText = findViewById(R.id.incomeEditText);
        resultTextView = findViewById(R.id.resultTextView);

        exdbHelper = new ExpenseDBHelper(this);
        loadTotalExpense();

        indbHelper = new IncomeDBHelper(this);
        loadSavedIncome();

        String incomeText = incomeTextView.getText().toString();
        String expenseText = expenseTextView.getText().toString();

        double income = Double.parseDouble(incomeText.replace("총 수입: ", ""));
        double expense = Double.parseDouble(expenseText.replace("총 지출 : ", ""));
        double result = income - expense;

        int roundedResult = (int) result;

        if (roundedResult >= 0) {
            resultTextView.setText("총 합계 : +" + roundedResult);
        } else {
            resultTextView.setText("총 합계 : " + roundedResult);
        }

        // 현재 날짜를 가져옵니다.
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.

        TextView todayDateTextView = findViewById(R.id.TodayDateTextView);
        todayDateTextView.setText(month + "월");

        Button incomeconbtn = findViewById(R.id.incomeconbt);
        incomeconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIncome();
            }
        });

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
    }

    private void loadTotalExpense() {
        int totalExpense = exdbHelper.getTotalExpense();
        expenseTextView.setText("총 지출 : " + totalExpense);
    }

    private void loadSavedIncome() {
        String savedIncome = indbHelper.getSavedIncome();
        try {
            double income = Double.parseDouble(savedIncome);
            int roundedIncome = (int) income;
            incomeTextView.setText("총 수입: " + roundedIncome);
            incomeTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showResetIncomeDialog();
                    return true;
                }
            });
        } catch (NumberFormatException e) {
            incomeTextView.setText("총 수입: 유효하지 않음");
        }
    }

    private void showResetIncomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("수입 초기화");
        builder.setMessage("수입을 초기화하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetIncome();
            }
        });
        builder.setNegativeButton("아니오", null);
        builder.show();
    }

    private void resetIncome() {
        indbHelper.saveIncome("0");
        loadSavedIncome();
        Toast.makeText(this, "수입이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void saveIncome() {
        String incomeInput = incomeEditText.getText().toString();
        String savedIncome = indbHelper.getSavedIncome();

        // 입력값이 유효한지 검사
        if (incomeInput.isEmpty()) {
            Toast.makeText(this, "수입을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double inputAmount = Double.parseDouble(incomeInput);

            // 기존에 저장된 수입과 새로 입력한 수입을 더해서 저장
            double totalIncome = Double.parseDouble(savedIncome) + inputAmount;
            indbHelper.saveIncome(String.valueOf(totalIncome));

            loadSavedIncome();
            Toast.makeText(this, "총 수입이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "유효한 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}