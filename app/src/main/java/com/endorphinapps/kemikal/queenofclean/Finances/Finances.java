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
    private int datePeriod;

    Finances(DBHelper db) {
        this.db = db;
        this. jobs = getJobsByDateRange(datePeriod);
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
}
