package com.endorphinapps.kemikal.queenofclean;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    private JobsClass jobsClass;
    // Total number of jobs for this week
    private ArrayList<Job> jobs;
    // Daily Jobs Lists
    private ArrayList<Job> mondaysJobs = new ArrayList<>();
    private ArrayList<Job> tuesdaysJobs = new ArrayList<>();
    private ArrayList<Job> wednesdaysJobs = new ArrayList<>();
    private ArrayList<Job> thursdaysJobs = new ArrayList<>();
    private ArrayList<Job> fridaysJobs = new ArrayList<>();
    private ArrayList<Job> saturdaysJobs = new ArrayList<>();
    private ArrayList<Job> sundaysJobs = new ArrayList<>();
    // Daily Employee Availabilities
    private ArrayList<Employee> totalEmployeeAvailability = new ArrayList<>();
    private ArrayList<Employee> mondaysAvailability = new ArrayList<>();
    private ArrayList<Employee> tuesdaysAvailability = new ArrayList<>();
    private ArrayList<Employee> wednesdaysAvailability = new ArrayList<>();
    private ArrayList<Employee> thursdaysAvailability = new ArrayList<>();
    private ArrayList<Employee> fridaysAvailability = new ArrayList<>();
    private ArrayList<Employee> saturdaysAvailability = new ArrayList<>();
    private ArrayList<Employee> sundaysAvailability = new ArrayList<>();
    // Daily Progress Bars
    private TextView tv_monday;
    private TextView tv_tuesday;
    private TextView tv_wednesday;
    private TextView tv_thursday;
    private TextView tv_friday;
    private TextView tv_saturday;
    private TextView tv_sunday;
    // Daily progress indicators (e.g. 5/10 -> 5 jobs booked / 10 available slots)
    private TextView tv_mondayIndicator;
    private TextView tv_tuesdayIndicator;
    private TextView tv_wednesdayIndicator;
    private TextView tv_thursdayIndicator;
    private TextView tv_fridayIndicator;
    private TextView tv_saturdayIndicator;
    private TextView tv_sundayIndicator;

    private NavigationBottom navigationBottom;

    /**
     * Exit the app on back press
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all views by ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Queen of Clean");

        // Instantiate new DBHelper and JobsClass classes
        db = new DBHelper(this);
        jobsClass = new JobsClass(db);

        // Initialise and create dataBase
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(
                db.getDatabaseName(), MODE_PRIVATE, null, null);
        db.onCreate(sqLiteDatabase);

        // Populate jobs list with this weeks jobs
        jobs = jobsClass.getJobsByDateRange(0);

        // Sort this weeks jobs into their own daily lists
        sortJobsByDayOfWeek();

        // Get all employees
        // TODO - this needs to be the number available for the day, use checkboxes in employee detail to denote availability
        totalEmployeeAvailability.addAll(db.getAllEmployees());

        // Display the daily indicators with the required figures (jobs booked & employee availability)
        displayDailyIndicators();

        // Set levels of the daily progress bars
        setLevelsOfDailyProgressBars();
    }

    /**
     * Find all views by their ID
     */
    private void findViews() {
        tv_monday = (TextView) findViewById(R.id.monday);
        tv_tuesday = (TextView) findViewById(R.id.tuesday);
        tv_wednesday = (TextView) findViewById(R.id.wednesday);
        tv_thursday = (TextView) findViewById(R.id.thursday);
        tv_friday = (TextView) findViewById(R.id.friday);
        tv_saturday = (TextView) findViewById(R.id.saturday);
        tv_sunday = (TextView) findViewById(R.id.sunday);
        tv_mondayIndicator = (TextView) findViewById(R.id.monday_indicator);
        tv_tuesdayIndicator = (TextView) findViewById(R.id.tuesday_indicator);
        tv_wednesdayIndicator = (TextView) findViewById(R.id.wednesday_indicator);
        tv_thursdayIndicator = (TextView) findViewById(R.id.thursday_indicator);
        tv_fridayIndicator = (TextView) findViewById(R.id.friday_indicator);
        tv_saturdayIndicator = (TextView) findViewById(R.id.saturday_indicator);
        tv_sundayIndicator = (TextView) findViewById(R.id.sunday_indicator);
    }

    /**
     * BottomNavigation onClick method.
     * View is the icon clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        navigationBottom = new NavigationBottom(this);
        navigationBottom.onClick(v);
    }

    /**
     * Iterates through this weeks jobs and sorts them
     * in to individual daily lists
     */
    private void sortJobsByDayOfWeek() {
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

    /**
     * Calculate the levels (for use in the progress bars)
     * jobs / availability * 10,000
     *
     * @param dailyJobsList
     * @param employeeAvailability
     * @return level as an int (up to 10,000)
     */
    private int calculateLevel(ArrayList<Job> dailyJobsList, ArrayList<Employee> employeeAvailability) {
        int jobs = dailyJobsList.size();
        int availability = employeeAvailability.size();

        // Calculate the jobs - availability %
        double percentage = ((double) jobs / (double) availability) * 100;

        // Calculate level up to 10,000
        int level = (int) percentage * 100;

        return level;
    }

    /**
     * Build daily jobs indicator
     * e.g. 5/10 - > jobs booked / employees available
     *
     * @param dailyJobsList
     * @param employeeAvailability
     * @return dailyJobIndicator as a String
     */
    private String buildDailyIndicator(ArrayList<Job> dailyJobsList, ArrayList<Employee> employeeAvailability) {
        StringBuilder dailyJobIndicator = new StringBuilder();

        dailyJobIndicator
                .append(dailyJobsList.size())
                .append("/")
                .append(employeeAvailability.size());

        return dailyJobIndicator.toString();
    }

    /**
     * Display daily indicators with the calculated figures
     */
    private void displayDailyIndicators() {
        tv_mondayIndicator.setText(buildDailyIndicator(mondaysJobs, totalEmployeeAvailability));
        tv_tuesdayIndicator.setText(buildDailyIndicator(tuesdaysJobs, totalEmployeeAvailability));
        tv_wednesdayIndicator.setText(buildDailyIndicator(wednesdaysJobs, totalEmployeeAvailability));
        tv_thursdayIndicator.setText(buildDailyIndicator(thursdaysJobs, totalEmployeeAvailability));
        tv_fridayIndicator.setText(buildDailyIndicator(fridaysJobs, totalEmployeeAvailability));
        tv_saturdayIndicator.setText(buildDailyIndicator(saturdaysJobs, totalEmployeeAvailability));
        tv_sundayIndicator.setText(buildDailyIndicator(sundaysJobs, totalEmployeeAvailability));
    }

    /**
     * Set the levels of the daily progress bars
     */
    private void setLevelsOfDailyProgressBars() {
        tv_monday.getBackground().setLevel(calculateLevel(mondaysJobs, totalEmployeeAvailability));
        tv_tuesday.getBackground().setLevel(calculateLevel(tuesdaysJobs, totalEmployeeAvailability));
        tv_wednesday.getBackground().setLevel(calculateLevel(wednesdaysJobs, totalEmployeeAvailability));
        tv_thursday.getBackground().setLevel(calculateLevel(thursdaysJobs, totalEmployeeAvailability));
        tv_friday.getBackground().setLevel(calculateLevel(fridaysJobs, totalEmployeeAvailability));
        tv_saturday.getBackground().setLevel(calculateLevel(saturdaysJobs, totalEmployeeAvailability));
        tv_sunday.getBackground().setLevel(calculateLevel(sundaysJobs, totalEmployeeAvailability));
    }
}
