package com.endorphinapps.kemikal.queenofclean.Entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 28/06/2017.
 */
public class JobItemTest {

    private JobItem jobItem = new JobItem(
            1, 2, "description", 3.3);

    @Test
    public void getJobItemId() throws Exception {
        long id = jobItem.getJobItemId();

        assertEquals(1, id);
    }

    @Test
    public void setJobItemId() throws Exception {
        jobItem.setJobItemId(2);

        assertEquals(2, jobItem.getJobItemId());
    }

    @Test
    public void getJob() throws Exception {
        long job = jobItem.getJob();

        assertEquals(2, job);
    }

    @Test
    public void setJob() throws Exception {
        jobItem.setJob(3);

        assertEquals(3, jobItem.getJob());
    }

    @Test
    public void getDescription() throws Exception {
        String desc= jobItem.getDescription();

        assertEquals("description", desc);
    }

    @Test
    public void setDescription() throws Exception {
        jobItem.setDescription("description_2");

        assertEquals("description_2", jobItem.getDescription());
    }

    @Test
    public void getPrice() throws Exception {
        double price = jobItem.getPrice();

        assertEquals(3.3, price, 0);
    }

    @Test
    public void setPrice() throws Exception {
        jobItem.setPrice(4.4);

        assertEquals(4.4, jobItem.getPrice(), 0);
    }

}