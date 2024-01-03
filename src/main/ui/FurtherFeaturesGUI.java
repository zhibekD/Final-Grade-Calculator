package ui;

import model.Course;
import model.FinalGradeCalculator;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Represents a GUI of Grade Calculator Application with further features such as
// save courses, load courses, filtering for session
public class FurtherFeaturesGUI extends GradeCalculatorGUI {

    private static final String JSON_STORE = "./data/finalGradeCalculator.json";  // Json store location
    private JPanel buttonInputPanel;                                              // Filter Panel
    private JsonWriter jsonWriter;                                                // Json Writer
    private JsonReader jsonReader;                                                // Json Reader
    private JButton saveButton;                                                   // Save Button
    private JButton loadButton;                                                   // Load Button
    private JButton filterButton;                                                 // Filter Button

    // EFFECTS: constructs FurtherFeaturesGUI, throws FileNotFoundException
    //          if file was not found while loading
    public FurtherFeaturesGUI() throws FileNotFoundException {
        super();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadCoursesFromJson(JSON_STORE);
        initializeSaveLoadButtons();
        initializeFilterButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes a button panel for save and load buttons
    protected void initializeSaveLoadButtons() {
        buttonInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon originalIcon = new ImageIcon("data/University.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        buttonInputPanel.add(imageLabel);
        initializeSaveButton();
        initializeLoadButton();
        add(buttonInputPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: initializes the saveButton and locates it on the left side
    private void initializeSaveButton() {
        saveButton = new JButton("Save to JSON");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveCoursesToJson(JSON_STORE);
            }
        });
        saveButton.setFont(new Font("Futura", Font.PLAIN, 12));
        JPanel buttonPanel = createButtonPanel(saveButton);
        buttonInputPanel.add(buttonPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: initializes the loadButton and locates it on the left side
    private void initializeLoadButton() {
        loadButton = new JButton("Load from JSON");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadButtonPressed = true;
                loadCoursesFromJson(JSON_STORE);
            }
        });

        loadButton.setFont(new Font("Futura", Font.PLAIN, 12));
        JPanel buttonPanel = createButtonPanel(loadButton);
        buttonInputPanel.add(buttonPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for buttons
    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(button);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: saves list of courses to JSON file
    private void saveCoursesToJson(String filePath) {
        try {
            jsonWriter.open();

            if (!loadButtonPressed) {
                finalGradeCalculator.setCourses(getCourseList());
            } else {
                finalGradeCalculator = new FinalGradeCalculator();
            }

            jsonWriter.write(finalGradeCalculator);
            jsonWriter.close();

            System.out.println(finalGradeCalculator.getCourses());
            System.out.println("Saved " + "final grade calculator" + " to " + JSON_STORE);

            JOptionPane.showMessageDialog(this,
                    "Courses saved successfully!");
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads list of courses from JSON file
    private void loadCoursesFromJson(String filePath) {
        if (loadButtonPressed) {
            try {
                FinalGradeCalculator loadedCalculator = jsonReader.read();
                List<Course> loadedCourses = loadedCalculator.getCourses();

                courseList.clear();
                courseListModel.removeAllElements();
                courseList.addAll(loadedCourses);
                finalGradeCalculator.setCourses(courseList);

                for (Course loadedCourse : loadedCourses) {
                    updateCourseDetailPanel(loadedCourse);
                    courseListModel.addElement(loadedCourse);
                }

                courseJList.revalidate();
                courseJList.repaint();
                JOptionPane.showMessageDialog(this,
                        "Courses loaded from JSON file successfully!");
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
                e.printStackTrace();
            } finally {
                // reset loadButtonPressed
                loadButtonPressed = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes filter button and locates it on left side
    private void initializeFilterButton() {
        filterButton = new JButton("Filter by Session");

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFilterDialog();
            }
        });

        filterButton.setFont(new Font("Futura", Font.PLAIN, 12));
        JPanel buttonPanel = createButtonPanel(filterButton);
        buttonInputPanel.add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: shows the filter dialog for session input
    private void showFilterDialog() {
        JDialog filterDialog = new JDialog(this, "Filter Courses", true);
        JPanel filterPanel = createFilterPanel(filterDialog);
        filterDialog.add(filterPanel);
        filterDialog.setSize(300, 200);
        filterDialog.setLocationRelativeTo(this);
        filterDialog.setVisible(true);
    }

    // EFFECTS: creates the filter panel with input fields
    private JPanel createFilterPanel(JDialog filterDialog) {
        JPanel filterPanel = new JPanel(new GridLayout(3, 2));
        JTextField sessionField = new JTextField();

        filterPanel.add(new JLabel("Session:"));
        filterPanel.add(sessionField);

        JButton applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String session = sessionField.getText();
                filterCoursesAndDisplay(session);
                filterDialog.dispose();
            }
        });

        filterPanel.add(new JLabel());
        filterPanel.add(applyFilterButton);

        return filterPanel;
    }

    // MODIFIES: this
    // EFFECTS: filters courses by session and updates display
    private void filterCoursesAndDisplay(String session) {
        List<Course> filteredCourses = finalGradeCalculator.filterCoursesBySession(session);

        courseListModel.clear();
        for (Course filteredCourse : filteredCourses) {
            courseListModel.addElement(filteredCourse);
            updateCourseDetailPanel(filteredCourse);
        }

        courseJList.revalidate();
        courseJList.repaint();
    }

}
