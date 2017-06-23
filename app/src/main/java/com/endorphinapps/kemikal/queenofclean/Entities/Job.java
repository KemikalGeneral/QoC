package com.endorphinapps.kemikal.queenofclean.Entities;

import com.endorphinapps.kemikal.queenofclean.ENUMs.JobStatus;

import java.util.Date;

/**
 * Created by User on 06/12/2016.
 */

public class Job {

    private int id;
    private Date startDate;
    private Date endDate;
    private Enum<JobStatus> jobStatusEnum;
    private int timeTaken;
    private int totalPrice;
    private String notes;
    private long customer;
    private long employee;

    public Job() {}

    public Job(int id, Date startDate, Date endDate,
               Enum<JobStatus> jobStatus, int timeTaken,
               int totalPrice, String notes, long customer,
               long employee) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobStatusEnum = jobStatus;
        this.timeTaken = timeTaken;
        this.totalPrice = totalPrice;
        this.notes = notes;
        this.customer = customer;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Enum<JobStatus> getJobStatusEnum() {
        return jobStatusEnum;
    }

    public void setJobStatusEnum(Enum<JobStatus> jobStatusEnum) {
        this.jobStatusEnum = jobStatusEnum;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", jobStatusEnum=" + jobStatusEnum +
                ", timeTaken=" + timeTaken +
                ", totalPrice=" + totalPrice +
                ", notes='" + notes + '\'' +
                ", customer=" + customer +
                ", employee=" + employee +
                '}';
    }
}