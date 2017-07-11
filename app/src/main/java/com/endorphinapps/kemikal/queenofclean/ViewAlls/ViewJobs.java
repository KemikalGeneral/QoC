package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.endorphinapps.kemikal.queenofclean.Adapters.JobArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddJob;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewJobs extends AppCompatActivity {

    private ListView lv_jobsListView;
    private JobArrayAdapter arrayAdapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        // Find all views by ID
        findViews();

        // Instantiate a new DBHelper class
        DBHelper db = new DBHelper(this);

        // Populate and setup ListView
        arrayAdapter = new JobArrayAdapter(this);
        arrayAdapter.addAll(db.getAllJobs());
        lv_jobsListView.setAdapter(arrayAdapter);

        // FAB to add a new Job
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewJobs.this, AddJob.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        lv_jobsListView = (ListView) findViewById(R.id.jobs_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
    }
}
