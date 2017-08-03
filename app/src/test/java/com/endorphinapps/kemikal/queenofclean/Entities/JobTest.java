package com.endorphinapps.kemikal.queenofclean.Entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 28/06/2017.
 */
public class JobTest {

    private Job job = new Job(
            1, 2, 3, 4, 45, 5, "status", "unpaid", "unpaid", 6, 7, 8.8, "notes");

    @Test
    public void getId() throws Exception {
        long id = job.getId();

        assertEquals(1, id);
    }

    @Test
    public void setId() throws Exception {
        job.setId(2);

        assertEquals(2, job.getId());
    }

    @Test
    public void getCustomer() throws Exception {
        long customer = job.getCustomer();

        assertEquals(2, customer);
    }

    @Test
    public void setCustomer() throws Exception {
        job.setCustomer(3);

        assertEquals(3, job.getCustomer());
    }

    @Test
    public void getEmployee() throws Exception {
        long employee = job.getEmployee();

        assertEquals(3, employee);
    }

    @Test
    public void setEmployee() throws Exception {
        job.setEmployee(4);

        assertEquals(4, job.getEmployee());
    }

    @Test
    public void getStartDate() throws Exception {
        long startDate = job.getStartDate();

        assertEquals(4, startDate);
    }

    @Test
    public void setStartDate() throws Exception {
        job.setStartDate(5);

        assertEquals(5, job.getStartDate());
    }

    @Test
    public void getEndDate() throws Exception {
        long end = job.getEndDate();

        assertEquals(5, end);
    }

    @Test
    public void setEndDate() throws Exception {
        job.setEndDate(6);

        assertEquals(6, job.getEndDate());
    }

    @Test
    public void getJobStatusEnum() throws Exception {
        String status = job.getJobStatusEnum();

        assertEquals("status", status);
    }

    @Test
    public void setJobStatusEnum() throws Exception {
        job.setJobStatusEnum("status_2");

        assertEquals("status_2", job.getJobStatusEnum());
    }

    @Test
    public void getEstimatedTime() throws Exception {
        int estimatedTime = job.getEstimatedTime();

        assertEquals(6, estimatedTime);
    }

    @Test
    public void setEstimatedTime() throws Exception {
        job.setEstimatedTime(7);

        assertEquals(7, job.getEstimatedTime());
    }

    @Test
    public void getActualTime() throws Exception {
        int actualTime = job.getActualTime();

        assertEquals(7, actualTime);
    }

    @Test
    public void setActualTime() throws Exception {
        job.setActualTime(8);

        assertEquals(8, job.getActualTime());
    }

    @Test
    public void getTotalPrice() throws Exception {
        double totalPrice = job.getTotalPrice();

        assertEquals(8.8, totalPrice, 0);
    }

    @Test
    public void setTotalPrice() throws Exception {
        job.setTotalPrice(9.9);

        assertEquals(9.9, job.getTotalPrice(), 0);
    }

    @Test
    public void getNotes() throws Exception {
        String notes = job.getNotes();

        assertEquals("notes", notes);
    }

    @Test
    public void setNotes() throws Exception {
        job.setNotes("notes_2");

        assertEquals("notes_2", job.getNotes());
    }

}