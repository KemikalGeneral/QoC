package com.endorphinapps.kemikal.queenofclean.Entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 28/06/2017.
 */
public class CustomerTest {

    private Customer customer = new Customer(
            1, "first", "last", "add1", "add2", "town", "city",
            "CF123AB", "07890123456", "02920123456",
            "e@mail.com", "notes", 0);

    @Test
    public void getCustomerId() throws Exception {
        long id = customer.getCustomerId();

        assertEquals(1, id);
    }

    @Test
    public void setCustomerId() throws Exception {
        customer.setCustomerId(2);

        assertEquals(2, customer.getCustomerId());
    }

    @Test
    public void getFirstName() throws Exception {
        String firstName = customer.getFirstName();

        assertEquals("first", firstName);
    }

    @Test
    public void setFirstName() throws Exception {
        customer.setFirstName("first2");

        assertEquals("first2", customer.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        String lastName = customer.getLastName();

        assertEquals("last", lastName);
    }

    @Test
    public void setLastName() throws Exception {
        customer.setLastName("last2");

        assertEquals("last2", customer.getLastName());
    }

    @Test
    public void getAddressLine1() throws Exception {
        String add1 = customer.getAddressLine1();

        assertEquals("add1", add1);
    }

    @Test
    public void setAddressLine1() throws Exception {
        customer.setAddressLine1("add1_2");

        assertEquals("add1_2", customer.getAddressLine1());

    }

    @Test
    public void getAddressLine2() throws Exception {
        String add2 = customer.getAddressLine2();

        assertEquals("add2", add2);
    }

    @Test
    public void setAddressLine2() throws Exception {
        customer.setAddressLine2("add2_2");

        assertEquals("add2_2", customer.getAddressLine2());
    }

    @Test
    public void getTown() throws Exception {
        String town = customer.getTown();

        assertEquals("town", town);
    }

    @Test
    public void setTown() throws Exception {
        customer.setTown("town_2");

        assertEquals("town_2", customer.getTown());
    }

    @Test
    public void getCity() throws Exception {
        String city = customer.getCity();

        assertEquals("city", city);
    }

    @Test
    public void setCity() throws Exception {
        customer.setCity("city_2");

        assertEquals("city_2", customer.getCity());
    }

    @Test
    public void getPostcode() throws Exception {
        String postCode = customer.getPostcode();

        assertEquals("CF123AB", postCode);
    }

    @Test
    public void setPostcode() throws Exception {
        customer.setPostcode("CF123AC");

        assertEquals("CF123AC", customer.getPostcode());
    }

    @Test
    public void getMobileNumber() throws Exception {
        String mobile = customer.getMobileNumber();

        assertEquals("07890123456", mobile);
    }

    @Test
    public void setMobileNumber() throws Exception {
        customer.setMobileNumber("07890987654");

        assertEquals("07890987654", customer.getMobileNumber());
    }

    @Test
    public void getHomeNumber() throws Exception {
        String homeNo = customer.getHomeNumber();

        assertEquals("02920123456", homeNo);
    }

    @Test
    public void setHomeNumber() throws Exception {
        customer.setHomeNumber("02920987654");

        assertEquals("02920987654", customer.getHomeNumber());
    }

    @Test
    public void getEmailAddress() throws Exception {
        String email = customer.getEmailAddress();

        assertEquals("e@mail.com", email);
    }

    @Test
    public void setEmailAddress() throws Exception {
        customer.setEmailAddress("e@mail.co.uk");

        assertEquals("e@mail.co.uk", customer.getEmailAddress());
    }

}