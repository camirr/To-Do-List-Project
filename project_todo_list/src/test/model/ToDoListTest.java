package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList testList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void runBefore() {
        testList = new ToDoList("My To-Do List");
        task1 = new Task("CPSC 210 Project", "Phase 1", "2021-10-15");
        task2 = new Task("MATH 215 HW", "Homework 3", "2021-10-05");
        task3 = new Task("Groceries", "Pick up groceries at Save-on-Foods", "2021-10-05");
        task1.setChecked(false);
        task2.setChecked(true);
        task3.setChecked(false);
    }

    @Test
    void testMyList() {
        assertEquals(testList.getName(), "My To-Do List");
        assertEquals(testList.getToDoList().size(), 0);
    }

    @Test
    void testAddTask() {
        testList.addTask(task1);
        assertEquals(testList.getToDoList().size(), 1);
        assertTrue(testList.getToDoList().contains(task1));
    }

    @Test
    void testGetToDoList() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(testList.getToDoList().size(), 3);
        assertTrue(testList.getToDoList().contains(task1));
        assertTrue(testList.getToDoList().contains(task2));
        assertTrue(testList.getToDoList().contains(task3));
    }

    @Test
    void testUncheckedTaskList() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(testList.uncheckedTaskList().size(), 2);
        assertTrue(testList.uncheckedTaskList().contains(task1));
        assertFalse(testList.uncheckedTaskList().contains(task2));
        assertTrue(testList.uncheckedTaskList().contains(task3));
    }

    @Test
    void testCheckedTaskList() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(testList.checkedTaskList().size(), 1);
        assertFalse(testList.checkedTaskList().contains(task1));
        assertTrue(testList.checkedTaskList().contains(task2));
        assertFalse(testList.checkedTaskList().contains(task3));
    }

    @Test
    void testSearchByTitle() {
        testList.addTask(task1);
        testList.addTask(task2);
        assertEquals(testList.searchByTitle("CPSC 210 Project").size(), 1);
        assertTrue(testList.searchByTitle("CPSC 210 Project").contains(task1));
        assertTrue(testList.searchByTitle("MATH 215 HW").contains(task2));
    }

    @Test
    void testSearchByDate() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(testList.searchByDate("2021-10-05").size(), 2);
        assertFalse(testList.searchByDate("2021-10-05").contains(task1));
    }

    @Test
    void testSize() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(testList.size(), 3);
        assertEquals(testList.uncheckedTaskSize(), 2);
        assertEquals(testList.checkedTaskSize(), 1);
    }

    @Test
    void testMarkAsDone() {
        testList.addTask(task1);
        assertEquals(1, testList.uncheckedTaskSize());
        testList.addTask(task3);
        assertEquals(2, testList.uncheckedTaskSize());
        assertTrue(testList.markAsDone(0));
        assertTrue(testList.markAsDone(1));
    }

    @Test
    void testEmptyList() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(3, testList.size());
        testList.emptyList();
        assertEquals(0, testList.size());
    }

    @Test
    void testGetTask() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(3, testList.size());
        assertEquals(task1, testList.getTask(0));
        assertEquals(task2, testList.getTask(1));
        assertEquals(task3, testList.getTask(2));
    }


}

