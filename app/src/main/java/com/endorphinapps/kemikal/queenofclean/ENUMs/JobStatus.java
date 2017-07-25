package com.endorphinapps.kemikal.queenofclean.ENUMs;

import java.util.ArrayList;

public enum JobStatus {
    Unconfirmed,
    Pending,
    Current,
    Completed,
    Canceled;

    public ArrayList<String> getAllStatuses() {
        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("Unconfirmed");
        statuses.add("Pending");
        statuses.add("Current");
        statuses.add("Completed");
        statuses.add("Canceled");

        return statuses;
    }

}
