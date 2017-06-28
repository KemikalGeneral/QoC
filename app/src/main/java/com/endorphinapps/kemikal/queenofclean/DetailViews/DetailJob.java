package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.JobItem;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class DetailJob extends AppCompatActivity {

    private TextView tv_customerName;
    private TextView tv_employeeName;
    private TextView tv_startDate;
    private TextView tv_jobStatus;
    private TextView tv_estimatedTime;
    private TextView tv_totalPrice;
    private TextView tv_notes;
    private LinearLayout ll_jobItemsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        Intent intent = getIntent();

        findViews();
        setAllTextViews();

        //TODO - jobItems - styling
        ArrayList<JobItem> jobItems;
        DBHelper db = new DBHelper(this);

        long id = intent.getIntExtra("EXTRAS_id", 0);
        System.out.println("z! id: " + id);

        // Populate jobItems according to the job in the DB
        jobItems = db.getJobItems(id);

        TextView description;
        TextView price;
        int length = jobItems.size();

        for (int i = 0; i < length; i++) {
            // Layout for each item row containing two TextViews
            LinearLayout jobItemRow = new LinearLayout(this);

            // Create 'description' TextView and populate it
            // with the jobItemDescription from the jobItem array
            description = new TextView(this);
            description.setText(jobItems.get(i).getDescription());
            jobItemRow.addView(description);

            // Create 'price' TextView and populate it
            // with the jobItemPrice from the jobItem array
            price = new TextView(this);
            price.setText(String.valueOf(jobItems.get(i).getPrice()));
            jobItemRow.addView(price);

            // Add new layout to the jobItems container
            ll_jobItemsContainer.addView(jobItemRow);
        }

    }

    private void findViews() {
        tv_customerName = (TextView) findViewById(R.id.first_name_customer);
        tv_employeeName = (TextView) findViewById(R.id.first_name_employee);
        tv_startDate = (TextView) findViewById(R.id.start_date);
        tv_jobStatus = (TextView) findViewById(R.id.status);
        tv_estimatedTime = (TextView) findViewById(R.id.estimated_time);
        tv_totalPrice = (TextView) findViewById(R.id.job_total_price);
        tv_notes = (TextView) findViewById(R.id.notes);
        ll_jobItemsContainer = (LinearLayout) findViewById(R.id.job_item_container);
    }

    private void setAllTextViews() {
        Intent intent = getIntent();
        tv_customerName.setText(intent.getStringExtra("EXTRAS_customer"));
        tv_employeeName.setText(intent.getStringExtra("EXTRAS_employee"));
        // Get date as a long and format to locale
        String date = DateFormat.getDateInstance()
                .format(intent.getLongExtra("EXTRAS_startDate", 0));
        tv_startDate.setText(date);
        tv_jobStatus.setText(intent.getStringExtra("EXTRAS_status"));
        // Get string value of estimated time
        int time = intent.getIntExtra("EXTRAS_estimatedTime", 0);
        tv_estimatedTime.setText(String.valueOf(time));
        tv_totalPrice.setText("£" + String.valueOf(intent.getDoubleExtra("EXTRAS_totalPrice", 0.0)));
        tv_notes.setText(intent.getStringExtra("EXTRAS_notes"));
    }
}
