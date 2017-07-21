package com.endorphinapps.kemikal.queenofclean.AddRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;

public class AddEmployee extends MenuMain {

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
    private EditText et_rateOfPay;
    // Availability Checkboxes
    private int mondayAM;
    private int mondayPM;
    private int tuesdayAM;
    private int tuesdayPM;
    private int wednesdayAM;
    private int wednesdayPM;
    private int thursdayAM;
    private int thursdayPM;
    private int fridayAM;
    private int fridayPM;
    private int saturdayAM;
    private int saturdayPM;
    private int sundayAM;
    private int sundayPM;

    private Button btn_populate;
    private Button btn_addNew;

    private DBHelper db;

    private int counter = 0;

    /**
     * Go back to ViewCustomers on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewEmployees.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        //Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add an Employee");

        //Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        //Populate form for testing
        btn_populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    counter++;
                    et_firstName.setText("Employee" + counter);
                    et_lastName.setText("last" + counter);
                    et_mobileNumber.setText("07511750244");
                    et_homeNumber.setText("02920485612");
                    et_eMail.setText("e@mail.com");
                    et_addressLine1.setText(counter + " My Street");
                    et_addressLine2.setText("No Road");
                    et_town.setText("Pengam Green");
                    et_city.setText("Cardiff");
                    et_postcode.setText("CF242HH");
                    et_rateOfPay.setText(counter + "0.50");
                    addNewEmployee();
                }
            }
        });

        //Add new Employee
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmployee();
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
        et_rateOfPay = (EditText) findViewById(R.id.add_rate_of_pay);
        btn_addNew = (Button) findViewById(R.id.btn_submit);
        btn_populate = (Button) findViewById(R.id.btn_populate);
    }

    /**
     * Get availability checkbox selections
     * @param view
     */
    public void checkBoxClick(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.detail_availability_monday_AM:
                if (isChecked) {
                    mondayAM = 1;
                } else {
                    mondayAM = 0;
                }
                break;
            case R.id.detail_availability_monday_PM:
                if (isChecked) {
                    mondayPM = 1;
                } else {
                    mondayPM = 0;
                }
                break;
            case R.id.detail_availability_tuesday_AM:
                if (isChecked) {
                    tuesdayAM = 1;
                } else {
                    tuesdayPM = 0;
                }
                break;
            case R.id.detail_availability_tuesday_PM:
                if (isChecked) {
                    tuesdayPM = 1;
                } else {
                    tuesdayPM = 0;
                }
                break;
            case R.id.detail_availability_wednesday_AM:
                if (isChecked) {
                    wednesdayAM = 1;
                } else {
                    wednesdayAM = 0;
                }
                break;
            case R.id.detail_availability_wednesday_PM:
                if (isChecked) {
                    wednesdayPM = 1;
                } else {
                    wednesdayPM = 0;
                }
                break;
            case R.id.detail_availability_thursday_AM:
                if (isChecked) {
                    thursdayAM = 1;
                } else {
                    thursdayAM = 0;
                }
                break;
            case R.id.detail_availability_thursday_PM:
                if (isChecked) {
                    thursdayPM = 1;
                } else {
                    thursdayPM = 0;
                }
                break;
            case R.id.detail_availability_friday_AM:
                if (isChecked) {
                    fridayAM = 1;
                } else {
                    fridayAM = 0;
                }
                break;
            case R.id.detail_availability_friday_PM:
                if (isChecked) {
                    fridayPM = 1;
                } else {
                    fridayPM = 0;
                }
                break;
            case R.id.detail_availability_saturday_AM:
                if (isChecked) {
                    saturdayAM = 1;
                } else {
                    saturdayAM = 0;
                }
                break;
            case R.id.detail_availability_saturday_PM:
                if (isChecked) {
                    saturdayPM = 1;
                } else {
                    saturdayPM = 0;
                }
                break;
            case R.id.detail_availability_sunday_AM:
                if (isChecked) {
                    sundayAM = 1;
                } else {
                    sundayAM = 0;
                }
                break;
            case R.id.detail_availability_sunday_PM:
                if (isChecked) {
                    sundayPM = 1;
                    Toast.makeText(this, "Sunday PM checked", Toast.LENGTH_SHORT).show();
                } else {
                    sundayPM = 0;
                    Toast.makeText(this, "Sunday PM unChecked", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Add New Employee - separates address from entity
     * for insertion to the DB.
     * The address PK is caught as 'addressID' to be used
     * as the FK in the entity table
     */
    private void addNewEmployee() {

        // Insert address details
        long addressID = db.insertAddress(
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim());

        // Insert work availability selections
        long availabilityID = db.insertAvailability(
                mondayAM, mondayPM,
                tuesdayAM, tuesdayPM,
                wednesdayAM, wednesdayPM,
                thursdayAM, thursdayPM,
                fridayAM, fridayPM,
                saturdayAM, saturdayPM,
                sundayAM, sundayPM);

        // Insert employee details
        long employeeId = db.insertEmployee(
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim(),
                addressID,
                Double.parseDouble(et_rateOfPay.getText().toString().trim()),
                availabilityID
        );

        System.out.println("z! AddEmployee - addressId: " + String.valueOf(addressID));
        System.out.println("z! AddEmployee - availabilityId: " + String.valueOf(availabilityID));
        System.out.println("z! AddEmployee - employeeId: " + String.valueOf(employeeId));

        startActivity(new Intent(AddEmployee.this, ViewEmployees.class));
        finish();
    }
}
