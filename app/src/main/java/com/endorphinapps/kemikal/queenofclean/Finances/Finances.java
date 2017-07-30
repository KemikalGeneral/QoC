package com.endorphinapps.kemikal.queenofclean.Finances;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

class Finances {

    private DBHelper db;
    private ArrayList<Job> jobs;
    private ArrayList<Job> annualFinances;
    private int datePeriod;
    private int year;

    Finances(DBHelper db) {
        this.db = db;
        this. jobs = getJobsByDateRange(datePeriod);
        this.annualFinances = getJobsByFinancialYear(year);
    }

    /**
     * Get all details of Jobs  between
     * Monday and Sunday of the current week
     * @return Jobs as an ArrayList
     */
    ArrayList<Job> getJobsByDateRange(int datePeriod) {
        jobs = new ArrayList<>();
        long dateFrom;
        long dateTo;

        // Get first day of week for dateFrom
        dateFrom = getDateFrom(datePeriod);

        // Get last day of week for dateTo
        dateTo = getDateTo(datePeriod);

        // Populate array with results from DB search
        jobs.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return jobs;
    }

    /**
     * Calculate the start of the week (Monday 00:00)
     * for the selected datePeriod (in weeks)
     * @param datePeriod
     * @return dateFrom in milliseconds as a long
     */
    long getDateFrom(int datePeriod) {
        long dateFrom;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, datePeriod);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        dateFrom = calendar.getTimeInMillis();
        String from = DateFormat.getDateInstance().format(dateFrom);
        System.out.println("Date From: " + from);
        return dateFrom;
    }

    /**
     * Calculate the end of the week (Sunday 23:59)
     * for the selected datePeriod (in weeks)
     * @param datePeriod
     * @return dateTo in milliseconds as a long
     */
    long getDateTo(int datePeriod) {
        long dateTo;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, datePeriod);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        dateTo = calendar.getTimeInMillis();
        String to = DateFormat.getDateInstance().format(dateTo);
        System.out.println("Date To: " + to);
        return dateTo;
    }

    /**
     * Cycle through the Jobs and calculate the
     * totalAmount of income for the current week
     * @return totalAmount as a double
     */
    double getTotalAmount_In() {
        double totalAmount = 0;
        int arrayLength = jobs.size();

        for (int i = 0; i < arrayLength; i++) {
            totalAmount += jobs.get(i).getTotalPrice();
        }

        return totalAmount;
    }

    /**
     * Cycle through the Jobs and calculate the
     * totalAmount of outgoings for the current week
     * @return totalAmount as a double
     */
    double getTotalAmount_out() {
        double totalAmount = 0;
        int arrayLength = jobs.size();
        long employeeId;
        Employee employee;
        double rateOfPay;
        int hours;
        double employeePayForJob;

        for (int i = 0; i < arrayLength; i++) {
            // Get job-employee by Id
            employeeId = jobs.get(i).getEmployee();
            employee = db.getEmployeeById(employeeId);

            // Get employee rateOfPay and
            // hours worked per job
            rateOfPay = employee.getRateOfPay();
            hours = jobs.get(i).getEstimatedTime();

            // Calculate employee pay for current job
            employeePayForJob = rateOfPay * hours;

            // Calculate total amount of pay to employees
            // for current week, Monday to Sunday
            totalAmount += employeePayForJob;
        }

        return totalAmount;
    }

    /**
     * Calculate the total sum (in - out)
     * @return sum as a double
     */
    double getTotalAmount_sum() {
        double in = getTotalAmount_In();
        double out = getTotalAmount_out();

        return in - out;
    }

    //////////////////////////////////////////////////////////
    //      Annual Finances
    //////////////////////////////////////////////////////////

    /**
     * Get all details of Jobs  between the UK financial year
     * from 6th April (this year) to 5th April  (next year)
     *
     * @param year
     * @return AnnualFinances as an ArrayList
     */
    ArrayList<Job> getJobsByFinancialYear(int year) {
        annualFinances = new ArrayList<>();
        long dateFrom;
        long dateTo;

        // Get first day of week for dateFrom
        dateFrom = getAnnualDateFrom(year);

        // Get last day of week for dateTo
        dateTo = getAnnualDateTo(year);

        // Populate array with results from DB search
        annualFinances.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return annualFinances;
    }

    /**
     * Calculate the start of the year (6th April)
     * for the selected year
     *
     * @param year
     * @return dateFrom in milliseconds as a long
     */
    long getAnnualDateFrom(int year) {
        long dateFrom;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        dateFrom = calendar.getTimeInMillis();
        String from = DateFormat.getDateInstance().format(dateFrom);
        System.out.println("Financial Year - Date From: " + from);
        return dateFrom;
    }

    /**
     * Calculate the end of the year (5th April)
     * for the selected year
     *
     * @param year
     * @return dateFrom in milliseconds as a long
     */
    long getAnnualDateTo(int year) {
        long dateTo;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year + 1);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        dateTo = calendar.getTimeInMillis();
        String to = DateFormat.getDateInstance().format(dateTo);
        System.out.println("Financial Year - Date To: " + to);
        return dateTo;
    }

    /**
     * Cycle through the AnnualFinances and calculate the
     * totalAmount of income for the current year
     *
     * @return totalAmount as a double
     */
    double getAnnualTotalAmount_In() {
        double totalAmount = 0;
        int arrayLength = annualFinances.size();

        for (int i = 0; i < arrayLength; i++) {
            totalAmount += annualFinances.get(i).getTotalPrice();
        }

        return totalAmount;
    }

    /**
     * Cycle through the AnnualFinances and calculate the
     * totalAmount of outgoings for the current year
     *
     * @return totalAmount as a double
     */
    double getAnnualTotalAmount_out() {
        double totalAmount = 0;
        int arrayLength = annualFinances.size();
        long employeeId;
        Employee employee;
        double rateOfPay;
        int hours;
        double employeePayForJob;

        for (int i = 0; i < arrayLength; i++) {
            // Get job-employee by Id
            employeeId = annualFinances.get(i).getEmployee();
            employee = db.getEmployeeById(employeeId);

            // Get employee rateOfPay and
            // hours worked per job
            rateOfPay = employee.getRateOfPay();
            hours = annualFinances.get(i).getEstimatedTime();

            // Calculate employee pay for current job
            employeePayForJob = rateOfPay * hours;

            // Calculate total amount of pay to employees
            // for current week, Monday to Sunday
            totalAmount += employeePayForJob;
        }

        return totalAmount;
    }

    /**
     * Calculate the total sum (in - out)
     *
     * @return sum as a double
     */
    double getAnnualTotalAmount_Sum() {
        double in = getAnnualTotalAmount_In();
        double out = getAnnualTotalAmount_out();

        return in - out;
    }

}
