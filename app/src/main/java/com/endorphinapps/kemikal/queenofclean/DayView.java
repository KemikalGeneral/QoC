package com.endorphinapps.kemikal.queenofclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.DayJobsArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.util.ArrayList;

public class DayView extends AppCompatActivity
        implements View.OnClickListener {

    private DBHelper db;
    private DayJobsArrayAdapter dayJobsArrayAdapter;
    private ListView lv_dayList;
    private ArrayList<Job> jobs;
    private TextView tv_emptyList;
    private NavigationBottom navigationBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

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
        String day = intent.getStringExtra("EXTRAS_day");
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

        // If there are no records, show the 'No Jobs'
        // message, if there are, hide the message
        if (dayJobsArrayAdapter.getCount() == 0) {
            tv_emptyList.setVisibility(View.VISIBLE);
        } else {
            tv_emptyList.setVisibility(View.GONE);
        }
    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        lv_dayList = (ListView) findViewById(R.id.day_view_listview);
        tv_emptyList = (TextView) findViewById(R.id.day_empty_list);
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
