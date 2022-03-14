package ui;

import model.Event;
import model.EventLog;
import model.ToDoList;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// This class references code from
// "https://www.tabnine.com/code/java/classes/javax.swing.JFrame"
// To-do list's main view
public class ToDoListUI extends JFrame implements ActionListener {
    private static final int POSITION = 100;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 20;
    private static final String VIEW_LIST_ACTION = "VIEW_LIST_ACTION";
    private static final String EMPTY_LIST_ACTION = "EMPTY_LIST_ACTION";
    private static final String LOAD_APP_ACTION = "LOAD_APP_ACTION";
    private static final String QUIT_APP_ACTION = "QUIT_APP_ACTION";
    private ToDoList toDoList = new ToDoList("My To-Do List");
    private ListView listView;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/toDoList.json";

    // MODIFIES: this
    // EFFECTS: constructs to-do list's graphic user interface
    public ToDoListUI() throws IOException {
        super("To-do List Application");
        this.setWindow();
        this.setBackground();
        this.setUpLabelsAndButtons();
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // This method references code from ButtonDemo
    // "https://docs.oracle.com/javase/tutorial/uiswing/components/button.html"
    // MODIFIES: this
    // EFFECTS: displays menu of options to user
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setUpLabelsAndButtons() {
        JLabel selectOptionLabel = new JLabel("Please select an option: ", JLabel.CENTER);
        selectOptionLabel.setBounds(26, 10, 300, 20);
        add(selectOptionLabel);
        selectOptionLabel.setForeground(Color.black);

        JButton viewListButton = new JButton("View my to-do list");
        viewListButton.setBounds(POSITION, 40, WIDTH, HEIGHT);
        add(viewListButton);
        viewListButton.setActionCommand(VIEW_LIST_ACTION);
        viewListButton.addActionListener(this);
        viewListButton.setForeground(Color.black);

        JButton emptyListButton = new JButton("Empty list");
        emptyListButton.setBounds(POSITION, 80, WIDTH, HEIGHT);
        add(emptyListButton);
        emptyListButton.setActionCommand(EMPTY_LIST_ACTION);
        emptyListButton.addActionListener(this);
        emptyListButton.setForeground(Color.black);

        JButton loadAppButton = new JButton("Load");
        loadAppButton.setBounds(POSITION, 120, WIDTH, HEIGHT);
        add(loadAppButton);
        loadAppButton.setActionCommand(LOAD_APP_ACTION);
        loadAppButton.addActionListener(this);
        loadAppButton.setForeground(Color.black);

        JButton quitAppButton = new JButton("Quit");
        quitAppButton.setBounds(POSITION, 160, WIDTH, HEIGHT);
        add(quitAppButton);
        quitAppButton.setActionCommand(QUIT_APP_ACTION);
        quitAppButton.addActionListener(this);
        quitAppButton.setForeground(Color.black);
    }

    // MODIFIES: this
    // EFFECTS: sets main window
    private void setWindow() {
        setPreferredSize(new Dimension(500, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
    }

    // This method references code from
    // "https://stackoverflow.com/questions/15493219/how-to-set-jframe-background-image-in-grouplayout-java"
    // MODIFIES: this
    // EFFECTS: sets background image
    public void setBackground() {
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/main/ui/background .jpeg"));
            setContentPane(new Background(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // EFFECTS: prompts user to select an option and performs the selected action
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals(LOAD_APP_ACTION)) {
            jsonReader = new JsonReader(JSON_STORE);
            try {
                toDoList = jsonReader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals(VIEW_LIST_ACTION)) {
            listView = new ListView(toDoList);
        } else if (action.equals(EMPTY_LIST_ACTION)) {
            toDoList.emptyList();
        } else if (action.equals(QUIT_APP_ACTION)) {
            EventLog log = EventLog.getInstance();
            for (Event event1 : log) {
                System.out.println(event1);
            }
            listView.dispose();
            dispose();
            System.exit(0);
        }
    }
}
