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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private static final int ALARM_ID_OFFSET = 1000;
    private ActivityMainBinding binding;
    private PendingIntent pendingIntent;
    private ArrayList<String> alarmSetList;
    private AlarmManager alarmManager;
    private AlarmDatabaseHelper alarmDB;
    private ArrayList<AlarmItem> alarmItems;
    private String alarmString;
    private long mCurCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermission();
        createNotificationChannel();

        // Connect to Database Helper
        alarmDB = new AlarmDatabaseHelper(this);

        // Get how many row exist in db table
        mCurCount = alarmDB.getProfilesCount();

        ImageButton alarmButton = findViewById(R.id.alarmButton);
        ImageButton diaryButton = findViewById(R.id.diaryButton);

        // Email Address
        String email = getIntent().getStringExtra("keyemail");

        // Open up Alarm Page when Button clicked
        alarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmPage.class);
                intent.putExtra("keyemail", email);
                startActivity(intent);
            }
        });

        // Open up Diary Page when Button clicked
        diaryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DiaryPage.class);
                intent.putExtra("keyemail", email);
                startActivity(intent);
            }
        });
    }

    // Checking Push Notification Permission, if not granted, ask for Push Notification permission.
    public void checkPermission() {
        // PERMISSION CHECK
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
    }

    // Set Alarm Function (Not Working)
    public void setAlarm(Context context, String title, String time, String repeatDays) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);

        // Time parsing
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Current Time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Set Repeat days
        int[] daysOfWeek = parseRepeatDays(repeatDays);

        // Alarm Set
            for (int day : daysOfWeek) {
            Calendar alarmCalendar = (Calendar) calendar.clone();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, day);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                                                                     intent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }

    // parse repat days to number
    private int[] parseRepeatDays(String repeatDays) {
        String[] days = repeatDays.split(",");
        List<Integer> dayList = new ArrayList<>();
        for (String day : days) {
            switch (day.trim()) {
                case "mon":
                    dayList.add(Calendar.MONDAY);
                    break;
                case "tue":
                    dayList.add(Calendar.TUESDAY);
                    break;
                case "wed":
                    dayList.add(Calendar.WEDNESDAY);
                    break;
                case "thu":
                    dayList.add(Calendar.THURSDAY);
                    break;
                case "fri":
                    dayList.add(Calendar.FRIDAY);
                    break;
                case "sat":
                    dayList.add(Calendar.SATURDAY);
                    break;
                case "sun":
                    dayList.add(Calendar.SUNDAY);
                    break;
            }
        }
        int[] dayArray = new int[dayList.size()];
        for (int i = 0; i < dayList.size(); i++) {
            dayArray[i] = dayList.get(i);

        }
        return dayArray;
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