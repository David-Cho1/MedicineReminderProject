package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private int titleLength = 0;
    private int dateLength = 0;
    private Boolean titleAccept = true;
    private Boolean dateAccept = true;
    private TextView errorTitleTV;

    private TextView errorDateTV;

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
        errorTitleTV = findViewById(R.id.errorTitleText);
        errorDateTV = findViewById(R.id.errorDateText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Variables that will be used in this function
                char characterSlash = '/';
                int numberOfSlash = 0;

                // Get values from Edit Text
                title = titleET.getText().toString();
                diarydate = dateET.getText().toString();
                contents = contentET.getText().toString();


                // If the title Length is too long
                titleLength = title.length();
                dateLength = diarydate.length();

                // Show Toast Message
                if (titleLength > 18) {
                    errorTitleTV.setText("* Title is too long"); // show error
                    titleAccept = false; // set title not acceptable
                }
                // If title length is shorter than 18 characters,
                else if (titleLength < 18) {
                    titleAccept = true; // set title acceptable
                    errorTitleTV.setText(""); // set Text to empty
                }
                // If title is left empty
                if (titleLength == 0) {
                    errorTitleTV.setText("* Please add a Title");   // show error
                    titleAccept = false; // set title not acceptable
                }

                // If the Email doesn't have the right format
                for (int i = 0; i < dateLength; i++) {
                    if (diarydate.charAt(i) == characterSlash) {
                        numberOfSlash++;
                    }
                }

                if (numberOfSlash != 2) {
                    errorDateTV.setText("* Incorrect \n Date Format");
                    dateAccept = false;
                }
                else {
                    dateAccept = true;
                    errorDateTV.setText("");

                }


                // Simple date formatter
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // get current time into "date" form
                String writeDate = date.format(Calendar.getInstance().getTime());

                // If title length is acceptable, insert diary data to database
                if (titleAccept && dateAccept) {
                    Boolean insert = diaryDB.insertDiary(user, title, contents, diarydate, writeDate);
                    if (insert) {
                        Toast.makeText(DiarySetPage.this, "Diary Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        intent.putExtra("keyemail", user);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}