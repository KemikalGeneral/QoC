package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class Finances extends AppCompatActivity {

    private DBHelper db;
    private Finances_logic finances;
    private TextView tv_totalAmountIn;
    private TextView tv_totalAmountOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);

        // Find all views by Id
        findViews();

        // Instantiate DB and Finance_logic classes
        db = new DBHelper(this);
        finances = new Finances_logic(db);

        // Display total amount in
        tv_totalAmountIn.setText("£");
        tv_totalAmountIn.append(String.valueOf(finances.getTotalAmount_In()));

        // Display total amount out
        tv_totalAmountOut.setText("£");
        tv_totalAmountOut.append(String.valueOf(finances.getTotalAmount_out()));
    }

    /**
     * Find all views by their Id's
     */
    private void findViews() {
        tv_totalAmountIn = (TextView) findViewById(R.id.total_in_amount);
        tv_totalAmountOut = (TextView) findViewById(R.id.total_out_amount);
    }
}
