package com.endorphinapps.kemikal.queenofclean.Entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 28/06/2017.
 */
public class EmployeeTest {

    private Employee employee = new Employee(
            1, "first", "last", "add1", "add2", "town", "city",
            "CF123AB", "07890123456", "02920123456",
            "e@mail.com", 123);

    @Test
    public void getEmployeeId() throws Exception {
        long id = employee.getEmployeeId();

        assertEquals(1, id);
    }

    @Test
    public void setEmployeeId() throws Exception {
        employee.setEmployeeId(2);

        assertEquals(2, employee.getEmployeeId());
    }

    @Test
    public void getFirstName() throws Exception {
        String firstName = employee.getFirstName();

        assertEquals("first", firstName);
    }

    @Test
    public void setFirstName() throws Exception {
        employee.setFirstName("first2");

        assertEquals("first2", employee.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        String lastName = employee.getLastName();

        assertEquals("last", lastName);
    }

    @Test
    public void setLastName() throws Exception {
        employee.setLastName("last2");

        assertEquals("last2", employee.getLastName());
    }

    @Test
    public void getAddressLine1() throws Exception {
        String add1 = employee.getAddressLine1();

        assertEquals("add1", add1);
    }

    @Test
    public void setAddressLine1() throws Exception {
        employee.setAddressLine1("add1_2");

        assertEquals("add1_2", employee.getAddressLine1());

    }

    @Test
    public void getAddressLine2() throws Exception {
        String add2 = employee.getAddressLine2();

        assertEquals("add2", add2);
    }

    @Test
    public void setAddressLine2() throws Exception {
        employee.setAddressLine2("add2_2");

        assertEquals("add2_2", employee.getAddressLine2());
    }

    @Test
    public void getTown() throws Exception {
        String town = employee.getTown();

        assertEquals("town", town);
    }

    @Test
    public void setTown() throws Exception {
        employee.setTown("town_2");

        assertEquals("town_2", employee.getTown());
    }

    @Test
    public void getCity() throws Exception {
        String city = employee.getCity();

        assertEquals("city", city);
    }

    @Test
    public void setCity() throws Exception {
        employee.setCity("city_2");

        assertEquals("city_2", employee.getCity());
    }

    @Test
    public void getPostcode() throws Exception {
        String postCode = employee.getPostcode();

        assertEquals("CF123AB", postCode);
    }

    @Test
    public void setPostcode() throws Exception {
        employee.setPostcode("CF123AC");

        assertEquals("CF123AC", employee.getPostcode());
    }

    @Test
    public void getMobileNumber() throws Exception {
        String mobile = employee.getMobileNumber();

        assertEquals("07890123456", mobile);
    }

    @Test
    public void setMobileNumber() throws Exception {
        employee.setMobileNumber("07890987654");

        assertEquals("07890987654", employee.getMobileNumber());
    }

    @Test
    public void getHomeNumber() throws Exception {
        String homeNo = employee.getHomeNumber();

        assertEquals("02920123456", homeNo);
    }

    @Test
    public void setHomeNumber() throws Exception {
        employee.setHomeNumber("02920987654");

        assertEquals("02920987654", employee.getHomeNumber());
    }

    @Test
    public void getEmailAddress() throws Exception {
        String email = employee.getEmailAddress();

        assertEquals("e@mail.com", email);
    }

    @Test
    public void setEmailAddress() throws Exception {
        employee.setEmailAddress("e@mail.co.uk");

        assertEquals("e@mail.co.uk", employee.getEmailAddress());
    }


    @Test
    public void getRateOfPay() throws Exception {
        int rate = employee.getRateOfPay();

        assertEquals(123, rate);
    }

    @Test
    public void setRateOfPay() throws Exception {
        employee.setRateOfPay(456);

        assertEquals(456, employee.getRateOfPay());
    }

}