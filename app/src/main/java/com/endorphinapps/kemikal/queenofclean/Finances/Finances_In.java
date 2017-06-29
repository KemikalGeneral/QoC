package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Entities.Job;
import com.endorphinapps.kemikal.queenofclean.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Finances_In extends AppCompatActivity {

    private ListView lv_listView;
    private TextView tv_totalAmountIn;
    private DBHelper db;
    private ArrayList<Job> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_in);

        // Find views by their ID
        findViews();

        // Initialise DB
        db = new DBHelper(this);

        // Populate Jobs array with DB results
        jobs = getJobsByDateRange();

        // Setup and display ListView
        // using the getJobsByDateRange method
        // Monday - Sunday of current week
        FinanceArrayAdapter financeArrayAdapter = new FinanceArrayAdapter(this);
        financeArrayAdapter.addAll(getJobsByDateRange());
        lv_listView.setAdapter(financeArrayAdapter);

        // Cycle through Jobs array and total job amounts.
        // Display to TextView.
        double totalPrice = getTotalPrice();
        tv_totalAmountIn.setText("£");
        tv_totalAmountIn.append(String.valueOf(totalPrice));
    }

    /**
     * Find views by their Id's
     */
    private void findViews() {
        lv_listView = (ListView) findViewById(R.id.finances_in_listview);
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
    }

    /**
     * Get all details of Jobs  between
     * Monday and Sunday of the current week
     * @return Jobs as an ArrayList
     */
    private ArrayList<Job> getJobsByDateRange() {
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
     * Cycle through the Jobs and calculate the total
     * @return totalPrice as a double
     */
    public double getTotalPrice() {
        double totalPrice = 0;
        int arrayLength = jobs.size();

        for (int i = 0; i < arrayLength; i++) {
            totalPrice += jobs.get(i).getTotalPrice();
        }

        return totalPrice;
    }
}
