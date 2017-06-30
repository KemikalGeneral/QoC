package com.endorphinapps.kemikal.queenofclean.Finances;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 30/06/2017.
 */

class Finances_logic {

    private DBHelper db;
    private ArrayList<Job> jobs;

    Finances_logic(DBHelper db) {
        this.db = db;
        this. jobs = getJobsByDateRange();
    }

    /**
     * Get all details of Jobs  between
     * Monday and Sunday of the current week
     * @return Jobs as an ArrayList
     */
    ArrayList<Job> getJobsByDateRange() {
        ArrayList<Job> jobsArr = new ArrayList<>();
        long dateFrom;
        long dateTo;
        Calendar calendar = Calendar.getInstance();

        // Get first day of week for dateFrom
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        dateFrom = calendar.getTimeInMillis();

        // Get last day of week for dateTo
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        dateTo = calendar.getTimeInMillis();

        // Populate array with results from DB search
        jobsArr.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return jobsArr;
    }

    /**
     * Cycle through the Jobs and calculate the
     * totalAmount coming in for the current week
     * @return totalPrice as a double
     */
    double getTotalAmount_In() {
        double totalAmount = 0;
        int arrayLength = jobs.size();

        for (int i = 0; i < arrayLength; i++) {
            totalAmount += jobs.get(i).getTotalPrice();
        }

        return totalAmount;
    }

    public double getTotalAmount_out() {
        double totalAmount = 0;
        int arrayLength = jobs.size();

        for (int i = 0; i < arrayLength; i++) {
            long employeeId = jobs.get(i).getEmployee();
            Employee employee = db.getEmployeeById(employeeId);

            System.out.println("Finances Employee: " + employee.getLastName());
        }

        return totalAmount;
    }


}
