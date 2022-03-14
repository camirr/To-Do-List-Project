package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.ToDoListApp;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    private JsonWriter testWriter1;
    private JsonWriter testWriter2;
    private JsonWriter testWriter3;
    private JsonReader testReader2;
    private JsonReader testReader3;

    @BeforeEach
    void runBefore() {
        testWriter1 = new JsonWriter("./data/my\0illegal:fileName.json");
        testWriter2 = new JsonWriter("./data/testWriterEmptyList.json");
        testWriter3 = new JsonWriter("./data/testWriterGeneralList.json");
        testReader2 = new JsonReader("./data/testWriterEmptyList.json");
        testReader3 = new JsonReader("./data/testWriterGeneralList.json");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tdl = new ToDoList("My To-Do List");
            testWriter1.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ToDoList tdl = new ToDoList("My To-Do List");
            testWriter2.open();
            testWriter2.write(tdl);
            testWriter2.close();

            tdl = testReader2.read();
            assertEquals("My To-Do List", tdl.getName());
            assertEquals(0, tdl.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralList() {
        try {
            ToDoList tdl = new ToDoList("My To-Do List");
            tdl.addTask(new Task("CPSC 210",
                    "Project phase 2",
                    "2021-10-29",
                    Task.TaskStatus.OVERDUE));
            tdl.addTask(new Task("MATH 215",
                    "Homework 6",
                    "2021-11-02",
                    Task.TaskStatus.OVERDUE));
            testWriter3.open();
            testWriter3.write(tdl);
            testWriter3.close();

            tdl = testReader3.read();
            assertEquals("My To-Do List", tdl.getName());
            List<Task> tasks = tdl.getToDoList();
            assertEquals(2, tasks.size());
            checkTask("CPSC 210", "Project phase 2", "2021-10-29", tasks.get(0));
            checkTask("MATH 215", "Homework 6", "2021-11-02", tasks.get(1));
            assertEquals(tdl.getTask(0).getStatus(), Task.TaskStatus.OVERDUE);
            tasks.get(1).setStatus(Task.TaskStatus.COMPLETED);
            assertEquals(tdl.getTask(1).getStatus(), Task.TaskStatus.COMPLETED);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}


