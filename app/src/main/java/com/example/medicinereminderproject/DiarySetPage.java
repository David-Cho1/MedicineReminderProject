package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiarySetPage extends AppCompatActivity {
    private DiaryDatabaseHelper diaryDB;
    private String user;
    private String title;
    private String diarydate;
    private String contents;
    private Button saveBtn;
    private EditText titleET;
    private EditText dateET;
    private EditText contentET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_set_page);

        user = getIntent().getStringExtra("keyemail");

        diaryDB = new DiaryDatabaseHelper(this);

        titleET = findViewById(R.id.titleEditText);
        dateET = findViewById(R.id.dateEditText);
        contentET = findViewById(R.id.contentET);
        saveBtn = findViewById(R.id.savebutton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from Edit Text
                title = titleET.getText().toString();
                diarydate = dateET.getText().toString();
                contents = contentET.getText().toString();

                // Simple date formatter
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String writeDate = date.format(Calendar.getInstance().getTime());

                Boolean insert = diaryDB.insertDiary(user, title, contents, diarydate, writeDate);
                if (insert) {
                    Toast.makeText(DiarySetPage.this, "Diary Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    intent.putExtra("keyemail", user);
                    startActivity(intent);
                }

            }
        });



    }
}