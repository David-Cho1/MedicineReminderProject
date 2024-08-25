package com.example.medicinereminderproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DiaryPage extends AppCompatActivity {
    String email;
    private RecyclerView rv_diary;
    private ArrayList<DiaryItem> mDiaryItems;
    private FloatingActionButton btn_write;
    private DiaryDatabaseHelper diaryDB;
    private DiaryCustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary);

        email = getIntent().getStringExtra("keyemail");
        setInit();

    }

    // Main Function
    public void setInit() {
        btn_write = findViewById(R.id.floatButtonDiary);
        rv_diary = findViewById(R.id.diaryRv);
        diaryDB = new DiaryDatabaseHelper(this);
        mDiaryItems = new ArrayList<>();

        // Execute loadRecentDB() method
        loadRecentDB();

        // Detect button clicks
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked", "True");
                // Pop up page open
                Dialog dialog = new Dialog(DiaryPage.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_diary);

                // Buttons in Dialog
                Button newBtn = dialog.findViewById(R.id.newDiaryButton);

                // Show Dialog
                dialog.show();

                // If new button is pressed
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // Open AlarmSet page
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), DiarySetPage.class);
                        intent.putExtra("keyemail", email);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    // Load Data from database to CustomAdapter
    private void loadRecentDB() {
        // Load saved data from database
        mDiaryItems = diaryDB.getDiaryList(email);
        if (mAdapter == null) {
            mAdapter = new DiaryCustomAdapter(email, mDiaryItems, this);
            rv_diary.setHasFixedSize(true);
            rv_diary.setAdapter(mAdapter);
        }
    }
}