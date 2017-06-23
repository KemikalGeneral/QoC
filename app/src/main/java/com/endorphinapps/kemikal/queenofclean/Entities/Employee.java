package com.endorphinapps.kemikal.queenofclean.Entities;

/**
 * Created by User on 06/12/2016.
 */

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
    private int rateOfPay;

    public Employee() {
    }

    public Employee(String firstName, String lastName,
                    String addressLine1, String addressLine2,
                    String town, String city, String postcode,
                    String mobileNumber, String homeNumber,
                    String emailAddress, int rateOfPay) {
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

    public int getRateOfPay() {
        return rateOfPay;
    }

    public void setRateOfPay(int rateOfPay) {
        this.rateOfPay = rateOfPay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + employeeId +
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
                ", rateOfPay=" + rateOfPay +
                '}';
    }
}
