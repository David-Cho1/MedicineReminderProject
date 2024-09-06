package com.example.medicinereminderproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;


public class AlarmDatabaseHelper extends SQLiteOpenHelper {
    // Alarm Database
    public static final String databaseName = "Alarm.db";

    // Amount of existing rows in data = 0
    public int curCount = 0;
    public AlarmDatabaseHelper(@Nullable Context context) {
        super(context, "Alarm.db",
                null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table alarmuser(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "user TEXT, time TEXT, repeat TEXT, med TEXT, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists alarmuser");
    }

    // Count number of row in table
    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "alarmuser");
        db.close();
        return count;
    }

    // Get Alarm list and put them into Arraylist
    public ArrayList<AlarmItem> getAlarmList(String _user) {
        ArrayList<AlarmItem> alarmItems = new ArrayList<>();
        SQLiteDatabase MyDatabase = getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from alarmuser where user = ? " +
                                            "order by writeDate DESC", new String[]{_user});

        // If there are more than 0 rows return Arraylist of entire data.
        if (cursor.getCount() > 0) {
            // When Data exist, repeat and set the variables to the value in database.
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String repeat = cursor.getString(cursor.getColumnIndexOrThrow("repeat"));
                String med = cursor.getString(cursor.getColumnIndexOrThrow("med"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                // Setting Intense.
                AlarmItem alarmItem = new AlarmItem();

                // Setting Varialbes.
                alarmItem.setId(id);
                alarmItem.setUser(user);
                alarmItem.setTime(time);
                alarmItem.setRepeat(repeat);
                alarmItem.setMed(med);
                alarmItem.setWriteDate(writeDate);

                // Adding it to ArrayList.
                alarmItems.add(alarmItem);
            }
        }
        cursor.close();

        // Return Arraylist.
        return alarmItems;
    }

    // Select Alarm from Database.
    public Boolean selectAlarm(String user, String time, String repeat, String med) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from alarmuser where user = ? " +
                                            "and time = ? and repeat = ? and med = ?",
                                            new String[]{user, time, repeat, med});

        // If there are more than 0 row, return true, if not return false.
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Insert Alarm into table in database.
    public Boolean insertAlarm(String user, String time, String repeat, String med, String writeDate) {
        SQLiteDatabase MyDatabase =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("time", time);
        contentValues.put("repeat", repeat);
        contentValues.put("med", med);
        contentValues.put("writeDate", writeDate);
        long result = MyDatabase.insert("alarmuser", null, contentValues);

        // If result == -1 return false, if not, return true.
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean deleteAlarm(String user, String time, String repeat, String med) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("DELETE FROM alarmuser WHERE user = ? and time = ? and repeat = ? and med = ?", new String[]{user, time, repeat, med});

        // If there are more than 0 row, return true, if not return false.
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
