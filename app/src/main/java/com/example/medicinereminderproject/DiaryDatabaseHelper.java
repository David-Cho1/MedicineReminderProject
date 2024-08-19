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


public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Diary.db";
    public int curCount = 0;

    public DiaryDatabaseHelper(@Nullable Context context) {
        super(context, "Diary.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table diarytable(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, title TEXT, context TEXT, date TEXT, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists diarytable");
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "diarytable");
        db.close();
        return count;
    }

    public ArrayList<DiaryItem> getDiaryList() {
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();

        SQLiteDatabase MyDatabase = getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from diarytable order by writeDate DESC", null);
        if (cursor.getCount() > 0) {
            // When Data exist, repeat and set the variables to the value in database
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String context = cursor.getString(cursor.getColumnIndexOrThrow("context"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                DiaryItem diaryItem = new DiaryItem();
                diaryItem.setId(id);
                diaryItem.setUser(user);
                diaryItem.setTitle(title);
                diaryItem.setContext(context);
                diaryItem.setDate(date);
                diaryItem.setWriteDate(writeDate);
                diaryItems.add(diaryItem);
            }
        }
        cursor.close();

        return diaryItems;
    }


    public Boolean selectDiary(String user, String title, String context) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from diarytable where user = ? and title = ? and context = ?", new String[]{user, title, context});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean insertDiary(String user, String title, String context, String date, String writeDate) {
        SQLiteDatabase MyDatabase =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("title", title);
        contentValues.put("context", context);
        contentValues.put("date", date);
        contentValues.put("writeDate", writeDate);
        long result = MyDatabase.insert("diarytable", null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public Boolean deleteDiary(String user, String title, String context) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("DELETE FROM diarytable WHERE user = ? and title = ? and date = ?", new String[]{user, title, context});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;


        }
    }


}
