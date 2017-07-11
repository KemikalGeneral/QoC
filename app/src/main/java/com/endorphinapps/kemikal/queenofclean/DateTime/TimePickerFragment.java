package com.endorphinapps.kemikal.queenofclean.DateTime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Kemikal on 10/07/2017.
 */

public class TimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Default to current time
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.set(Calendar.MINUTE, 0);
        int minute = calendar.get(Calendar.MINUTE);

        // Create and return a new instance on the timePickerDialog
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener) getActivity(),
                hour,
                minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
