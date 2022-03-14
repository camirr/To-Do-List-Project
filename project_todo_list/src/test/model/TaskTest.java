package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private Task testTask1;
    private Task testTask2;
    private Task testTask3;

    @BeforeEach
    void runBefore() {
        testTask1 = new Task("CPSC 210 Project", "Phase 1", "2021-10-15");
        testTask2 = new Task("CPSC 210", "Phase 3", "2021-11-19", Task.TaskStatus.OVERDUE);
        testTask3 = new Task("MATH", "HW", "2025-01-01", Task.TaskStatus.INCOMPLETE);
    }

    @Test
    void testConstructor() {
        assertEquals(testTask1.getTitle(), "CPSC 210 Project");
        assertEquals(testTask1.getDescription(), "Phase 1");
        assertEquals(testTask1.getDueDate(), "2021-10-15");
        assertFalse(testTask1.isChecked());
        assertEquals(testTask2.getTitle(), "CPSC 210");
        assertEquals(testTask2.getDescription(), "Phase 3");
        assertEquals(testTask2.getDueDate(), "2021-11-19");
        assertEquals(testTask2.getStatus(), Task.TaskStatus.OVERDUE);
        assertEquals(testTask3.getTitle(), "MATH");
        assertEquals(testTask3.getDescription(), "HW");
        assertEquals(testTask3.getDueDate(), "2025-01-01");
        assertEquals(testTask3.getStatus(), Task.TaskStatus.INCOMPLETE);
    }

    @Test
    void testSetChecked() {
        testTask1.setChecked(true);
        assertTrue(testTask1.isChecked());
        testTask1.setChecked(false);
        assertFalse(testTask1.isChecked());
    }

    @Test
    void testIsOverdue() {
        assertTrue(testTask2.isOverdue());
        assertFalse(testTask3.isOverdue());
    }

    @Test
    void testSetStatus() {
        testTask2.setStatus(Task.TaskStatus.COMPLETED);
        assertEquals(testTask2.getStatus(), Task.TaskStatus.COMPLETED);
    }

    @Test
    void testIsTodayAfterDueDate() {
        assertTrue(testTask1.isTodayAfterDueDate());
        assertTrue(testTask2.isTodayAfterDueDate());
        assertFalse(testTask3.isTodayAfterDueDate());
    }

}

