package com.endorphinapps.kemikal.queenofclean.Entities;

/**
 * Created by User on 06/12/2016.
 */

public class Job {

    private int id;
    private long customer;
    private long employee;
    private long startDate;
    private long endDate;
    private String jobStatusEnum;
    private int estimatedTime;
    private int actualTime;
    private double totalPrice;
    private String notes;

    public Job() {}

    public Job(int id, long customer, long employee,
               long startDate, long endDate, String jobStatusEnum,
               int estimatedTime, int actualTime, double totalPrice, String notes) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobStatusEnum = jobStatusEnum;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.totalPrice = totalPrice;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public long getEmployee() {
        return employee;
    }

    public void setEmployee(long employee) {
        this.employee = employee;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getJobStatusEnum() {
        return String.valueOf(jobStatusEnum);
    }

    public void setJobStatusEnum(String jobStatusEnum) {
        this.jobStatusEnum = jobStatusEnum;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", customer=" + customer +
                ", employee=" + employee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", jobStatusEnum=" + jobStatusEnum +
                ", estimatedTime=" + estimatedTime +
                ", actualTime=" + actualTime +
                ", totalPrice=" + totalPrice +
                ", notes='" + notes + '\'' +
                '}';
    }
}