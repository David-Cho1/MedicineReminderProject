package com.example.medicinereminderproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccDatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Signup.db";

    public AccDatabaseHelper(@Nullable Context context) {
        super(context, "Signup.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists allusers");
    }

    // Insert Email and Password
    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);

        // If result is -1 return true, and if not return false
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Check data with email address
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?", new String[]{email});

        // If more than 0 rows are exist return true, and if not return false
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    // Check email and password
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});

        // If more than 0 rows are exist return true, and if not return false
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
