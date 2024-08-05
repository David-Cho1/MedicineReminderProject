package com.example.medicinereminderproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;


public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public AlarmDatabaseHelper(@Nullable Context context) {
        super(context, "Alarm.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table alarmuser(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, time TEXT, repeat TEXT, med TEXT, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists alarmuser");
    }

    public ArrayList<AlarmItem> getAlarmList() {
        ArrayList<AlarmItem> alarmItems = new ArrayList<>();

        SQLiteDatabase MyDatabase = getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from alarmuser order by writeDate DESC", null);
        if (cursor.getCount() > 0) {
            // When Data exist, repeat and set the variables to the value in database
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String repeat = cursor.getString(cursor.getColumnIndexOrThrow("repeat"));
                String med = cursor.getString(cursor.getColumnIndexOrThrow("med"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                AlarmItem alarmItem = new AlarmItem();
                alarmItem.setId(id);
                alarmItem.setUser(user);
                alarmItem.setTime(time);
                alarmItem.setRepeat(repeat);
                alarmItem.setMed(med);
                alarmItem.setWriteDate(writeDate);
                alarmItems.add(alarmItem);
            }
        }
        cursor.close();

        return alarmItems;
    }
    public Boolean selectAlarm(String user, String time, String repeat, String med) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from alarmuser where user = ? and time = ? and repeat = ? and med = ? and writeDate = ?", new String[]{user, time, repeat, med});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean insertAlarm(String user, String time, String repeat, String med, String writeDate) {
        SQLiteDatabase MyDatabase =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("time", time);
        contentValues.put("repeat", repeat);
        contentValues.put("med", med);
        contentValues.put("writeDate", writeDate);
        long result = MyDatabase.insert("alarmuser", null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public Boolean deleteAlarm(String user, String time, String repeat, String med) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("DELETE FROM alarmuser WHERE user = ? and time = ? and repeat = ? and med = ?", new String[]{user, time, repeat, med});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;


        }
    }
}
