package com.endorphinapps.kemikal.queenofclean.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.endorphinapps.kemikal.queenofclean.Finances.Finances_overview;
import com.endorphinapps.kemikal.queenofclean.MainActivity;
import com.endorphinapps.kemikal.queenofclean.R;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewCustomers;
import com.endorphinapps.kemikal.queenofclean.ViewAlls.ViewJobs;

public class NavigationBottom extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    public NavigationBottom(final Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_bottom_btn_home:
                context.startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.nav_bottom_btn_people:
                context.startActivity(new Intent(context, ViewCustomers.class));
                break;
            case R.id.nav_bottom_btn_jobs:
                context.startActivity(new Intent(context, ViewJobs.class));
                break;
            case R.id.nav_bottom_btn_finances:
                context.startActivity(new Intent(context, Finances_overview.class));
                break;

        }
        ((Activity) context).finish();
    }


}
