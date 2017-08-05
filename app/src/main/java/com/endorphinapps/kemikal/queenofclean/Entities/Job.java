package com.endorphinapps.kemikal.queenofclean.Entities;

public class Job {

    private long id;
    private long customer;
    private long employee;
    private long startDate;
    private long startTime;
    private long endDate;
    private String jobStatusEnum;
    private String customerPaymentStatusEnum;
    private String employeePaymentStatusEnum;
    private double estimatedTime;
    private int actualTime;
    private double totalPrice;
    private String notes;

    public Job() {}

    public Job(long id, long customer, long employee,
               long startDate, long startTime, long endDate,
               String jobStatusEnum, String customerPaymentStatusEnum,
               String employeePaymentStatusEnum, double estimatedTime,
               int actualTime, double totalPrice, String notes) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.jobStatusEnum = jobStatusEnum;
        this.customerPaymentStatusEnum = customerPaymentStatusEnum;
        this.employeePaymentStatusEnum = employeePaymentStatusEnum;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.totalPrice = totalPrice;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
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

    public String getCustomerPaymentStatusEnum() {
        return customerPaymentStatusEnum;
    }

    public void setCustomerPaymentStatusEnum(String customerPaymentStatusEnum) {
        this.customerPaymentStatusEnum = customerPaymentStatusEnum;
    }

    public String getEmployeePaymentStatusEnum() {
        return employeePaymentStatusEnum;
    }

    public void setEmployeePaymentStatusEnum(String employeePaymentStatusEnum) {
        this.employeePaymentStatusEnum = employeePaymentStatusEnum;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
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
                ", startTime=" + startTime +
                ", endDate=" + endDate +
                ", jobStatusEnum='" + jobStatusEnum + '\'' +
                ", customerPaymentStatusEnum='" + customerPaymentStatusEnum + '\'' +
                ", employeePaymentStatusEnum='" + employeePaymentStatusEnum + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", actualTime=" + actualTime +
                ", totalPrice=" + totalPrice +
                ", notes='" + notes + '\'' +
                '}';
    }
}