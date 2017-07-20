package com.endorphinapps.kemikal.queenofclean.EditRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;

public class EditEmployee extends MenuMain {

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
    private CheckBox ch_mondayAM;
    private CheckBox ch_mondayPM;
    private CheckBox ch_tuesdayAM;
    private CheckBox ch_tuesdayPM;
    private CheckBox ch_wednesdayAM;
    private CheckBox ch_wednesdayPM;
    private CheckBox ch_thursdayAM;
    private CheckBox ch_thursdayPM;
    private CheckBox ch_fridayAM;
    private CheckBox ch_fridayPM;
    private CheckBox ch_saturdayAM;
    private CheckBox ch_saturdayPM;
    private CheckBox ch_sundayAM;
    private CheckBox ch_sundayPM;
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

    private Button btn_applyChanges;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        //Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit an Employee");

        //Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        // Get the ID of the employee from the Detail intent
        // and get the required employee by the ID
        Intent intent = getIntent();
        final long employeeId = intent.getLongExtra("EXTRAS_id", 0);
        Employee employee = db.getEmployeeById(employeeId);

        // Populate employee detail fields with employee info
        et_firstName.setText(employee.getFirstName());
        et_lastName.setText(employee.getLastName());
        et_mobileNumber.setText(employee.getMobileNumber());
        et_homeNumber.setText(employee.getHomeNumber());
        et_eMail.setText(employee.getEmailAddress());
        et_addressLine1.setText(employee.getAddressLine1());
        et_addressLine2.setText(employee.getAddressLine2());
        et_town.setText(employee.getTown());
        et_city.setText(employee.getCity());
        et_postcode.setText(employee.getPostcode());
        et_rateOfPay.setText(String.valueOf(employee.getRateOfPay()));

        // Populate employee work availability checkboxes
        populateAvailabilityCheckboxes(employee);

