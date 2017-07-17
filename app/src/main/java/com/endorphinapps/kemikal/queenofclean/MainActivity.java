package com.endorphinapps.kemikal.queenofclean;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Finances.Finances_In;
import com.endorphinapps.kemikal.queenofclean.Finances.Finances_out;
import com.endorphinapps.kemikal.queenofclean.Finances.Finances_overview;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

public class MainActivity extends MenuMain
        implements View.OnClickListener {

    private Button btn_viewCustomers;
    private Button btn_viewEmployees;
    private Button btn_viewJob;
    private Button btn_financesIn;
    private Button btn_finances;
    private Button btn_financesOut;

    private NavigationBottom navigationBottom;

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

        btn_viewCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewCustomers.class));
            }
        });

        btn_viewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewEmployees.class));
            }
        });

        btn_viewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewJobs.class));
            }
        });

        btn_financesIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Finances_In.class));
            }
        });

        btn_finances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Finances_overview.class));
            }
        });

        btn_financesOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Finances_out.class));
            }
        });
    }

    /**
     * Find all views by their ID
     */
    private void findViews() {
        btn_viewCustomers = (Button) findViewById(R.id.view_customers);
        btn_viewEmployees = (Button) findViewById(R.id.view_employees);
        btn_viewJob = (Button) findViewById(R.id.view_job);
        btn_financesIn = (Button) findViewById(R.id.view_finances_in);
        btn_finances = (Button) findViewById(R.id.view_finances);
        btn_financesOut = (Button) findViewById(R.id.view_finances_out);
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
