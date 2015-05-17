package com.janmalec.jan.doorlock;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static java.util.concurrent.TimeUnit.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


public class MainActivity extends ActionBarActivity {
    private Context context;
    final MySQLiteHelper db = new MySQLiteHelper(this);
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    String homeSSID = "DragonH";
    int home = 0; //0: smo doma, 1: nas ni doma
    int alarmed = 0; //0: no need for interaction, 1: enter info

    ImageButton gZakleni;
    ImageButton gNisem;
    ImageView cat;
    ImageView girl;
    TextView gLocked;
    ScheduledExecutorService exec;
    ScheduledFuture<?> future;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

        Button gLog;
        context = this.getApplicationContext(); // important

        gZakleni = (ImageButton)findViewById(R.id.button_zakleni);
        gNisem = (ImageButton)findViewById(R.id.button_nisem);
        gLog = (Button)findViewById(R.id.button_log);
        gLocked = (TextView)findViewById(R.id.textLocked);
        cat = (ImageView)findViewById(R.id.catImg);
        girl = (ImageView)findViewById(R.id.girlImg);
        gZakleni.setVisibility(View.INVISIBLE);
        gNisem.setVisibility(View.INVISIBLE);
        gLocked.setVisibility(View.INVISIBLE);


        gZakleni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addEvent(new Entry(1));
                hideButtons();
                future = exec.scheduleAtFixedRate(neki, 10, 5, SECONDS);
            }

        });

        gNisem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addEvent(new Entry(0));
                hideButtons();
                future = exec.scheduleAtFixedRate(neki, 10, 5, SECONDS);
            }
        });

        gLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_I = new Intent(context, ListActivity.class);
                ArrayList <Entry> events = new ArrayList<>();
                events = db.getAllEvents();
                if (events.size() > 0) {
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
        String SSID = wifiInfo.getSSID();
        SSID = SSID.substring(1, SSID.length()-1);
        Log.d("SSID: ", SSID);
        Log.d("home SSID: ", homeSSID);
        if (SSID.equals(homeSSID)){
            home = 0;
            new showCat().execute("");
        }
        else{
            home = 1;
            new showGirl().execute("");
        }
        exec = Executors.newSingleThreadScheduledExecutor();
        future = exec.scheduleAtFixedRate(neki, 10, 5, SECONDS);
    }


    public Runnable neki = new Runnable() {
        String SSID;
        @Override
        public void run(){
            Log.d("0.1 hertz", "1");
            wifiInfo = wifiManager.getConnectionInfo();
            SSID = wifiInfo.getSSID();
            SSID = SSID.substring(1, SSID.length()-1);
            Log.d("SSID: ", SSID);
            Log.d("home: ", Integer.toString(home));
            Log.d("alarmed: ", Integer.toString(alarmed));
            if(home == 0 && alarmed == 0) {

                if (!SSID.equals(homeSSID)) {
                new showButtons().execute("");
                }
            }
             if(home == 1 && alarmed == 0){
                    if(SSID.equals(homeSSID)){
                        home = 0;
                        new showCat().execute("");
                        Log.d("You are: ", "home");
                    }
                }
            }
    };

    private class showButtons extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            alarmed = 1;
            home = 1;
            Log.d("You are: ", "gone");

            Log.d("Showing", "Buttons");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            gZakleni.setVisibility(View.VISIBLE);
            gNisem.setVisibility(View.VISIBLE);
            gLocked.setVisibility(View.VISIBLE);
            cat.setVisibility(View.INVISIBLE);
            girl.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class showCat extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            cat.setVisibility(View.VISIBLE);
            girl.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class showGirl extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            girl.setVisibility(View.VISIBLE);
            cat.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void hideButtons(){
        gZakleni.setVisibility(View.INVISIBLE);
        gNisem.setVisibility(View.INVISIBLE);
        gLocked.setVisibility(View.INVISIBLE);
        girl.setVisibility(View.VISIBLE);
        alarmed = 0;
        Log.d("Hiding", "Buttons");
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

        switch(id){
            case R.id.menu_log:

                Intent log_I = new Intent(context, ListActivity.class);
                ArrayList <Entry> events = new ArrayList<>();
                events = db.getAllEvents();
                if (events.size() > 0) {
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
                break;
            case R.id.menu_reset_database:
                db.Reset();
                break;
            case R.id.menu_help:
                Intent help = new Intent(context, Help.class);
                startActivity(help);
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
