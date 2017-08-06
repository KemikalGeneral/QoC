package com.endorphinapps.kemikal.queenofclean.AddRecords;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.DateTime.DatePickerFragment;
import com.endorphinapps.kemikal.queenofclean.DateTime.TimePickerFragment;
import com.endorphinapps.kemikal.queenofclean.ENUMs.CustomerPaymentStatus;
import com.endorphinapps.kemikal.queenofclean.ENUMs.EmployeePaymentStatus;
import com.endorphinapps.kemikal.queenofclean.ENUMs.JobStatus;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

public class AddJob extends MenuMain
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private DBHelper db;
    private Customer customer;
    private Employee employee;
    private ScrollView sv_pageContainer;
    private TextView tv_dummyCustomer;
    private ImageView iv_dummyCustomerIcon;
    private Spinner sp_customerSpinner;
    private ImageView iv_customerSpinnerIcon;
    private ConstraintLayout ll_addressContainer;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;
    private TextView tv_dummyEmployee;
    private ImageView iv_dummyEmployeeIcon;
    private Spinner sp_employeeSpinner;
    private ImageView iv_employeeSpinnerIcon;
    private TextView tv_startDate;
    private long startDate;
    private TextView tv_startTime;
    private long startTime;
    private Spinner sp_jobStatusSpinner;
    private Spinner sp_customerPaymentStatusSpinner;
    private Spinner sp_employeePaymentStatusSpinner;
    private LinearLayout ll_jobListContainer;
    private TextView tv_addNewJobRow;
    private EditText et_estimatedTime;
    private TextView tv_jobTotalPrice;
    private EditText et_notes;
    private Button btn_JobSubmit;

    /**
     * Go back to ViewCustomers on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewJobs.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        //Find all views by their Id's
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a Job");

        // Set some views to GONE until needed
        sp_customerSpinner.setVisibility(View.GONE);
        iv_customerSpinnerIcon.setVisibility(View.GONE);
        ll_addressContainer.setVisibility(View.GONE);
        sp_employeeSpinner.setVisibility(View.GONE);
        iv_employeeSpinnerIcon.setVisibility(View.GONE);

        // On Customer click, hide and show the required views
        tv_dummyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_dummyCustomer.setVisibility(View.GONE);
                iv_dummyCustomerIcon.setVisibility(View.GONE);
                sp_customerSpinner.setVisibility(View.VISIBLE);
                iv_customerSpinnerIcon.setVisibility(View.VISIBLE);
                ll_addressContainer.setVisibility(View.VISIBLE);
            }
        });

        // On Employee click, hide and show the required fields
        tv_dummyEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_dummyEmployee.setVisibility(View.GONE);
                iv_dummyEmployeeIcon.setVisibility(View.GONE);
                sp_employeeSpinner.setVisibility(View.VISIBLE);
                iv_employeeSpinnerIcon.setVisibility(View.VISIBLE);
            }
        });

        // Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        // Populate customers ArrayList and spinner with
        // customer details from the DB.
        getCustomersAndPopulateSpinner();
        // On button click, populate the customer's
        // address fields from the spinner selection
        populateCustomerDetailsFromSpinnerSelection();

        // Populate employees ArrayList and spinner with
        // employee details from the DB.
        getEmployeesAndPopulateSpinner();
        // On button click, select the employee for the job
        populateEmployeeDetailsFromSpinnerSelection();

        // Pick a date for the start of the job, using a datePicker
        // Returned in onDateSet
        createAndReturnDatePicker();

        // Pick a time for the start of the job, using a timePicker
        // Returned in onTimeSet
        createAndReturnTimePicker();

        // Populate JobStatus, CustomerPaymentStatus & EmployeePaymentStatus
        // spinners with values from Enum classes
        sp_jobStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, JobStatus.values()));
        sp_customerPaymentStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, CustomerPaymentStatus.values()));
        sp_employeePaymentStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, EmployeePaymentStatus.values()));

        //Calls addJobRow() to add a new row layout
        tv_addNewJobRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobRow();
            }
        });

        // Get the values of the Description and Price, for each row
        btn_JobSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate against NullPointerExceptions
                if (isValidated()) {
                    onSubmit();
                }
            }
        });

        // Get Intent (day of week) from day selection in ViewDayJobs
        // If a day is present, call the method to populate the startDate
        Intent intent = getIntent();
        if (intent.hasExtra("addJobDay")) {
            populateSelectedStartDate(intent);
        }
    }

    /**
     * Find all views by their ID's
     */
    private void findViews() {
        sv_pageContainer = (ScrollView) findViewById(R.id.add_job_page_container);
        tv_dummyCustomer = (TextView) findViewById(R.id.dummy_customer);
        iv_dummyCustomerIcon = (ImageView) findViewById(R.id.icon_dummy_customer);
        sp_customerSpinner = (Spinner) findViewById(R.id.customer_spinner);
        iv_customerSpinnerIcon = (ImageView) findViewById(R.id.icon_customer_spinner);
        ll_addressContainer = (ConstraintLayout) findViewById(R.id.customer_address_container);
        tv_addressLine1 = (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.address_town);
        tv_city = (TextView) findViewById(R.id.address_city);
        tv_postcode = (TextView) findViewById(R.id.address_postcode);
        tv_dummyEmployee = (TextView) findViewById(R.id.dummy_employee);
        iv_dummyEmployeeIcon = (ImageView) findViewById(R.id.icon_dummy_employee);
        sp_employeeSpinner = (Spinner) findViewById(R.id.employee_spinner);
        iv_employeeSpinnerIcon = (ImageView) findViewById(R.id.icon_employee_spinner);
        tv_startDate = (TextView) findViewById(R.id.start_date);
        tv_startTime = (TextView) findViewById(R.id.start_time);
        sp_jobStatusSpinner = (Spinner) findViewById(R.id.job_status_spinner);
        sp_customerPaymentStatusSpinner = (Spinner) findViewById(R.id.customer_payment_status_spinner);
        sp_employeePaymentStatusSpinner = (Spinner) findViewById(R.id.employee_payment_status_spinner);
        ll_jobListContainer = (LinearLayout) findViewById(R.id.job_list_container);
        tv_addNewJobRow = (TextView) findViewById(R.id.job_item_row);
        et_estimatedTime = (EditText) findViewById(R.id.estimated_time);
        tv_jobTotalPrice = (TextView) findViewById(R.id.total_price);
        et_notes = (EditText) findViewById(R.id.notes);
        btn_JobSubmit = (Button) findViewById(R.id.add_Job_btn_submit);

    }

    /**
     * Take the day of the week from the Intent sent from ViewDayJobs
     * and use it to populate the startDate day of the current week
     *
     * @param intent
     */
    private void populateSelectedStartDate(Intent intent) {
        // Get the day from the intent
        String day = intent.getStringExtra("addJobDay");

        // Create a calendar object and set it to the current week
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);

        switch (day) {
            case "monday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case "tuesday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case "wednesday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case "thursday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case "friday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case "saturday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            case "sunday":
                calendar.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
        }
        // Set and format the startDate
        startDate = calendar.getTimeInMillis();
        String date = DateFormat.getDateInstance().format(startDate);

        // Populate the startDate TextView
        tv_startDate.setText(date);
    }

    /**
     * Validate against NullPointerExceptions.
     * Customer cannot be null.
     * Employee cannot be null.
     * StartDate cannot be null.
     *
     * @return true if valid
     */
    public boolean isValidated() {

        // Check Customer
        if (getCustomerId() == -1) {
            Toast.makeText(this, "You must select a Customer!", Toast.LENGTH_SHORT).show();
            sv_pageContainer.smoothScrollTo(0, tv_dummyCustomer.getTop());
            return false;
        }

        // Check Employee
        if (getEmployeeId() == -1) {
            Toast.makeText(this, "You must select an Employee!", Toast.LENGTH_SHORT).show();
            sv_pageContainer.smoothScrollTo(0, tv_dummyEmployee.getTop());
            return false;
        }

        // Check Start Date
        if (startDate == 0) {
            Toast.makeText(this, "You must select a Start Date!", Toast.LENGTH_SHORT).show();
            sv_pageContainer.smoothScrollTo(0, tv_startDate.getTop());
            return false;
        }

        return true;
    }

    /**
     * Populate 'customers' ArrayList with all customer details from the DB.
     * Loop through and populate the 'customerNames' ArrayList with the first and last names of the customers.
     * Set-up customer adapter.
     */
    private void getCustomersAndPopulateSpinner() {
        final ArrayList<Customer> customers = db.getAllCustomers();
        ArrayList<String> customerNames = new ArrayList<>();
        String fullName;
        for (int i = 0; i < customers.size(); i++) {
            fullName = customers.get(i).getFirstName() + " " + customers.get(i).getLastName();
            customerNames.add(fullName);
        }
        ArrayAdapter<String> customerSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_list_item, customerNames);
        sp_customerSpinner.setAdapter(customerSpinnerAdapter);
    }

    /**
     * On button click, populate the customer's
     * address fields from the spinner selection
     */
    private void populateCustomerDetailsFromSpinnerSelection() {
        sp_customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customer = db.getCustomerById(id + 1);
                tv_addressLine1.setText(customer.getAddressLine1());
                tv_addressLine2.setText(customer.getAddressLine2());
                tv_town.setText(customer.getTown());
                tv_city.setText(customer.getCity());
                tv_postcode.setText(customer.getPostcode());

                System.out.println("z! AddJob - populateCustomerDetailsFromSpinnerSelection(): " +
                        customer.getCustomerId() + "/" + customer.getFirstName() + " " + customer.getLastName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    /**
     * Populate 'employees' ArrayList with all employee details from the DB.
     * Loop through and populate the 'employeeNames' ArrayList with the first and last names of the employees.
     * Set-up employee adapter.
     */
    private void getEmployeesAndPopulateSpinner() {
        final ArrayList<Employee> employees = db.getAllEmployees();
        ArrayList<String> employeeName = new ArrayList<>();
        String fullName;
        for (int i = 0; i < employees.size(); i++) {
            fullName = employees.get(i).getFirstName() + " " + employees.get(i).getLastName();
            employeeName.add(fullName);
        }
        ArrayAdapter<String> employeeSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_list_item, employeeName);
        sp_employeeSpinner.setAdapter(employeeSpinnerAdapter);
    }

    /**
     * On button click, select the employee for the job
     */
    private void populateEmployeeDetailsFromSpinnerSelection() {
        sp_employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee = db.getEmployeeById(id + 1);

                System.out.println("z! AddJob - populateEmployeeDetailsFromSpinnerSelection(): " +
                        employee.getEmployeeId() + "/" + employee.getFirstName() + " " + employee.getLastName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    /**
     * Create a datePicker Dialog box.
     * Return the selection in the onDateSet method.
     */
    private void createAndReturnDatePicker() {
        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    /**
     * onDateSet returns the date chosen from the datePicker,
     * sets values to a calendar object and converts
     * to millis for inserting into DB
     * Displays the date in the relevant TextView.
     *
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

        // Display formatted date in the startDate TextView
        String date = DateFormat.getDateInstance().format(startDate);
        tv_startDate.setText(date);

        System.out.println("z! AddJob - onDateSet(): " + date);
    }

    /**
     * Create a timePicker Dialog box.
     * Return the selection in the onTimeSet method.
     */
    private void createAndReturnTimePicker() {
        tv_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getFragmentManager(), "timePicker");
            }
        });
    }

    /**
     * onTimeSet returns the time chosen from the timePicker,
     * sets the value to a calendar object and converts
     * to millis for inserting to DB.
     * Displays the time in the relevant TextView.
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.getTime();

        startTime = calendar.getTimeInMillis();

        // Display formatted time in the startTime TextView
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime);
        tv_startTime.setText(time);

        System.out.println("z! AddJob - onTimeSet(): " + time);
    }

    /**
     * Get the job status from the job status spinner
     *
     * @return job status as a string
     */
    private String getJobStatus() {
        String jobStatus = sp_jobStatusSpinner.getSelectedItem().toString();

        System.out.println("z! AddJob - getJobStatus(): " + jobStatus);

        return jobStatus;
    }

    /**
     * Get the customer payment status from the
     * customer payment status spinner
     *
     * @return payment status as a string
     */
    private String getCustomerPaymentStatus() {
        String paymentStatus = sp_customerPaymentStatusSpinner.getSelectedItem().toString();

        System.out.println("z! AddJob - getCustomerPaymentStatus(): " + paymentStatus);

        return paymentStatus;
    }

    /**
     * Get the employee payment status from the
     * employee payment status spinner
     *
     * @return payment status as a string
     */
    private String getEmployeePaymentStatus() {
        String paymentStatus = sp_employeePaymentStatusSpinner.getSelectedItem().toString();

        System.out.println("z! AddJob - getEmployeePaymentStatus(): " + paymentStatus);

        return paymentStatus;
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
        price.setHint("£0.00");

        //Create a new button to delete the row
        final ImageButton delete = new ImageButton(this);
        delete.setBackground(getDrawable(R.drawable.ic_clear_24dp));

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
     *
     * @param delete
     */
    private void removeJobRow(ImageButton delete) {
        LinearLayout currentRow = (LinearLayout) delete.getParent();
        ((ViewGroup) currentRow.getParent()).removeView(currentRow);
    }

    /**
     * Calculate the price of all the entered job items
     *
     * @return total price as a double
     */
    private double calculatePriceOfJobItems() {
        double totalPriceOfJobItems = 0;
        LinearLayout currentRow;
        EditText editText;
        String description;
        double price;

        //Get the values from the description and price fields
        for (int i = 0; i < ll_jobListContainer.getChildCount(); i++) {
            currentRow = (LinearLayout) ll_jobListContainer.getChildAt(i);

            //Get the text from the Description field
            editText = (EditText) currentRow.findViewById(R.id.add_item_description);
            description = editText.getText().toString();

            //Get the text from the Price field, if it's not
            // empty convert it to a double, and keep a
            // running totalPriceOfJobItems
            editText = (EditText) currentRow.findViewById(R.id.add_item_price);
            price = 0;
            if (!editText.getText().toString().equals("")) {
                price = Double.parseDouble(editText.getText().toString());
            }
            totalPriceOfJobItems += price;

            System.out.println("z! AddJob - calculatePriceOfJobItems(): " +
                    description + " @ " + price);
        }

        System.out.println("z! AddJob - calculatePriceOfJobItems(): " +
                String.valueOf(totalPriceOfJobItems));

        return totalPriceOfJobItems;
    }

    /**
     * Get the estimated time needed for the employee pay
     *
     * @return estimatedTime as an int
     */
    private double getEstimatedTime() {
        double estimatedTime = 0;

        if (!et_estimatedTime.getText().toString().equals("")) {
            estimatedTime = Double.valueOf(et_estimatedTime.getText().toString());
        }
        System.out.println("z! AddJob - getEstimatedTime(): " +
                String.valueOf(estimatedTime));

        return estimatedTime;
    }

    /**
     * Calculate the total pay for the employee,
     * by multiplying the rate of pay by hours worked.
     * Calculate weekend pay at the set rate.
     *
     * @return total pay to employee as a double
     */
    private double calculatePayToEmployee() {
        double rateOfPay = 0;
        double weekendPay = 0;
        double employeePay;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String day = sdf.format(startDate).toLowerCase();

        // Get Employee's rate of pay
        try {
            rateOfPay = employee.getRateOfPay();

        } catch (NullPointerException e) {
            System.out.println("z! AddJob - calculatePayToEmployee NPE: " + e);
        }

        // If the day is a Saturday or Sunday, the employee gets
        // an extra £1 per hour
        if (day.equals("saturday") || day.equals("sunday")) {
            weekendPay = getEstimatedTime();
            System.out.println("z! AddJob - calculatePayToEmployee() - weekendPay: " + weekendPay);
        }

        // Calculate pay
        employeePay = (rateOfPay * getEstimatedTime()) + weekendPay;
        System.out.println("z! AddJob - calculatePayToEmployee() - employeePay: " +
                String.valueOf(employeePay));

        return employeePay;
    }

    /**
     * Get any job notes entered
     *
     * @return job notes as a string
     */
    @NonNull
    private String getJobNotes() {
        String jobNotes = et_notes.getText().toString();

        System.out.println("z! AddJob - getJobNotes(): " +
                jobNotes);

        return jobNotes;
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

        // Get customer payment status
        String customerPaymentStatus = getCustomerPaymentStatus();

        // Get employee payment status
        String employeePaymentStatus = getEmployeePaymentStatus();

        //Calculate price of job items
        double priceOfJobItems = calculatePriceOfJobItems();

        //Get estimated employee time required
        double estimateJobTime = getEstimatedTime();

        //Calculate pay to employee
        double payToEmployee = calculatePayToEmployee();

        //Total cost of job, (jobItems + employee pay)
        double totalCostForJob = priceOfJobItems + payToEmployee;
        System.out.println("z! AddJob - totalCostForJob: " +
                String.valueOf(totalCostForJob));

        //Set TextView with the total cost for the job
        tv_jobTotalPrice.setText(String.valueOf(totalCostForJob));

        //Get job notes
        String jobNotes = getJobNotes();

        //Save all job attributes to the DB
        saveToDB(startDate, startTime, jobStatus, customerPaymentStatus,
                employeePaymentStatus, estimateJobTime,
                totalCostForJob, jobNotes, customerId, employeeId);

        startActivity(new Intent(AddJob.this, ViewJobs.class));
        finish();
    }

    /**
     * Get the ID of the customer
     *
     * @return ID as a long
     */
    private long getCustomerId() {
        long customerId = 0;
        try {
            customerId = customer.getCustomerId();

            System.out.println("z! AddJob - getCustomerId(): " +
                    String.valueOf(customerId));

        } catch (NullPointerException e) {
            System.out.println("AddJob - getCustomerId NPE: " + e);
            return -1;
        }
        return customerId;
    }

    /**
     * Get the ID of the employee
     *
     * @return ID as a long
     */
    private long getEmployeeId() {
        long employeeId = 0;

        try {
            employeeId = employee.getEmployeeId();

            System.out.println("z! AddJob - getEmployeeId(): " +
                    String.valueOf(employeeId));

        } catch (NullPointerException e) {
            System.out.println("AddJob - getEmployeeId NPE: " + e);
            return -1;
        }
        return employeeId;
    }

    /**
     * Save all details of Job to DB.
     * Save the JobItems separately as they require the
     * id returned from the saved Job as a FK
     *
     * @param startDate
     * @param jobStatus
     * @param estimateJobTime
     * @param totalCostForJob
     * @param jobNotes
     * @param customerId
     * @param employeeId
     */
    private void saveToDB(long startDate, long startTime,
                          String jobStatus, String customerPaymentStatus,
                          String employeePaymentStatus, double estimateJobTime,
                          double totalCostForJob, String jobNotes,
                          long customerId, long employeeId) {
        long jobId = db.insertJob(startDate, startTime,
                jobStatus, customerPaymentStatus, employeePaymentStatus,
                estimateJobTime, totalCostForJob, jobNotes,
                customerId, employeeId);

        System.out.println("z! AddJob - saveToDB() - jobId: " +
                jobId);

        // Save the jobItems separately as they require
        // the jobId as a FK
        saveJobItemsToDB(jobId);
    }

    /**
     * Save the jobItems to the DB using the
     * id returned from saving the Job
     *
     * @param jobId
     */
    private void saveJobItemsToDB(long jobId) {
        LinearLayout currentRow;
        EditText editText;
        String description;
        double price;

        //Get the values from the description and price fields
        for (int i = 0; i < ll_jobListContainer.getChildCount(); i++) {
            currentRow = (LinearLayout) ll_jobListContainer.getChildAt(i);

            //Get the text from the Description field
            editText = (EditText) currentRow.findViewById(R.id.add_item_description);
            description = editText.getText().toString();

            //Get the text from the Price field, if it's not
            // empty convert it to a double, and keep a
            // running totalPriceOfJobItems
            editText = (EditText) currentRow.findViewById(R.id.add_item_price);
            price = 0;
            if (!editText.getText().toString().equals("")) {
                price = Double.parseDouble(editText.getText().toString());
            }
            // Save to DB
            db.insertJobItem(jobId, description, price);
        }

    }

}