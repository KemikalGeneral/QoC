package com.endorphinapps.kemikal.queenofclean.EditRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;

public class EditCustomer extends MenuMain {

    private EditText et_firstName;
    private EditText et_lastName;
    private EditText et_mobileNumber;
    private EditText et_homeNumber;
    private EditText et_eMail;
    private EditText et_addressLine1;
    private EditText et_addressLine2;
    private EditText et_town;
    private EditText et_city;
    private EditText et_postcode;

    private Button btn_applyChanges;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit a Customer");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Get the ID of the customer from the Detail intent
        // and get the required customer by the ID
        Intent intent = getIntent();
        final long customerId = intent.getLongExtra("EXTRAS_id", 0);
        Customer customer = db.getCustomerById(customerId);

        // Populate customer detail fields with customer info
        et_firstName.setText(customer.getFirstName());
        et_lastName.setText(customer.getLastName());
        et_mobileNumber.setText(customer.getMobileNumber());
        et_homeNumber.setText(customer.getHomeNumber());
        et_eMail.setText(customer.getEmailAddress());
        et_addressLine1.setText(customer.getAddressLine1());
        et_addressLine2.setText(customer.getAddressLine2());
        et_town.setText(customer.getTown());
        et_city.setText(customer.getCity());
        et_postcode.setText(customer.getPostcode());

        btn_applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate fields on form.
                // Update DB if they all pass
                if (isValidated()) {
                    updateCustomer(customerId);
                }
            }
        });
    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        et_firstName = (EditText) findViewById(R.id.add_first_name);
        et_lastName = (EditText) findViewById(R.id.add_last_name);
        et_mobileNumber = (EditText) findViewById(R.id.add_mobile_number);
        et_homeNumber = (EditText) findViewById(R.id.add_home_number);
        et_eMail = (EditText) findViewById(R.id.add_email);
        et_addressLine1 = (EditText) findViewById(R.id.add_address_line_1);
        et_addressLine2 = (EditText) findViewById(R.id.add_address_line_2);
        et_town = (EditText) findViewById(R.id.add_town);
        et_city = (EditText) findViewById(R.id.add_city);
        et_postcode = (EditText) findViewById(R.id.add_postcode);

        btn_applyChanges = (Button) findViewById(R.id.apply_changes);
    }

    /**
     * Validates the essential details so that they cannot be null
     * Full Name cannot be null.
     * Last Name cannot be null.
     * Mobile Number cannot be null, and must have 11 digits.
     * Address Line 1 cannot be null.
     * Town cannot be null.
     * Postcode cannot be null.
     *
     * @return false if any fields fail, true if they're all ok
     */
    private boolean isValidated() {

        // First Name
        if (et_firstName.getText().toString().trim().equals("")) {
            et_firstName.setError("Your customer must have a first name!");
            return false;
        }

        // Last Name
        if (et_lastName.getText().toString().trim().equals("")) {
            et_lastName.setError("Your customer must have a last name!");
            return false;
        }

        // Mobile Number
        if (et_mobileNumber.getText().toString().trim().equals("")) {
            et_mobileNumber.setError("Your customer must have a mobile number!");
            return false;
        } else if (et_mobileNumber.getText().toString().trim().length() != 11) {
            et_mobileNumber.setError("Mobile number should contain 11 numbers!");
            return false;
        }

        // Address Line 1
        if (et_addressLine1.getText().toString().trim().equals("")) {
            et_addressLine1.setError("Your customer's address must have a street name!");
            return false;
        }

        // Town
        if (et_town.getText().toString().trim().equals("")) {
            et_town.setError("Your customer's address must have a town!");
            return false;
        }

        // Postcode
        if (et_postcode.getText().toString().trim().equals("")) {
            et_postcode.setError("Your customer's address must have a postcode!");
            return false;
        }

        // Return true if all fields pass validation
        return true;
    }

    /**
     * Update the customer record.
     * Update the address details first
     * and then the personal details.
     * @param customerId
     */
    private void updateCustomer(long customerId) {
        // Get the address FK from the customer table
        long addressId = db.getAddressIdFromCustomerId(customerId);

        // Update customer's address details
        db.updateAddress(
                addressId,
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim()
        );

        // Update customer's personal details
        db.updateCustomer(
                customerId,
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim()
        );

        startActivity(new Intent(EditCustomer.this, ViewCustomers.class));
        finish();
    }
}
