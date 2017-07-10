package com.endorphinapps.kemikal.queenofclean.Adapters;


import android.content.Context;
import android.content.Intent;
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

/**
 * Created by User on 13/02/2017.
 */

public class JobArrayAdapter extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());

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

        /**
         * Customer details
         */
        // Get Customer from DB by ID
        final Customer customer = db.getCustomerById(job.getCustomer());

        // Fetch, concatenate and populate the first and last name
        final String customerFullName = customer.getFirstName() + " " + customer.getLastName();
        TextView customerFirstLast = (TextView) convertView.findViewById(R.id.full_name_customer);
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

        // On click of listView item, send details to the DetailView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailJob.class);
                intent.putExtra("EXTRAS_id", job.getId());
                intent.putExtra("EXTRAS_customer_full_name", customerFullName);
                intent.putExtra("EXTRAS_employee_full_name", employeeFullName);
                intent.putExtra("EXTRAS_startDate", job.getStartDate());
                intent.putExtra("EXTRAS_startTime", job.getStartTime());
                intent.putExtra("EXTRAS_status", job.getJobStatusEnum());
                intent.putExtra("EXTRAS_estimatedTime", job.getEstimatedTime());
                intent.putExtra("EXTRAS_totalPrice", job.getTotalPrice());
                intent.putExtra("EXTRAS_notes", job.getNotes());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
