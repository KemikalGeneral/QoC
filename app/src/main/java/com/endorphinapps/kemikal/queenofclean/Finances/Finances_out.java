package com.endorphinapps.kemikal.queenofclean.Finances;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.endorphinapps.kemikal.queenofclean.Adapters.FinanceArrayAdapter_out;
import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.R;

public class Finances_out extends AppCompatActivity {

    private DBHelper db;
    private Finances_logic finances;
    private ListView lv_listView;
    private TextView tv_totalAmountOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances_out);

        // Find all views by Id
        findViews();

        // Instantiate DB and Finance_logic classes
        db = new DBHelper(this);
        finances = new Finances_logic(db);

        // Setup and display ListView
        // using the getJobsByDateRange method
        // Monday - Sunday of current week
        FinanceArrayAdapter_out adapter = new FinanceArrayAdapter_out(this);
        adapter.addAll(finances.getJobsByDateRange());
        lv_listView.setAdapter(adapter);

        // Cycle through Jobs and total pay to employees.
        // Display to TextView
        double totalPayToEmployee = finances.getTotalAmount_out();
        tv_totalAmountOut.setText("£");
        tv_totalAmountOut.append(String.format("%.2f", totalPayToEmployee));
    }

    /**
     * Find all views by their Id's
     */
    private void findViews() {
        lv_listView = (ListView) findViewById(R.id.finances_out_listview);
        tv_totalAmountOut = (TextView) findViewById(R.id.total_out_amount);
    }

}
