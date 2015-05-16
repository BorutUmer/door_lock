package com.janmalec.jan.doorlock;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DoorDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table of Events
        String CREATE_DOOR_TABLE = "CREATE TABLE events ( " +
                "timestamp INTEGER, " +
                "open_close INTEGER )";

        // create books table
        db.execSQL(CREATE_DOOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS events");

        // create fresh table
        this.onCreate(db);
    }

    public void addEvent(Entry event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues = new ContentValues();
    }

}