package com.endorphinapps.kemikal.queenofclean;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.ENUMs.JobStatus;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;

import java.util.ArrayList;
import java.util.Calendar;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

public class AddJob extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DBHelper db;

    private Spinner sp_customerSpinner;
    private LinearLayout ll_addressContainer;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;
    private Spinner sp_employeeSpinner;
    private TextView tv_startDate;
    private long startDate;
    private Spinner sp_statusSpinner;
    private LinearLayout ll_jobListContainer;
    private TextView tv_addNewJobRow;
    private EditText et_estimatedTime;
    private TextView tv_jobTotalPrice;
    private EditText et_notes;
    private Button btn_JobSubmit;

    private Customer customer;
    private Employee employee;

    private TextView tv_dummyCustomer;
    private TextView tv_dummyEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        //Find all views by their Id's
        findViews();

        sp_customerSpinner.setVisibility(View.GONE);
        ll_addressContainer.setVisibility(View.GONE);
        sp_employeeSpinner.setVisibility(View.GONE);

        tv_dummyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_dummyCustomer.setVisibility(View.GONE);
                sp_customerSpinner.setVisibility(View.VISIBLE);
                ll_addressContainer.setVisibility(View.VISIBLE);
            }
        });

        tv_dummyEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_dummyEmployee.setVisibility(View.GONE);
                sp_employeeSpinner.setVisibility(View.VISIBLE);
            }
        });

        //Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        /**
         * Populate Customers ArrayList with all customer
         * details from the DB
         * Set-up customer adapter
         */
        final ArrayList<Customer> customers = db.getAllCustomers();
        ArrayAdapter<Customer> customerSpinnerAdapter = new ArrayAdapter<Customer>(
                this, R.layout.spinner_list_item, customers);
        sp_customerSpinner.setAdapter(customerSpinnerAdapter);

        /**
         * On button click, populate the customer's
         * address fields from the spinner selection
         */
        sp_customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customer = (Customer) parent.getItemAtPosition(position);
                tv_addressLine1.setText(customer.getAddressLine1());
                tv_addressLine2.setText(customer.getAddressLine2());
                tv_town.setText(customer.getTown());
                tv_city.setText(customer.getCity());
                tv_postcode.setText(customer.getPostcode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        /**
         * Populate employees with all customer details from the DB
         * Set-up employee adapter
         */
        final ArrayList<Employee> employees = db.getAllEmployees();
        ArrayAdapter<Employee> employeeSpinnerAdapter = new ArrayAdapter<Employee>(
                this, R.layout.spinner_list_item, employees);
        sp_employeeSpinner.setAdapter(employeeSpinnerAdapter);

        /**
         * On button click, select the employee for the job
         */
        sp_employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee = (Employee) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        /**
         * Pick a date for the start of the job, using a datePicker
         * Returned in onDateSet
         */
        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        /**
         * Populate JobStatus spinner with values from Enum class
         */
        sp_statusSpinner.setAdapter(new ArrayAdapter<JobStatus>(this, R.layout.spinner_list_item, JobStatus.values()));

        /**
         * Calls addJobRow() to add a new row layout
         */
        tv_addNewJobRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobRow();
            }
        });

        /**
         * Get the values of the Description and Price, for each row
         */
        btn_JobSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        ll_addressContainer = (LinearLayout) findViewById(R.id.add_job_customer_container);
        tv_addressLine1 = (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.address_town);
        tv_city = (TextView) findViewById(R.id.address_city);
        tv_postcode = (TextView) findViewById(R.id.address_postcode);
        sp_customerSpinner = (Spinner) findViewById(R.id.add_customer_spinner);
        sp_employeeSpinner = (Spinner) findViewById(R.id.add_employee_spinner);
        tv_startDate = (TextView) findViewById(R.id.add_start_date);
        sp_statusSpinner = (Spinner) findViewById(R.id.add_status_spinner);
        ll_jobListContainer = (LinearLayout) findViewById(R.id.add_job_list_container);
        tv_addNewJobRow = (TextView) findViewById(R.id.add_job_item_row);
        et_estimatedTime = (EditText) findViewById(R.id.add_estimated_time);
        tv_jobTotalPrice = (TextView) findViewById(R.id.job_total_price);
        et_notes = (EditText) findViewById(R.id.add_job_notes);
        btn_JobSubmit = (Button) findViewById(R.id.add_submit);

        tv_dummyCustomer = (TextView) findViewById(R.id.dummy_customer);
        tv_dummyEmployee = (TextView) findViewById(R.id.dummy_employee);
    }

    /**
     * onDateSet returns the date chosen from the datePicker,
     * sets values to a calendar object and converts
     * to millis for inserting into DB
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.getTime();

        startDate = calendar.getTimeInMillis();
        Log.v("z! StartDate: ", String.valueOf(startDate));
    }

    /**
     * Creates a new LinearLayout as row container
     * Populates the row with description and price EditTexts's
     * Adds a listener for the delete button to remove the current row
     */
    private void addJobRow() {

        //Create a new row container
        LinearLayout jobRowContainer = new LinearLayout(this);

        //Create a new EditText for the Description
        final EditText description = new EditText(this);
        description.setId(R.id.add_item_description);
        description.setHint("Description...");
        description.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        //Create an EditText for the Price
        //Will only accept a decimal number
        final EditText price = new EditText(this);
        price.setId(R.id.add_item_price);
        price.setInputType(InputType.TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
        price.setHint("00.00");

        //Create a new button to delete the row
        final Button delete = new Button(this);
        delete.setBackgroundColor(Color.RED);
        delete.setText("X");

        //Add Description, Price and Delete to the row container
        jobRowContainer.addView(description);
        jobRowContainer.addView(price);
        jobRowContainer.addView(delete);

        //Add the Row Container to the Jobs List Container
        ll_jobListContainer.addView(jobRowContainer);

        //Delete the current row
        //This is set here to avoid a nullPointer on activity load.
        //The listener starts when row is added
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeJobRow(delete);
            }
        });
    }

    /**
     * Removes the current row (LinearLayout)
     * @param delete
     */
    private void removeJobRow(Button delete) {
        LinearLayout currentRow = (LinearLayout) delete.getParent();
        ((ViewGroup) currentRow.getParent()).removeView(currentRow);
    }

    /**
     * On submit button click, get values from all description and price fields,
     * calculate pay to employee and cost of job items,
     * totaling the jobs total price
     */
    private void onSubmit() {
        //Get customer ID
        long customerId = getCustomerId();

        //Get employee ID
        long employeeId = getEmployeeId();

        //Get job status
        String jobStatus = getJobStatus();

        //Calculate price of job items
        double priceOfJobItems = calculatePriceOfJobItems();

        //Get estimated employee time required
        int estimateJobTime = getHoursWorked();

        //Calculate pay to employee
        double payToEmployee = calculatePayToEmployee();

        //Total cost of job, (jobItems + employee pay)
        double totalCostForJob = priceOfJobItems + payToEmployee;

        //Set TextView with the total cost for the job
        tv_jobTotalPrice.setText(String.valueOf(totalCostForJob));

        //Get job notes
        String jobNotes = getJobNotes();

        //Save all job attributes to the DB
        saveToDB(startDate, jobStatus, estimateJobTime, totalCostForJob, jobNotes, customerId, employeeId);

        Intent intent = new Intent(AddJob.this, ViewJobs.class);
        startActivity(intent);
        finish();
    }

    /**
     * Get the ID of the customer
     * @return ID as a long
     */
    private long getCustomerId() {
        long customerId = customer.getCustomerId();
        Log.v("z! CustomerID: ", String.valueOf(customerId));

        return customerId;
    }

    /**
     * Get the ID of the customer
     * @return ID as a long
     */
    private long getEmployeeId() {
        long employeeId = employee.getEmployeeId();
        Log.v("z! EmployeeId: ", String.valueOf(employeeId));

        return employeeId;
    }

    /**
     * Get the job status from the job status spinner
     * @return job status as a string
     */
    private String getJobStatus() {
        String jobStatus = sp_statusSpinner.getSelectedItem().toString();
        Log.v("z! JobStatus: ", jobStatus);

        return jobStatus;
    }

    /**
     * Calculate the price of all the entered job items
     * @return total price as a double
     */
    private double calculatePriceOfJobItems() {
        double totalPriceOfJobItems = 0;

        //Get the values from the description and price fields
        for (int i = 0; i < ll_jobListContainer.getChildCount(); i++) {
            LinearLayout currentRow = (LinearLayout) ll_jobListContainer.getChildAt(i);

            //Get the text from the Description field
            EditText editText = (EditText) currentRow.findViewById(R.id.add_item_description);
            String description = editText.getText().toString();

            //Get the text from the Price field, if it's not
            // empty convert it to a double, and keep a
            // running totalPriceOfJobItems
            editText = (EditText) currentRow.findViewById(R.id.add_item_price);
            double price = 0;
            if (!editText.getText().toString().equals("")) {
                price = Double.parseDouble(editText.getText().toString());
            }
            totalPriceOfJobItems += price;
            Log.v("z! JobItems: ", description + " @ " + price);
        }
        Log.v("z! JobItemsTotal: ", String.valueOf(totalPriceOfJobItems));
        return totalPriceOfJobItems;
    }

    /**
     * Get the estimated hours needed for the employee pay
     * @return hours worked as an int
     */
    private int getHoursWorked() {
        int hoursWorked = 0;

        if (!et_estimatedTime.getText().toString().equals("")) {
            hoursWorked = Integer.valueOf(et_estimatedTime.getText().toString());
        }
        Log.v("z! HoursWorked: ", String.valueOf(hoursWorked));

        return hoursWorked;
    }

    /**
     * Calculate the total pay for the employee,
     * by multiplying the rate of pay by hours worked
     * @return total pay to employee as a double
     */
    private double calculatePayToEmployee() {
        double rateOfPay = employee.getRateOfPay();
        double employeePay = rateOfPay * getHoursWorked();
        Log.v("z! EmployeePay: ", String.valueOf(employeePay));

        return employeePay;
    }

    /**
     * Get any job notes entered
     * @return job notes as a string
     */
    @NonNull
    private String getJobNotes() {
        String jobNotes = et_notes.getText().toString();
        Log.v("z! JobNotes: ", jobNotes);

        return jobNotes;
    }

    private void saveToDB(long startDate, String jobStatus,
                          int estimateJobTime, double totalCostForJob,
                          String jobNotes, long customerId, long employeeId) {
        long jobId = db.insertJob(startDate, jobStatus, estimateJobTime,
                totalCostForJob, jobNotes, customerId, employeeId);

        Log.v("z! saveToDB jobId: ", String.valueOf(jobId));
    }
}