        btn_applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employeeId);
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
        btn_applyChanges = (Button) findViewById(R.id.apply_changes);
        // Checkboxes
        ch_mondayAM = (CheckBox) findViewById(R.id.detail_availability_monday_AM);
        ch_mondayPM = (CheckBox) findViewById(R.id.detail_availability_monday_PM);
        ch_tuesdayAM = (CheckBox) findViewById(R.id.detail_availability_tuesday_AM);
        ch_tuesdayPM = (CheckBox) findViewById(R.id.detail_availability_tuesday_PM);
        ch_wednesdayAM = (CheckBox) findViewById(R.id.detail_availability_wednesday_AM);
        ch_wednesdayPM = (CheckBox) findViewById(R.id.detail_availability_wednesday_PM);
        ch_thursdayAM = (CheckBox) findViewById(R.id.detail_availability_thursday_AM);
        ch_thursdayPM = (CheckBox) findViewById(R.id.detail_availability_thursday_PM);
        ch_fridayAM = (CheckBox) findViewById(R.id.detail_availability_friday_AM);
        ch_fridayPM = (CheckBox) findViewById(R.id.detail_availability_friday_PM);
        ch_saturdayAM = (CheckBox) findViewById(R.id.detail_availability_saturday_AM);
        ch_saturdayPM = (CheckBox) findViewById(R.id.detail_availability_saturday_PM);
        ch_sundayAM = (CheckBox) findViewById(R.id.detail_availability_sunday_AM);
        ch_sundayPM = (CheckBox) findViewById(R.id.detail_availability_sunday_PM);
    }

    /**
     * Populate the employee work availability checkboxes
     * from the DB
     *
     * @param employee
     */
    private void populateAvailabilityCheckboxes(Employee employee) {
        // Populate Availability Checkboxes
        if (employee.getMondayAM() == 1) {
            ch_mondayAM.setChecked(true);
        }
        if (employee.getMondayPM() == 1) {
            ch_mondayPM.setChecked(true);
        }
        if (employee.getTuesdayAM() == 1) {
            ch_tuesdayAM.setChecked(true);
        }
        if (employee.getTuesdayPM() == 1) {
            ch_tuesdayPM.setChecked(true);
        }
        if (employee.getWednesdayAM() == 1) {
            ch_wednesdayAM.setChecked(true);
        }
        if (employee.getWednesdayPM() == 1) {
            ch_wednesdayPM.setChecked(true);
        }
        if (employee.getThursdayAM() == 1) {
            ch_thursdayAM.setChecked(true);
        }
        if (employee.getThursdayPM() == 1) {
            ch_thursdayPM.setChecked(true);
        }
        if (employee.getFridayAM() == 1) {
            ch_fridayAM.setChecked(true);
        }
        if (employee.getFridayPM() == 1) {
            ch_fridayPM.setChecked(true);
        }
        if (employee.getSaturdayAM() == 1) {
            ch_saturdayAM.setChecked(true);
        }
        if (employee.getSaturdayPM() == 1) {
            ch_saturdayPM.setChecked(true);
        }
        if (employee.getSundayAM() == 1) {
            ch_sundayAM.setChecked(true);
        }
        if (employee.getSundayPM() == 1) {
            ch_sundayPM.setChecked(true);
        }
    }

    /**
     * Get checkbox selections
     *
     * @param view
     */
    public void checkBoxClick(View view) {

        if (ch_mondayAM.isChecked()) {
            mondayAM = 1;
        } else {
            mondayAM = 0;
        }
        if (ch_mondayPM.isChecked()) {
            mondayPM = 1;
        } else {
            mondayPM = 0;
        }
        if (ch_tuesdayAM.isChecked()) {
            tuesdayAM = 1;
        } else {
            tuesdayPM = 0;
        }
        if (ch_tuesdayPM.isChecked()) {
            tuesdayPM = 1;
        } else {
            tuesdayPM = 0;
        }
        if (ch_wednesdayAM.isChecked()) {
            wednesdayAM = 1;
        } else {
            wednesdayAM = 0;
        }
        if (ch_wednesdayPM.isChecked()) {
            wednesdayPM = 1;
        } else {
            wednesdayPM = 0;
        }
        if (ch_thursdayAM.isChecked()) {
            thursdayAM = 1;
        } else {
            thursdayAM = 0;
        }
        if (ch_thursdayPM.isChecked()) {
            thursdayPM = 1;
        } else {
            thursdayPM = 0;
        }
        if (ch_fridayAM.isChecked()) {
            fridayAM = 1;
        } else {
            fridayAM = 0;
        }
        if (ch_fridayPM.isChecked()) {
            fridayPM = 1;
        } else {
            fridayPM = 0;
        }
        if (ch_saturdayAM.isChecked()) {
            saturdayAM = 1;
        } else {
            saturdayAM = 0;
        }
        if (ch_saturdayPM.isChecked()) {
            saturdayPM = 1;
        } else {
            saturdayPM = 0;
        }
        if (ch_sundayAM.isChecked()) {
            sundayAM = 1;
        } else {
            sundayAM = 0;
        }
        if (ch_sundayPM.isChecked()) {
            sundayPM = 1;
        } else {
            sundayPM = 0;
                }
        }

    /**
     * Update the employee record.
     * Update the address details first
     * and then the personal details.
     * @param employeeId
     */
    private void updateEmployee(long employeeId) {
        // Get the address FK from the employee table
        long addressId = db.getAddressIdFromEmployeeId(employeeId);
        long availabilityId = db.getAvailabilityFromEmployeeId(employeeId);

        // Update employee's address details
        db.updateAddress(
                addressId,
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim()
        );

        // Update employee availability
        db.updateAvailability(
                availabilityId,
                mondayAM, mondayPM,
                tuesdayAM, tuesdayPM,
                wednesdayAM, wednesdayPM,
                thursdayAM, thursdayPM,
                fridayAM, fridayPM,
                saturdayAM, saturdayPM,
                sundayAM, sundayPM
        );

        // Update employee's personal details
        db.updateEmployee(
                employeeId,
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim(),
                Double.parseDouble(et_rateOfPay.getText().toString().trim())
        );

        Intent intent = new Intent(EditEmployee.this, ViewEmployees.class);
        startActivity(intent);
        finish();
    }
}