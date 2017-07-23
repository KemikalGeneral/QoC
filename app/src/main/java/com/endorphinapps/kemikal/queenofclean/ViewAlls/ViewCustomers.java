package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.CustomerArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddCustomer;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewCustomers extends MenuMain
        implements View.OnClickListener {

    private TextView tv_employeesTab;
    private TextView tv_emptyList;
    private ListView lv_customersListView;
    private CustomerArrayAdapter arrayAdapter;
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
        setContentView(R.layout.activity_view_customers);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Customers");

        // Instantiate a new DBHelper class
        DBHelper db = new DBHelper(this);

        // Employees Tab
        tv_employeesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewCustomers.this, ViewEmployees.class));
                finish();
            }
        });

        // Populate and setup ListView
        arrayAdapter = new CustomerArrayAdapter(this);
        arrayAdapter.addAll(db.getAllCustomers());
        lv_customersListView.setAdapter(arrayAdapter);

        // If there are no records, show the 'No Customers'
        // message, if there are, hide the message
        if (arrayAdapter.getCount() == 0) {
            tv_emptyList.setVisibility(View.VISIBLE);
        } else {
            tv_emptyList.setVisibility(View.GONE);
        }

        // FAB to add a new customer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewCustomers.this, AddCustomer.class));
                finish();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_employeesTab = (TextView) findViewById(R.id.employees_tab);
        tv_emptyList = (TextView) findViewById(R.id.customers_empty_list);
        lv_customersListView = (ListView) findViewById(R.id.customers_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
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
