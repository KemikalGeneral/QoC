package com.endorphinapps.kemikal.queenofclean;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;

/**
 * Main Options menu that runs throughout the app.
 * It is extended by all activity classes that use it.
 */
public class MenuMain extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_drop_tables:
                db.dropTables();
                Toast.makeText(getApplicationContext(), "Tables Dropped", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_main_about:
                //TODO - add a credits and contacts page
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
    }
}
