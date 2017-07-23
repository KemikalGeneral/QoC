package com.endorphinapps.kemikal.queenofclean.AddRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;

public class AddCustomer extends MenuMain {

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

    private Button btn_populate;
    private Button btn_addNew;

    private DBHelper db;

    private int counter = 0;

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

        //Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a Customer");

        //Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        //Populate form for testing
        btn_populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    counter++;
                    et_firstName.setText("Customer" + counter);
                    et_lastName.setText("last" + counter);
                    et_mobileNumber.setText("07511750244");
                    et_homeNumber.setText("02920485612");
                    et_eMail.setText("e@mail.com");
                    et_addressLine1.setText(counter + " My Street");
                    et_addressLine2.setText("No Road");
                    et_town.setText("Pengam Green");
                    et_city.setText("Cardiff");
                    et_postcode.setText("CF242HH");
                    addNewCustomer();
                }
            }
        });

        //Add new Customer
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCustomer();
            }
        });
    }

    /**
     * Find all views by id
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
        btn_addNew = (Button) findViewById(R.id.btn_submit);
        btn_populate = (Button) findViewById(R.id.btn_populate);
    }

    /**
     * Add New Customer - separates address from entity
     * for insertion to the DB.
     * The address PK is caught as 'addressID' to be used
     * as the FK in the entity table
     */
    private void addNewCustomer() {

        int addressID = (int) db.insertAddress(
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim());

        int customerId = (int) db.insertCustomer(
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim(),
                addressID
        );

        System.out.println("z! AddCustomer - addressId: " + String.valueOf(addressID));
        System.out.println("z! AddCustomer - customerId: " + String.valueOf(customerId));

        startActivity(new Intent(AddCustomer.this, ViewCustomers.class));
        finish();
    }
}
