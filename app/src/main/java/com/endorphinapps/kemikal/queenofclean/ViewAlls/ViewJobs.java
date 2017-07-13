package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.JobArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddJob;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewJobs extends MenuMain {

    private TextView tv_emptyList;

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

        // If there are no records, show the 'No Jobs'
        // message, if there are, hide the message
        if (arrayAdapter.getCount() == 0) {
            tv_emptyList.setVisibility(View.VISIBLE);
        } else {
            tv_emptyList.setVisibility(View.GONE);
        }

        // FAB to add a new Job
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewJobs.this, AddJob.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_emptyList = (TextView) findViewById(R.id.jobs_empty_list);
        lv_jobsListView = (ListView) findViewById(R.id.jobs_list_view);
        fab = (FloatingActionButton) findViewById(R.id.FAB);
    }
}
