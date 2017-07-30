package com.endorphinapps.kemikal.queenofclean.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.DetailViews.DetailJob;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.Locale;

public class JobArrayAdapter extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());
    private long jobId;

    public JobArrayAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_job, parent, false);
        }

        final Job job = getItem(position);

        /*
          Customer details
         */
        // Get Customer from DB by ID
        final Customer customer = db.getCustomerById(job.getCustomer());

        // Fetch, concatenate and populate the first and last name
        final String customerFullName = customer.getFirstName() + " " + customer.getLastName();
        TextView customerFirstLast = (TextView) convertView.findViewById(R.id.full_name);
        customerFirstLast.setText(customerFullName);

        // Fetch and populate the AddressLine1 field
        TextView customerAddress = (TextView) convertView.findViewById(R.id.address_line_1);
        customerAddress.setText(customer.getAddressLine1());

        /**
         * Employee details
         */
        //Get Employee from DB by ID
        final Employee employee = db.getEmployeeById(job.getEmployee());

        // Fetch, concatenate and populate the first and last name
        final String employeeFullName = employee.getFirstName() + " " + employee.getLastName();
        TextView employeeFirstLast = (TextView) convertView.findViewById(R.id.full_name_employee);
        employeeFirstLast.setText(employeeFullName);

        /**
         * Job details
         */
        // Price
        TextView totalPrice = (TextView) convertView.findViewById(R.id.total_price);
        totalPrice.setText("£");
        totalPrice.append(String.format(Locale.getDefault(), "%.2f", job.getTotalPrice()));

        // Estimated Time
        TextView estimatedTime = (TextView) convertView.findViewById(R.id.estimated_time);
        int time = job.getEstimatedTime();
        estimatedTime.setText(String.valueOf(time));
        if (time == 1) {
            estimatedTime.append(" hour");
        } else {
            estimatedTime.append(" hours");
        }

        // Status
        TextView jobStatus = (TextView) convertView.findViewById(R.id.job_status);
        jobStatus.setText(job.getJobStatusEnum());
        // Set status text colour accordingly
        if (job.getJobStatusEnum().equals("Unconfirmed")) {
            jobStatus.setTextColor(Color.LTGRAY);
        } else if (job.getJobStatusEnum().equals("Pending")) {
            jobStatus.setTextColor(Color.DKGRAY);
        } else if (job.getJobStatusEnum().equals("Current")) {
            jobStatus.setTextColor(Color.MAGENTA);
        } else if (job.getJobStatusEnum().equals("Completed")) {
            jobStatus.setTextColor(Color.GREEN);
        } else if (job.getJobStatusEnum().equals("Cancelled")) {
            jobStatus.setTextColor(Color.RED);
        }

        // Start Date
        TextView startDate = (TextView) convertView.findViewById(R.id.job_start_date);
        String startDateFormat = DateFormat.getDateInstance().format(job.getStartDate());
        startDate.setText(startDateFormat);

        // Handle on listView item click and send job ID
        // so that it can be displayed in the DetailJob activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailJob.class);
                intent.putExtra("EXTRAS_jobID", job.getId());
                System.out.println("z! JobsArrayAdapter - onClick - jobID: " + job.getId());
                getContext().startActivity(intent);
            }
        });

        // Handle on listView item long click and send job ID
        // so that it can be used for the ListView contextMenu
        // Returns false to avoid conflicts with the context menu
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setJobId(job.getId());
                return false;
            }
        });

        return convertView;
    }

    public long getJobId() {
        return jobId;
    }

    private void setJobId(long jobId) {
        this.jobId = jobId;
    }
}
