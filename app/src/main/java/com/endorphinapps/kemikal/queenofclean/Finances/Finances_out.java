package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter_out;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;

public class Finances_out extends AppCompatActivity {

    private DBHelper db;
    private Finances finances;
    private ListView lv_listView;
    private TextView tv_totalAmountOut;

    private Button btn_dateBack;
    private TextView tv_dateRange;
    private Button btn_dateFWD;
    private int datePeriod = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_out);

        // Find all views by Id
        findViews();

        // Instantiate DB and Finance_logic classes
        db = new DBHelper(this);
        finances = new Finances(db);

        // Setup and display ListView
        // using the getJobsByDateRange method
        // Monday - Sunday of current week
        getJobsAndSetupListView();

        // Cycle through Jobs and total pay to employees.
        // Display to TextView
        displayTotalToTextView();

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
                displayTotalToTextView();
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
                displayTotalToTextView();
                displayDateRange();
            }
        });
    }

    /**
     * Find all views by their Id's
     */
    private void findViews() {
        lv_listView = (ListView) findViewById(R.id.finances_out_listview);
        tv_totalAmountOut = (TextView) findViewById(R.id.total_out_amount);
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
        FinanceArrayAdapter_out financeArrayAdapter_out =
                new FinanceArrayAdapter_out(this);
        financeArrayAdapter_out.addAll(finances.getJobsByDateRange(datePeriod));
        lv_listView.setAdapter(financeArrayAdapter_out);
    }

    /**
     * Display the total money out for the
     * given week (Monday to Sunday), and display it
     * to two decimal places.
     */
    private void displayTotalToTextView() {
        double totalPayToEmployee = finances.getTotalAmount_out();
        tv_totalAmountOut.setText("£");
        tv_totalAmountOut.append(String.format("%.2f", totalPayToEmployee));
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
