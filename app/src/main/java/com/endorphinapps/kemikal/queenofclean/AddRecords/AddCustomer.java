package com.endorphinapps.kemikal.queenofclean.AddRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;

public class AddCustomer extends MenuMain {

    private DBHelper db;

    private ScrollView sv_pageContainer;
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
    private EditText et_notes;
    //    private Button btn_populate;
    private Button btn_addNew;

//    private int counter = 0;

    /**
     * Go back to ViewCustomers on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewCustomers.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        //Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a Customer");

        //Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        //Populate form for testing
//        btn_populate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < 10; i++) {
//                    counter++;
//                    et_firstName.setText("Customer" + counter);
//                    et_lastName.setText("last" + counter);
//                    et_mobileNumber.setText("07511750244");
//                    et_homeNumber.setText("02920485612");
//                    et_eMail.setText("e@mail.com");
//                    et_addressLine1.setText(counter + " My Street");
//                    et_addressLine2.setText("No Road");
//                    et_town.setText("Pengam Green");
//                    et_city.setText("Cardiff");
//                    et_postcode.setText("CF242HH");
//                    addNewCustomer();
//                }
//            }
//        });

        //Add new Customer
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate fields on form.
                // Add to DB if they all pass
                if (isValidated()) {
                    addNewCustomer();
                }
            }
        });
    }

    /**
     * Find all views by id
     */
    private void findViews() {
        sv_pageContainer = (ScrollView) findViewById(R.id.activity_add_customer);
        et_firstName = (EditText) findViewById(R.id.first_name);
        et_lastName = (EditText) findViewById(R.id.last_name);
        et_mobileNumber = (EditText) findViewById(R.id.mobile_number);
        et_homeNumber = (EditText) findViewById(R.id.home_number);
        et_eMail = (EditText) findViewById(R.id.email);
        et_addressLine1 = (EditText) findViewById(R.id.address_line_1);
        et_addressLine2 = (EditText) findViewById(R.id.address_line_2);
        et_town = (EditText) findViewById(R.id.town);
        et_city = (EditText) findViewById(R.id.city);
        et_postcode = (EditText) findViewById(R.id.postcode);
        et_notes = (EditText) findViewById(R.id.notes);
//        btn_populate = (Button) findViewById(R.id.btn_populate);
        btn_addNew = (Button) findViewById(R.id.btn_submit);
    }

    /**
     * Validates the essential details so that they cannot be null
     * Full Name cannot be null.
     * Last Name cannot be null.
     * Mobile Number cannot be null, and must have 11 digits.
     * Address Line 1 cannot be null.
     * Town cannot be null.
     * Postcode cannot be null.
     * @return false if any fields fail, true if they're all ok
     */
    private boolean isValidated() {

        // First Name
        if (et_firstName.getText().toString().trim().equals("")) {
            sv_pageContainer.smoothScrollTo(0, et_firstName.getTop());
            Toast.makeText(this, "You must enter a First Name!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Last Name
        if (et_lastName.getText().toString().trim().equals("")) {
            sv_pageContainer.smoothScrollTo(0, et_lastName.getTop());
            Toast.makeText(this, "You must enter a Last Name!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Address Line 1
        if (et_addressLine1.getText().toString().trim().equals("")) {
            sv_pageContainer.smoothScrollTo(0, et_addressLine1.getTop());
            Toast.makeText(this, "You must enter an Address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Town
        if (et_town.getText().toString().trim().equals("")) {
            sv_pageContainer.smoothScrollTo(0, et_town.getTop());
            Toast.makeText(this, "You must enter a Town!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Postcode
        if (et_postcode.getText().toString().trim().equals("")) {
            sv_pageContainer.smoothScrollTo(0, et_postcode.getTop());
            Toast.makeText(this, "You must enter a Postcode!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Return true if all fields pass validation
        return true;
    }

    /**
     * Add New Customer - separates address from entity
     * for insertion to the DB.
     * The address PK is caught as 'addressID' to be used
     * as the FK in the entity table
     */
    private void addNewCustomer() {
        // Add address details
        int addressID = (int) db.insertAddress(
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim());

        // Add customer details
        int customerId = (int) db.insertCustomer(
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim(),
                et_notes.getText().toString().trim(),
                addressID
        );

        System.out.println("z! AddCustomer - addressId: " + String.valueOf(addressID));
        System.out.println("z! AddCustomer - customerId: " + String.valueOf(customerId));

        startActivity(new Intent(AddCustomer.this, ViewCustomers.class));
        finish();
    }
}
