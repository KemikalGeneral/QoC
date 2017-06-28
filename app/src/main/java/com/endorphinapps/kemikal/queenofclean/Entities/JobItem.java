package com.endorphinapps.kemikal.queenofclean.Entities;

/**
 * Created by User on 27/06/2017.
 */

public class JobItem {

    private long jobItemId;
    private long job;
    private String description;
    private double price;

    public JobItem(){}

    public JobItem(long jobItemId, long job, String description,
                   double price) {
        this.jobItemId = jobItemId;
        this.job = job;
        this.description = description;
        this.price = price;
    }

    public long getJobItemId() {
        return jobItemId;
    }

    public void setJobItemId(long jobItemId) {
        this.jobItemId = jobItemId;
    }

    public long getJob() {
        return job;
    }

    public void setJob(long job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "JobItem{" +
                "jobItemId=" + jobItemId +
                ", job=" + job +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
