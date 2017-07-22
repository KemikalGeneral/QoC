package com.endorphinapps.kemikal.queenofclean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.endorphinapps.kemikal.queenofclean.Entities.Job;

/**
 * Created by User on 22/07/2017.
 */

class DayListAdapter extends ArrayAdapter<Job> {

    public DayListAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_job, parent, false);
        }

        Job job = getItem(position);


        return convertView;
    }
}
