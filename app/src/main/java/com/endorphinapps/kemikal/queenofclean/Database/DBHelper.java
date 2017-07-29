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

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "queenOfKleen.db";
    private static final int DATABASE_VERSION = 7;

    /**
     * Tables
     */
    private static final String TABLE_ADDRESSES = "addresses";
    private static final String TABLE_AVAILABILITY = "availability";
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
    //Availability
    private static final String COLUMN_AVAILABILITY_ID = "availability_id";
    private static final String COLUMN_MONDAY_AM = "monday_am";
    private static final String COLUMN_MONDAY_PM = "monday_pm";
    private static final String COLUMN_TUESDAY_AM = "tuesday_am";
    private static final String COLUMN_TUESDAY_PM = "tuesday_pm";
    private static final String COLUMN_WEDNESDAY_AM = "wednesday_am";
    private static final String COLUMN_WEDNESDAY_PM = "wednesday_pm";
    private static final String COLUMN_THURSDAY_AM = "thursday_am";
    private static final String COLUMN_THURSDAY_PM = "thursday_pm";
    private static final String COLUMN_FRIDAY_AM = "friday_am";
    private static final String COLUMN_FRIDAY_PM = "friday_pm";
    private static final String COLUMN_SATURDAY_AM = "saturday_am";
    private static final String COLUMN_SATURDAY_PM = "saturday_pm";
    private static final String COLUMN_SUNDAY_AM = "sunday_am";
    private static final String COLUMN_SUNDAY_PM = "sunday_pm";
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
    private static final String COLUMN_AVAILABILITY = "availability";
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

    private static final String CREATE_AVAILABILITY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_AVAILABILITY + "(" +
                    COLUMN_AVAILABILITY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_MONDAY_AM + " INTEGER, " +
                    COLUMN_MONDAY_PM + " INTEGER, " +
                    COLUMN_TUESDAY_AM + " INTEGER, " +
                    COLUMN_TUESDAY_PM + " INTEGER, " +
                    COLUMN_WEDNESDAY_AM + " INTEGER, " +
                    COLUMN_WEDNESDAY_PM + " INTEGER, " +
                    COLUMN_THURSDAY_AM + " INTEGER, " +
                    COLUMN_THURSDAY_PM + " INTEGER, " +
                    COLUMN_FRIDAY_AM + " INTEGER, " +
                    COLUMN_FRIDAY_PM + " INTEGER, " +
                    COLUMN_SATURDAY_AM + " INTEGER, " +
                    COLUMN_SATURDAY_PM + " INTEGER, " +
                    COLUMN_SUNDAY_AM + " INTEGER, " +
                    COLUMN_SUNDAY_PM + " INTEGER);";

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
                    COLUMN_AVAILABILITY + " INTEGER NOT NULL, " +
                    " FOREIGN KEY (" + COLUMN_ADDRESS + ") REFERENCES " + TABLE_ADDRESSES + " (" + COLUMN_EMPLOYEE_ID + ")," +
                    " FOREIGN KEY (" + COLUMN_AVAILABILITY + ") REFERENCES " + TABLE_AVAILABILITY + " (" + COLUMN_AVAILABILITY_ID + "));";


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
        db.execSQL(CREATE_AVAILABILITY_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AVAILABILITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        onCreate(db);
    }

    //////////////////////////////////////////////////////////
    //      ADDRESS
    //////////////////////////////////////////////////////////
    /**
     * Insert Address details
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
     * Get the address ID (FK in the customers table)
     * from the customer ID, so that it can be used
     * to update the address record.
     * @param customerId
     * @return addressID as a type long
     */
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

    /**
     * Get the address ID (FK in the employee table)
     * from the employee ID, so that it can be used
     * to update the address record.
     *
     * @param employeeId
     * @return addressID as a type long
     */
    public long getAddressIdFromEmployeeId(long employeeId) {
        SQLiteDatabase db = getReadableDatabase();
        long addressId = 0;

        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_ADDRESS +
                        " FROM " + TABLE_EMPLOYEES +
                        " WHERE " + COLUMN_EMPLOYEE_ID + " = " + employeeId + ";",
                null
        );

        if (cursor.moveToFirst()) {
            addressId = cursor.getLong(cursor.getColumnIndex(COLUMN_ADDRESS));
        }
        cursor.close();
        db.close();

        return addressId;
    }

    /**
     * Update the address of either the
     * customers or employees, using the PK, sought from
     * the FK of the respective table.
     *
     * @param id
     * @param line1
     * @param line2
     * @param town
     * @param city
     * @param postcode
     */
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


    //////////////////////////////////////////////////////////
    //      AVAILABILITY
    //////////////////////////////////////////////////////////
    /**
     * Insert daily AM & PM availabilities for employees
     * PK is a FK in Employees table
     *
     * @param mondayAM
     * @param mondayPM
     * @param tuesdayAM
     * @param tuesdayPM
     * @param wednesdayAM
     * @param wednesdayPM
     * @param thursdayAM
     * @param thursdayPM
     * @param fridayAM
     * @param fridayPM
     * @param saturdayAM
     * @param saturdayPM
     * @param sundayAM
     * @param sundayPM
     * @return availabilityID as a type long
     */
    public long insertAvailability(
            int mondayAM, int mondayPM,
            int tuesdayAM, int tuesdayPM,
            int wednesdayAM, int wednesdayPM,
            int thursdayAM, int thursdayPM,
            int fridayAM, int fridayPM,
            int saturdayAM, int saturdayPM,
            int sundayAM, int sundayPM) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MONDAY_AM, mondayAM);
        values.put(COLUMN_MONDAY_PM, mondayPM);
        values.put(COLUMN_TUESDAY_AM, tuesdayAM);
        values.put(COLUMN_TUESDAY_PM, tuesdayPM);
        values.put(COLUMN_WEDNESDAY_AM, wednesdayAM);
        values.put(COLUMN_WEDNESDAY_PM, wednesdayPM);
        values.put(COLUMN_THURSDAY_AM, thursdayAM);
        values.put(COLUMN_THURSDAY_PM, thursdayPM);
        values.put(COLUMN_FRIDAY_AM, fridayAM);
        values.put(COLUMN_FRIDAY_PM, fridayPM);
        values.put(COLUMN_SATURDAY_AM, saturdayAM);
        values.put(COLUMN_SATURDAY_PM, saturdayPM);
        values.put(COLUMN_SUNDAY_AM, sundayAM);
        values.put(COLUMN_SUNDAY_PM, sundayPM);
        long availabilityID = db.insert(TABLE_AVAILABILITY, null, values);
        db.close();

        return availabilityID;
    }

    /**
     * Get the availability ID (FK in the employee table)
     * from the employee ID, so that it can be used
     * to update the availability record.
     *
     * @param employeeId
     * @return availabilityID as a type long
     */
    public long getAvailabilityFromEmployeeId(long employeeId) {
        SQLiteDatabase db = getReadableDatabase();
        long availabilityId = 0;

        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_AVAILABILITY +
                        " FROM " + TABLE_EMPLOYEES +
                        " WHERE " + COLUMN_EMPLOYEE_ID + " = " + employeeId + ";",
                null
        );

        if (cursor.moveToFirst()) {
            availabilityId = cursor.getLong(cursor.getColumnIndex(COLUMN_AVAILABILITY));
        }
        cursor.close();
        db.close();

        return availabilityId;
    }

    /**
     * Update the availability of the employees,
     * using the PK, sought from
     * the FK of the respective table.
     * @param id
     * @param mondayAM
     * @param mondayPM
     * @param tuesdayAM
     * @param tuesdayPM
     * @param wednesdayAM
     * @param wednesdayPM
     * @param thursdayAM
     * @param thursdayPM
     * @param fridayAM
     * @param fridayPM
     * @param saturdayAM
     * @param saturdayPM
     * @param sundayAM
     * @param sundayPM
     */
    public void updateAvailability(long id,
                                   int mondayAM, int mondayPM,
                                   int tuesdayAM, int tuesdayPM,
                                   int wednesdayAM, int wednesdayPM,
                                   int thursdayAM, int thursdayPM,
                                   int fridayAM, int fridayPM,
                                   int saturdayAM, int saturdayPM,
                                   int sundayAM, int sundayPM) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MONDAY_AM, mondayAM);
        values.put(COLUMN_MONDAY_PM, mondayPM);
        values.put(COLUMN_TUESDAY_AM, tuesdayAM);
        values.put(COLUMN_TUESDAY_PM, tuesdayPM);
        values.put(COLUMN_WEDNESDAY_AM, wednesdayAM);
        values.put(COLUMN_WEDNESDAY_PM, wednesdayPM);
        values.put(COLUMN_THURSDAY_AM, thursdayAM);
        values.put(COLUMN_THURSDAY_PM, thursdayPM);
        values.put(COLUMN_FRIDAY_AM, fridayAM);
        values.put(COLUMN_FRIDAY_PM, fridayPM);
        values.put(COLUMN_SATURDAY_AM, saturdayAM);
        values.put(COLUMN_SATURDAY_PM, saturdayPM);
        values.put(COLUMN_SUNDAY_AM, sundayAM);
        values.put(COLUMN_SUNDAY_PM, sundayPM);

        db.update(TABLE_AVAILABILITY, values, COLUMN_AVAILABILITY_ID + " = " + id, null);
        db.close();
    }


    //////////////////////////////////////////////////////////
    //      CUSTOMER
    //////////////////////////////////////////////////////////
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
     * Update the customer's details using the ID
     * @param id
     * @param firstName
     * @param lastName
     * @param homeNumber
     * @param mobileNumber
     * @param eMail
     */
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
     * Delete the customer by the row ID
     *
     * @param customerId
     */
    public void deleteCustomerById(long customerId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_CUSTOMERS, COLUMN_CUSTOMER_ID + " = " + customerId, null);
        db.close();
    }


    //////////////////////////////////////////////////////////
    //      EMPLOYEE
    //////////////////////////////////////////////////////////
    /**
     * Insert Employee details passed from AddEmployee
     *
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
                               String eMail, long address, double rateOfPay,
                               long availability) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        values.put(COLUMN_RATE_OF_PAY, rateOfPay);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_AVAILABILITY, availability);

        long employeeId = db.insert(TABLE_EMPLOYEES, null, values);
        db.close();

        return employeeId;
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
                        "JOIN " + TABLE_ADDRESSES + " a ON a." + COLUMN_ADDRESS_ID + " = e." + COLUMN_ADDRESS +
                        " JOIN " + TABLE_AVAILABILITY + " av ON av." + COLUMN_AVAILABILITY_ID + " = e." + COLUMN_AVAILABILITY + ";",
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
                // Availability
                employee.setMondayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_MONDAY_AM)));
                employee.setMondayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_MONDAY_PM)));
                employee.setTuesdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_TUESDAY_AM)));
                employee.setTuesdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_TUESDAY_PM)));
                employee.setWednesdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_WEDNESDAY_AM)));
                employee.setWednesdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_WEDNESDAY_PM)));
                employee.setThursdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_THURSDAY_AM)));
                employee.setThursdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_THURSDAY_PM)));
                employee.setFridayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIDAY_AM)));
                employee.setFridayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIDAY_PM)));
                employee.setSaturdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_SATURDAY_AM)));
                employee.setSaturdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_SATURDAY_PM)));
                employee.setSundayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_SUNDAY_AM)));
                employee.setSundayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_SUNDAY_PM)));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
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
                        "JOIN " + TABLE_AVAILABILITY + " av ON av." + COLUMN_AVAILABILITY_ID + " = e." + COLUMN_AVAILABILITY + " " +
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
            employee.setMondayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_MONDAY_AM)));
            employee.setMondayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_MONDAY_PM)));
            employee.setTuesdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_TUESDAY_AM)));
            employee.setTuesdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_TUESDAY_PM)));
            employee.setWednesdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_WEDNESDAY_AM)));
            employee.setWednesdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_WEDNESDAY_PM)));
            employee.setThursdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_THURSDAY_AM)));
            employee.setThursdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_THURSDAY_PM)));
            employee.setFridayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIDAY_AM)));
            employee.setFridayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIDAY_PM)));
            employee.setSaturdayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_SATURDAY_AM)));
            employee.setSaturdayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_SATURDAY_PM)));
            employee.setSundayAM(cursor.getInt(cursor.getColumnIndex(COLUMN_SUNDAY_AM)));
            employee.setSundayPM(cursor.getInt(cursor.getColumnIndex(COLUMN_SUNDAY_PM)));
        }
        cursor.close();
        db.close();
        return employee;
    }

    /**
     * Update the customer's details using the ID
     * @param id
     * @param firstName
     * @param lastName
     * @param homeNumber
     * @param mobileNumber
     * @param eMail
     */
    public void updateEmployee(long id, String firstName,
                               String lastName, String homeNumber,
                               String mobileNumber, String eMail,
                               double rateOfPay) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_HOME_NUMBER, homeNumber);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_EMAIL_ADDRESS, eMail);
        values.put(COLUMN_RATE_OF_PAY, rateOfPay);

        db.update(TABLE_EMPLOYEES, values, COLUMN_EMPLOYEE_ID + " = " + id, null);
        db.close();
    }

    /**
     * Delete the employee by the row ID
     * @param employeeId
     */
    public void deleteEmployeeById(long employeeId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_EMPLOYEES, COLUMN_EMPLOYEE_ID + " = " + employeeId, null);
        db.close();
    }


    //////////////////////////////////////////////////////////
    //      JOB
    //////////////////////////////////////////////////////////
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
     * Select all details of all Jobs
     *
     * @return list of Jobs
     */
    public ArrayList<Job> getAllJobs() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Job> jobs = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_JOBS +
                        " ORDER BY " + COLUMN_START_DATE + ";"
                , null
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
     * Get a single Job by ID
     *
     * @param id
     * @return selected job
     */
    public Job getJobById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Job job = new Job();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_JOBS +
                        " WHERE " + COLUMN_JOB_ID + " = " + id,
                null
        );

        if (cursor.moveToFirst()) {
            job.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID)));
            job.setCustomer(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER)));
            job.setEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE)));
            job.setStartDate(cursor.getLong(cursor.getColumnIndex(COLUMN_START_DATE)));
            job.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_START_TIME)));
            job.setJobStatusEnum(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
            job.setEstimatedTime(cursor.getInt(cursor.getColumnIndex(COLUMN_ESTIMATED_TIME)));
            job.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_JOB_COST)));
            job.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));
        }

        System.out.println("z! ---------- getJobById ----------");
        System.out.println("z! job id: " + job.getId());
        System.out.println("z! customer id: " + job.getCustomer());
        System.out.println("z! employee id: " + job.getEmployee());
        System.out.println("z! start date: " + job.getStartDate());
        System.out.println("z! start time: " + job.getStartTime());
        System.out.println("z! job status: " + job.getJobStatusEnum());
        System.out.println("z! estimated time: " + job.getEstimatedTime());
        System.out.println("z! total price: " + job.getTotalPrice());
        System.out.println("z! notes: " + job.getNotes());
        cursor.close();
        db.close();

        return job;
    }

    /**
     * Select all jobs details between a date range.
     * The dates will be FROM the Monday of the present week
     * TO the Sunday of the present week.
     *
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
                        " AND " + COLUMN_START_DATE + " <= " + to +
                        " ORDER BY " + COLUMN_START_DATE + ";",
                null
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
     * Update Job details passed from EditJob
     *
     * @param jobId
     * @param startDate
     * @param status
     * @param estimatedTime
     * @param totalPrice
     * @param notes
     * @param customer
     * @param employee
     * @return jobId as a long
     */
    public void updateJob(long jobId, long startDate,
                          long startTime, String status, int estimatedTime,
                          double totalPrice, String notes, long customer,
                          long employee) {
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
        db.update(TABLE_JOBS, values, COLUMN_JOB_ID + " = " + jobId, null);
        db.close();
    }

    /**
     * Changes the JobStatus to the new status passed in
     * along with the rowID
     *
     * @param jobId
     * @param status
     */
    public void changeJobStatus(long jobId, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STATUS, status);
        db.update(TABLE_JOBS, values, COLUMN_JOB_ID + " = " + jobId, null);

        db.close();
    }

    /**
     * Delete a job by ID
     *
     * @param jobId
     */
    public void deleteJobById(long jobId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_JOBS, COLUMN_JOB_ID + " = " + jobId, null);
        db.close();
    }


    //////////////////////////////////////////////////////////
    //      JOB ITEMS
    //////////////////////////////////////////////////////////
    /**
     * Insert JobItem details
     * @param job
     * @param description
     * @param price
     */
    public void insertJobItem(long job, String description, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_JOB, job);
        values.put(COLUMN_JOB_ITEM_DESCRIPTION, description);
        values.put(COLUMN_JOB_ITEM_PRICE, price);
        long id = db.insert(TABLE_JOB_ITEMS, null, values);

        System.out.println("z! ---------- insertJobItem ----------");
        System.out.println("z! jobItemId: " + id);
        System.out.println("z! jobId: " + job);
        System.out.println("z! description: " + description);
        System.out.println("z! price: " + price);

        db.close();
    }

    /**
     * Select details of jobItems by Job ID
     * @param jobId
     * @return jobItems as arrayList
     */
    public ArrayList<JobItem> getJobItemsByJobId(long jobId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<JobItem> jobItems = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                "FROM " + TABLE_JOB_ITEMS +
                        " WHERE " + COLUMN_JOB + " = " + jobId
                , null
        );

        if (cursor.moveToFirst()) {
            do{
                JobItem jobItem = new JobItem();
                jobItem.setJobItemId(cursor.getLong(cursor.getColumnIndex(COLUMN_JOB_ITEM_ID)));
                jobItem.setJob(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB)));
                jobItem.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_ITEM_DESCRIPTION)));
                jobItem.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_JOB_ITEM_PRICE)));
                jobItems.add(jobItem);

                System.out.println("z! ---------- getJobItem ----------");
                System.out.println("z! job item id: " + jobItem.getJobItemId());
                System.out.println("z! job id: " + jobItem.getJob());
                System.out.println("z! desc: " + jobItem.getDescription());
                System.out.println("z! price: " + jobItem.getPrice());

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobItems;
    }

    /**
     * Get a list of the jobItem ID's, by their job ID
     * @param jobId
     * @return jobItemId as a type long
     */
    public ArrayList<JobItem> getJobItemsIdsByJobId(long jobId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<JobItem> jobItemIds = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_JOB_ITEM_ID +
                        " FROM " + TABLE_JOB_ITEMS +
                        " WHERE " + COLUMN_JOB + " = " + jobId,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                JobItem jobItem = new JobItem();
                jobItem.setJobItemId(cursor.getLong(cursor.getColumnIndex(COLUMN_JOB_ITEM_ID)));
                jobItemIds.add(jobItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return jobItemIds;
    }

    /**
     * Update JobItem details
     * @param jobItemId
     * @param description
     * @param price
     */
    public void updateJobItem(
            long jobItemId,
            String description,
            double price) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_JOB_ITEM_DESCRIPTION, description);
        values.put(COLUMN_JOB_ITEM_PRICE, price);
        db.update(TABLE_JOB_ITEMS, values, COLUMN_JOB_ITEM_ID + " = " + jobItemId, null);

        System.out.println("z! ----- updateJobItem -----");
        System.out.println("z! jobItemId: " + jobItemId);
        System.out.println("z! description: " + description);
        System.out.println("z! price: " + price);

        db.close();
    }

    /**
     * Delete all jobItems that contain the jobId
     * @param jobId
     */
    public void deleteJobItemByJobId(long jobId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_JOB_ITEMS, COLUMN_JOB + " = " + jobId, null);
        db.close();
    }


    //////////////////////////////////////////////////////////
    //      MISC.
    //////////////////////////////////////////////////////////
    /**
     * Drop all tables
     */
    public void dropTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AVAILABILITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_ITEMS);
        onCreate(db);
    }

    /**
     * Get Database Name
     * @return DB name as string
     */
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

}
