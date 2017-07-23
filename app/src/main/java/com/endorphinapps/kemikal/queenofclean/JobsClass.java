package com.endorphinapps.kemikal.queenofclean;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class JobsClass {

    private DBHelper db;
    private ArrayList<Job> jobs;
    private int datePeriod = 0;
    // Daily Jobs Lists
    private ArrayList<Job> mondaysJobs = new ArrayList<>();
    private ArrayList<Job> tuesdaysJobs = new ArrayList<>();
    private ArrayList<Job> wednesdaysJobs = new ArrayList<>();
    private ArrayList<Job> thursdaysJobs = new ArrayList<>();
    private ArrayList<Job> fridaysJobs = new ArrayList<>();
    private ArrayList<Job> saturdaysJobs = new ArrayList<>();
    private ArrayList<Job> sundaysJobs = new ArrayList<>();

    public JobsClass(DBHelper db) {
        this.db = db;
    }

    /**
     * Get all details of Jobs  between
     * Monday and Sunday of the current week
     * @param datePeriod
     * @return Jobs as an ArrayList
     */
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

    /**
     * Iterates through this weeks jobs and sorts them
     * in to individual daily lists
     */
    public void sortJobsByDayOfWeek() {
        int arraySize = jobs.size();

        // Create a SimpleDateFormat'd date
        // that show the day of the week as a String
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        long startDate;
        String day;

        // Loop through all the jobs for this week
        // and sort them in to their own daily lists
        for (int i = 0; i < arraySize; i++) {
            // Get the startDate of the current job
            startDate = jobs.get(i).getStartDate();
            day = sdf.format(startDate);

            switch (day) {
                case "Monday":
                    mondaysJobs.add(jobs.get(i));
                    break;
                case "Tuesday":
                    tuesdaysJobs.add(jobs.get(i));
                    break;
                case "Wednesday":
                    wednesdaysJobs.add(jobs.get(i));
                    break;
                case "Thursday":
                    thursdaysJobs.add(jobs.get(i));
                    break;
                case "Friday":
                    fridaysJobs.add(jobs.get(i));
                    break;
                case "Saturday":
                    saturdaysJobs.add(jobs.get(i));
                    break;
                case "Sunday":
                    sundaysJobs.add(jobs.get(i));
                    break;
            }
        }
    }

    public ArrayList<Job> getMondaysJobs() {
        return mondaysJobs;
    }

    public ArrayList<Job> getTuesdaysJobs() {
        return tuesdaysJobs;
    }

    public ArrayList<Job> getWednesdaysJobs() {
        return wednesdaysJobs;
    }

    public ArrayList<Job> getThursdaysJobs() {
        return thursdaysJobs;
    }

    public ArrayList<Job> getFridaysJobs() {
        return fridaysJobs;
    }

    public ArrayList<Job> getSaturdaysJobs() {
        return saturdaysJobs;
    }

    public ArrayList<Job> getSundaysJobs() {
        return sundaysJobs;
    }
}


