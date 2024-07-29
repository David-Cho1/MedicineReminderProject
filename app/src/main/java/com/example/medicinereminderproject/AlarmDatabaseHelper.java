package com.example.medicinereminderproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalTime;


public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public AlarmDatabaseHelper(@Nullable Context context) {
        super(context, "Alarm.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table alarmuser(user TEXT primary key, time TIME, repeat TEXT, med TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists alarmuser");
    }

    public Boolean insertData(String user, String time, String repeat, String med) {
        SQLiteDatabase MyDatabase =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("time", time);
        contentValues.put("repeat", repeat);
        contentValues.put("med", med);
        long result = MyDatabase.insert("alarmuser", null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public Boolean deleteData(String user, String time, String repeat, String med) {
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
