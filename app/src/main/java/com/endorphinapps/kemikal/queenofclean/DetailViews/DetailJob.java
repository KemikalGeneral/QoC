package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.TableRow;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.JobItem;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class DetailJob extends MenuMain {

    private TextView tv_customerName;
    private TextView tv_employeeName;
    private TextView tv_startDate;
    private TextView tv_startTime;
    private TextView tv_jobStatus;
    private TextView tv_estimatedTime;
    private TextView tv_totalPrice;
    private TextView tv_notes;
    private ConstraintLayout tl_jobDetailContainer;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        intent = getIntent();

        // Find all views by ID's
        findViews();

        // Populate all fields of the job from adapter intent
        setAllTextViews();

        // Display all job items
        displayJobItems();
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_customerName = (TextView) findViewById(R.id.full_name_customer);
        tv_employeeName = (TextView) findViewById(R.id.full_name_employee);
        tv_startDate = (TextView) findViewById(R.id.start_date);
        tv_startTime = (TextView) findViewById(R.id.start_time);
        tv_jobStatus = (TextView) findViewById(R.id.status);
        tv_estimatedTime = (TextView) findViewById(R.id.estimated_time);
        tv_totalPrice = (TextView) findViewById(R.id.total_price);
        tv_notes = (TextView) findViewById(R.id.notes);
        tl_jobDetailContainer = (ConstraintLayout) findViewById(R.id.job_detail_container);
    }

    /**
     * Set all text fields with the details from the intent,
     * sent from the jobArrayAdapter
     */
    private void setAllTextViews() {
        Intent intent = getIntent();
        // Get date as a long and format to locale
        String date = DateFormat.getDateInstance()
                .format(intent.getLongExtra("EXTRAS_startDate", 0));
        // Get time as a long and format to hh:mm
        String time = DateFormat.getTimeInstance(DateFormat.SHORT)
                .format(intent.getLongExtra("EXTRAS_startTime", 0));
        // Get string value of estimated time
        int estimatedTime = intent.getIntExtra("EXTRAS_estimatedTime", 0);

        tv_customerName.setText(intent.getStringExtra("EXTRAS_customer_full_name"));
        tv_employeeName.setText(intent.getStringExtra("EXTRAS_employee_full_name"));
        tv_startDate.setText(date);
        tv_startTime.setText(time);
        tv_jobStatus.setText(intent.getStringExtra("EXTRAS_status"));
        tv_estimatedTime.setText(String.valueOf(estimatedTime));
        double totalPrice = intent.getDoubleExtra("EXTRAS_totalPrice", 0.0);
        tv_totalPrice.setText("£");
        tv_totalPrice.append(String.format("%.2f", totalPrice));
        tv_notes.setText(intent.getStringExtra("EXTRAS_notes"));
    }

    /**
     * Create views for, and display all job items.
     */
    private void displayJobItems() {
        ArrayList<JobItem> jobItems;
        DBHelper db = new DBHelper(this);

        // Ge the Job ID
        long id = intent.getIntExtra("EXTRAS_id", 0);

        // Populate jobItems according to the job in the DB
        jobItems = db.getJobItems(id);

        // Create TextViews for descriptions and prices
        TextView description;
        TextView price;
        int length = jobItems.size();

        // Loop through JobItems array,
        // instantiating new TextViews for the
        // descriptions and prices
        for (int i = 0; i < length; i++) {
            // Layout for each item row containing two TextViews
            TableRow jobItemRow = new TableRow(this);

            // Create 'description' TextView and populate it
            // with the jobItemDescription from the jobItem array
            // and add TextView to the row
            description = new TextView(new ContextThemeWrapper(this, R.style.field_end));
            description.setText(jobItems.get(i).getDescription());
            jobItemRow.addView(description);

            // Create 'price' TextView and populate it
            // with the jobItemPrice from the jobItem array
            // and add TextView to the row
            price = new TextView(new ContextThemeWrapper(this, R.style.field_start));
            price.setText("£");
            price.append(String.format("%.2f", jobItems.get(i).getPrice()));
            jobItemRow.addView(price);

            // Add new row to the jobItems container
            tl_jobDetailContainer.addView(jobItemRow);
        }
    }
}
