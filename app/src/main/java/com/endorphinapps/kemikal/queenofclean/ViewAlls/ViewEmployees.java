package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.endorphinapps.kemikal.queenofclean.Adapters.EmployeeArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewEmployees extends AppCompatActivity {

    private ListView lv_employeesListView;
    private EmployeeArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);

        findViews();

        DBHelper db = new DBHelper(this);

        arrayAdapter = new EmployeeArrayAdapter(this);
        arrayAdapter.addAll(db.getAllEmployees());
        lv_employeesListView.setAdapter(arrayAdapter);
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        lv_employeesListView = (ListView) findViewById(R.id.employees_list_view);
    }
}
