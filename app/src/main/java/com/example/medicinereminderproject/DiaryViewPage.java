package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DiaryViewPage extends AppCompatActivity {
    private String email;
    private String title;
    private String contents;
    private String date;
    private String content;
    private TextView titleTV;
    private TextView contentTV;
    private TextView dateTV;
    private DiaryDatabaseHelper DiaryDB;
    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_view_page);

        DiaryDB = new DiaryDatabaseHelper(this);

        email = getIntent().getStringExtra("keyemail");
        title = getIntent().getStringExtra("keytitle");
        date = getIntent().getStringExtra("keydate");

        content = DiaryDB.getContent(email, title, date);

        titleTV = findViewById(R.id.titleTV);
        contentTV = findViewById(R.id.contentsTV);
        dateTV = findViewById(R.id.dateTV);
        closeBtn = findViewById(R.id.closebutton);

        titleTV.setText(String.format("Title: %s", title));
        dateTV.setText(String.format("Date: %s", date));
        contentTV.setText(String.format(content));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryPage.class);
                intent.putExtra("keyemail", email);
                startActivity(intent);
            }
        });


    }
}