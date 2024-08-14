package com.example.medicinereminderproject;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    private String email;
    private CustomAdapter mAdapter;
    private BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);
        mReceiver = new AlarmReceiver();   // Define mReceiver as new MyReceiver Class
        setInit();
    }



    private void setInit() {
        // Setting a variables name
        alarmDB = new AlarmDatabaseHelper(this);
        rv_alarm = findViewById(R.id.alarmsRv);
        btn_write = findViewById(R.id.optionFB);
        alarmItems = new ArrayList<>();
        email = getIntent().getStringExtra("keyemail");

        // Load database
        loadRecentDB();
        rv_alarm.setNestedScrollingEnabled(false);
        rv_alarm.smoothScrollToPosition(0);


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

//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_POWER_CONNECTED);
//        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//        // Local
//        filter.addAction(MyReceiver.MyAction);
//        registerReceiver(mReceiver, filter);
//    }
//    // Stop Receiver
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mReceiver);
//    }
//
//    // Send an action to Broacast Receiver
//    public void sendBroadCast(View view) {
//        // Include Action and Sent intent to Receiver
//        Intent intent = new Intent(MyReceiver.MyAction);
//        sendBroadcast(intent);
//    }

    private void loadRecentDB() {
        // Load saved data from database
        alarmItems = alarmDB.getAlarmList();
        if(mAdapter == null) {
            mAdapter = new CustomAdapter(alarmItems, this, email);
            rv_alarm.setHasFixedSize(true);
            rv_alarm.setAdapter(mAdapter);
        }
    }
}