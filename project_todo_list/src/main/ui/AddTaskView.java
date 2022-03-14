package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// This class references code from ListDemo
// "https://docs.oracle.com/javase/tutorial/uiswing/components/list.html#mutable"
// Add Task window
public class AddTaskView extends JFrame implements ActionListener {
    JTextField taskTitleField;
    JTextField taskDescriptionField;
    JTextField taskDueDateField;
    ListView listView;
    ToDoList toDoList;
    private static final String FINISH_ACTION = "FINISH_ACTION";

    // MODIFIES: this
    // EFFECTS: constructs addTask
    public AddTaskView(ListView listView, ToDoList toDoList) {
        super("Add a task");
        this.listView = listView;
        this.toDoList = toDoList;
        this.setWindow();
        this.setLabelsFieldsButtons();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    // MODIFIES: this
    // EFFECTS: displays menu of options to user and its text box
    private void setLabelsFieldsButtons() {
        JLabel taskTitleLabel = new JLabel("Enter task title: ");
        taskTitleLabel.setBounds(48, 40, 400, 20);
        add(taskTitleLabel);
        taskTitleLabel.setForeground(Color.darkGray);
        taskTitleField = new JTextField(30);
        taskTitleField.setBounds(50, 70, 300, 20);
        add(taskTitleField);

        JLabel taskDescriptionLabel = new JLabel("Enter task description: ");
        taskDescriptionLabel.setBounds(50, 100, 600, 20);
        add(taskDescriptionLabel);
        taskDescriptionLabel.setForeground(Color.darkGray);
        taskDescriptionField = new JTextField(30);
        taskDescriptionField.setBounds(50, 130, 300, 20);
        add(taskDescriptionField);

        JLabel taskDueDateLabel = new JLabel(
                "Enter task due date: (Date format should be yyyy-MM-dd)");
        taskDueDateLabel.setBounds(52, 160, 800, 20);
        add(taskDueDateLabel);
        taskDueDateLabel.setForeground(Color.darkGray);
        taskDueDateField = new JTextField(30);
        taskDueDateField.setBounds(50, 190, 300, 20);
        add(taskDueDateField);

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(330, 230, 300, 20);
        add(finishButton);
        finishButton.setActionCommand(FINISH_ACTION);
        finishButton.addActionListener(this);
        finishButton.setForeground(Color.darkGray);
    }

    // MODIFIES: this
    // EFFECTS: sets a addTask window
    private void setWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(700, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
    }

    // EFFECTS: prompts user to enter the required text in the box and closes the window after he/she finishes
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals(FINISH_ACTION)) {
            String title = taskTitleField.getText();
            String description = taskDescriptionField.getText();
            String dueDate = taskDueDateField.getText();
            toDoList.addTask(new Task(title, description, dueDate));
            listView.dispose();
            dispose();
            new ListView(toDoList);
        }
    }
}
