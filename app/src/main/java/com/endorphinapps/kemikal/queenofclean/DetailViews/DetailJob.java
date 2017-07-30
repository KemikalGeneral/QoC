package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.EditRecords.EditJob;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.Entities.JobItem;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

public class DetailJob extends MenuMain {

    private DBHelper db;
    private Job job;
    private Customer customer;
    private Employee employee;
    private Button btnEdit;
    private Button btnDelete;
    private TextView tv_customerName;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;
    private TextView tv_employeeName;
    private TextView tv_startDate;
    private TextView tv_startTime;
    private TextView tv_jobStatus;
    private TextView tv_customerPaymentStatus;
    private TextView tv_employeePaymentStatus;
    private TextView tv_estimatedTime;
    private TextView tv_totalPrice;
    private TextView tv_notes;
    private ConstraintLayout tl_jobDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        // Find all views by ID's
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jobs");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Get current Job from ID
        Intent intent = getIntent();
        final long jobId = intent.getLongExtra("EXTRAS_jobID", 0);
        job = db.getJobById(jobId);

        // Populate all fields of the job
        setAllTextViews();

        // Display all job items
        displayJobItems();

        // Go to the EditJob activity
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(DetailJob.this, EditJob.class);
                editIntent.putExtra("EXTRAS_id", jobId);
                startActivity(editIntent);
                finish();
            }
        });

        // Delete the job, and the jobItems with the jobId
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteJobById(jobId);
                db.deleteJobItemByJobId(jobId);
                Intent deleteIntent = new Intent(DetailJob.this, ViewJobs.class);
                startActivity(deleteIntent);
                finish();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        btnEdit = (Button) findViewById(R.id.btn_edit_job);
        btnDelete = (Button) findViewById(R.id.btn_delete_job);
        tv_customerName = (TextView) findViewById(R.id.full_name_customer);
        tv_addressLine1 = (TextView) findViewById(R.id.detail_Job_address_1);
        tv_addressLine2 = (TextView) findViewById(R.id.detail_Job_address_2);
        tv_town = (TextView) findViewById(R.id.detail_Job_address_town);
        tv_city = (TextView) findViewById(R.id.detail_Job_address_city);
        tv_postcode = (TextView) findViewById(R.id.detail_Job_address_postcode);
        tv_employeeName = (TextView) findViewById(R.id.full_name_employee);
        tv_startDate = (TextView) findViewById(R.id.start_date);
        tv_startTime = (TextView) findViewById(R.id.start_time);
        tv_jobStatus = (TextView) findViewById(R.id.job_status);
        tv_customerPaymentStatus = (TextView) findViewById(R.id.customer_payment_status);
        tv_employeePaymentStatus = (TextView) findViewById(R.id.employee_payment_status);
        tv_estimatedTime = (TextView) findViewById(R.id.estimated_time);
        tv_totalPrice = (TextView) findViewById(R.id.total_price);
        tv_notes = (TextView) findViewById(R.id.notes);
        tl_jobDetailContainer = (ConstraintLayout) findViewById(R.id.job_detail_container);
    }

    /**
     * Set all text fields with the details from the DB,
     * using the ID from the Adapter  Intent
     */
    private void setAllTextViews() {
        customer = db.getCustomerById(job.getCustomer());
        employee = db.getEmployeeById(job.getEmployee());
        // Get date as a long and format to locale
        String date = DateFormat.getDateInstance()
                .format(job.getStartDate());
        // Get time as a long and format to hh:mm
        String time = DateFormat.getTimeInstance(DateFormat.SHORT)
                .format(job.getStartTime());
        // Set TextViews
        tv_customerName.setText(customer.getFirstName() + " " + customer.getLastName());
        tv_addressLine1.setText(customer.getAddressLine1());
        tv_addressLine2.setText(customer.getAddressLine2());
        tv_town.setText(customer.getTown());
        tv_city.setText(customer.getCity());
        tv_postcode.setText(customer.getPostcode());
        tv_employeeName.setText(employee.getFirstName() + " " + employee.getLastName());
        tv_startDate.setText(date);
        tv_startTime.setText(time);
        tv_jobStatus.setText(job.getJobStatusEnum());
        tv_customerPaymentStatus.setText(job.getCustomerPaymentStatusEnum());
        tv_employeePaymentStatus.setText(job.getEmployeePaymentStatusEnum());
        tv_estimatedTime.setText(String.valueOf(job.getEstimatedTime()));
        tv_totalPrice.setText("£");
        tv_totalPrice.append(String.format(Locale.getDefault(), "%.2f", job.getTotalPrice()));
        tv_notes.setText(job.getNotes());
    }

    /**
     * Create views for, and display all job items.
     */
    private void displayJobItems() {
        // Create a list of JobItems, using the current JobId
        ArrayList<JobItem> jobItems;
        jobItems = db.getJobItemsByJobId(job.getId());

        // Iterate through the list of jobsItems and create
        // and populate the description and price fields
        // of each JobItem
        int arraySize = jobItems.size();
        for (int i = 0; i < arraySize; i++) {

            //Create a new row container
            LinearLayout jobRowContainer = new LinearLayout(this);

            //Create a new EditText for the Description
            TextView description = new TextView(this);
            description.setId(R.id.add_item_description);
            // Set current description
            description.setText(jobItems.get(i).getDescription());
            description.setTextSize(18);
            description.setPadding(0, 16, 0, 16);
            description.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));

            //Create an EditText for the Price
            //Will only accept a decimal number
            TextView price = new TextView(this);
            price.setId(R.id.add_item_price);
            price.setInputType(InputType.TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
            // Ser current price, formatted to two decimal places
            price.setText("£");
            price.append(String.format(Locale.getDefault(), "%.2f", jobItems.get(i).getPrice()));
            price.setTextSize(18);

            //Add Description and Price to the row container
            jobRowContainer.addView(description);
            jobRowContainer.addView(price);

            //Add the Row Container to the Jobs List Container
            LinearLayout v = (LinearLayout) findViewById(R.id.detail_test_item_row);
            v.addView(jobRowContainer);
        }
    }
}
