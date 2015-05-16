package com.janmalec.jan.doorlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DoorDB";
    private static final String TABLE_EVENTS = "events";

//    private static final String KEY_ID  = "id";
    private static final String EVENT_TS  = "timestamp";
    private static final String EVENT_OC =  "open_close";

    private static final String[] COLUMNS = {EVENT_TS,EVENT_OC};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table of Events
        String CREATE_DOOR_TABLE = "CREATE TABLE events ( " +
                "timestamp TEXT, " +
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

        //values.put(KEY_ID, event.getId());
        values.put(EVENT_TS, event.getTimestamp());
        values.put(EVENT_OC, event.getOpenClose());

        long id = db.insert(TABLE_EVENTS, null, values);

        Log.d("Adding event: ", event.toString());
        Log.d("With id: ", Long.toString(id));
    }

    public ArrayList<Entry> getAllEvents(){
        ArrayList <Entry> events = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Entry entry;
        String databaseDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date dataDate = null;
        int i = 0;
        if(cursor.moveToFirst()){

            do{
                Log.d("i: ", Integer.toString(i));
                i++;
                entry = new Entry();
                databaseDate = cursor.getString(0);
                Log.d("ddate: ", databaseDate);
                entry.setoClose(Integer.parseInt(cursor.getString(1)));
                Log.d("semnisem ", Integer.toString(entry.getOpenClose()));

                try{
                    dataDate = dateFormat.parse(databaseDate);
                } catch (Exception e){
                    Log.d("Parse exception", "!!");
                }

                entry.setTimestamp(dataDate);
                events.add(entry);
            } while (cursor.moveToNext());
        }

        return events;

    }

}