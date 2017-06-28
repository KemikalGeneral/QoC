package com.endorphinapps.kemikal.queenofclean.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.DetailViews.DetailEmployee;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.R;

/**
 * Created by Kemikal on 29/01/2017.
 */
public class EmployeeArrayAdapter extends ArrayAdapter<Employee> {

    /**
     * Employee array adapter for displaying from DB to entity ListView
     * @param context
     */
    public EmployeeArrayAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_list_item, parent, false);
        }

        final Employee employee = getItem(position);

        // Fetch and populate the ID field
        TextView employeeId = (TextView) convertView.findViewById(R.id.employee_id);
        employeeId.setText(String.valueOf(employee.getEmployeeId()));

        // Fetch and populate the FirstName field
        TextView firstName = (TextView) convertView.findViewById(R.id.first_name);
        firstName.setText(employee.getFirstName());

        // Fetch and populate the LastName field
        TextView lastName = (TextView) convertView.findViewById(R.id.last_name);
        lastName.setText(employee.getLastName());

        // Onclick, send all details below to the DetailCustomer page
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailEmployee.class);
                intent.putExtra("EXTRAS_id", employee.getEmployeeId());
                intent.putExtra("EXTRAS_firstName", employee.getFirstName());
                intent.putExtra("EXTRAS_lastName", employee.getLastName());
                intent.putExtra("EXTRAS_homeNumber", employee.getHomeNumber());
                intent.putExtra("EXTRAS_mobileNumber", employee.getMobileNumber());
                intent.putExtra("EXTRAS_emailAddress", employee.getEmailAddress());
                intent.putExtra("EXTRAS_addressLine1", employee.getAddressLine1());
                intent.putExtra("EXTRAS_addressLine2", employee.getAddressLine2());
                intent.putExtra("EXTRAS_town", employee.getTown());
                intent.putExtra("EXTRAS_city", employee.getCity());
                intent.putExtra("EXTRAS_postcode", employee.getPostcode());
                intent.putExtra("EXTRAS_rateOfPay", employee.getRateOfPay());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
