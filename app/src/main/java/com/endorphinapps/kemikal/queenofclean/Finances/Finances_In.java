package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter_in;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class Finances_In extends AppCompatActivity {

    private DBHelper db;
    private Finances finances;
    private ListView lv_listView;
    private TextView tv_totalAmountIn;

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
        FinanceArrayAdapter_in financeArrayAdapterIn = new FinanceArrayAdapter_in(this);
        financeArrayAdapterIn.addAll(finances.getJobsByDateRange());
        lv_listView.setAdapter(financeArrayAdapterIn);

        // Cycle through Jobs array and total job amounts.
        // Display to TextView.
        double totalPrice = finances.getTotalAmount_In();
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

}
