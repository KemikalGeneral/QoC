package com.endorphinapps.kemikal.queenofclean.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.DetailViews.DetailCustomer;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.R;

/**
 * Created by Kemikal on 29/01/2017.
 */
public class CustomerArrayAdapter extends ArrayAdapter<Customer> {

    /**
     * Customer array adapter for displaying from DB to entity ListView
     * @param context
     */
    public CustomerArrayAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_list_item, parent, false);
        }

        final Customer customer = getItem(position);

        // Fetch and populate the ID field
        TextView customerId = (TextView) convertView.findViewById(R.id.customer_id);
        customerId.setText(String.valueOf(customer.getCustomerId()));

        // Fetch and populate the FirstName field
        TextView firstName = (TextView) convertView.findViewById(R.id.first_name);
        firstName.setText(customer.getFirstName());

        // Fetch and populate the LastName field
        TextView lastName = (TextView) convertView.findViewById(R.id.last_name);
        lastName.setText(customer.getLastName());

        // Fetch and populate the AddressLine1  field
        TextView addressLine1 = (TextView) convertView.findViewById(R.id.address_line_1);
        addressLine1.setText(customer.getAddressLine1());

        // Onclick, send all details below to the DetailCustomer page
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailCustomer.class);
                intent.putExtra("EXTRAS_id", customer.getCustomerId());
                intent.putExtra("EXTRAS_firstName", customer.getFirstName());
                intent.putExtra("EXTRAS_lastName", customer.getLastName());
                intent.putExtra("EXTRAS_homeNumber", customer.getHomeNumber());
                intent.putExtra("EXTRAS_mobileNumber", customer.getMobileNumber());
                intent.putExtra("EXTRAS_emailAddress", customer.getEmailAddress());
                intent.putExtra("EXTRAS_addressLine1", customer.getAddressLine1());
                intent.putExtra("EXTRAS_addressLine2", customer.getAddressLine2());
                intent.putExtra("EXTRAS_town", customer.getTown());
                intent.putExtra("EXTRAS_city", customer.getCity());
                intent.putExtra("EXTRAS_postcode", customer.getPostcode());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
