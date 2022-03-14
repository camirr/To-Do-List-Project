package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    private JsonReader testReader1;
    private JsonReader testReader2;
    private JsonReader testReader3;

    @BeforeEach
    void runBefore() {
        testReader1 = new JsonReader("./data/noSuchFile.json");
        testReader2 = new JsonReader("./data/testReaderEmptyList.json");
        testReader3 = new JsonReader("./data/testReaderGeneralList.json");
    }


    @Test
    void testReaderNonExistentFile() {
        try {
            ToDoList tdl = testReader1.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyList() {
        try {
            ToDoList tdl = testReader2.read();
            assertEquals("My To-Do List", tdl.getName());
            assertEquals(0, tdl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralist() {
        try {
            ToDoList tdl = testReader3.read();
            assertEquals("My To-Do List", tdl.getName());
            List<Task> tasks = tdl.getToDoList();
            assertEquals(2, tasks.size());
            checkTask("CPSC 210", "Project phase 2", "2021-10-09", tasks.get(0));
            checkTask("MATH 215", "Homework 6", "2021-11-06", tasks.get(1));
            assertEquals(tdl.getTask(0).getStatus(), Task.TaskStatus.OVERDUE);
            tasks.get(1).setStatus(Task.TaskStatus.COMPLETED);
            assertEquals(tdl.getTask(1).getStatus(), Task.TaskStatus.COMPLETED);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}

