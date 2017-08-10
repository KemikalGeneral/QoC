package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.DayJobsArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddJob;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.JobsClass;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.R;

import java.util.ArrayList;

public class ViewDayJobs extends AppCompatActivity
        implements View.OnClickListener {

    private DBHelper db;
    private DayJobsArrayAdapter dayJobsArrayAdapter;
    private ListView lv_dayList;
    private ArrayList<Job> jobs;
    private TextView tv_emptyList;
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

    /**
     * Create a context menu on a long press of the
     * ViewDayJobs ListView item to change the job status
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Set menu options
        menu.setHeaderTitle("Change Job Status to...");
        menu.add(0, v.getId(), 0, "Unconfirmed");
        menu.add(0, v.getId(), 0, "Pending");
        menu.add(0, v.getId(), 0, "Current");
        menu.add(0, v.getId(), 0, "Completed");
        menu.add(0, v.getId(), 0, "Cancelled");
        menu.add(0, v.getId(), 0, "Quote");
    }

    /**
     * Action the item selected in the context menu
     * by calling changeJobStatus() and passing the JobId
     * and the amended status
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        long jobId = dayJobsArrayAdapter.getJobId();

        if (item.getTitle().equals("Unconfirmed")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        } else if (item.getTitle().equals("Pending")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        } else if (item.getTitle().equals("Current")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        } else if (item.getTitle().equals("Completed")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        } else if (item.getTitle().equals("Cancelled")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        } else if (item.getTitle().equals("Quote")) {
            db.changeJobStatus(jobId, item.getTitle().toString());
        }
        System.out.println("z! ViewDayJobs - onContextItemSelected - jobId:" + jobId + " - " + item.getTitle().toString());

        // Recreate the activity to apply changes
        recreate();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        // Find views by  ID
        findViews();

        // Instantiate new DBHelper and JobsClass classes
        db = new DBHelper(this);
        JobsClass jobsClass = new JobsClass(db);

        // Filter Jobs by this week, and then days
        jobsClass.getJobsByDateRange(0);
        jobsClass.sortJobsByDayOfWeek();

        // Get the day of the week from the (MainActivity) intent
        // populate Jobs ArrayList with the relevant days jobs
        Intent intent = getIntent();
        final String day = intent.getStringExtra("EXTRAS_day");
        switch (day) {
            case "monday":
                jobs = jobsClass.getMondaysJobs();
                break;
            case "tuesday":
                jobs = jobsClass.getTuesdaysJobs();
                break;
            case "wednesday":
                jobs = jobsClass.getWednesdaysJobs();
                break;
            case "thursday":
                jobs = jobsClass.getThursdaysJobs();
                break;
            case "friday":
                jobs = jobsClass.getFridaysJobs();
                break;
            case "saturday":
                jobs = jobsClass.getSaturdaysJobs();
                break;
            case "sunday":
                jobs = jobsClass.getSundaysJobs();
                break;
        }
        dayJobsArrayAdapter = new DayJobsArrayAdapter(this);
        dayJobsArrayAdapter.addAll(jobs);
        lv_dayList.setAdapter(dayJobsArrayAdapter);

        // Register for long clickable context menu
        // used to change the job status
        registerForContextMenu(lv_dayList);

        // If there are no records, show the 'No Jobs'
        // message, if there are, hide the message
        if (dayJobsArrayAdapter.getCount() == 0) {
            tv_emptyList.setVisibility(View.VISIBLE);
        } else {
            tv_emptyList.setVisibility(View.GONE);
        }

        // On FAB click, start the AddJob activity passing the day of the week
        // which will be used to add a job for that day
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewDayJobs.this, AddJob.class);
                intent1.putExtra("addJobDay", day);
                startActivity(intent1);
                finish();
            }
        });
    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        lv_dayList = (ListView) findViewById(R.id.day_view_listview);
        tv_emptyList = (TextView) findViewById(R.id.day_empty_list);
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
