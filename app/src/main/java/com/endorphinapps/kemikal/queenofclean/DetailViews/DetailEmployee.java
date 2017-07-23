package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.EditRecords.EditEmployee;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;

import java.util.Locale;

public class DetailEmployee extends MenuMain {

    private TextView tv_fullName;
    private TextView tv_homeNumber;
    private TextView tv_mobileNumber;
    private TextView tv_eMailAddress;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;
    private TextView tv_rateOfPay;
    // Availability Checkboxes
    private CheckBox mondayAM;
    private CheckBox mondayPM;
    private CheckBox tuesdayAM;
    private CheckBox tuesdayPM;
    private CheckBox wednesdayAM;
    private CheckBox wednesdayPM;
    private CheckBox thursdayAM;
    private CheckBox thursdayPM;
    private CheckBox fridayAM;
    private CheckBox fridayPM;
    private CheckBox saturdayAM;
    private CheckBox saturdayPM;
    private CheckBox sundayAM;
    private CheckBox sundayPM;

    private Button btn_edit;
    private Button btn_delete;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employee);

        // Find all views by Id
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Employees");

        // Instantiate a new DBHelper class
        db = new DBHelper(this);

        // Get employee ID from Adapter Intent
        Intent intent = getIntent();
        final long employeeId = intent.getLongExtra("EXTRAS_id", 0);

        // Get employee from the ID
        Employee employee = db.getEmployeeById(employeeId);

        // Populate employee detail fields
        StringBuilder fullName = new StringBuilder();
        fullName
                .append(employee.getFirstName())
                .append(" ")
                .append(employee.getLastName());
        tv_fullName.setText(fullName);
        tv_homeNumber.setText(employee.getHomeNumber());
        tv_mobileNumber.setText(employee.getMobileNumber());
        tv_eMailAddress.setText(employee.getEmailAddress());
        tv_addressLine1.setText(employee.getAddressLine1());
        tv_addressLine2.setText(employee.getAddressLine2());
        tv_town.setText(employee.getTown());
        tv_city.setText(employee.getCity());
        tv_postcode.setText(employee.getPostcode());
        tv_rateOfPay.setText("£");
        tv_rateOfPay.append(String.format(Locale.getDefault(), "%.2f", employee.getRateOfPay()));

        // Populate employee work availability checkboxes
        populateAvailabilityCheckboxes(employee);

        // On Edit button click, send the employee ID
        // in the intent and start the EditEmployee activity
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(DetailEmployee.this, EditEmployee.class);
                editIntent.putExtra("EXTRAS_id", employeeId);
                startActivity(editIntent);
                finish();
            }
        });

        // On Delete button click,
        // delete the record by row ID
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEmployeeById(employeeId);
                Intent deleteIntent = new Intent(DetailEmployee.this, ViewEmployees.class);
                startActivity(deleteIntent);
                finish();
            }
        });
    }

    /**
     * Find all views bt their Id's
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
        tv_rateOfPay = (TextView) findViewById(R.id.rate_of_pay);

        mondayAM = (CheckBox) findViewById(R.id.detail_availability_monday_AM);
        mondayPM = (CheckBox) findViewById(R.id.detail_availability_monday_PM);
        tuesdayAM = (CheckBox) findViewById(R.id.detail_availability_tuesday_AM);
        tuesdayPM = (CheckBox) findViewById(R.id.detail_availability_tuesday_PM);
        wednesdayAM = (CheckBox) findViewById(R.id.detail_availability_wednesday_AM);
        wednesdayPM = (CheckBox) findViewById(R.id.detail_availability_wednesday_PM);
        thursdayAM = (CheckBox) findViewById(R.id.detail_availability_thursday_AM);
        thursdayPM = (CheckBox) findViewById(R.id.detail_availability_thursday_PM);
        fridayAM = (CheckBox) findViewById(R.id.detail_availability_friday_AM);
        fridayPM = (CheckBox) findViewById(R.id.detail_availability_friday_PM);
        saturdayAM = (CheckBox) findViewById(R.id.detail_availability_saturday_AM);
        saturdayPM = (CheckBox) findViewById(R.id.detail_availability_saturday_PM);
        sundayAM = (CheckBox) findViewById(R.id.detail_availability_sunday_AM);
        sundayPM = (CheckBox) findViewById(R.id.detail_availability_sunday_PM);

        btn_edit = (Button) findViewById(R.id.btn_edit_employee);
        btn_delete = (Button) findViewById(R.id.btn_delete_employee);
    }

    /**
     * Populate the employee work availability checkboxes
     * from the DB.
     * Set to enabled(false) so they can't be accidently
     * edited without going through the EditEmployee activity
     *
     * @param employee
     */
    private void populateAvailabilityCheckboxes(Employee employee) {
        // Availability Checkboxes
        if (employee.getMondayAM() == 1) {
            mondayAM.setChecked(true);
        }
        if (employee.getMondayPM() == 1) {
            mondayPM.setChecked(true);
        }
        if (employee.getTuesdayAM() == 1) {
            tuesdayAM.setChecked(true);
        }
        if (employee.getTuesdayPM() == 1) {
            tuesdayPM.setChecked(true);
        }
        if (employee.getWednesdayAM() == 1) {
            wednesdayAM.setChecked(true);
        }
        if (employee.getWednesdayPM() == 1) {
            wednesdayPM.setChecked(true);
        }
        if (employee.getThursdayAM() == 1) {
            thursdayAM.setChecked(true);
        }
        if (employee.getThursdayPM() == 1) {
            thursdayPM.setChecked(true);
        }
        if (employee.getFridayAM() == 1) {
            fridayAM.setChecked(true);
        }
        if (employee.getFridayPM() == 1) {
            fridayPM.setChecked(true);
        }
        if (employee.getSaturdayAM() == 1) {
            saturdayAM.setChecked(true);
        }
        if (employee.getSaturdayPM() == 1) {
            saturdayPM.setChecked(true);
        }
        if (employee.getSundayAM() == 1) {
            sundayAM.setChecked(true);
        }
        if (employee.getSundayPM() == 1) {
            sundayPM.setChecked(true);
        }
    }
}
