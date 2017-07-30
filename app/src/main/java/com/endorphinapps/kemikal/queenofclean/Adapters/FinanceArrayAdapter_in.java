package com.endorphinapps.kemikal.queenofclean.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.Locale;

public class FinanceArrayAdapter_in extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());
    private long jobId;

    public FinanceArrayAdapter_in(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_finances_in, parent, false);
        }

        final Job job = getItem(position);

        // Get job-customer details to provide the name
        long customerId = job.getCustomer();
        Customer customer = db.getCustomerById(customerId);
        String customerFullName = customer.getFirstName() + " " + customer.getLastName();

        // Fetch and display customer name
        TextView customerName = (TextView) convertView.findViewById(R.id.finances_customer);
        customerName.setText(customerFullName);

        // Fetch and display customer's address
        TextView customerAddress = (TextView) convertView.findViewById(R.id.finances_address);
        customerAddress.setText(customer.getAddressLine1());

        // Fetch and display total amount
        TextView totalAmount = (TextView) convertView.findViewById(R.id.finances_amount);
        totalAmount.setText("£");
        totalAmount.append(String.format(Locale.getDefault(), "%.2f", job.getTotalPrice()));

        // Fetch and display date
        TextView endDate = (TextView) convertView.findViewById(R.id.finances_date);
        String date = DateFormat.getDateInstance().format(job.getStartDate());
        endDate.setText(date);

        // Fetch and display status
        TextView status = (TextView) convertView.findViewById(R.id.finances_status);
        status.setText(job.getJobStatusEnum());
        // Set status text colour accordingly
        if (job.getJobStatusEnum().equals("UnPaid")) {
            status.setTextColor(Color.RED);
        } else if (job.getJobStatusEnum().equals("Paid")) {
            status.setTextColor(Color.GREEN);
        }

        // Handle on listView item click and send job ID
        // so that it can be displayed in the DetailJob activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailJob.class);
                intent.putExtra("EXTRAS_jobID", job.getId());
                System.out.println("z! FinanceArrayAdapter_in - onClick - jobId: " + job.getId());
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
