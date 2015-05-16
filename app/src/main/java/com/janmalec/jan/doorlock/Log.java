package com.janmalec.jan.doorlock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 16.5.2015.
 */
public class Log extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getIntent().getExtras();
        /*
        ArrayList <Entry> log = (ArrayList <Entry>) bundle.getSerializable("log") ;
        String [] timeStamps = null;
        int i = 0;
        for(Entry current : log){
            timeStamps[i] = current.getTimestamp();
            i = i + 1;
        }


        ListAdapter adapter = new ArrayAdapter<String>(Log.this, android.R.layout.simple_list_item_1, timeStamps);
        ListView resultsList = (ListView) findViewById(R.id.listView);
        resultsList.setAdapter(adapter);
        */
    }

}
