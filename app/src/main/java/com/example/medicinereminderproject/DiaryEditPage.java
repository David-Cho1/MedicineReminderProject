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

public class DiaryEditPage extends AppCompatActivity {
    private String user;
    private String originalTitle;
    private String originalDate;
    private String originalContent;
    private int diaryId;
    private String newTitle;
    private String newDate;
    private String newContent;
    private Button saveBtn;
    private Button cancelBtn;
    private EditText titleET;
    private EditText dateET;
    private EditText contentET;
    private int titleLength = 0;
    private int dateLength = 0;
    private Boolean titleAccept = true;
    private Boolean dateAccept = true;
    private TextView errorTitleTV;
    private TextView errorDateTV;
    private DiaryDatabaseHelper diaryDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_edit_page);

        // Get Variables from previous Activity
        user = getIntent().getStringExtra("keyemail");
        originalTitle = getIntent().getStringExtra("keytitle");
        originalDate = getIntent().getStringExtra("keydate");
        String tempId = getIntent().getStringExtra("keyid");   // Temporary Id (String)

        // Parse string value to Integer
        diaryId = Integer.parseInt(tempId);

        // Database Helper Connect
        diaryDB = new DiaryDatabaseHelper(this);

        // Set original content from Database
        originalContent = diaryDB.getContent(user, originalTitle, originalDate);

        // Find the GUI id and give them a name
        titleET = findViewById(R.id.titleEditText);
        dateET = findViewById(R.id.dateEditText);
        contentET = findViewById(R.id.contentET);
        saveBtn = findViewById(R.id.savebutton);
        cancelBtn = findViewById(R.id.cancelbutton);
        errorTitleTV = findViewById(R.id.errorTitleText);
        errorDateTV = findViewById(R.id.errorDateText);

        // Fill up the Text box with origianl Text
        titleET.setText(originalTitle);
        dateET.setText(originalDate);
        contentET.setText(originalContent, TextView.BufferType.EDITABLE);

        // Save Button Clicked - update the database
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Variables that will be used in this function
                char characterSlash = '/';
                int numberOfSlash = 0;

                // Get values from Edit Text
                newTitle = titleET.getText().toString();
                newDate = dateET.getText().toString();
                newContent = contentET.getText().toString();


                // If the title Length is too long
                titleLength = newTitle.length();
                dateLength = newDate.length();

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
                    if (newDate.charAt(i) == characterSlash) {
                        numberOfSlash++;
                    }
                }

                // If the date Input doesn't contain Slash < / >, set DateAccept False
                if (numberOfSlash != 2) {
                    errorDateTV.setText("* Incorrect \n Date Format");
                    dateAccept = false;

                }
                // If it does, set dateAccept true and delete error message
                else {
                    dateAccept = true;
                    errorDateTV.setText("");
                }

                // If title length is acceptable, update diary data to database
                if (titleAccept && dateAccept) {
                    // DB update
                    diaryDB.updateDiary(newTitle, newContent, newDate, diaryId);

                    // Show toast
                    Toast.makeText(DiaryEditPage.this, "Diary Updated", Toast.LENGTH_SHORT).show();

                    // Send back to Main Page
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    intent.putExtra("keyemail", user);
                    startActivity(intent);
                }
            }
        });

        // If cancel button is clicked
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            // Discard changes and Send to Diary page
            @Override
            public void onClick(View v) {

            }
        });

    }
}