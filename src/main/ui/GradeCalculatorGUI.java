package ui;

// References:
// https://www.edureka.co/blog/java-swing/
// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
// https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/JPanel.html
// http://www.datadisk.co.uk/html_docs/java/layout_managers.htm
// http://www.java2s.com/Tutorial/Java/CatalogJava.htm

import model.ComponentCalculator;
import model.Component;
import model.Course;
import model.CourseCalculator;
import model.FinalGradeCalculator;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// Represents a GUI of Grade Calculator Application with
// add course, remove course, add component, add assignment buttons features
public class GradeCalculatorGUI extends JFrame {

    protected boolean loadButtonPressed = false;            // is load button pressed
    private JPanel courseDetailPanel;                       // course detail panel
    private JPanel mainContentPanel;                        // main content Panel
    protected FinalGradeCalculator finalGradeCalculator;    // final grade calculator
    protected List<Course> courseList;                      // list of courses
    protected DefaultListModel<Course> courseListModel;     // model item list
    protected JList<Course> courseJList;                    // courses JList
    private JTextField courseJName;                         // course JName
    private JTextField courseJTerm;                         // course JTerm
    private JTextField courseJSession;                      // course JSession


    // EFFECTS: constructs GradeCalculatorGUI
    public GradeCalculatorGUI() {
        loadButtonPressed = false;
        courseDetailPanel = new JPanel();
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));

        initializeGuiFields();
        createGui();
        createButtonPanel();
        setUpFrame();
        add(mainContentPanel, BorderLayout.WEST);

        courseJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = courseJList.locationToIndex(e.getPoint());
                if (selectedIndex != -1) {
                    Course selectedCourse = courseList.get(selectedIndex);
                    updateCourseDetailPanel(selectedCourse);
                }
            }
        });
    }

    // EFFECTS: returns course list
    public List<Course> getCourseList() {
        return courseList;
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields for GUI
    private void initializeGuiFields() {
        // list of courses fields:
        this.finalGradeCalculator = new FinalGradeCalculator();
        this.courseList = new ArrayList<>();
        finalGradeCalculator.setCourses(courseList);
        this.courseListModel = new DefaultListModel<>();
        this.courseJList = new JList<>(courseListModel);

        // fields of a course:
        this.courseJName = new JTextField();
        this.courseJTerm = new JTextField();
        this.courseJSession = new JTextField();

        Font textFieldFont = new Font("Futura", Font.PLAIN, 12);
        this.courseJName.setFont(textFieldFont);
        this.courseJTerm.setFont(textFieldFont);
        this.courseJSession.setFont(textFieldFont);
    }

    // MODIFIES: this
    // EFFECTS: creates GUI with input panel, scroll pane, button panel,
    //          fits and places them in certain regions; sets the color to yellow
    protected void createGui() {
        getContentPane().setBackground(new Color(242, 229, 179));

        JPanel inputPanel = createInputPanel();
        inputPanel.setBackground(new Color(242, 229, 179));

        JScrollPane scrollPane = new JScrollPane(courseJList);
        scrollPane.setPreferredSize(new Dimension(50, 0));
        scrollPane.getViewport().setBackground(new Color(242, 229, 179));

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(new Color(242, 229, 179));

        mainContentPanel.setOpaque(true);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(courseDetailPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    // EFFECTS: creates input panel for course information with image, course name, term, and session fields
    //          sets specified fonts for input fields
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        addImageLabel(inputPanel, gbc);

        Font labelFont = new Font("Futura", Font.PLAIN, 12);
        Font textFieldFont = new Font("Futura", Font.PLAIN, 12);

        addInputLabelAndField(inputPanel, gbc, "Course Name:", courseJName,
                labelFont, textFieldFont, 0, 0);
        addInputLabelAndField(inputPanel, gbc, "Term:", courseJTerm,
                labelFont, textFieldFont, 1, 0);
        addInputLabelAndField(inputPanel, gbc, "Session:", courseJSession,
                labelFont, textFieldFont, 2, 0);

        setColumnSizes();
        return inputPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds image label to input panel at grid position
    private void addImageLabel(JPanel inputPanel, GridBagConstraints gbc) {
        ImageIcon originalImageIcon = new ImageIcon("data/University.png");
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledImageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(imageLabel, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds label and text field for a specific input to input panel at grid position
    //          sets specified fonts for label and text field
    private void addInputLabelAndField(JPanel inputPanel, GridBagConstraints gbc, String labelText,
                                       JTextField textField, Font labelFont, Font textFieldFont,
                                       int gridX, int gridY) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        textField.setFont(textFieldFont);

        gbc.gridx = gridX;
        gbc.gridy = gridY + 1;
        inputPanel.add(label, gbc);

        gbc.gridx = gridX;
        gbc.gridy = gridY + 2;
        inputPanel.add(textField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: sets column sizes for course name, term, and session text fields
    private void setColumnSizes() {
        courseJName.setColumns(15);
        courseJTerm.setColumns(15);
        courseJSession.setColumns(15);
    }

    // EFFECTS: creates a panel with addCourseButton, addCourseComponentButton, and removeCourseButton
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createAddCourseButton());
        buttonPanel.add(createAddComponentButton());
        buttonPanel.add(createRemoveButton());

        return buttonPanel;
    }

    // EFFECTS: creates a button that adds courses
    //          with button icon and green colored outer color
    private JButton createAddCourseButton() {
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setFont(new Font("Futura", Font.PLAIN, 12));
        addCourseButton.setBackground(new Color(127, 141, 106));
        addCourseButton.setOpaque(true);
        addCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });
        return addCourseButton;
    }

    // EFFECTS: creates a button that adds a component for a certain course
    private JButton createAddComponentButton() {
        JButton addComponentButton = new JButton("Add Component");
        addComponentButton.setFont(new Font("Futura", Font.PLAIN, 12));
        addComponentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addComponentForSelectedCourse();
            }
        });
        return addComponentButton;
    }

    // EFFECTS: creates a red colored button that removes selected course
    private JButton createRemoveButton() {
        JButton removeButton = new JButton("Remove Course");
        removeButton.setFont(new Font("Futura", Font.PLAIN, 12));
        removeButton.setBackground(new Color(178, 112, 107));
        removeButton.setOpaque(true);

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCourse();
            }
        });
        return removeButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a frame with title, sets up its size, location, visibility,
    //          close operation shows a dialog to confirm the window closing
    private void setUpFrame() {
        setTitle("Grade Calculator");
        ImageIcon logoIcon = new ImageIcon("data/University.png");
        setIconImage(logoIcon.getImage());
        setPreferredSize(new Dimension(1000, 800));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
        pack();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showExitConfirmationDialog();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: shows an exit confirmation dialog, closes application if the user chooses "Yes"
    private void showExitConfirmationDialog() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?" + " Make sure to save your courses!",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a course with its fields to list of courses,
    //          clears all text fields once item was added to the list
    private void addCourse() {
        String courseName = courseJName.getText();
        int term = Integer.parseInt(courseJTerm.getText());
        String session = courseJSession.getText();

        Course c = new Course(courseName, term, session, new CourseCalculator());
        courseList.add(c);
        finalGradeCalculator.setCourses(courseList);
        courseListModel.addElement(c);

        courseJName.setText("");
        courseJTerm.setText("");
        courseJSession.setText("");

        updateCourseDetailPanel(c);
        updateMainContentPanel();
    }

    // REQUIRES: courseList should contain valid course,
    //           courseJList should display list of courses
    //           in the same order as courseList
    // MODIFIES: courseList, courseJList
    // EFFECTS: removes selected course from the GUI's courseJList and
    //          its data structure courseList
    private void removeCourse() {
        int selectedIndex = courseJList.getSelectedIndex();
        if (selectedIndex != -1) {
            Course removedItem = courseList.remove(selectedIndex);
            finalGradeCalculator.removeCourse(removedItem);
            courseListModel.remove(selectedIndex);
            updateMainContentPanel();
        }
    }

    // REQUIRES: select course by clicking on it
    // MODIFIES: this
    // EFFECTS: adds a component with its fields for a selected course
    private void addComponentForSelectedCourse() {
        int selectedIndex = courseJList.getSelectedIndex();
        if (selectedIndex != -1) {
            Course selectedCourse = courseList.get(selectedIndex);
            createAddComponentInputPanel(selectedCourse);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a course to add a component to.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for inputs of component fields as a JDialog
    private void createAddComponentInputPanel(Course selectedCourse) {
        JDialog inputDialog = new JDialog(this, "Add Component", true);
        JPanel inputPanel = createComponentInputPanel(selectedCourse, inputDialog);
        inputDialog.add(inputPanel);
        inputDialog.setSize(300, 200);
        inputDialog.setLocationRelativeTo(this);
        inputDialog.setVisible(true);
    }

    // EFFECTS: creates the input panel for adding a component
    private JPanel createComponentInputPanel(Course selectedCourse, JDialog inputDialog) {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        ImageIcon logoIcon = new ImageIcon("data/University.png");
        inputDialog.setIconImage(logoIcon.getImage());

        JTextField componentNameField = new JTextField();
        JTextField maxMarkField = new JTextField();
        JButton addComponentBtn = new JButton("Add Component");

        inputPanel.add(new JLabel("Component Name:"));
        inputPanel.add(componentNameField);
        inputPanel.add(new JLabel("Max Mark:"));
        inputPanel.add(maxMarkField);
        inputPanel.add(addComponentBtn);

        addComponentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddComponent(selectedCourse, componentNameField, maxMarkField, inputDialog);
            }
        });

        return inputPanel;
    }

    // MODIFIES: selectedCourse, this
    // EFFECTS: adds component to selected course, updates course detail panel,
    //          and displays input dialog
    private void handleAddComponent(Course selectedCourse, JTextField componentNameField,
                                    JTextField maxMarkField, JDialog inputDialog) {

        String componentName = componentNameField.getText();
        int maxMark = Integer.parseInt(maxMarkField.getText());

        Component component = new Component(componentName, maxMark, new ComponentCalculator());
        selectedCourse.getCourseCalculator().addCourseComponent(component);

        JLabel componentNameLabel = new JLabel("Component: " + component.getComponentName());
        JLabel maxMarkLabel = new JLabel("Max Mark: " + component.getMaxMark());

        courseDetailPanel.add(componentNameLabel);
        courseDetailPanel.add(maxMarkLabel);

        courseDetailPanel.revalidate();
        courseDetailPanel.repaint();
        updateCourseDetailPanel(selectedCourse);

        inputDialog.dispose();
    }

    // MODIFIES: this
    // EFFECTS: updates a course detail panel
    protected void updateCourseDetailPanel(Course course) {
        courseDetailPanel.removeAll();
        JPanel coursePanel = createCoursePanel(course);
        JPanel componentsPanel = createComponentsPanel(course);

        coursePanel.add(componentsPanel);
        courseDetailPanel.add(coursePanel);

        courseDetailPanel.revalidate();
        courseDetailPanel.repaint();
    }

    // EFFECTS: creates a panel for displaying components of a course
    private JPanel createComponentsPanel(Course course) {
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
        componentsPanel.add(new JLabel("Components:"));
        componentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        for (Component component : course.getCourseCalculator().getComponents()) {
            JPanel componentPanel = new JPanel();
            componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

            JLabel componentNameLabel = new JLabel("Component: " + component.getComponentName());
            JLabel maxMarkLabel = new JLabel("Max Mark: " + component.getMaxMark());

            componentPanel.add(componentNameLabel);
            componentPanel.add(maxMarkLabel);

            componentsPanel.add(componentPanel);
        }
        return componentsPanel;
    }

    // MODIFIES: this:
    // EFFECTS: updates the main content panel
    private void updateMainContentPanel() {
        mainContentPanel.removeAll();

        for (Course course : courseList) {
            mainContentPanel.add(createCoursePanel(course));
            mainContentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // EFFECTS: creates a course panel
    protected JPanel createCoursePanel(Course course) {
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        coursePanel.setBorder(BorderFactory.createTitledBorder(border, course.getName()));

        JLabel courseDetailsLabel = new JLabel("Term " + course.getTerm() + ", Session " + course.getSession());
        JLabel courseNameLabel = new JLabel("Name: " + course.getName());

        coursePanel.add(courseNameLabel);
        coursePanel.add(courseDetailsLabel);

        return coursePanel;
    }

}
