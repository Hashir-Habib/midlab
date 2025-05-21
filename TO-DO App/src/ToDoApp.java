import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JTextField titleField, descField;
    private List<Task> taskList;

    // === MODEL ===
    static class Task {
        String title;
        String description;
        boolean completed;

        Task(String title, String description) {
            this.title = title;
            this.description = description;
            this.completed = false;
        }

        @Override
        public String toString() {
            return (completed ? "[✓] " : "[ ] ") + title + " - " + description;
        }

        public void markCompleted() {
            this.completed = true;
        }
    }

    public ToDoApp() {
        setTitle("To-Do App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new ArrayList<>();

        // ==== TOP PANEL (INPUT) ====
        JPanel inputPanel = new JPanel(new GridLayout(5, 1));
        titleField = new JTextField();
        descField = new JTextField();
        JButton addButton = new JButton("Add Task");

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // ==== CENTER (TASK LIST) ====
        final JList<String> taskJList = new JList<>(taskListModel);
        add(new JScrollPane(taskJList), BorderLayout.CENTER);

        // ==== BOTTOM (MARK COMPLETE) ====
        JButton completeButton = new JButton("Mark Selected as Complete");
        add(completeButton, BorderLayout.SOUTH);

        // ==== ACTIONS ====
        addButton.addActionListener((ActionEvent e) -> {
            String title1 = titleField.getText().trim();
            String desc = descField.getText().trim();
            if (!title1.isEmpty()) {
                Task task = new Task(title1, desc);
                taskList.add(task);
                taskListModel.addElement(task.toString());
                titleField.setText("");
                descField.setText("");
            } else {
                JOptionPane.showMessageDialog(ToDoApp.this, "Title is required!");
            }
        });

        completeButton.addActionListener((ActionEvent e) -> {
            int selected = taskJList.getSelectedIndex();
            if (selected >= 0 && selected < taskList.size()) {
                taskList.get(selected).markCompleted();
                taskListModel.set(selected, taskList.get(selected).toString());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoApp toDoApp = new ToDoApp();
        });
    }
}
