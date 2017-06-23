package com.endorphinapps.kemikal.queenofclean;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.endorphinapps.kemikal.queenofclean.Adapters.JobArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;

public class ViewJobs extends AppCompatActivity {

    private ListView lv_jobsListView;
    private JobArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        findViews();

        DBHelper db = new DBHelper(this);

        arrayAdapter = new JobArrayAdapter(this);
        arrayAdapter.addAll(db.getAllJobs());
        lv_jobsListView.setAdapter(arrayAdapter);
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        lv_jobsListView = (ListView) findViewById(R.id.jobs_list_view);
    }
}
