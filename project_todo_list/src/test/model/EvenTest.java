package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenTest {
    private Event e;
    private Date d;


    @BeforeEach
    public void runBefore() {
        e = new Event("New Task has been added.");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("New Task has been added.", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New Task has been added.", e.toString());
    }
}
