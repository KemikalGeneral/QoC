package com.endorphinapps.kemikal.queenofclean.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.endorphinapps.kemikal.queenofclean.Entities.Customer;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.util.ArrayList;

/**
 * Created by KeMiKaL on 06/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "queenOfKleen.db";
    private static final int DATABASE_VERSION = 5;

    /**
     * Tables
     */
    private static final String TABLE_ADDRESSES = "addresses";
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_JOBS = "jobs";

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
    private static final String COLUMN_END_DATE = "endDate";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TIME_TAKEN = "timeTaken";
    private static final String COLUMN_TOTAL_JOB_COST = "totalJobCost";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_CUSTOMER = "customer";
    private static final String COLUMN_EMPLOYEE = "employee";

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
                    COLUMN_RATE_OF_PAY + " INTEGER, " +
                    COLUMN_ADDRESS + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + COLUMN_ADDRESS + ") REFERENCES " + TABLE_ADDRESSES + " (" + COLUMN_EMPLOYEE_ID + "));";

    private static final String CREATE_JOBS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_JOBS + "(" +
                    COLUMN_JOB_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_START_DATE + " INTEGER NOT NULL, " +
                    COLUMN_END_DATE + " INTEGER, " +
                    COLUMN_STATUS + " VARCHAR(50) NOT NULL, " +
                    COLUMN_TIME_TAKEN + " INTEGER NOT NULL, " +
                    COLUMN_TOTAL_JOB_COST + " INTEGER NOT NULL, " +
                    COLUMN_NOTES + " TEXT, " +
                    COLUMN_CUSTOMER + " INTEGER NOT NULL, " +
                    COLUMN_EMPLOYEE + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_CUSTOMER + ") REFERENCES " + TABLE_CUSTOMERS + " (" + COLUMN_CUSTOMER_ID + "), " +
                        " FOREIGN KEY (" + COLUMN_EMPLOYEE + ") REFERENCES " + TABLE_EMPLOYEES + " (" + COLUMN_EMPLOYEE_ID + "));";


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
                               String eMail, long address, int rateOfPay) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        values.put(COLUMN_RATE_OF_PAY, rateOfPay);
        values.put(COLUMN_ADDRESS, address);
        long employeeId = db.insert(TABLE_EMPLOYEES, null, values);
        db.close();

        return employeeId;
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

    /**
     * Insert Job details passed from AddJob
     * @param startDate
     * @param status
     * @param timeTaken
     * @param totalPrice
     * @param notes
     * @param customer
     * @param employee
     * @return jobId as a long
     */
    public long insertJob(long startDate, String status,
                          int timeTaken, double totalPrice,
                          String notes, long customer, long employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_START_DATE, startDate);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_TIME_TAKEN, timeTaken);
        values.put(COLUMN_TOTAL_JOB_COST, totalPrice);
        values.put(COLUMN_NOTES, notes);
        values.put(COLUMN_CUSTOMER, customer);
        values.put(COLUMN_EMPLOYEE, employee);
        long jobId = db.insert(TABLE_JOBS, null, values);
        db.close();

        return jobId;
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
            employee.setRateOfPay(cursor.getInt(cursor.getColumnIndex(COLUMN_RATE_OF_PAY)));
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
                employee.setRateOfPay(cursor.getInt(cursor.getColumnIndex(COLUMN_RATE_OF_PAY)));
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
                job.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));
                job.setCustomer(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER)));
                job.setEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE)));
                jobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobs;
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
    }

    /**
     * Get Database Name
     * @return DB name as string
     */
    public String getDatabaseName() {
        return DATABASE_NAME;
    }
}
