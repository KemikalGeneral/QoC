package com.endorphinapps.kemikal.queenofclean.DetailViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.R;

public class DetailEmployee extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employee);

        // Find all views by Id
        findViews();

        // Get all details from Adapter Intent
        Intent intent = getIntent();
        String fullName =
                intent.getStringExtra("EXTRAS_firstName") + " " +
                        intent.getStringExtra("EXTRAS_lastName");
        tv_fullName.setText(fullName);
        tv_homeNumber.setText(intent.getStringExtra("EXTRAS_homeNumber"));
        tv_mobileNumber.setText(intent.getStringExtra("EXTRAS_mobileNumber"));
        tv_eMailAddress.setText(intent.getStringExtra("EXTRAS_emailAddress"));
        tv_addressLine1.setText(intent.getStringExtra("EXTRAS_addressLine1"));
        tv_addressLine2.setText(intent.getStringExtra("EXTRAS_addressLine2"));
        tv_town.setText(intent.getStringExtra("EXTRAS_town"));
        tv_city.setText(intent.getStringExtra("EXTRAS_city"));
        tv_postcode.setText(intent.getStringExtra("EXTRAS_postcode"));
        double rate = intent.getDoubleExtra("EXTRAS_rateOfPay", 0);
        tv_rateOfPay.setText("£");
        tv_rateOfPay.append(String.format("%.2f", rate));
    }

    /**
     * Find all views bt their Id's
     */
    private void findViews() {
        tv_fullName = (TextView) findViewById(R.id.employee_full_name);
        tv_homeNumber = (TextView) findViewById(R.id.home_number);
        tv_mobileNumber = (TextView) findViewById(R.id.mobile_number);
        tv_eMailAddress = (TextView) findViewById(R.id.email_address);
        tv_addressLine1= (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.address_town);
        tv_city = (TextView) findViewById(R.id.address_city);
        tv_postcode = (TextView) findViewById(R.id.address_postcode);
        tv_rateOfPay = (TextView) findViewById(R.id.rate_of_pay);
    }
}
