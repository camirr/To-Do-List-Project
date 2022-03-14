package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// This class references code from TellerApp
// To-do list application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/toDoList.json";
    private Scanner input;
    private ToDoList list;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // MODIFIES: this
    // EFFECTS: constructs to-do list and runs application
    public ToDoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        list = new ToDoList("My To-Do List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");
        runToDoList();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runToDoList() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    //EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view my to-do list");
        System.out.println("\ta -> add a new task");
        System.out.println("\tf -> search my task");
        System.out.println("\tm -> mark a task as done");
        System.out.println("\ts -> save list to file");
        System.out.println("\tl -> load list from file");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("v")) {
            doView();
        } else if (command.equals("f")) {
            doSearch();
        } else if (command.equals("m")) {
            markAsDone();
        } else if (command.equals("s")) {
            doSave();
        } else if (command.equals("l")) {
            doLoad();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: loads to-do list from file
    private void doLoad() {
        try {
            list = jsonReader.read();
            System.out.println("Loaded " + list.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: saves the to-do list to file
    private void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println("Saved " + list.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: prompts user to add a new task to to-do list
    private void doAdd() {
        System.out.println("I want to add a new task to my list.");
        System.out.println("The title of new task is: ");
        String taskTitle = input.next();
        System.out.println("The description of new task is: ");
        String taskDesc = input.next();
        System.out.println("The due date of new task is: ");
        String taskDate = input.next();
        list.addTask(new Task(taskTitle, taskDesc, taskDate));
    }


    //EFFECTS: prints a list of tasks in selected view option
    private void doView() {
        System.out.println("\nI want to view");
        ArrayList<Task> selected = (ArrayList<Task>) selectOption();
        System.out.println(selected);
    }

    //REQUIRES: at least one task in the list
    //EFFECTS: prompts user to select a list of all, incomplete or completed tasks and return it and its size
    private Object selectOption() {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("b") || selection.equals("c"))) {
            System.out.println("a for a list of all tasks");
            System.out.println("b for a list of incomplete tasks");
            System.out.println("c for a list of completed tasks");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            System.out.println("You currently have " + list.size() + " to-do tasks in your list");
            return list.getToDoList();
        } else if (selection.equals("b")) {
            System.out.println("You still have " + list.uncheckedTaskSize() + " incomplete to-do tasks in your list");
            return list.uncheckedTaskList();
        } else {
            System.out.println("You've already completed " + list.checkedTaskSize() + " tasks!");
            return list.checkedTaskList();
        }
    }


    //EFFECTS: prints the search result
    private void doSearch() {
        System.out.println("\nI want to search by");
        ArrayList<Task> searched = (ArrayList<Task>) searchOption();
        System.out.println(searched);
    }

    //REQUIRES: at least one task in the list
    //EFFECTS: prompts user to select a list of all, incomplete or completed tasks and returns it and its size
    private Object searchOption() {
        String selection = "";
        while (!(selection.equals("t") || selection.equals("d"))) {
            System.out.println("t for search by title");
            System.out.println("d date for search by due date");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("t")) {
            System.out.println("The title of task is: ");
            String taskTitle = input.next();
            if (list.searchByTitle(taskTitle).isEmpty()) {
                System.out.println("Not found...");
            }
            return list.searchByTitle(taskTitle);
        } else {
            System.out.println("The due date of task is: ");
            String taskDate = input.next();
            if (list.searchByDate(taskDate).isEmpty()) {
                System.out.println("Not found...");
            }
            return list.searchByDate(taskDate);
        }
    }

    //EFFECTS: prompts user to mark a specific task's status as done
    private void markAsDone() {
        System.out.println("Which item would you like to mark as done?");
        int itemIndex = input.nextInt();
        input.nextLine();
        list.markAsDone(itemIndex);
        System.out.println("Your item has been marked as done.");
    }
}


