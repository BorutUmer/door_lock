package com.janmalec.jan.doorlock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<String> timeStamps = (ArrayList<String>) getIntent().getSerializableExtra("timestamps");
        ArrayList<Integer> locked = (ArrayList<Integer>) getIntent().getSerializableExtra("ocs");

        Log.d("prvi timestamp", timeStamps.get(0));

        String [] timeStampsString = new String [timeStamps.size()];
        String uLocked = " Locked";
        String uNlocked = " Not locked";

        for(int i = 0; i < timeStamps.size(); i++){
            if(locked.get(i) == 1) {
                timeStampsString[i] = timeStamps.get(i) + uLocked;
            }
            else if(locked.get(i) == 0){
                timeStampsString[i] = timeStamps.get(i) + uNlocked;
            }
        }


        ListAdapter adapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, timeStampsString);
        ListView resultsList = (ListView) findViewById(R.id.listView);
        resultsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
