package com.endorphinapps.kemikal.queenofclean.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.Locale;

public class DayJobsArrayAdapter extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());

    public DayJobsArrayAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_day_view, parent, false);
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

        /*
          Employee details
         */
        //Get Employee from DB by ID
        final Employee employee = db.getEmployeeById(job.getEmployee());

        // Fetch, concatenate and populate the first and last name
        final String employeeFullName = employee.getFirstName() + " " + employee.getLastName();
        TextView employeeFirstLast = (TextView) convertView.findViewById(R.id.full_name_employee);
        employeeFirstLast.setText(employeeFullName);

        /*
          Job details
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

        // Start Time
        TextView startTime = (TextView) convertView.findViewById(R.id.job_start_time);
        String startDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(job.getStartTime());
        startTime.setText(startDateFormat);

        return convertView;
    }
}
