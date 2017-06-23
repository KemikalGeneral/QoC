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

/**
 * Created by User on 13/02/2017.
 */

public class JobArrayAdapter extends ArrayAdapter<Job> {

    DBHelper db = new DBHelper(getContext());

    public JobArrayAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_list_item, parent, false);
        }

        Job job = getItem(position);

        /**
         * Customer details
         */
        //Get Customer from DB by ID
        Customer customer = db.getCustomerById(job.getCustomer());
        //Customer First Name
        TextView customerFirstName = (TextView) convertView.findViewById(R.id.first_name_customer);
        customerFirstName.setText(customer.getFirstName());
        //Customer Last Name
        TextView customerLastName = (TextView) convertView.findViewById(R.id.last_name_customer);
        customerLastName.setText(customer.getLastName());
        //Customer Address Line 1
        TextView customerAddress = (TextView) convertView.findViewById(R.id.address_line_1);
        customerAddress.setText(customer.getAddressLine1());

        /**
         * Employee details
         */
        //Get Employee from DB by ID
        Employee employee = db.getEmployeeById(job.getEmployee());
        //Employee First Name
        TextView employeeFirstName = (TextView) convertView.findViewById(R.id.first_name_employee);
        employeeFirstName.setText(employee.getFirstName());
        //Employee Last Name
        TextView employeeLastName = (TextView) convertView.findViewById(R.id.last_name_employee);
        employeeLastName.setText(employee.getLastName());

        return convertView;
    }
}
