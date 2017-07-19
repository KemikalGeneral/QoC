package com.endorphinapps.kemikal.queenofclean;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;

public class MainActivity extends MenuMain
        implements View.OnClickListener {

    private TextView tv_monday;
    private TextView tv_tuesday;
    private TextView tv_wednesday;
    private TextView tv_thursday;
    private TextView tv_friday;
    private TextView tv_saturday;
    private TextView tv_sunday;
    private TextView tv_mondayIndicator;
    private TextView tv_tuesdayIndicator;
    private TextView tv_wednesdayIndicator;
    private TextView tv_thursdayIndicator;
    private TextView tv_fridayIndicator;
    private TextView tv_saturdayIndicator;
    private TextView tv_sundayIndicator;
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
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(
                dbHelper.getDatabaseName(), MODE_PRIVATE, null, null);

        dbHelper.onCreate(sqLiteDatabase);

        // Set levels of day views
        tv_monday.getBackground().setLevel(10000);
        tv_tuesday.getBackground().setLevel(3000);
        tv_wednesday.getBackground().setLevel(8000);
        tv_thursday.getBackground().setLevel(1000);
        tv_friday.getBackground().setLevel(6000);
        tv_saturday.getBackground().setLevel(5000);
        tv_sunday.getBackground().setLevel(9000);



    }

    /**
     * Find all views by their ID
     */
    private void findViews() {
        tv_monday = (TextView) findViewById(R.id.monday);
        tv_tuesday = (TextView) findViewById(R.id.tuesday);
        tv_wednesday = (TextView) findViewById(R.id.wednesday);
        tv_thursday = (TextView) findViewById(R.id.thursday);
        tv_friday = (TextView) findViewById(R.id.friday);
        tv_saturday = (TextView) findViewById(R.id.saturday);
        tv_sunday = (TextView) findViewById(R.id.sunday);
        tv_mondayIndicator = (TextView) findViewById(R.id.monday_indicator);
        tv_tuesdayIndicator = (TextView) findViewById(R.id.tuesday_indicator);
        tv_wednesdayIndicator = (TextView) findViewById(R.id.wednesday_indicator);
        tv_thursdayIndicator = (TextView) findViewById(R.id.thursday_indicator);
        tv_fridayIndicator = (TextView) findViewById(R.id.friday_indicator);
        tv_saturdayIndicator = (TextView) findViewById(R.id.saturday_indicator);
        tv_sundayIndicator = (TextView) findViewById(R.id.sunday_indicator);
    }

    /**
     * BottomNavigation onClick method.
     * View is the icon clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        navigationBottom = new NavigationBottom(this);
        navigationBottom.onClick(v);
    }
}
