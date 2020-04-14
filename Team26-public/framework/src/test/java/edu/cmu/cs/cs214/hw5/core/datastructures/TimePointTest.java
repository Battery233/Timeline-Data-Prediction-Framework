package edu.cmu.cs.cs214.hw5.core.datastructures;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimePointTest {
    private TimePoint tp;
    private LocalDate d1;
    private double v1;
    private String name;

    @Before
    public void setUp() {
        d1 = LocalDate.of(2000, 2, 11);
        v1 = 17214;
        name = "test";
        tp = new TimePoint(d1, v1, name);
    }

    @Test
    public void testGetValue() {
        assertTrue(v1 == tp.getValue());
    }

    @Test
    public void testGetTime() {
        assertEquals(d1, tp.getTime());
    }

    @Test
    public void testGetName() {
        assertEquals(name, tp.getName());
    }

    @Test
    public void testToString() {
        assertEquals(name, tp.toString());
    }
}
