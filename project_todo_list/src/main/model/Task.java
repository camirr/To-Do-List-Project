package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Represent a to-do task with its title, description, due date, and completion
@SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:SuppressWarnings"})
public class Task implements Writable {
    public enum TaskStatus {COMPLETED, OVERDUE, INCOMPLETE}

    private TaskStatus status;              // task status
    private String title;                   // task name
    private String description;             // task description
    private String dueDate;                 // task due date (yyyy-MM-dd)
    private boolean checked;                // checked/uncheck (completion)

    //MODIFIES: this
    //EFFECTS: creates a new task
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        if (isTodayAfterDueDate()) {
            status = TaskStatus.OVERDUE;
        } else {
            status = TaskStatus.INCOMPLETE;
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new task include task status
    public Task(String title, String description, String dueDate, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.checked = false;
        if (status != TaskStatus.COMPLETED && isTodayAfterDueDate()) {
            this.status = TaskStatus.OVERDUE;
        } else {
            this.status = status;
        }
    }

    //EFFECTS: returns true if entered due date is valid format as we required and is the date after due date,
    //         otherwise false
    protected boolean isTodayAfterDueDate() {
        Date today = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date due = format.parse(dueDate);
            return due.before(today);
        } catch (ParseException e) {
            return false;
        }
    }

    //EFFECTS: returns task name/title
    public String getTitle() {
        return title;
    }

    //EFFECTS: returns task description
    public String getDescription() {
        return description;
    }

    //EFFECTS: returns task due date
    public String getDueDate() {
        return dueDate;
    }

    //MODIFIES: this
    //EFFECTS: checks the task as completed
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //EFFECT: returns current status of the item
    public TaskStatus getStatus() {
        return status;
    }

    //EFFECTS: returns true if the task is checked/completed, otherwise returns false
    public boolean isChecked() {
        return checked;
    }

    //MODIFIES: this
    //EFFECTS: sets status of item
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    //EFFECTS: returns true if the item is overdue otherwise false
    public boolean isOverdue() {
        return status == TaskStatus.OVERDUE;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        json.put("due date", dueDate);
        json.put("status", status);
        return json;
    }

    @Override
    public String toString() {
        return "Task{" + "status=" + status + ", title='" + title + '\'' + ", description='" + description + '\''
                + ", dueDate='" + dueDate + '\'' + ", checked=" + checked + '}';
    }
}
