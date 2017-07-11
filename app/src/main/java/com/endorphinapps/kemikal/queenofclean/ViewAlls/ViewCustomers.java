package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.endorphinapps.kemikal.queenofclean.Adapters.CustomerArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddCustomer;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewCustomers extends AppCompatActivity {

    private ListView lv_customersListView;
    private CustomerArrayAdapter arrayAdapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        // Find all views by ID
        findViews();

        // Instantiate a new DBHelper class
        DBHelper db = new DBHelper(this);

        // Populate and setup ListView
        arrayAdapter = new CustomerArrayAdapter(this);
        arrayAdapter.addAll(db.getAllCustomers());
        lv_customersListView.setAdapter(arrayAdapter);

        // FAB to add a new customer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCustomers.this, AddCustomer.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        lv_customersListView = (ListView) findViewById(R.id.customers_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
    }
}
