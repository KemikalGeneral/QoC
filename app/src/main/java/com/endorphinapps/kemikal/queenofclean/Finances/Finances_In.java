package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter_in;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;

public class Finances_In extends AppCompatActivity {

    private DBHelper db;
    private Finances finances;
    private ListView lv_listView;
    private TextView tv_totalAmountIn;
    private Button btn_dateBack;
    private TextView tv_dateRange;
    private Button btn_dateFWD;
    private int datePeriod = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_in);

        // Find views by their ID
        findViews();

        // Instantiate DB and Finance_logic classes
        db = new DBHelper(this);
        finances = new Finances(db);

        // Setup and display ListView
        // using the getJobsByDateRange method
        // Monday - Sunday of current week
        getJobsAndSetupListView();

        // Cycle through Jobs array and total job amounts.
        // Display to TextView.
        calculateAndDisplayTotalIn();

        // Display the date range for the selected period
        // Monday - Sunday, defaults to current week.
        displayDateRange();

        // With each 'back' button press on the date range
        // the previous weeks jobs will be calculated and displayed
        btn_dateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePeriod -= 1;
                System.out.println("Date Period: " + datePeriod);
                getJobsAndSetupListView();
                calculateAndDisplayTotalIn();
                displayDateRange();
            }
        });

        // With each 'forward' button press on the date range
        // the next weeks jobs will be calculated and displayed
        btn_dateFWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePeriod += 1;
                System.out.println("Date Period: " + datePeriod);
                getJobsAndSetupListView();
                calculateAndDisplayTotalIn();
                displayDateRange();
            }
        });
    }

    /**
     * Find views by their Id's
     */
    private void findViews() {
        lv_listView = (ListView) findViewById(R.id.finances_in_listview);
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
        btn_dateBack = (Button) findViewById(R.id.finance_calendar_button_back);
        tv_dateRange = (TextView) findViewById(R.id.finance_date_range);
        btn_dateFWD = (Button) findViewById(R.id.finance_calendar_button_forward);
    }

    /**
     * Get current weeks jobs from the DB and use the
     * results to populate the ListView.
     * Take the datePeriod as a parameter from a
     * back or forwards button press to cycle through
     * the weeks.
     */
    private void getJobsAndSetupListView() {
        FinanceArrayAdapter_in financeArrayAdapterIn = new FinanceArrayAdapter_in(this);
        financeArrayAdapterIn.addAll(finances.getJobsByDateRange(datePeriod));
        lv_listView.setAdapter(financeArrayAdapterIn);
    }

    /**
     * Calculate the total money in for the
     * given week (Monday to Sunday), and display it
     * to two decimal places.
     */
    private void calculateAndDisplayTotalIn() {
        double totalPrice = finances.getTotalAmount_In();
        tv_totalAmountIn.setText("£");
        tv_totalAmountIn.append(String.format("%.2f", totalPrice));
    }

    /**
     * Display the date range for the selected period
     * Monday - Sunday, defaults to current week.
     */
    private void displayDateRange() {
        String from = DateFormat.getDateInstance().format(finances.getDateFrom(datePeriod));
        String to = DateFormat.getDateInstance().format(finances.getDateTo(datePeriod));
        StringBuilder range = new StringBuilder();
        range
                .append(from)
                .append(" - ")
                .append(to);
        tv_dateRange.setText(range);
    }

}