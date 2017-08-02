package com.endorphinapps.kemikal.queenofclean.EditRecords;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.DateTime.DatePickerFragment;
import com.endorphinapps.kemikal.queenofclean.DateTime.TimePickerFragment;
import com.endorphinapps.kemikal.queenofclean.ENUMs.CustomerPaymentStatus;
import com.endorphinapps.kemikal.queenofclean.ENUMs.EmployeePaymentStatus;
import com.endorphinapps.kemikal.queenofclean.ENUMs.JobStatus;
import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.Entities.JobItem;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

public class EditJob extends MenuMain
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private DBHelper db;

    private Spinner sp_customerSpinner;
    private ConstraintLayout ll_addressContainer;
    private TextView tv_addressLine1;
    private TextView tv_addressLine2;
    private TextView tv_town;
    private TextView tv_city;
    private TextView tv_postcode;

    private Spinner sp_employeeSpinner;
    private TextView tv_startDate;
    private TextView tv_startTime;
    private long startDate;
    private long startTime;
    private Spinner sp_jobStatusSpinner;
    private Spinner sp_customerPaymentStatusSpinner;
    private Spinner sp_employeePaymentStatusSpinner;
    private LinearLayout ll_jobListContainer;
    private TextView tv_addNewJobRow;
    private EditText et_estimatedTime;
    private TextView tv_jobTotalPrice;
    private EditText et_notes;
    private Button btn_edit;

    private Customer customer;
    private Employee employee;
    private Job job;

    private TextView tv_dummyCustomer;
    private TextView tv_dummyEmployee;

    private long jobId;
    private Intent intent;

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
        setContentView(R.layout.activity_edit_job);

        // Instantiate a new instance of the DBHelper
        db = new DBHelper(this);

        intent = getIntent();
        jobId = intent.getLongExtra("EXTRAS_id", 0);
        job = db.getJobById(jobId);
        customer = db.getCustomerById(job.getCustomer());
        employee = db.getEmployeeById(job.getEmployee());

        //Find all views by their Id's
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit a Job");

        tv_dummyCustomer.setVisibility(View.GONE);
        tv_dummyEmployee.setVisibility(View.GONE);

        // Populate customers ArrayList and spinner with
        // customer details from the DB.
        // Set spinner to current customer
        getCustomersAndPopulateSpinner();
        // On button click, populate the customer's
        // address fields from the spinner selection
        populateCustomerDetailsFromSpinnerSelection();

        // Populate employees ArrayList and spinner with
        // employee details from the DB.
        // Set spinner to current employee
        getEmployeesAndPopulateSpinner();
        // On button click, select the employee for the job
        populateEmployeeDetailsFromSpinnerSelection();

        // Pick a date for the start of the job, using a datePicker
        // Returned in onDateSet
        createAndReturnDatePicker();
        // Populate the startDate with the current job's start date
        populateCurrentStartDate();

        // Pick a time for the start of the job, using a timePicker
        // Returned in onTimeSet
        createAndReturnTimePicker();
        // Populate startTime with the current job's start time
        populateCurrentStartTime();

        // Populate JobStatus spinner with values from Enum class
        sp_jobStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, JobStatus.values()));
        // Populate jobStatus with the current job's status
        int jobStatusPosition = JobStatus.valueOf(job.getJobStatusEnum()).ordinal();
        sp_jobStatusSpinner.setSelection(jobStatusPosition);

        // Populate CustomerPaymentStatus spinner with values from Enum class
        sp_customerPaymentStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, CustomerPaymentStatus.values()));
        // Populate paymentStatus with the current job's status
        int customerPaymentStatusPosition = CustomerPaymentStatus.valueOf(job.getCustomerPaymentStatusEnum()).ordinal();
        sp_customerPaymentStatusSpinner.setSelection(customerPaymentStatusPosition);

        // Populate EmployeePaymentStatus spinner with values from Enum class
        sp_employeePaymentStatusSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_list_item, EmployeePaymentStatus.values()));
        // Populate paymentStatus with the current job's status
        int employeePaymentStatusPosition = EmployeePaymentStatus.valueOf(job.getEmployeePaymentStatusEnum()).ordinal();
        sp_employeePaymentStatusSpinner.setSelection(employeePaymentStatusPosition);

        // Populate estimatedTime with the current job's time
        et_estimatedTime.setText(String.valueOf(job.getEstimatedTime()));

        // Populate Total Price with the current job's price
        tv_jobTotalPrice.setText("£");
        tv_jobTotalPrice.append(String.format(Locale.getDefault(), "%.2f", job.getTotalPrice()));

        // Populate jobNotes with the current job's notes
        et_notes.setText(job.getNotes());

        // Populate jobItems with the current job's items
        populateJobRow();

        //Calls addJobRow() to add a new row layout
        tv_addNewJobRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobRow();
            }
        });

        // Get the values of the Description and Price, for each row
        btn_edit.setOnClickListener(new View.OnClickListener() {
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
        ll_addressContainer = (ConstraintLayout) findViewById(R.id.add_job_customer_address_container);
        tv_addressLine1 = (TextView) findViewById(R.id.address_line_1);
        tv_addressLine2 = (TextView) findViewById(R.id.address_line_2);
        tv_town = (TextView) findViewById(R.id.address_town);
        tv_city = (TextView) findViewById(R.id.address_city);
        tv_postcode = (TextView) findViewById(R.id.address_postcode);
        sp_customerSpinner = (Spinner) findViewById(R.id.add_customer_spinner);
        sp_employeeSpinner = (Spinner) findViewById(R.id.add_employee_spinner);
        tv_startDate = (TextView) findViewById(R.id.add_start_date);
        tv_startTime = (TextView) findViewById(R.id.add_start_time);
        sp_jobStatusSpinner = (Spinner) findViewById(R.id.add_job_status_spinner);
        sp_customerPaymentStatusSpinner = (Spinner) findViewById(R.id.add_customer_payment_status_spinner);
        sp_employeePaymentStatusSpinner = (Spinner) findViewById(R.id.add_employee_payment_status_spinner);
        ll_jobListContainer = (LinearLayout) findViewById(R.id.add_job_list_container);
        tv_addNewJobRow = (TextView) findViewById(R.id.add_job_item_row);
        et_estimatedTime = (EditText) findViewById(R.id.add_job_estimated_time);
        tv_jobTotalPrice = (TextView) findViewById(R.id.add_job_total_price);
        et_notes = (EditText) findViewById(R.id.add_job_notes);
        btn_edit = (Button) findViewById(R.id.apply_changes);

        tv_dummyCustomer = (TextView) findViewById(R.id.dummy_customer);
        tv_dummyEmployee = (TextView) findViewById(R.id.dummy_employee);
    }

    /**
     * Populate 'customers' ArrayList with all customer details from the DB.
     * Loop through and populate the 'customerNames' ArrayList with the first and last names of the customers.
     * Set-up customer adapter.
     * Iterate through the list of customers and check a match to the current name
     * set spinner to current customer
     */
    private void getCustomersAndPopulateSpinner() {
        final ArrayList<Customer> customers = db.getAllCustomers();
        ArrayList<String> customerNames = new ArrayList<>();

        String fullName;
        int arraySize = customers.size();
        for (int i = 0; i < arraySize; i++) {
            fullName = customers.get(i).getFirstName() + " " + customers.get(i).getLastName();
            customerNames.add(fullName);
        }

        ArrayAdapter<String> customerSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_list_item, customerNames);
        sp_customerSpinner.setAdapter(customerSpinnerAdapter);

        // Iterate through the list of customers and check
        // to see if the current name matches.
        // Set index as position
        String currentCustomerFullName = customer.getFirstName() + " " + customer.getLastName();
        int arraySize2 = customerNames.size();
        for (int i = 0; i < arraySize2; i++) {
            if (currentCustomerFullName.equals(customerNames.get(i))) {
                sp_customerSpinner.setSelection(i);
            }
        }
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
     * Iterate through the list of employees and check a match to the current name
     * set spinner to current employee
     */
    private void getEmployeesAndPopulateSpinner() {
        final ArrayList<Employee> employees = db.getAllEmployees();
        ArrayList<String> employeeNames = new ArrayList<>();

        String fullName;
        int arraySize = employees.size();
        for (int i = 0; i < arraySize; i++) {
            fullName = employees.get(i).getFirstName() + " " + employees.get(i).getLastName();
            employeeNames.add(fullName);
        }

        ArrayAdapter<String> employeeSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_list_item, employeeNames);
        sp_employeeSpinner.setAdapter(employeeSpinnerAdapter);

        // Iterate through the list of employees and check
        // to see if the current name matches.
        // Set index as position
        String currentEmployeeFullName = employee.getFirstName() + " " + employee.getLastName();
        int arraySize2 = employeeNames.size();
        for (int i = 0; i < arraySize2; i++) {
            if (currentEmployeeFullName.equals(employeeNames.get(i))) {
                sp_employeeSpinner.setSelection(i);
            }
        }
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
     * Populate the startDate with the current job's startDate
     * and display it in the tv_startDate TextView
     */
    public void populateCurrentStartDate() {
        // Get current startDate
        startDate = job.getStartDate();

        // Format date to dd:month:yyyy
        String date = DateFormat.getDateInstance().format(startDate);

        // Display current start date in TextView
        tv_startDate.setText(date);

        System.out.println("z! AddJob - populateCurrentStartDate(): " + date);
    }

    /**
     * onDateSet returns the date chosen from the datePicker,
     * sets values to a calendar object and converts
     * to millis for inserting into DB
     * Displays the date in the relevant TextView.
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
     * Populate the startTime with the current job's startTime
     * and display it in the tv_startTime TextView
     */
    public void populateCurrentStartTime() {
        // Get current startTime
        startTime = job.getStartTime();

        // Format time to hh:mm
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime);

        // Display in TextView
        tv_startTime.setText(time);

        System.out.println("z! AddJob - populateCurrentStartTime(): " + time);
    }

    /**
     * onTimeSet returns the time chosen from the timePicker,
     * sets the value to a calendar object and converts
     * to millis for inserting to DB.
     * Displays the time in the relevant TextView.
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
     * @return job status as a string
     */
    private String getJobStatus() {
        String jobStatus = sp_jobStatusSpinner.getSelectedItem().toString();

        System.out.println("z! EditJob - getJobStatus(): " + jobStatus);

        return jobStatus;
    }

    /**
     * Get the customer payment status from the
     * customer payment status spinner
     * @return customer payment status as a string
     */
    private String getCustomerPaymentStatus() {
        String paymentStatus = sp_customerPaymentStatusSpinner.getSelectedItem().toString();

        System.out.println("z! EditJob - getCustomerJobStatus(): " + paymentStatus);

        return paymentStatus;
    }

    /**
     * Get the employee payment status from the
     * employee payment status spinner
     *
     * @return employee payment status as a string
     */
    private String getEmployeePaymentStatus() {
        String paymentStatus = sp_employeePaymentStatusSpinner.getSelectedItem().toString();

        System.out.println("z! EditJob - getEmployeeJobStatus(): " + paymentStatus);

        return paymentStatus;
    }

    /**
     * Creates a new LinearLayout as row container
     * Populates the row with the current JobItem's
     * description and price as EditText fields
     * Adds a listener for the delete button to remove the current row
     */
    private void populateJobRow() {
        // Create a list of JobItems, using the current JobId
        ArrayList<JobItem> jobItems;
        jobItems = db.getJobItemsByJobId(job.getId());

        // Iterate through the list of jobsItems and create
        // and populate the description and price fields
        // of each JobItem
        int arraySize = jobItems.size();
        for (int i = 0; i < arraySize; i++) {

            //Create a new row container
            LinearLayout jobRowContainer = new LinearLayout(this);

            //Create a new EditText for the Description
            EditText description = new EditText(this);
            description.setId(R.id.add_item_description);
            // Set current description
            description.setText(jobItems.get(i).getDescription());
            description.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));

            //Create an EditText for the Price
            //Will only accept a decimal number
            EditText price = new EditText(this);
            price.setId(R.id.add_item_price);
            price.setInputType(InputType.TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
            // Ser current price, formatted to two decimal places
            price.setText(String.format(Locale.getDefault(), "%.2f", jobItems.get(i).getPrice()));

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
                    db.deleteJobItemByJobId(jobId);
                    removeJobRow(delete);
                }
            });
        }
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
        EditText description = new EditText(this);
        description.setId(R.id.add_item_description);
        description.setHint("Description...");
        description.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        //Create an EditText for the Price
        //Will only accept a decimal number
        EditText price = new EditText(this);
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
        int arraySize = ll_jobListContainer.getChildCount();
        for (int i = 0; i < arraySize; i++) {
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
    private int getEstimatedTime() {
        int estimatedTime = 0;

        if (!et_estimatedTime.getText().toString().equals("")) {
            estimatedTime = Integer.valueOf(et_estimatedTime.getText().toString());
        }
        System.out.println("z! AddJob - getEstimatedTime(): " +
                String.valueOf(estimatedTime));

        return estimatedTime;
    }

    /**
     * Calculate the total pay for the employee,
     * by multiplying the rate of pay by hours worked
     *
     * @return total pay to employee as a double
     */
    private double calculatePayToEmployee() {
        double rateOfPay = employee.getRateOfPay();
        double employeePay = rateOfPay * getEstimatedTime();

        System.out.println("z! AddJob - calculatePayToEmployee(): " +
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
        int estimateJobTime = getEstimatedTime();

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
        saveToDB(jobId, startDate, startTime, jobStatus,
                customerPaymentStatus, employeePaymentStatus,
                estimateJobTime, totalCostForJob, jobNotes,
                customerId, employeeId);

        // Go back to the all jobs ListView
        startActivity(new Intent(EditJob.this, ViewJobs.class));
        finish();
    }

    /**
     * Get the ID of the customer
     * @return ID as a long
     */
    private long getCustomerId() {
        long customerId = customer.getCustomerId();

        System.out.println("z! AddJob - getCustomerId(): " +
                String.valueOf(customerId));

        return customerId;
    }

    /**
     * Get the ID of the customer
     * @return ID as a long
     */
    private long getEmployeeId() {
        long employeeId = employee.getEmployeeId();

        System.out.println("z! AddJob - getEmployeeId(): " +
                String.valueOf(employeeId));

        return employeeId;
    }

    /**
     * Save all details of Job to DB.
     * Save the JobItems separately as they require the
     * id returned from the saved Job as a FK
     * @param id
     * @param startDate
     * @param startTime
     * @param jobStatus
     * @param estimateJobTime
     * @param totalCostForJob
     * @param jobNotes
     * @param customerId
     * @param employeeId
     */
    private void saveToDB(long id, long startDate,
                          long startTime, String jobStatus, String customerPaymentStatus,
                          String employeePaymentStatus, int estimateJobTime,
                          double totalCostForJob, String jobNotes,
                          long customerId, long employeeId) {
        db.updateJob(id, startDate, startTime,
                jobStatus, customerPaymentStatus, employeePaymentStatus,
                estimateJobTime, totalCostForJob, jobNotes,
                customerId, employeeId);

        System.out.println("z! EditJob - saveToDB() - jobId: " + id);

        // Save the jobItems separately as they require
        // the jobId as a FK
        saveJobItemsToDB(jobId);
    }

    /**
     * Save the jobItems to the DB using the
     * id returned from saving the Job
     * @param jobId
     */
    private void saveJobItemsToDB(long jobId) {
        LinearLayout currentRow;
        EditText editText;
        String description;
        double price;

        // Check how many job items there are in the activity.
        int arraySize = ll_jobListContainer.getChildCount();
        // Get a list of jobItem id's for this Job
        ArrayList<JobItem> jobItemIds = db.getJobItemsIdsByJobId(jobId);
        // Create a counter for the number of updates
        int counter = 0;
        // Get the values from the description and price fields.
        for (int i = 0; i < arraySize; i++) {
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
            // Increment the counter. If the counter is less than
            // the existing number of jobItems, then they should be updated.
            // It the counter is more, then the extra should be inserted as new.
            counter++;
            if (counter <= jobItemIds.size()) {
                long jobItemId = jobItemIds.get(i).getJobItemId();
                db.updateJobItem(jobItemId, description, price);
            } else {
                db.insertJobItem(jobId, description, price);
            }
        }
    }

}