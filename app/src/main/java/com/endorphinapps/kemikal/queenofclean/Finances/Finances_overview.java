package com.endorphinapps.kemikal.queenofclean.Finances;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.ActivityHelper;
import com.endorphinapps.kemikal.queenofclean.Globals.MenuMain;
import com.endorphinapps.kemikal.queenofclean.Globals.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.R;

import java.util.Locale;

public class Finances_overview extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    private Finances finances;

    private TextView tv_inTab;
    private TextView tv_outTab;

    private ConstraintLayout cl_weekContainer;
    private TextView tv_totalAmountIn;
    private TextView tv_totalAmountOut;
    private TextView tv_totalAmountSum;

    private ConstraintLayout cl_yearContainer;
    private TextView tv_totalAnnualAmountIn;
    private TextView tv_totalAnnualAmountOut;
    private TextView tv_totalAnnualAmountSum;

    private Button btn_switchViews;
    private NavigationBottom navigationBottom;

    /**
     * Go back to the MainActivity on back press
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_overview);
        // Set orientation to portrait
        ActivityHelper.initialise(this);

        // Find all views by Id
        findViews();

        // Hide annual section on load
        cl_yearContainer.setVisibility(View.GONE);

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Finances Overview");

        // Instantiate DB and Finance classes
        db = new DBHelper(this);
        finances = new Finances(db);

        // In Tab
        tv_inTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Finances_overview.this, Finances_In.class));
                finish();
            }
        });

        // Out Tab
        tv_outTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Finances_overview.this, Finances_out.class));
                finish();
            }
        });

        btn_switchViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cl_weekContainer.getVisibility() == View.VISIBLE) {
                    cl_weekContainer.setVisibility(View.GONE);
                    cl_yearContainer.setVisibility(View.VISIBLE);
                    btn_switchViews.setText("Switch to WEEK view");
                } else if (cl_yearContainer.getVisibility() == View.VISIBLE) {
                    cl_weekContainer.setVisibility(View.VISIBLE);
                    cl_yearContainer.setVisibility(View.GONE);
                    btn_switchViews.setText("Switch to ANNUAL view");
                }


            }
        });

        // Display total amount in
        tv_totalAmountIn.setText("£");
        tv_totalAmountIn.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_In()));

        // Display total amount out
        tv_totalAmountOut.setText("£");
        tv_totalAmountOut.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_out()));

        // Display total amount sum to 2 decimal places
        tv_totalAmountSum.setText("£");
        tv_totalAmountSum.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_sum()));

        ///////////////////////////////////////////////////////

        // Display annual total amount in
        tv_totalAnnualAmountIn.setText("£");
        tv_totalAnnualAmountIn.append(String.format(Locale.getDefault(), "%.2f", finances.getAnnualTotalAmount_In()));

        // Display annual total amount out
        tv_totalAnnualAmountOut.setText("£");
        tv_totalAnnualAmountOut.append(String.format(Locale.getDefault(), "%.2f", finances.getAnnualTotalAmount_out()));

        // Display annual total amount sum to 2 decimal places
        tv_totalAnnualAmountSum.setText("£");
        tv_totalAnnualAmountSum.append(String.format(Locale.getDefault(), "%.2f", finances.getAnnualTotalAmount_Sum()));
    }

    /**
     * Find all views by their Id's
     */
    private void findViews() {
        tv_inTab = (TextView) findViewById(R.id.inTab);
        tv_outTab = (TextView) findViewById(R.id.outTab);

        cl_weekContainer = (ConstraintLayout) findViewById(R.id.finances_week_container);
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
        tv_totalAmountOut = (TextView) findViewById(R.id.total_out_amount);
        tv_totalAmountSum = (TextView) findViewById(R.id.total_sum_amount);

        cl_yearContainer = (ConstraintLayout) findViewById(R.id.finances_year_container);
        tv_totalAnnualAmountIn = (TextView) findViewById(R.id.total_in_amount_year);
        tv_totalAnnualAmountOut = (TextView) findViewById(R.id.total_out_amount_year);
        tv_totalAnnualAmountSum = (TextView) findViewById(R.id.total_sum_amount_year);

        btn_switchViews = (Button) findViewById(R.id.finance_switch_views);
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
