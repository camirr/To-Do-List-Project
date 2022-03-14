package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from JsonSerializationDemo
public class JsonTest {
    protected void checkTask(String title, String description, String dueDate, Task task) {
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
    }
}

