package com.endorphinapps.kemikal.queenofclean.Finances;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.MenuMain;
import com.endorphinapps.kemikal.queenofclean.NavigationBottom;
import com.endorphinapps.kemikal.queenofclean.R;

import java.util.Locale;

public class Finances_overview extends MenuMain
        implements View.OnClickListener {

    private DBHelper db;
    private Finances finances;
    private TextView tv_inTab;
    private TextView tv_outTab;
    private TextView tv_totalAmountIn;
    private TextView tv_totalAmountOut;
    private TextView tv_totalAmountSum;

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

        // Find all views by Id
        findViews();

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

        // Display total amount in
        tv_totalAmountIn.setText("£");
        tv_totalAmountIn.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_In()));

        // Display total amount out
        tv_totalAmountOut.setText("£");
        tv_totalAmountOut.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_out()));

        // Display total amount sum to 2 decimal places
        tv_totalAmountSum.setText("£");
        tv_totalAmountSum.append(String.format(Locale.getDefault(), "%.2f", finances.getTotalAmount_sum()));
    }

    /**
     * Find all views by their Id's
     */
    private void findViews() {
        tv_inTab = (TextView) findViewById(R.id.inTab);
        tv_outTab = (TextView) findViewById(R.id.outTab);
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
        tv_totalAmountOut = (TextView) findViewById(R.id.total_out_amount);
        tv_totalAmountSum = (TextView) findViewById(R.id.total_sum_amount);
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
