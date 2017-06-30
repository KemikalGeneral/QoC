package com.endorphinapps.kemikal.queenofclean.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;

/**
 * Created by Kemi on 30/06/2017.
 */

public class FinanceArrayAdapter_out extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());

    public FinanceArrayAdapter_out(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_finances_out, parent, false);
        }

        Job job = getItem(position);
        Employee employee = db.getEmployeeById(job.getEmployee());

        // Fetch, concatenate and display
        // employee first and last names
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        TextView employeeFirstLast = (TextView) convertView.findViewById(R.id.finances_employee);
        employeeFirstLast.setText("Employee: " + fullName);

        // Fetch and display the date
        //TODO - change to endDate
        TextView startDate = (TextView) convertView.findViewById(R.id.finances_date);
        String date = DateFormat.getDateInstance().format(job.getStartDate());
        startDate.setText("Date: " + date);

        // Fetch and display the employee rate of pay
        TextView rateOfPay = (TextView) convertView.findViewById(R.id.rate_of_pay);
        rateOfPay.setText("Rate of Pay: " + employee.getRateOfPay());

        // Fetch and display estimated time required
        //TODO - change to actual time taken
        TextView estimatedTime = (TextView) convertView.findViewById(R.id.estimated_time);
        estimatedTime.setText("Hours: " + String.valueOf(job.getEstimatedTime()));

        // Calculate the pay to employee for the job
        TextView payForJob = (TextView) convertView.findViewById(R.id.finances_amount);
        double pay = employee.getRateOfPay() * job.getEstimatedTime();
        payForJob.setText("Job Pay: " + String.valueOf(pay));

        return convertView;
    }
}
