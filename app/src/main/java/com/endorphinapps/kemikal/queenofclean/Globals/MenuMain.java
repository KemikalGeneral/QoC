package com.endorphinapps.kemikal.queenofclean.Globals;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.endorphinapps.kemikal.queenofclean.Database.DBHelper;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.R;

/**
 * Main Options menu that runs throughout the app.
 * It is extended by all activity classes that use it.
 */
public class MenuMain extends AppCompatActivity
        implements ConfirmationDialog.ConfirmationDialogListener {

    DBHelper db = new DBHelper(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_drop_tables:
                // Show confirmation dialog yes/no to drop all tables
                showConfirmationDialog();
                break;
            case R.id.menu_main_about:
                //TODO - add a credits and contacts page
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
    }

    /**
     * Instantiate an show new ConfirmationDialog class and pass through the dialog message
     */
    private void showConfirmationDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.setMessage(
                "Are you sure you want to DELETE all records in the DATABASE?" +
                        "\n\n" +
                        "...this is bad and cannot be undone!");
        confirmationDialog.show(getFragmentManager(), "dropTables");
    }

    /**
     * On positive click, drop all tables and start the MainActivity activity
     *
     * @param dialogFragment
     */
    @Override
    public void dialogPositiveClick(DialogFragment dialogFragment) {
        // Delete all tables
        db.dropTables();
        Toast.makeText(getApplicationContext(), "Tables Dropped", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    /**
     * On negative click, dismiss dialog and do nothing
     *
     * @param dialogFragment
     */
    @Override
    public void dialogNegativeClick(DialogFragment dialogFragment) {
        // Do nothing
    }
}
