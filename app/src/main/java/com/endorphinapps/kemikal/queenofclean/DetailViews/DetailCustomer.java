package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.EditRecords.EditCustomer;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;

public class DetailCustomer extends MenuMain {

    private TextView tv_fullName;
    private TextView tv_homeNumber;
    private TextView tv_mobileNumber;
    private TextView tv_eMailAddress;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;

    private Button btn_edit;
    private Button btn_delete;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Customers");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Get customer ID from Adapter Intent
        Intent intent = getIntent();
        final long customerId = intent.getLongExtra("EXTRAS_id", 0);

        // Get customer from the ID
        Customer customer = db.getCustomerById(customerId);

        // Populate customer detail fields
        StringBuilder fullName = new StringBuilder();
        fullName
                .append(customer.getFirstName())
                .append(" ")
                .append(customer.getLastName());
        tv_fullName.setText(fullName);
        tv_homeNumber.setText(customer.getHomeNumber());
        tv_mobileNumber.setText(customer.getMobileNumber());
        tv_eMailAddress.setText(customer.getEmailAddress());
        tv_addressLine1.setText(customer.getAddressLine1());
        tv_addressLine2.setText(customer.getAddressLine2());
        tv_town.setText(customer.getTown());
        tv_city.setText(customer.getCity());
        tv_postcode.setText(customer.getPostcode());


//        String fullName =
//                intent.getStringExtra("EXTRAS_firstName") + " " +
//                        intent.getStringExtra("EXTRAS_lastName");
//        tv_fullName.setText(fullName);
//        tv_homeNumber.setText(intent.getStringExtra("EXTRAS_homeNumber"));
//        tv_mobileNumber.setText(intent.getStringExtra("EXTRAS_mobileNumber"));
//        tv_eMailAddress.setText(intent.getStringExtra("EXTRAS_emailAddress"));
//        tv_addressLine1.setText(intent.getStringExtra("EXTRAS_addressLine1"));
//        tv_addressLine2.setText(intent.getStringExtra("EXTRAS_addressLine2"));
//        tv_town.setText(intent.getStringExtra("EXTRAS_town"));
//        tv_city.setText(intent.getStringExtra("EXTRAS_city"));
//        tv_postcode.setText(intent.getStringExtra("EXTRAS_postcode"));

        // On Edit button click, send the customer ID
        // in the intent and start the EditCustomer activity
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(DetailCustomer.this, EditCustomer.class);
                editIntent.putExtra("EXTRAS_id", customerId);
                startActivity(editIntent);
                finish();
            }
        });

        // On Delete button click,
        // delete the record by row ID
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCustomerById(customerId);
                Intent deleteIntent = new Intent(DetailCustomer.this, ViewCustomers.class);
                startActivity(deleteIntent);
                finish();
            }
        });
    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        tv_fullName = (TextView) findViewById(R.id.full_name);
        tv_homeNumber = (TextView) findViewById(R.id.home_number);
        tv_mobileNumber = (TextView) findViewById(R.id.mobile_number);
        tv_eMailAddress = (TextView) findViewById(R.id.email_address);
        tv_addressLine1= (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.address_town);
        tv_city = (TextView) findViewById(R.id.address_city);
        tv_postcode = (TextView) findViewById(R.id.address_postcode);

        btn_edit = (Button) findViewById(R.id.btn_edit_customer);
        btn_delete = (Button) findViewById(R.id.btn_delete_customer);
    }
}
