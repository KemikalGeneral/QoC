package com.endorphinapps.kemikal.queenofclean.Finances;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

class Finances {

    private DBHelper db;
    private ArrayList<Job> weeklyJobs;
    private ArrayList<Job> annualJobs;
    private int datePeriod;
    private int year;

    Finances(DBHelper db) {
        this.db = db;
        this.weeklyJobs = getJobsByDateRange(datePeriod);
        this.annualJobs = getJobsByFinancialYear(year);
    }

    /**
     * Get all details of Jobs  between
     * Monday and Sunday of the current week
     *
     * @return Jobs as an ArrayList
     */
    ArrayList<Job> getJobsByDateRange(int datePeriod) {
        weeklyJobs = new ArrayList<>();
        long dateFrom;
        long dateTo;

        // Get first day of week for dateFrom
        dateFrom = getDateFrom(datePeriod);

        // Get last day of week for dateTo
        dateTo = getDateTo(datePeriod);

        // Populate array with results from DB search
        weeklyJobs.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return weeklyJobs;
    }

    /**
     * Calculate the start of the week (Monday 00:00)
     * for the selected datePeriod (in weeks)
     *
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
     *
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
     * totalAmount of income for the current week.
     * DO NOT calculate 'Unconfirmed', 'Cancelled' or 'Quote' statuses.
     *
     * @return totalAmount as a double
     */
    double getTotalAmount_In() {
        double totalAmount = 0;
        int arrayLength = weeklyJobs.size();

        for (int i = 0; i < arrayLength; i++) {
            // Don't include the following statuses in the calculations
            if (weeklyJobs.get(i).getJobStatusEnum().equals("Unconfirmed")
                    || weeklyJobs.get(i).getJobStatusEnum().equals("Cancelled")
                    || weeklyJobs.get(i).getJobStatusEnum().equals("Quote")) {
            } else {
                totalAmount += weeklyJobs.get(i).getTotalPrice();
            }
        }

        return totalAmount;
    }

    /**
     * Cycle through the Jobs and calculate the
     * totalAmount of outgoings for the current week.
     * DO NOT calculate 'Unconfirmed', 'Cancelled' or 'Quote' statuses.
     *
     * @return totalAmount as a double
     */
    double getTotalAmount_out() {
        double totalAmount = 0;
        int arrayLength = weeklyJobs.size();
        long employeeId;
        Employee employee;
        double rateOfPay;
        double hours;
        double employeePayForJob;

        for (int i = 0; i < arrayLength; i++) {
            // Don't include the following statuses in the calculations
            if (weeklyJobs.get(i).getJobStatusEnum().equals("Unconfirmed")
                    || weeklyJobs.get(i).getJobStatusEnum().equals("Cancelled")
                    || weeklyJobs.get(i).getJobStatusEnum().equals("Quote")) {
            } else {
                // Get job-employee by Id
                employeeId = weeklyJobs.get(i).getEmployee();
                employee = db.getEmployeeById(employeeId);

                // Get employee rateOfPay and
                // hours worked per job
                rateOfPay = employee.getRateOfPay();
                hours = weeklyJobs.get(i).getEstimatedTime();

                // Calculate employee pay for current job
                employeePayForJob = rateOfPay * hours;

                // Calculate total amount of pay to employees
                // for current week, Monday to Sunday
                totalAmount += employeePayForJob;
            }
        }

        return totalAmount;
    }

    /**
     * Calculate the total sum (in - out)
     *
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
        annualJobs = new ArrayList<>();
        long dateFrom;
        long dateTo;

        // Get first day of week for dateFrom
        dateFrom = getAnnualDateFrom(year);

        // Get last day of week for dateTo
        dateTo = getAnnualDateTo(year);

        // Populate array with results from DB search
        annualJobs.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return annualJobs;
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
     * totalAmount of income for the current year.
     * DO NOT calculate 'Unconfirmed', 'Cancelled' or 'Quote' statuses.
     *
     * @return totalAmount as a double
     */
    double getAnnualTotalAmount_In() {
        double totalAmount = 0;
        int arrayLength = annualJobs.size();

        for (int i = 0; i < arrayLength; i++) {
            // Don't include the following statuses in the calculations
            if (annualJobs.get(i).getJobStatusEnum().equals("Unconfirmed")
                    || annualJobs.get(i).getJobStatusEnum().equals("Cancelled")
                    || annualJobs.get(i).getJobStatusEnum().equals("Quote")) {
            } else {
                totalAmount += annualJobs.get(i).getTotalPrice();
            }
        }

        return totalAmount;
    }

    /**
     * Cycle through the AnnualFinances and calculate the
     * totalAmount of outgoings for the current year.
     * DO NOT calculate 'Unconfirmed', 'Cancelled' or 'Quote' statuses.
     *
     * @return totalAmount as a double
     */
    double getAnnualTotalAmount_out() {
        double totalAmount = 0;
        int arrayLength = annualJobs.size();
        long employeeId;
        Employee employee;
        double rateOfPay;
        double hours;
        double employeePayForJob;

        for (int i = 0; i < arrayLength; i++) {
            // Don't include the following statuses in the calculations
            if (annualJobs.get(i).getJobStatusEnum().equals("Unconfirmed")
                    || annualJobs.get(i).getJobStatusEnum().equals("Cancelled")
                    || annualJobs.get(i).getJobStatusEnum().equals("Quote")) {
            } else {
                // Get job-employee by Id
                employeeId = annualJobs.get(i).getEmployee();
                employee = db.getEmployeeById(employeeId);

                // Get employee rateOfPay and
                // hours worked per job
                rateOfPay = employee.getRateOfPay();
                hours = annualJobs.get(i).getEstimatedTime();

                // Calculate employee pay for current job
                employeePayForJob = rateOfPay * hours;

                // Calculate total amount of pay to employees
                // for current week, Monday to Sunday
                totalAmount += employeePayForJob;
            }
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
