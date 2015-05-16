package com.janmalec.jan.doorlock;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    private Button gZakleni;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MySQLiteHelper db = new MySQLiteHelper(this);

        gZakleni = (Button)findViewById(R.id.button_zakleni);

        gZakleni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Zaklenil si vrata",
                        Toast.LENGTH_LONG);
                db.addEvent(new Entry(10, 1));

                msg.show();
            }
        });



        db.addEvent(new Entry(10, 1));
        db.addEvent(new Entry(10, 1));
        db.getEvent(0);

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
