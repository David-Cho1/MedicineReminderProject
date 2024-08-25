package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DiaryViewPage extends AppCompatActivity {
    // Private Variables
    private String email;
    private String title;
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

        // Connecting Database
        DiaryDB = new DiaryDatabaseHelper(this);

        // Receive variables from different activity
        email = getIntent().getStringExtra("keyemail");
        title = getIntent().getStringExtra("keytitle");
        date = getIntent().getStringExtra("keydate");

        // Get Content of selected diary
        content = DiaryDB.getContent(email, title, date);

        // Find the GUI Id and set it to variable
        titleTV = findViewById(R.id.titleTV);
        contentTV = findViewById(R.id.contentsTV);
        dateTV = findViewById(R.id.dateTV);
        closeBtn = findViewById(R.id.closebutton);

        // Scroll Text View
        contentTV.setMovementMethod(new ScrollingMovementMethod());

        // Fill up the Textview with the selected data details
        titleTV.setText(String.format("Title: %s", title));
        dateTV.setText(String.format("Date: %s", date));
        contentTV.setText(String.format(content));

        // When Close button is pressed, send to Diary Page
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