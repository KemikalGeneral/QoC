package com.endorphinapps.kemikal.queenofclean;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;

public class MainActivity extends MenuMain
        implements View.OnClickListener {

    private NavigationBottom navigationBottom;

    /**
     * Exit the app on back press
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Queen of Clean");

        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase sqLiteDatabase =
                openOrCreateDatabase(
                        dbHelper.getDatabaseName(), MODE_PRIVATE, null, null);

        dbHelper.onCreate(sqLiteDatabase);
    }

    /**
     * Find all views by their ID
     */
    private void findViews() {
    }

    /**
     * BottomNavigation onClick method.
     * View is the icon clicked.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        navigationBottom = new NavigationBottom(this);
        navigationBottom.onClick(v);
    }
}
