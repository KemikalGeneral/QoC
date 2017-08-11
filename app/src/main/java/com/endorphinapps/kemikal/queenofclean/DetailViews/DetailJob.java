package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.telephony.SmsManager;
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
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.ConfirmationDialog;
import com.endorphinapps.kemikal.queenofclean.Globals.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

public class DetailJob extends MenuMain implements ConfirmationDialog.ConfirmationDialogListener {

    private DBHelper db;
    private Customer customer;
    private Employee employee;
    private Job job;
    private long jobId;
    private TextView tv_customerName;
    private TextView tv_homeNumber;
    private TextView tv_mobileNumber;
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
    private Button btn_edit;
    private Button btn_delete;
    private Button btn_sms;

    private StringBuilder message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        // Find all views by ID's
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jobs");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Get current Job from ID
        Intent intent = getIntent();
        jobId = intent.getLongExtra("EXTRAS_jobID", 0);
        job = db.getJobById(jobId);

        // Populate all fields of the job
        setAllTextViews();

        // Display all job items
        displayJobItems();

        // Go to the EditJob activity
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(DetailJob.this, EditJob.class);
                editIntent.putExtra("EXTRAS_id", jobId);
                startActivity(editIntent);
                finish();
            }
        });

        // Delete the job, and the jobItems with the jobId
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog yes/no to delete
                showConfirmationDialog();
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send SMS after permissions are granted
                checkPermissionsForSMS();
            }
        });
    }

    /**
     * Find all views by ID
     */
    private void findViews() {
        tv_customerName = (TextView) findViewById(R.id.full_name_customer);
        tv_homeNumber = (TextView) findViewById(R.id.home_number);
        tv_mobileNumber = (TextView) findViewById(R.id.mobile_number);
        tv_addressLine1 = (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.town);
        tv_city = (TextView) findViewById(R.id.city);
        tv_postcode = (TextView) findViewById(R.id.postcode);
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
        btn_edit = (Button) findViewById(R.id.btn_edit_job);
        btn_delete = (Button) findViewById(R.id.btn_delete_job);
        btn_sms = (Button) findViewById(R.id.btn_sms_job);
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
        tv_homeNumber.setText(customer.getHomeNumber());
        tv_mobileNumber.setText(customer.getMobileNumber());
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
        if (job.getEstimatedTime() == 1) {
            tv_estimatedTime.append(" hour");
        } else {
            tv_estimatedTime.append(" hours");
        }
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
            LinearLayout v = (LinearLayout) findViewById(R.id.item_row_container);
            v.addView(jobRowContainer);
        }
    }

    //////////////////////////////////////////////////
    //      SMS
    //////////////////////////////////////////////////
    /**
     * Check permissions for sending an SMS
     */
    private void checkPermissionsForSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            sendSMS();
        }
    }

    /**
     * Call to sendSMS() if the permissions are granted
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    System.out.println("DetailJob - Message  NOT sent to");
                }
        }
    }

    /**
     * Get the mobile number of the Employee, build a message and send it off
     */
    private void sendSMS() {
        String smsPhoneNumber = employee.getMobileNumber();
        message = new StringBuilder();
        // Get date as a long and format to locale
        String date = DateFormat.getDateInstance().format(job.getStartDate());
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(job.getStartTime());
        message
                .append("Hi ")
                .append(employee.getFirstName() + ", ")
                .append("On ")
                .append(date + " ")
                .append("@ ")
                .append(time + " ")
                .append("there's a job at ")
                .append(customer.getAddressLine1() + ", " + customer.getTown() + ".\n\n")
                .append("The customer is " + customer.getFirstName() + " " + customer.getLastName() + " ")
                .append("and they need you to ");

        // Get all the jobItems
        ArrayList<JobItem> descriptions;
        descriptions = db.getJobItemsByJobId(jobId);

        // Cycle through the jobItems and pull out the descriptions
        int arrayLength = descriptions.size();
        for (int i = 0; i < arrayLength; i++) {
            // insert and 'and', before the last item
            if (i == descriptions.size() - 1) {
                message.append("and ");
            }
            message
                    .append(descriptions.get(i).getDescription() + ", ");
        }

        // Estimated hours
        message
                .append("so I've put you down for " + job.getEstimatedTime() + " ");
        // 'hour' or 'hours'
        if (job.getEstimatedTime() == 1) {
            message.append("hour.");
        } else {
            message.append("hours.");
        }

        message.append("\n\n");

        // Notes
        if (job.getNotes().length() > 0) {
            message.append("Notes: " + job.getNotes());
        } else {
            message.append("There are no extra notes for this job.");
        }

        // Send message
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message.toString());
        smsManager.sendMultipartTextMessage(smsPhoneNumber, null, parts, null, null);
    }

    //////////////////////////////////////////////////
    //      Confirmation Dialog
    //////////////////////////////////////////////////
    /**
     * Instantiate an show new ConfirmationDialog class and pass through the dialog message
     */
    private void showConfirmationDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.setMessage("Delete this Job?");
        confirmationDialog.show(getFragmentManager(), "deleteJob");
    }

    /**
     * On positive click, remove the Customer by ID and start the ViewJobs activity
     *
     * @param dialogFragment
     */
    @Override
    public void dialogPositiveClick(DialogFragment dialogFragment) {
        db.deleteJobById(jobId);
        db.deleteJobItemByJobId(jobId);
        startActivity(new Intent(DetailJob.this, ViewJobs.class));
        finish();
    }

    /**
     * On negative click, dismiss dialog and do nothing
     *
     * @param dialogFragment
     */
    @Override
    public void dialogNegativeClick(DialogFragment dialogFragment) {

    }
}
