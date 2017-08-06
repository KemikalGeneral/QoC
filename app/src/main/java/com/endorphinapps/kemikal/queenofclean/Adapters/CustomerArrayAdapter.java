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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_customer, parent, false);
        }

        final Customer customer = getItem(position);

        // Fetch and populate the FirstName field
        String customerFullName = customer.getFirstName() + " " + customer.getLastName();
        TextView customerName = (TextView) convertView.findViewById(R.id.full_name);
        customerName.setText(customerFullName);

        // Fetch and populate the AddressLine1 field
        TextView addressLine1 = (TextView) convertView.findViewById(R.id.address_line_1);
        addressLine1.setText(customer.getAddressLine1());

        // Fetch and populate the Town field
        TextView town = (TextView) convertView.findViewById(R.id.town);
        town.setText(", ");
        town.append(customer.getTown());

        // Onclick, send all details below to the DetailCustomer page
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailCustomer.class);
                intent.putExtra("EXTRAS_id", customer.getCustomerId());
                getContext().startActivity(intent);
//                ((Activity) getContext()).finish();
            }
        });

        return convertView;
    }
}
