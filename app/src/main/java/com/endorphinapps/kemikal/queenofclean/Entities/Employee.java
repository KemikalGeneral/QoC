package com.endorphinapps.kemikal.queenofclean.Entities;

public class Employee {

    private long employeeId;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String city;
    private String postcode;
    private String mobileNumber;
    private String homeNumber;
    private String emailAddress;
    private String notes;
    private double rateOfPay;
    // Availability
    private int mondayAM;
    private int mondayPM;
    private int tuesdayAM;
    private int tuesdayPM;
    private int wednesdayAM;
    private int wednesdayPM;
    private int thursdayAM;
    private int thursdayPM;
    private int fridayAM;
    private int fridayPM;
    private int saturdayAM;
    private int saturdayPM;
    private int sundayAM;
    private int sundayPM;

    public Employee() {
    }

    public Employee(long employeeId, String firstName,
                    String lastName, String addressLine1,
                    String addressLine2, String town, String city,
                    String postcode, String mobileNumber,
                    String homeNumber, String emailAddress,
                    String notes, double rateOfPay) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.city = city;
        this.postcode = postcode;
        this.mobileNumber = mobileNumber;
        this.homeNumber = homeNumber;
        this.emailAddress = emailAddress;
        this.notes = notes;
        this.rateOfPay = rateOfPay;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getRateOfPay() {
        return rateOfPay;
    }

    public void setRateOfPay(double rateOfPay) {
        this.rateOfPay = rateOfPay;
    }

    public int getMondayAM() {
        return mondayAM;
    }

    public void setMondayAM(int mondayAM) {
        this.mondayAM = mondayAM;
    }

    public int getMondayPM() {
        return mondayPM;
    }

    public void setMondayPM(int mondayPM) {
        this.mondayPM = mondayPM;
    }

    public int getTuesdayAM() {
        return tuesdayAM;
    }

    public void setTuesdayAM(int tuesdayAM) {
        this.tuesdayAM = tuesdayAM;
    }

    public int getTuesdayPM() {
        return tuesdayPM;
    }

    public void setTuesdayPM(int tuesdayPM) {
        this.tuesdayPM = tuesdayPM;
    }

    public int getWednesdayAM() {
        return wednesdayAM;
    }

    public void setWednesdayAM(int wednesdayAM) {
        this.wednesdayAM = wednesdayAM;
    }

    public int getWednesdayPM() {
        return wednesdayPM;
    }

    public void setWednesdayPM(int wednesdayPM) {
        this.wednesdayPM = wednesdayPM;
    }

    public int getThursdayAM() {
        return thursdayAM;
    }

    public void setThursdayAM(int thursdayAM) {
        this.thursdayAM = thursdayAM;
    }

    public int getThursdayPM() {
        return thursdayPM;
    }

    public void setThursdayPM(int thursdayPM) {
        this.thursdayPM = thursdayPM;
    }

    public int getFridayAM() {
        return fridayAM;
    }

    public void setFridayAM(int fridayAM) {
        this.fridayAM = fridayAM;
    }

    public int getFridayPM() {
        return fridayPM;
    }

    public void setFridayPM(int fridayPM) {
        this.fridayPM = fridayPM;
    }

    public int getSaturdayAM() {
        return saturdayAM;
    }

    public void setSaturdayAM(int saturdayAM) {
        this.saturdayAM = saturdayAM;
    }

    public int getSaturdayPM() {
        return saturdayPM;
    }

    public void setSaturdayPM(int saturdayPM) {
        this.saturdayPM = saturdayPM;
    }

    public int getSundayAM() {
        return sundayAM;
    }

    public void setSundayAM(int sundayAM) {
        this.sundayAM = sundayAM;
    }

    public int getSundayPM() {
        return sundayPM;
    }

    public void setSundayPM(int sundayPM) {
        this.sundayPM = sundayPM;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", town='" + town + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", notes='" + notes + '\'' +
                ", rateOfPay=" + rateOfPay +
                ", mondayAM=" + mondayAM +
                ", mondayPM=" + mondayPM +
                ", tuesdayAM=" + tuesdayAM +
                ", tuesdayPM=" + tuesdayPM +
                ", wednesdayAM=" + wednesdayAM +
                ", wednesdayPM=" + wednesdayPM +
                ", thursdayAM=" + thursdayAM +
                ", thursdayPM=" + thursdayPM +
                ", fridayAM=" + fridayAM +
                ", fridayPM=" + fridayPM +
                ", saturdayAM=" + saturdayAM +
                ", saturdayPM=" + saturdayPM +
                ", sundayAM=" + sundayAM +
                ", sundayPM=" + sundayPM +
                '}';
    }
}
