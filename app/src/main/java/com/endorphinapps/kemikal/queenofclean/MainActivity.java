package com.endorphinapps.kemikal.queenofclean;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.AddRecords.AddCustomer;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddEmployee;
import com.endorphinapps.kemikal.queenofclean.AddRecords.AddJob;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Finances.Finances;
import com.endorphinapps.kemikal.queenofclean.Finances.Finances_In;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewEmployees;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

public class MainActivity extends AppCompatActivity {

    private Button btn_addCustomer;
    private Button btn_addEmployee;
    private Button btn_viewCustomers;
    private Button btn_viewEmployees;
    private Button btn_addJob;
    private Button btn_viewJob;
    private Button btn_financesIn;
    private Button btn_finances;
    private Button btn_financesOut;
    private Button btn_dropTables;

    private Intent intent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase sqLiteDatabase =
                openOrCreateDatabase(
                        dbHelper.getDatabaseName(), MODE_PRIVATE, null, null);

        dbHelper.onCreate(sqLiteDatabase);


        btn_addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddCustomer.class);
                startActivity(intent);
            }
        });

        btn_addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddEmployee.class);
                startActivity(intent);
            }
        });

        btn_viewCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ViewCustomers.class);
                startActivity(intent);
            }
        });

        btn_viewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ViewEmployees.class);
                startActivity(intent);
            }
        });

        btn_addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddJob.class);
                startActivity(intent);
            }
        });

        btn_viewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ViewJobs.class);
                startActivity(intent);
            }
        });

        btn_financesIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Finances_In.class);
                startActivity(intent);
            }
        });

        btn_finances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Finances.class);
                startActivity(intent);
            }
        });

        btn_dropTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.dropTables(sqLiteDatabase);
                Toast.makeText(getApplicationContext(), "Tables Dropped", Toast.LENGTH_LONG).show();
                recreate();
            }
        });
    }

    /**
     * Find all views by their ID
     */
    private void findViews() {
        btn_addCustomer = (Button) findViewById(R.id.add_customer);
        btn_addEmployee = (Button) findViewById(R.id.add_employee);
        btn_viewCustomers = (Button) findViewById(R.id.view_customers);
        btn_viewEmployees = (Button) findViewById(R.id.view_employees);
        btn_addJob = (Button) findViewById(R.id.add_job);
        btn_viewJob = (Button) findViewById(R.id.view_job);
        btn_financesIn = (Button) findViewById(R.id.view_finances_in);
        btn_finances = (Button) findViewById(R.id.view_finances);
        btn_financesOut = (Button) findViewById(R.id.view_finances_out);
        btn_dropTables = (Button) findViewById(R.id.drop_tables);
    }
}
