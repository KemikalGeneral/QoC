package com.endorphinapps.kemikal.queenofclean.Finances;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter_in;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.Menus.MenuMain;
import com.endorphinapps.kemikal.queenofclean.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.R;

import java.text.DateFormat;
import java.util.Locale;

public class Finances_In extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    private Finances finances;
    private FinanceArrayAdapter_in financeArrayAdapter_in;
    private TextView tv_overviewTab;
    private TextView tv_outTab;
    private ListView lv_listView;
    private TextView tv_totalAmountIn;
    private TextView tv_dateBack;
    private TextView tv_dateRange;
    private TextView tv_dateFWD;
    private int datePeriod = 0;

    private NavigationBottom navigationBottom;

    /**
     * Go back to the MainActivity on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * Create a context menu on a long press of the
     * Finance_In ListView item to change the payment status
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Set menu options
        menu.setHeaderTitle("Change payment status to...");
        menu.add(0, v.getId(), 0, "UnPaid");
        menu.add(0, v.getId(), 0, "Paid");
    }

    /**
     * Action the item selected in the context menu
     * by calling ??? and passing the ???
     * and the amended status
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long jobId = financeArrayAdapter_in.getJobId();

        if (item.getTitle().equals("UnPaid")) {
            db.changeCustomerPaymentStatus(jobId, item.getTitle().toString());
            System.out.println("z! Finances_in - onContextItemSelected - UnPaid - jobId: " + jobId);
        } else if (item.getTitle().equals("Paid")) {
            db.changeCustomerPaymentStatus(jobId, item.getTitle().toString());
            System.out.println("z! Finances_in - onContextItemSelected - Paid - jobId: " + jobId);
        }
        // Recreate the activity to apply changes
        recreate();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_in);

        // Find views by their ID
        findViews();

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Finances In");

        // Instantiate DB and Finance_logic classes
        db = new DBHelper(this);
        finances = new Finances(db);

        // Overview Tab
        tv_overviewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Finances_In.this, Finances_overview.class));
                finish();
            }
        });

        // Out Tab
        tv_outTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Finances_In.this, Finances_out.class));
                finish();
            }
        });

        // Setup and display ListView
        // using the getJobsByDateRange method
        // Monday - Sunday of current week
        getJobsAndSetupListView();

        // Register for long clickable context menu
        // used to change the payment status
        registerForContextMenu(lv_listView);

        // Cycle through Jobs array and total job amounts.
        // Display to TextView.
        displayTotalToTextView();

        // Display the date range for the selected period
        // Monday - Sunday, defaults to current week.
        displayDateRange();

        // With each 'back' button press on the date range
        // the previous weeks jobs will be calculated and displayed
        tv_dateBack.setOnClickListener(new View.OnClickListener() {
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
        tv_dateFWD.setOnClickListener(new View.OnClickListener() {
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
     * Find views by their Id's
     */
    private void findViews() {
        tv_overviewTab = (TextView) findViewById(R.id.overviewTab);
        tv_outTab = (TextView) findViewById(R.id.outTab);
        lv_listView = (ListView) findViewById(R.id.finances_in_listview);
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
        tv_dateBack = (TextView) findViewById(R.id.finance_calendar_button_back);
        tv_dateRange = (TextView) findViewById(R.id.finance_date_range);
        tv_dateFWD = (TextView) findViewById(R.id.finance_calendar_button_forward);
    }

    /**
     * Get current weeks jobs from the DB and use the
     * results to populate the ListView.
     * Take the datePeriod as a parameter from a
     * back or forwards button press to cycle through
     * the weeks.
     */
    private void getJobsAndSetupListView() {
        financeArrayAdapter_in = new FinanceArrayAdapter_in(this);
        financeArrayAdapter_in.addAll(finances.getJobsByDateRange(datePeriod));
        lv_listView.setAdapter(financeArrayAdapter_in);
    }

    /**
     * Display the total money in for the
     * given week (Monday to Sunday),
     * to two decimal places.
     */
    private void displayTotalToTextView() {
        double totalPrice = finances.getTotalAmount_In();
        tv_totalAmountIn.setText("Total in this week: £");
        tv_totalAmountIn.append(String.format(Locale.getDefault(), "%.2f", totalPrice));
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

    /**
     * BottomNavigation onClick method.
     * View is the icon clicked.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        navigationBottom = new NavigationBottom(this);
        navigationBottom.onClick(v);
    }
}