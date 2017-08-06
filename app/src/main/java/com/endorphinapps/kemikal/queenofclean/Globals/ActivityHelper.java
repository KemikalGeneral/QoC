package com.endorphinapps.kemikal.queenofclean.Globals;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * Created by kemik on 06/08/2017.
 */

public class ActivityHelper {

    public static void initialise(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
