package com.example.ggb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



}