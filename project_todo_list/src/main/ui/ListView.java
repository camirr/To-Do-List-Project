package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// This class references code from TableDemo
// "https://docs.oracle.com/javase/tutorial/uiswing/components/table.html"
// View my to-do list window
public class ListView extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private ToDoList toDoList;
    private static final String ADD_ITEM = "ADD_ITEM";
    private static final String MARK_AS_DONE = "MARK_AS_DONE";
    private static final String SAVE = "SAVE";
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/toDoList.json";

    // MODIFIES: this
    // EFFECTS: constructs listView
    public ListView(ToDoList toDoList) {
        this.toDoList = toDoList;
        jsonWriter = new JsonWriter(JSON_STORE);
        this.setBackground();
        final String[] columnLabels = new String[]{
                "Index",
                "Title",
                "Description",
                "Due Date",
                "Status"
        };
        tableModel = new DefaultTableModel(null, columnLabels) {
        };
        table = new JTable(tableModel);
        this.tableRows();

        add(new JScrollPane(table));
        this.setButtons();
        setTitle("My To-Do List");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets option buttons, adding a new task or marking a task as done
    private void setButtons() {
        JButton addItemButton = new JButton(("Add"));
        add(addItemButton);
        addItemButton.setActionCommand(ADD_ITEM);
        addItemButton.addActionListener(this);
        addItemButton.setForeground(Color.darkGray);

        JButton markItemAsDoneButton = new JButton("Check");
        add(markItemAsDoneButton);
        markItemAsDoneButton.setActionCommand(MARK_AS_DONE);
        markItemAsDoneButton.addActionListener(this);
        markItemAsDoneButton.setForeground(Color.darkGray);

        JButton saveButton = new JButton("Save");
        add(saveButton);
        saveButton.setActionCommand(SAVE);
        saveButton.addActionListener(this);
        saveButton.setForeground(Color.darkGray);
    }

    // EFFECTS: displays to-so list table rows
    private void tableRows() {
        for (int i = 0; i < toDoList.size(); i++) {
            Task task = toDoList.getTask(i);
            Object[] tableRow = new Object[]{
                    i,
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getStatus().name()
            };
            tableModel.addRow(tableRow);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets background image
    private void setBackground() {
        try {
            BufferedImage background = ImageIO.read(new File("src/main/ui/background .jpeg"));
            setContentPane(new Background(background));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: prompts user to select an option and performs the selected action
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals(ADD_ITEM)) {
            new AddTaskView(this, toDoList);
        } else if (action.equals(MARK_AS_DONE)) {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(null, "Please select a task to mark as done.");
                return;
            }
            toDoList.markAsDone(selectedRowIndex);
            table.setValueAt((Object) toDoList.getTask(selectedRowIndex).getStatus().name(),
                    selectedRowIndex,
                    4);
        } else if (action.equals(SAVE)) {
            try {
                jsonWriter.open();
                jsonWriter.write(toDoList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Your list has been saved!");
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
            dispose();
        }
    }

}
