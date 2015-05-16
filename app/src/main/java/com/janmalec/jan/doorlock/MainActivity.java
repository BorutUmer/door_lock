package com.janmalec.jan.doorlock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MySQLiteHelper db = new MySQLiteHelper(this);
        Button gZakleni;
        Button gNisem;
        Button gLog;
        context = this.getApplicationContext(); // important

        gZakleni = (Button)findViewById(R.id.button_zakleni);
        gNisem = (Button)findViewById(R.id.button_nisem);
        gLog = (Button)findViewById(R.id.button_log);

        gZakleni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Zaklenil si vrata",
                        Toast.LENGTH_LONG);
                db.addEvent(new Entry(1));

                msg.show();
            }
        });

        gNisem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Nemarnez",
                        Toast.LENGTH_LONG);
                db.addEvent(new Entry(0));

                msg.show();
            }
        });

        gLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_I = new Intent(context, ListActivity.class);
                ArrayList <Entry> events = new ArrayList<>();
                events = db.getAllEvents();
                if (events.size() > 1) {
                    Log.d("prvi timestamp", events.get(0).getTimestamp());
                    Log.d("drugi timestamp", events.get(1).getTimestamp());
                    ArrayList<String> timeStamps = new ArrayList<String>();
                    ArrayList<Integer> locked = new ArrayList<Integer>();

                    int i = 0;
                    for (Entry current : events) {
                        timeStamps.add(i, current.getTimestamp());
                        locked.add(i, current.getOpenClose());
                        i = i + 1;
                    }

                    log_I.putExtra("timestamps", timeStamps);
                    log_I.putExtra("ocs", locked);
                    startActivity(log_I);
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
