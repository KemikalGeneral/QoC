package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.EmployeeArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddEmployee;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.MenuMain;
import com.endorphinapps.kemikal.queenofclean.Globals.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewEmployees extends MenuMain
        implements View.OnClickListener {

    private TextView tv_customersTab;
    private TextView tv_emptyList;
    private ListView lv_employeesListView;
    private EmployeeArrayAdapter arrayAdapter;
    private FloatingActionButton fab;
    private NavigationBottom navigationBottom;

    /**
     * Go back to the MainActivity on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Employees");

        // Instantiate a new DBHelper class
        DBHelper db = new DBHelper(this);

        // Customers tab
        tv_customersTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewEmployees.this, ViewCustomers.class));
                finish();
            }
        });

        // Populate and setup ListView
        arrayAdapter = new EmployeeArrayAdapter(this);
        arrayAdapter.addAll(db.getAllEmployees());
        lv_employeesListView.setAdapter(arrayAdapter);

        // If there are no records, show the 'No Employees'
        // message, if there are, hide the message
        if (arrayAdapter.getCount() == 0) {
            tv_emptyList.setVisibility(View.VISIBLE);
        } else {
            tv_emptyList.setVisibility(View.GONE);
        }

        // FAB to add a new employee
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewEmployees.this, AddEmployee.class));
                finish();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_customersTab = (TextView) findViewById(R.id.customer_tab);
        tv_emptyList = (TextView) findViewById(R.id.employees_empty_list);
        lv_employeesListView = (ListView) findViewById(R.id.employees_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
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
