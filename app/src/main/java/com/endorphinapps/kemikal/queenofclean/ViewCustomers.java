package com.endorphinapps.kemikal.queenofclean;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.endorphinapps.kemikal.queenofclean.Adapters.CustomerArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;

public class ViewCustomers extends AppCompatActivity {

    private ListView lv_customersListView;
    private CustomerArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        findViews();

        DBHelper db = new DBHelper(this);

        arrayAdapter = new CustomerArrayAdapter(this);
        arrayAdapter.addAll(db.getAllCustomers());
        lv_customersListView.setAdapter(arrayAdapter);
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        lv_customersListView = (ListView) findViewById(R.id.customers_list_view);
    }
}
