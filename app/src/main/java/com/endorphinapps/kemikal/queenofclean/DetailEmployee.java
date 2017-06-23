package com.endorphinapps.kemikal.queenofclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailEmployee extends AppCompatActivity {

    private TextView tv_firstName;
    private TextView tv_lastName;
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

        findViews();

        Intent intent = getIntent();
        tv_firstName.setText(intent.getStringExtra("EXTRAS_firstName"));
        tv_lastName.setText(intent.getStringExtra("EXTRAS_lastName"));
        tv_homeNumber.setText(intent.getStringExtra("EXTRAS_homeNumber"));
        tv_mobileNumber.setText(intent.getStringExtra("EXTRAS_mobileNumber"));
        tv_eMailAddress.setText(intent.getStringExtra("EXTRAS_emailAddress"));
        tv_addressLine1.setText(intent.getStringExtra("EXTRAS_addressLine1"));
        tv_addressLine2.setText(intent.getStringExtra("EXTRAS_addressLine2"));
        tv_town.setText(intent.getStringExtra("EXTRAS_town"));
        tv_city.setText(intent.getStringExtra("EXTRAS_city"));
        tv_postcode.setText(intent.getStringExtra("EXTRAS_postcode"));
        tv_rateOfPay.setText(String.valueOf(intent.getIntExtra("EXTRAS_rateOfPay", 0)));
    }

    private void findViews() {
        tv_firstName = (TextView) findViewById(R.id.first_name);
        tv_lastName = (TextView) findViewById(R.id.last_name);
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
