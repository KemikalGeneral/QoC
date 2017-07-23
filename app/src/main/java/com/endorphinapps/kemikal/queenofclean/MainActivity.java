package com.endorphinapps.kemikal.queenofclean;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Employee;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;

import java.util.ArrayList;

public class MainActivity extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    private JobsClass jobsClass;
    // Total number of jobs for this week
    private ArrayList<Job> jobs;
    // Daily Employee Availabilities
    private ArrayList<Employee> allEmployees = new ArrayList<>();
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
     * Call finish() to exit the app on back press
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
        jobsClass.sortJobsByDayOfWeek();

        // Populate each day array with the employee availabilities
        getDailyAvailabilities();

        // Display the daily indicators with the required figures (jobs booked & employee availability)
        displayDailyIndicators();

        // Set levels of the daily progress bars
        setLevelsOfDailyProgressBars();

        tv_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "monday");
                startActivity(intent);
            }
        });

        tv_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "tuesday");
                startActivity(intent);
            }
        });

        tv_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "wednesday");
                startActivity(intent);
            }
        });

        tv_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "thursday");
                startActivity(intent);
            }
        });

        tv_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "friday");
                startActivity(intent);
            }
        });

        tv_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "saturday");
                startActivity(intent);
            }
        });

        tv_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DayView.class);
                intent.putExtra("EXTRAS_day", "sunday");
                startActivity(intent);
            }
        });
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
     * Calculate the levels (for use in the progress bars)
     * jobs / availability * 10,000
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
        tv_mondayIndicator.setText(buildDailyIndicator(jobsClass.getMondaysJobs(), mondaysAvailability));
        tv_tuesdayIndicator.setText(buildDailyIndicator(jobsClass.getTuesdaysJobs(), tuesdaysAvailability));
        tv_wednesdayIndicator.setText(buildDailyIndicator(jobsClass.getWednesdaysJobs(), wednesdaysAvailability));
        tv_thursdayIndicator.setText(buildDailyIndicator(jobsClass.getThursdaysJobs(), thursdaysAvailability));
        tv_fridayIndicator.setText(buildDailyIndicator(jobsClass.getFridaysJobs(), fridaysAvailability));
        tv_saturdayIndicator.setText(buildDailyIndicator(jobsClass.getSaturdaysJobs(), saturdaysAvailability));
        tv_sundayIndicator.setText(buildDailyIndicator(jobsClass.getSundaysJobs(), sundaysAvailability));
    }

    /**
     * Set the levels of the daily progress bars
     */
    private void setLevelsOfDailyProgressBars() {
        tv_monday.getBackground().setLevel(calculateLevel(jobsClass.getMondaysJobs(), mondaysAvailability));
        tv_tuesday.getBackground().setLevel(calculateLevel(jobsClass.getTuesdaysJobs(), tuesdaysAvailability));
        tv_wednesday.getBackground().setLevel(calculateLevel(jobsClass.getWednesdaysJobs(), wednesdaysAvailability));
        tv_thursday.getBackground().setLevel(calculateLevel(jobsClass.getThursdaysJobs(), thursdaysAvailability));
        tv_friday.getBackground().setLevel(calculateLevel(jobsClass.getFridaysJobs(), fridaysAvailability));
        tv_saturday.getBackground().setLevel(calculateLevel(jobsClass.getSaturdaysJobs(), saturdaysAvailability));
        tv_sunday.getBackground().setLevel(calculateLevel(jobsClass.getSundaysJobs(), sundaysAvailability));
    }

    /**
     * Iterate through the list of allEmployees,
     * checking if each one contains a 1 (true).
     * If it does, it gets added to the [day]Availability
     * ArrayList
     */
    private void getDailyAvailabilities() {
        allEmployees.addAll(db.getAllEmployees());

        for (int i = 0; i < allEmployees.size(); i++) {
            if (allEmployees.get(i).getMondayAM() == 1) {
                mondaysAvailability.add(allEmployees.get(i));
                System.out.println("Mo/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getMondayPM() == 1) {
                mondaysAvailability.add(allEmployees.get(i));
                System.out.println("Mo/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getTuesdayAM() == 1) {
                tuesdaysAvailability.add(allEmployees.get(i));
                System.out.println("Tu/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getTuesdayPM() == 1) {
                tuesdaysAvailability.add(allEmployees.get(i));
                System.out.println("Tu/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getWednesdayAM() == 1) {
                wednesdaysAvailability.add(allEmployees.get(i));
                System.out.println("We/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getWednesdayPM() == 1) {
                wednesdaysAvailability.add(allEmployees.get(i));
                System.out.println("We/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getThursdayAM() == 1) {
                thursdaysAvailability.add(allEmployees.get(i));
                System.out.println("Th/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getThursdayPM() == 1) {
                thursdaysAvailability.add(allEmployees.get(i));
                System.out.println("Th/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getFridayAM() == 1) {
                fridaysAvailability.add(allEmployees.get(i));
                System.out.println("Fr/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getFridayPM() == 1) {
                fridaysAvailability.add(allEmployees.get(i));
                System.out.println("Fr/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getSaturdayAM() == 1) {
                saturdaysAvailability.add(allEmployees.get(i));
                System.out.println("Sa/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getSaturdayPM() == 1) {
                saturdaysAvailability.add(allEmployees.get(i));
                System.out.println("Sa/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getSundayAM() == 1) {
                sundaysAvailability.add(allEmployees.get(i));
                System.out.println("Su/AM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }

            if (allEmployees.get(i).getSundayPM() == 1) {
                sundaysAvailability.add(allEmployees.get(i));
                System.out.println("Su/PM: " + allEmployees.get(i).getFirstName() + " " + allEmployees.get(i).getLastName());
            }
        }
    }
}
