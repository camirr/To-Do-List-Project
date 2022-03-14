package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Represents a list of to-do tasks
public class ToDoList extends ArrayList<Task> implements Writable {
    private ArrayList<Task> toDoList;
    private String name;
    private static final String JSON_STORE = "./data/toDoList.json";

    // MODIFIES: this
    //EFFECTS: constructs a new to-do list with a name and empty list
    public ToDoList(String name) {
        this.name = name;
        toDoList = new ArrayList<>();
    }

    // EFFECTS: returns the name of to-do list
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: adds a task to the to-do list
    public void addTask(Task task) {
        toDoList.add(task);
        EventLog.getInstance().logEvent(new Event("Added task: " + task.getTitle()));
    }

    // MODIFIES: this
    // EFFECTS: mark one task as done
    public boolean markAsDone(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < toDoList.size()) {
            Task task = toDoList.get(taskIndex);
            task.setStatus(Task.TaskStatus.COMPLETED);
            EventLog.getInstance().logEvent(new Event("Marked " + task.getTitle() + " as complete."));
            return true;
        } else {
            System.out.println("Invalid Index");
            return false;
        }
    }

    //EFFECTS: returns the to-do list
    public ArrayList<Task> getToDoList() {
        return toDoList;
    }


    //This method references code from
    //"https://github.com/mengyuhan/CPSC210/blob/master/lab/TodoListLab3/src/ca/ubc/cpsc210/todo/model/TodoList.java"
    //REQUIRES: at least one incomplete task in the list
    //EFFECTS: returns a list of unchecked to-do tasks
    public ArrayList<Task> uncheckedTaskList() {
        ArrayList<Task> uncheckedTask = new ArrayList<>();
        for (Task task : toDoList) {
            if (!task.isChecked()) {
                uncheckedTask.add(task);
            }
        }
        return uncheckedTask;
    }


    //REQUIRES: at least one completed task in the list
    //EFFECTS: returns a list of checked to-do tasks
    public ArrayList<Task> checkedTaskList() {
        ArrayList<Task> checkedTask = new ArrayList<>();
        for (Task task : toDoList) {
            if (task.isChecked()) {
                checkedTask.add(task);
            }
        }
        return checkedTask;
    }

    //EFFECTS: returns a list of to-do tasks searched by title
    public ArrayList<Task> searchByTitle(String searchTitle) {
        ArrayList<Task> listSearchedByTitle = new ArrayList<>();
        for (Task task : toDoList) {
            String title = task.getTitle();
            if (title.equals(searchTitle)) {
                listSearchedByTitle.add(task);
            }
        }
        return listSearchedByTitle;
    }

    //EFFECTS: returns a list of to-do tasks searched by due date
    public ArrayList<Task> searchByDate(String searchDate) {
        ArrayList<Task> listSearchedByDate = new ArrayList<>();
        for (Task task : toDoList) {
            String dueDate = task.getDueDate();
            if (dueDate.equals(searchDate)) {
                listSearchedByDate.add(task);
            }
        }
        return listSearchedByDate;
    }


    //REQUIRES: at least one incomplete task in the list
    //EFFECTS: returns the number of incomplete tasks
    public int uncheckedTaskSize() {
        ArrayList<Task> uncheckedTask = new ArrayList<>();
        for (Task task : toDoList) {
            if (!task.isChecked()) {
                uncheckedTask.add(task);
            }
        }
        return uncheckedTask.size();
    }

    //REQUIRES: at least one completed task in the list
    //EFFECTS: returns the number of completed tasks
    public int checkedTaskSize() {
        ArrayList<Task> checkedTask = new ArrayList<>();
        for (Task task : toDoList) {
            if (task.isChecked()) {
                checkedTask.add(task);
            }
        }
        return checkedTask.size();
    }

    //EFFECTS: returns the number of all tasks in the list
    public int size() {
        return toDoList.size();
    }

    // MODIFIES: this
    //EFFECTS: empty the to-do list
    public void emptyList() {
        toDoList.clear();
        clearFile();
        EventLog.getInstance().logEvent(new Event("To-do list has been emptied."));
    }

    // MODIFIES: this
    // EFFECTS: empty the saved list file
    private void clearFile() {
        try {
            PrintWriter writer = new PrintWriter(JSON_STORE, "UTF-8");
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("Encountered IOException while saving todo list.");
        }
    }

    //EFFECTS: returns task by entering its index
    public Task getTask(int index) {
        return toDoList.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tasks", tasksToJson());
        return json;
    }

    //EFFECTS: returns things in this to-do list as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : toDoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}



