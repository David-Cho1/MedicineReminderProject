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
        createNotificationChannel();

        // Connect to Database Helper
        alarmDB = new AlarmDatabaseHelper(this);

        // Get how many row exist in db table
        mCurCount = alarmDB.getProfilesCount();
        Log.d("mCurCount", "" + mCurCount);

        // Get the alarm List from
        alarmItems = alarmDB.getAlarmList();
        alarmSetList = new ArrayList<>();

        for (int i = 0; i < mCurCount; i++) {
            String nameInDB = alarmItems.get(i).getMed();
            String repeatInDB = alarmItems.get(i).getRepeat();
            String timeInDB = alarmItems.get(i).getTime();


            String alarmStrings = ("<" + nameInDB + "> " + "<" + repeatInDB + "> " + "<" + timeInDB + ">");
            alarmSetList.add(alarmStrings);

            Log.d("Printing AAA", "" + alarmSetList);
            setAlarm(this ,nameInDB, timeInDB, repeatInDB);
        }




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

//                Intent intent = new Intent(getApplicationContext(), DiaryPage.class);
//                intent.putExtra("keyemail", email);
//                startActivity(intent);
            }
        });
    }


    public void setAlarm(Context context, String title, String time, String repeatDays) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);

        // 시간 파싱
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        Log.d("Time Set", hour + ":" + minute);

        // 현재 시간
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // 반복 요일 설정
        int[] daysOfWeek = parseRepeatDays(repeatDays);

        // 알람 설정
        for (int day : daysOfWeek) {
            Calendar alarmCalendar = (Calendar) calendar.clone();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, day);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }

    // 반복 요일 문자열을 파싱하여 요일 번호 배열 반환
    private int[] parseRepeatDays(String repeatDays) {
        String[] days = repeatDays.split(",");
        List<Integer> dayList = new ArrayList<>();
        for (String day : days) {
            switch (day.trim()) {
                case "Mon":
                    dayList.add(Calendar.MONDAY);
                    break;
                case "Tue":
                    dayList.add(Calendar.TUESDAY);
                    break;
                case "Wed":
                    dayList.add(Calendar.WEDNESDAY);
                    break;
                case "Thu":
                    dayList.add(Calendar.THURSDAY);
                    break;
                case "Fri":
                    dayList.add(Calendar.FRIDAY);
                    break;
                case "Sat":
                    dayList.add(Calendar.SATURDAY);
                    break;
                case "Sun":
                    dayList.add(Calendar.SUNDAY);
                    break;
            }
        }
        int[] dayArray = new int[dayList.size()];
        for (int i = 0; i < dayList.size(); i++) {
            dayArray[i] = dayList.get(i);
        }
        Log.d("Day List", "" + dayList);
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