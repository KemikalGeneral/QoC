package com.endorphinapps.kemikal.queenofclean.EditRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
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

        // Update employee's address details
        db.updateAddress(
                addressId,
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim()
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
