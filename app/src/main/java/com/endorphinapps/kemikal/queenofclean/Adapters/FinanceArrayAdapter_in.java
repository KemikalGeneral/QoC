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
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;

/**
 * Created by User on 28/06/2017.
 */

public class FinanceArrayAdapter_in extends ArrayAdapter<Job> {

    private DBHelper db = new DBHelper(getContext());

    public FinanceArrayAdapter_in(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_finances_in, parent, false);
        }

        Job job = getItem(position);

        // Get job-customer details to provide the name
        long customerId = job.getCustomer();
        Customer customer = db.getCustomerById(customerId);
        String customerFullName = customer.getFirstName() + " " + customer.getLastName();

        // Fetch and display customer name
        TextView customerName = (TextView) convertView.findViewById(R.id.finances_customer);
        customerName.setText("Customer: " + customerFullName);

        // Fetch and display total amount
        TextView totalAmount = (TextView) convertView.findViewById(R.id.finances_amount);
        totalAmount.setText("Total: £" + String.valueOf(job.getTotalPrice()));

        // Fetch and display date
        TextView endDate = (TextView) convertView.findViewById(R.id.finances_date);
        String date = DateFormat.getDateInstance().format(job.getStartDate());
        endDate.setText("Date: " + date);

        // Fetch and display status
        TextView status = (TextView) convertView.findViewById(R.id.finances_status);
        status.setText("Status: " + job.getJobStatusEnum());

        return convertView;
    }
}
