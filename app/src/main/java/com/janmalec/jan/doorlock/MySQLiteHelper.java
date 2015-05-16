package com.janmalec.jan.doorlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DoorDB";
    private static final String TABLE_EVENTS = "events";

    private static final String EVENT_ID  = "id";
    private static final String EVENT_TS  = "timestamp";
    private static final String EVENT_OC =  "open_close";

    private static final String[] COLUMNS = {EVENT_ID,EVENT_TS,EVENT_OC};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table of Events
        String CREATE_DOOR_TABLE = "CREATE TABLE events ( " +
                "id INTEGER, " +
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

        ContentValues values = new ContentValues();

        values.put(EVENT_ID, event.getId());
        values.put(EVENT_TS, event.getTimestamp());
        values.put(EVENT_OC, event.getOpenClose());

        db.insert(TABLE_EVENTS, null, values);
    }

    public Entry getEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor= db.query(TABLE_EVENTS,
                COLUMNS,
                " id = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null
                );
        if (cursor!= null)
            cursor.moveToFirst();

        Entry event = new Entry();
        event.setId(Integer.parseInt(cursor.getString(0)));
        event.setTimestamp(Integer.parseInt(cursor.getString(1)));
        event.setoClose(Integer.parseInt(cursor.getString(2)));
        Log.d("Event", event.toString());

        return event;

    }

}