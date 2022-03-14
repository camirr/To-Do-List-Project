package persistence;


import model.Task;
import model.ToDoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class references code from JsonSerializationDemo
// Represents a reader that reads to-do list from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads to-do list from file and returns it;
    //throw IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: pares to-do list from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ToDoList tdl = new ToDoList(name);
        addTasks(tdl, jsonObject);
        return tdl;
    }

    //MODIFIES: tdl
    //EFFECTS: parses tasks from JSON object and adds them to to-do list
    private void addTasks(ToDoList tdl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tdl, nextTask);
        }
    }

    //MODIFIES: tdl
    //EFFECTS: parses task from JSON object and adds it to to-do list
    private void addTask(ToDoList tdl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String dueDate = jsonObject.getString("due date");
        Task.TaskStatus status = jsonObject.getEnum(Task.TaskStatus.class, "status");
        Task task = new Task(title, description, dueDate, status);
        tdl.addTask(task);
    }
}



