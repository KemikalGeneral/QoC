package com.endorphinapps.kemikal.queenofclean.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class FinanceArrayAdapter_out extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());
    private long jobId;

    public FinanceArrayAdapter_out(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_finances_out, parent, false);
        }

        final Job job = getItem(position);
        Employee employee = db.getEmployeeById(job.getEmployee());
        Customer customer = db.getCustomerById(job.getCustomer());

        // Fetch, concatenate and display
        // employee first and last names
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        TextView employeeFirstLast = (TextView) convertView.findViewById(R.id.finances_employee);
        employeeFirstLast.setText(fullName);

        // Fetch and display the customer's address
        TextView customerAddress = (TextView) convertView.findViewById(R.id.finances_address);
        customerAddress.setText(customer.getAddressLine1());

        // Fetch and display the date
        //TODO - change to endDate
        TextView startDate = (TextView) convertView.findViewById(R.id.finances_date);
        String date = DateFormat.getDateInstance().format(job.getStartDate());
        startDate.setText(date);

        // Calculate the pay to employee for the job
        TextView payForJob = (TextView) convertView.findViewById(R.id.finances_amount);
        double pay = employee.getRateOfPay() * job.getEstimatedTime();
        payForJob.setText("£");
        payForJob.append(String.format(Locale.getDefault(), "%.2f", pay));

        // Fetch and display estimated time required
        //TODO - change to actual time taken
        TextView estimatedTime = (TextView) convertView.findViewById(R.id.estimated_time);
        estimatedTime.setText(String.valueOf(job.getEstimatedTime()));
        estimatedTime.append(" hours @ ");

        // Fetch and display the employee rate of pay
        TextView rateOfPay = (TextView) convertView.findViewById(R.id.rate_of_pay);
        rateOfPay.setText("£");
        rateOfPay.setText(String.format(Locale.getDefault(), "%.2f", employee.getRateOfPay()));

        //TODO - swap for payment status
        TextView jobStatus = (TextView) convertView.findViewById(R.id.payment_status);
        jobStatus.setText(job.getJobStatusEnum());

        // Handle on listView item click and send job ID
        // so that it can be displayed in the DetailJob activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailJob.class);
                intent.putExtra("EXTRAS_jobID", job.getId());
                System.out.println("z! FinanceArrayAdapter_out - onClick - jobId: " + job.getId());
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
