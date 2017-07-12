package com.endorphinapps.kemikal.queenofclean.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.Entities.JobItem;

import java.util.ArrayList;

/**
 * Created by KeMiKaL on 06/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "queenOfKleen.db";
    private static final int DATABASE_VERSION = 7;

    /**
     * Tables
     */
    private static final String TABLE_ADDRESSES = "addresses";
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_JOBS = "jobs";
    private static final String TABLE_JOB_ITEMS = "jobItems";

    /**
     * Columns
     */
    //Addresses
    private static final String COLUMN_ADDRESS_ID = "address_id";
    private static final String COLUMN_ADDRESS_LINE_1 = "addressLine1";
    private static final String COLUMN_ADDRESS_LINE_2 = "addressLine2";
    private static final String COLUMN_TOWN = "town";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_POSTCODE = "postcode";
    //Person
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_HOME_NUMBER = "homeNumber";
    private static final String COLUMN_MOBILE_NUMBER = "mobileNumber";
    private static final String COLUMN_EMAIL_ADDRESS = "emailAddress";
    private static final String COLUMN_ADDRESS = "address";
    //Person - Customer
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    //Person - Employee
    private static final String COLUMN_EMPLOYEE_ID = "employee_id";
    private static final String COLUMN_RATE_OF_PAY = "rateOfPay";
    //Job Columns
    private static final String COLUMN_JOB_ID = "job_id";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_START_TIME = "startTime";
    private static final String COLUMN_END_DATE = "endDate";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ESTIMATED_TIME = "estimatedTime";
    private static final String COLUMN_TOTAL_JOB_COST = "totalJobCost";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_CUSTOMER = "customer";
    private static final String COLUMN_EMPLOYEE = "employee";
    //Job Items
    private static final String COLUMN_JOB_ITEM_ID = "jobItem_id";
    private static final String COLUMN_JOB = "job";
    private static final String COLUMN_JOB_ITEM_DESCRIPTION = "jobItemDescription";
    private static final String COLUMN_JOB_ITEM_PRICE = "jobItemPrice";

    /**
     * Create Tables
     */
    private static final String CREATE_ADDRESS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESSES + "(" +
                    COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_ADDRESS_LINE_1 + " VARCHAR(50) NOT NULL, " +
                    COLUMN_ADDRESS_LINE_2 + " VARCHAR(50), " +
                    COLUMN_TOWN + " VARCHAR(50) NOT NULL, " +
                    COLUMN_CITY + " VARCHAR(50), " +
                    COLUMN_POSTCODE + " VARCHAR(7) NOT NULL);";

    private static final String CREATE_CUSTOMERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMERS + "(" +
                    COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_FIRST_NAME + " VARCHAR(50) NOT NULL, " +
                    COLUMN_LAST_NAME + " VARCHAR(50) NOT NULL, " +
                    COLUMN_HOME_NUMBER + " VARCHAR(11), " +
                    COLUMN_MOBILE_NUMBER + " VARCHAR(11) NOT NULL, " +
                    COLUMN_EMAIL_ADDRESS + " VARCHAR(50), " +
                    COLUMN_ADDRESS + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + COLUMN_ADDRESS + ") REFERENCES " + TABLE_ADDRESSES + " (" + COLUMN_CUSTOMER_ID + "));";

    private static final String CREATE_EMPLOYEES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEES + "(" +
                    COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_FIRST_NAME + " VARCHAR(50) NOT NULL, " +
                    COLUMN_LAST_NAME + " VARCHAR(50) NOT NULL, " +
                    COLUMN_HOME_NUMBER + " VARCHAR(11), " +
                    COLUMN_MOBILE_NUMBER + " VARCHAR(11) NOT NULL, " +
                    COLUMN_EMAIL_ADDRESS + " VARCHAR(50), " +
                    COLUMN_RATE_OF_PAY + " REAL, " +
                    COLUMN_ADDRESS + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + COLUMN_ADDRESS + ") REFERENCES " + TABLE_ADDRESSES + " (" + COLUMN_EMPLOYEE_ID + "));";

    private static final String CREATE_JOBS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_JOBS + "(" +
                    COLUMN_JOB_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_START_DATE + " INTEGER NOT NULL, " +
                    COLUMN_START_TIME + " INTEGER NOT NULL, " +
                    COLUMN_END_DATE + " INTEGER, " +
                    COLUMN_STATUS + " VARCHAR(50) NOT NULL, " +
                    COLUMN_ESTIMATED_TIME + " INTEGER NOT NULL, " +
                    COLUMN_TOTAL_JOB_COST + " REAL NOT NULL, " +
                    COLUMN_NOTES + " TEXT, " +
                    COLUMN_CUSTOMER + " INTEGER NOT NULL, " +
                    COLUMN_EMPLOYEE + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_CUSTOMER + ") REFERENCES " + TABLE_CUSTOMERS + " (" + COLUMN_CUSTOMER_ID + "), " +
                        " FOREIGN KEY (" + COLUMN_EMPLOYEE + ") REFERENCES " + TABLE_EMPLOYEES + " (" + COLUMN_EMPLOYEE_ID + "));";

    private static final String CREATE_JOB_ITEMS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_JOB_ITEMS + "(" +
                    COLUMN_JOB_ITEM_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_JOB + " INTEGER NOT NULL, " +
                    COLUMN_JOB_ITEM_DESCRIPTION + " VARCHAR(100) NOT NULL, " +
                    COLUMN_JOB_ITEM_PRICE + " REAL NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_JOB + ") REFERENCES " + TABLE_JOBS + "(" + COLUMN_JOB_ID + "));";

    /**
     * Main constructor for DBHelper class
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create all tables from string constants
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADDRESS_TABLE);
        db.execSQL(CREATE_CUSTOMERS_TABLE);
        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL(CREATE_JOBS_TABLE);
        db.execSQL(CREATE_JOB_ITEMS_TABLE);
    }

    /**
     * Drop and re-create all tables
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        onCreate(db);
    }

    /**
     * Insert Address details passed from AddEmployee
     * @param line1
     * @param line2
     * @param town
     * @param postcode
     * @return addressId as a long
     */
    public long insertAddress(String line1, String line2,
                              String town, String city,
                              String postcode) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDRESS_LINE_1, line1);
        values.put(COLUMN_ADDRESS_LINE_2, line2);
        values.put(COLUMN_TOWN, town);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_POSTCODE, postcode);
        long addressID = db.insert(TABLE_ADDRESSES, null, values);
        db.close();

        return addressID;
    }

    public void updateAddress(long id, String line1,
                              String line2, String town, String city,
                              String postcode) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDRESS_LINE_1, line1);
        values.put(COLUMN_ADDRESS_LINE_2, line2);
        values.put(COLUMN_TOWN, town);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_POSTCODE, postcode);

        db.update(TABLE_ADDRESSES, values, COLUMN_ADDRESS_ID + " = " + id, null);
        db.close();
    }

    /**
     * Insert Customer details passed from AddCustomer
     * @param firstName
     * @param lastName
     * @param homeNumber
     * @param mobileNumber
     * @param eMail
     * @param address
     * @return customerId as a long
     */
    public long insertCustomer(String firstName, String lastName,
                               String homeNumber, String mobileNumber,
                               String eMail, int address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        values.put(COLUMN_ADDRESS, address);
        long customerId = db.insert(TABLE_CUSTOMERS, null, values);
        db.close();

        return customerId;
    }

    public void updateCustomer(long id, String firstName,
                               String lastName, String homeNumber,
                               String mobileNumber, String eMail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        db.update(TABLE_CUSTOMERS, values, COLUMN_CUSTOMER_ID + " = " + id, null);
        db.close();
    }

    /**
     * Insert Employee details passed from AddEmployee
     * @param firstName
     * @param lastName
     * @param homeNumber
     * @param mobileNumber
     * @param eMail
     * @param address
     * @param rateOfPay
     * @return employeeId as a long
     */
    public long insertEmployee(String firstName, String lastName,
                               String homeNumber, String mobileNumber,
                               String eMail, long address, double rateOfPay) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        values.put(COLUMN_RATE_OF_PAY, rateOfPay);
        System.out.println("DBHelper - insertEmployee - rateOfPay: " + rateOfPay);
        values.put(COLUMN_ADDRESS, address);
        long employeeId = db.insert(TABLE_EMPLOYEES, null, values);
        db.close();

        return employeeId;
    }

    /**
     * Insert Job details passed from AddJob
     * @param startDate
     * @param status
     * @param estimatedTime
     * @param totalPrice
     * @param notes
     * @param customer
     * @param employee
     * @return jobId as a long
     */
    public long insertJob(long startDate, long startTime,
                          String status, int estimatedTime, double totalPrice,
                          String notes, long customer, long employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_START_DATE, startDate);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_ESTIMATED_TIME, estimatedTime);
        values.put(COLUMN_TOTAL_JOB_COST, totalPrice);
        values.put(COLUMN_NOTES, notes);
        values.put(COLUMN_CUSTOMER, customer);
        values.put(COLUMN_EMPLOYEE, employee);
        long jobId = db.insert(TABLE_JOBS, null, values);
        db.close();

        return jobId;
    }

    /**
     * Insert JobItem details
     * @param job
     * @param description
     * @param price
     */
    public void insertJobItem(long job, String description, double price) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_JOB, job);
        values.put(COLUMN_JOB_ITEM_DESCRIPTION, description);
        values.put(COLUMN_JOB_ITEM_PRICE, price);
        long id = db.insert(TABLE_JOB_ITEMS, null, values);

        System.out.println("z! insert: " + id);
        System.out.println("z! insert: " + job);
        System.out.println("z! insert: " + description);
        System.out.println("z! insert: " + price);

        db.close();
    }

    /**
     * Get a single Customer by ID
     * @param id to identify the Customer (PK)
     * @return a Customer object
     */
    public Customer getCustomerById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Customer customer = new Customer();
        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_CUSTOMERS + " c " +
                "JOIN " + TABLE_ADDRESSES + " a ON a." + COLUMN_ADDRESS_ID + " = c." + COLUMN_ADDRESS + " " +
                "WHERE c." + COLUMN_CUSTOMER_ID + " = " + id,
                null
        );

        if (cursor.moveToFirst()) {
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
            customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
            customer.setHomeNumber(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_NUMBER)));
            customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS)));
            customer.setAddressLine1(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_1)));
            customer.setAddressLine2(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_2)));
            customer.setTown(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN)));
            customer.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            customer.setPostcode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE)));
        }
        cursor.close();
        db.close();
        return customer;
    }

    /**
     * Select all details of all customers
     * @return list of customers
     */
    public ArrayList<Customer> getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Customer> customers = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_CUSTOMERS + " c " +
                "JOIN " + TABLE_ADDRESSES + " a ON a." + COLUMN_ADDRESS_ID + " = c." + COLUMN_ADDRESS + ";",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
                customer.setHomeNumber(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_NUMBER)));
                customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS)));
                customer.setAddressLine1(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_1)));
                customer.setAddressLine2(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_2)));
                customer.setTown(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN)));
                customer.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                customer.setPostcode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE)));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    /**
     * Get a single Employee by ID
     * @param id to identify the Employee(PK)
     * @return an Employee object
     */
    public Employee getEmployeeById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Employee employee = new Employee();
        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_EMPLOYEES + " e " +
                "JOIN " + TABLE_ADDRESSES + " a ON a." + COLUMN_ADDRESS_ID + " = e." + COLUMN_ADDRESS + " " +
                "WHERE e." + COLUMN_EMPLOYEE_ID + " = " + id,
                null
        );

        if (cursor.moveToFirst()) {
            employee.setEmployeeId(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)));
            employee.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            employee.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
            employee.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
            employee.setHomeNumber(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_NUMBER)));
            employee.setEmailAddress(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS)));
            employee.setAddressLine1(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_1)));
            employee.setAddressLine2(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_2)));
            employee.setTown(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN)));
            employee.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            employee.setPostcode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE)));
            employee.setRateOfPay(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATE_OF_PAY)));
        }
        cursor.close();
        db.close();
        return employee;
    }

    /**
     * Select all details of all employees
     * @return list of employees
     */
    public ArrayList<Employee> getAllEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Employee> employees = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_EMPLOYEES + " e " +
                "JOIN " + TABLE_ADDRESSES + " a ON a." + COLUMN_ADDRESS_ID + " = e." + COLUMN_ADDRESS + ";",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setEmployeeId(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)));
                employee.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                employee.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                employee.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
                employee.setHomeNumber(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_NUMBER)));
                employee.setEmailAddress(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS)));
                employee.setAddressLine1(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_1)));
                employee.setAddressLine2(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_LINE_2)));
                employee.setTown(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN)));
                employee.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                employee.setPostcode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE)));
                employee.setRateOfPay(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATE_OF_PAY)));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }

    /**
     * Select all details of all Jobs
     * @return list of Jobs
     */
    public ArrayList<Job> getAllJobs() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Job> jobs = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_JOBS + ";"
                ,null
        );

        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID)));
                job.setCustomer(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER)));
                job.setEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE)));
                job.setStartDate(cursor.getLong(cursor.getColumnIndex(COLUMN_START_DATE)));
                job.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_START_TIME)));
                job.setJobStatusEnum(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                job.setEstimatedTime(cursor.getInt(cursor.getColumnIndex(COLUMN_ESTIMATED_TIME)));
                job.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_JOB_COST)));
                job.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));
                jobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobs;
    }

    /**
     * Select all jobs details between a date range.
     * The dates will be FROM the Monday of the present week
     * TO the Sunday of the present week.
     * @param from
     * @param to
     * @return All details of Jobs as an ArrayList
     */
    public ArrayList<Job> getJobsByDateRange(long from, long to) {
        ArrayList<Job> jobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_JOBS +
                " WHERE " + COLUMN_START_DATE + " >= " + from +
                " AND " + COLUMN_START_DATE + " <= " + to + ";",
                null
        );

        if (cursor.moveToFirst()) {
            do{
                Job job = new Job();
                job.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID)));
                job.setCustomer(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER)));
                job.setEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE)));
                job.setStartDate(cursor.getLong(cursor.getColumnIndex(COLUMN_START_DATE)));
                job.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_START_TIME)));
                job.setJobStatusEnum(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                job.setEstimatedTime(cursor.getInt(cursor.getColumnIndex(COLUMN_ESTIMATED_TIME)));
                job.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_JOB_COST)));
                job.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));
                jobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobs;
    }

    /**
     * Select details of jobItems by Job ID
     * @param id
     * @return jobItems as arrayList
     */
    public ArrayList<JobItem> getJobItems(long id) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<JobItem> jobItems = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_JOB_ITEMS +
                " WHERE " + COLUMN_JOB + " = " + id
                , null
        );

        if (cursor.moveToFirst()) {
            do{
                JobItem jobItem = new JobItem();
                jobItem.setJobItemId(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ITEM_ID)));
                jobItem.setJob(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB)));
                jobItem.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_ITEM_DESCRIPTION)));
                jobItem.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_JOB_ITEM_PRICE)));
                jobItems.add(jobItem);

                System.out.println("z! --------------------------------");
                System.out.println("z! id: " + jobItem.getJobItemId());
                System.out.println("z! job: " + jobItem.getJob());
                System.out.println("z! desc: " + jobItem.getDescription());
                System.out.println("z! price: " + jobItem.getPrice());

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobItems;
    }

    /**
     * Drop all tables
     * @param db
     */
    public void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_ITEMS);
    }

    /**
     * Get Database Name
     * @return DB name as string
     */
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public long getAddressIdFromCustomerId(long customerId) {
        SQLiteDatabase db = getReadableDatabase();
        long addressId = 0;
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_ADDRESS +
                        " FROM " + TABLE_CUSTOMERS +
                        " WHERE " + COLUMN_CUSTOMER_ID + " = " + customerId + ";",
                null
        );

        if (cursor.moveToFirst()) {
            addressId = cursor.getLong(cursor.getColumnIndex(COLUMN_ADDRESS));
        }
        cursor.close();
        db.close();

        return addressId;
    }
}
