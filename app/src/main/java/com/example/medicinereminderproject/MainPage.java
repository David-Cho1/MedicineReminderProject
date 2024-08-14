package com.example.medicinereminderproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medicinereminderproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class MainPage extends AppCompatActivity {
    private static final int ALARM_ID_OFFSET = 1000;
    private ActivityMainBinding binding;
    private PendingIntent pendingIntent;
    private ArrayList<AlarmItem> alarmSetList;

    private AlarmManager alarmManager;
    private AlarmDatabaseHelper mDBHelper;
    private ArrayList<AlarmItem> alarmItems;
    private String alarmString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();


        // Get alarm lists from database
        mDBHelper = new AlarmDatabaseHelper(this);

        alarmItems = mDBHelper.getAlarmList();
        // get the time for 2nd database
        String time = alarmItems.get(1).getTime();

        Log.d("alarm Set", "" + time);

        // Checking the Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if permission is enabled or not
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                // If Permission is NOT Granted Ask for permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

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


    private static void scheduleAlarm(Context context, int dayOfWeek, int hour, int minute, int second) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 알림을 트리거할 Intent 및 PendingIntent 설정
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, dayOfWeek + ALARM_ID_OFFSET, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "medicineReminderChannel";
            String description = "Hey it's time to take your medicine";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("AlarmChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}