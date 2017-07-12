package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.EmployeeArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddEmployee;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewEmployees extends AppCompatActivity {

    private TextView tv_emptyList;

    private ListView lv_employeesListView;
    private EmployeeArrayAdapter arrayAdapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);

        // Find all views by ID
        findViews();

        // Instantiate a new DBHelper class
        DBHelper db = new DBHelper(this);

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
                Intent intent = new Intent(ViewEmployees.this, AddEmployee.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_emptyList = (TextView) findViewById(R.id.employees_empty_list);
        lv_employeesListView = (ListView) findViewById(R.id.employees_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
    }
}
