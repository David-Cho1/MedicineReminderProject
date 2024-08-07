package com.example.medicinereminderproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlarmPage extends AppCompatActivity {
    private RecyclerView rv_alarm;
    private FloatingActionButton btn_write;
    private ArrayList<AlarmItem> alarmItems;
    private AlarmDatabaseHelper alarmDB;
    private String email;
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

        email = getIntent().getStringExtra("keyemail");



        // Detect button clicks
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked", "True");
                // Pop up page open
                Dialog dialog = new Dialog(AlarmPage.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_alarm);

                // Buttons in Dialog
                Button newBtn = dialog.findViewById(R.id.newButton);
                Button deleteBtn = dialog.findViewById(R.id.deleteButton);

                // Show Dialog
                dialog.show();

                // If new button is pressed
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // Open AlarmSet page
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), AlarmSetPage.class);
                        intent.putExtra("keyemail", email);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}