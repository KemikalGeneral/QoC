package com.endorphinapps.kemikal.queenofclean;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class JobsClass {

    private DBHelper db;
    private ArrayList<Job> jobs;
    private int datePeriod = 0;

    public JobsClass(DBHelper db) {
        this.db = db;
    }

    public ArrayList<Job> getJobsByDateRange(int datePeriod) {
        jobs = new ArrayList<>();
        long dateFrom;
        long dateTo;

        dateFrom = getDateFrom(datePeriod);

        dateTo = getDateTo(datePeriod);

        jobs.addAll(db.getJobsByDateRange(dateFrom, dateTo));

        return jobs;
    }

    /**
     * Calculate the start of the week (Monday 00:00)
     * for the selected datePeriod (in weeks)
     *
     * @param datePeriod
     * @return dateFrom in milliseconds as a long
     */
    private long getDateFrom(int datePeriod) {
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
    private long getDateTo(int datePeriod) {
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
}
