package com.example.medicinereminderproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton alarmButton = findViewById(R.id.alarmButton);
        ImageButton diaryButton = findViewById(R.id.diaryButton);

        String email = getIntent().getStringExtra("keyemail");

        alarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmPage.class);
                intent.putExtra("keyemail", email);
                startActivity(intent);
            }
        });


        diaryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryPage.class);
                intent.putExtra("keyemail", email);
                startActivity(intent);
            }
        });
    }

}