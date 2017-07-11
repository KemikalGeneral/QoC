package com.endorphinapps.kemikal.queenofclean.AddRecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;

public class AddEmployee extends AppCompatActivity {

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

    private Button btn_populate;
    private Button btn_addNew;

    private DBHelper db;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        //Find all views by ID
        findViews();

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
        btn_addNew = (Button) findViewById(R.id.add_submit);
        btn_populate = (Button) findViewById(R.id.btn_populate);
    }

    /**
     * Add New Employee - separates address from entity
     * for insertion to the DB.
     * The address PK is caught as 'addressID' to be used
     * as the FK in the entity table
     */
    private void addNewEmployee() {

        long addressID = db.insertAddress(
                et_addressLine1.getText().toString().trim(),
                et_addressLine2.getText().toString().trim(),
                et_town.getText().toString().trim(),
                et_city.getText().toString().trim(),
                et_postcode.getText().toString().trim());

        long employeeId = db.insertEmployee(
                et_firstName.getText().toString().trim(),
                et_lastName.getText().toString().trim(),
                et_homeNumber.getText().toString().trim(),
                et_mobileNumber.getText().toString().trim(),
                et_eMail.getText().toString().trim(),
                addressID,
                Double.parseDouble(et_rateOfPay.getText().toString().trim())
        );

        Log.v("z! EmployeeId: ", String.valueOf(employeeId));

        Intent intent = new Intent(AddEmployee.this, ViewEmployees.class);
        startActivity(intent);
        finish();
    }
}
