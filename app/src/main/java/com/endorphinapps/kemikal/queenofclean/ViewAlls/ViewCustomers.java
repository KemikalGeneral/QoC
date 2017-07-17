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
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewCustomers extends MenuMain {

    private TextView tv_emptyList;

    private ListView lv_customersListView;
    private CustomerArrayAdapter arrayAdapter;

    private FloatingActionButton fab;

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

        System.out.println("Number of records in ViewCustomers: " + arrayAdapter.getCount());

        // FAB to add a new customer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCustomers.this, AddCustomer.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_emptyList = (TextView) findViewById(R.id.customers_empty_list);
        lv_customersListView = (ListView) findViewById(R.id.customers_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
    }
}
