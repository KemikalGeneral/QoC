package com.endorphinapps.kemikal.queenofclean.ViewAlls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.JobArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddJob;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.DetailViews.DetailJob;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.R;

public class ViewJobs extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    //    private long jobId;
    private TextView tv_emptyList;
    private JobArrayAdapter arrayAdapter;
    private ListView lv_jobsListView;
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
     * ViewJobs ListView item to change the job status
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
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle().equals("Unconfirmed")) {
            db.changeJobStatus(info.id + 1, item.getTitle().toString());
        } else if (item.getTitle().equals("Pending")) {
            db.changeJobStatus(info.id + 1, item.getTitle().toString());
        } else if (item.getTitle().equals("Current")) {
            db.changeJobStatus(info.id + 1, item.getTitle().toString());
        } else if (item.getTitle().equals("Completed")) {
            db.changeJobStatus(info.id + 1, item.getTitle().toString());
        } else if (item.getTitle().equals("Cancelled")) {
            db.changeJobStatus(info.id + 1, item.getTitle().toString());
        }
        // Recreate the activity to apply changes
        recreate();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jobs");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Populate and setup ListView
        arrayAdapter = new JobArrayAdapter(this);
        arrayAdapter.addAll(db.getAllJobs());
        lv_jobsListView.setAdapter(arrayAdapter);

        // Register for long clickable context menu
        // used to change the job status
        registerForContextMenu(lv_jobsListView);

        // Handle on listView item click and send job ID
        // so that it can be displayed in the DetailJob activity
        lv_jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewJobs.this, DetailJob.class);
                intent.putExtra("EXTRAS_jobID", id + 1);
                startActivity(intent);
            }
        });

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
                startActivity(new Intent(ViewJobs.this, AddJob.class));
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

    /**
     * BottomNavigation onClick method.
     * View is the icon clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        navigationBottom = new NavigationBottom(this);
        navigationBottom.onClick(v);
    }
}
