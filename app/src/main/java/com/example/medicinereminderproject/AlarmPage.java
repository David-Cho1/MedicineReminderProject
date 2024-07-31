package com.example.medicinereminderproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlarmPage extends AppCompatActivity {
    private RecyclerView rv_alarm;
    private FloatingActionButton btn_write;
    private ArrayList<AlarmItem> alarmItems;
    private AlarmDatabaseHelper alarmDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);

        setInit();

    }

    private void setInit() {
        // Setting a variables name
        alarmDB = new AlarmDatabaseHelper(this);
        rv_alarm = findViewById(R.id.alarmsRv);
        btn_write = findViewById(R.id.optionFB);
        alarmItems = new ArrayList<>();

        // Detect button clicks
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop up page open
                Dialog dialog = new Dialog(AlarmPage.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_alarm);
            }
        });
    }
}