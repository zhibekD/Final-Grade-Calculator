//package ui;
//
//import model.*;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//
//// Represents Grade Calculator Application
//public class GradeCalculatorApp {
//    private static final String JSON_STORE = "./data/finalGradeCalculator.json";    // Json store location
//    private JsonWriter jsonWriter;                                                  // Json Writer
//    private JsonReader jsonReader;                                                  // Json Reader
//    private FinalGradeCalculator finalGradeCalculator;                              // final grade calculator
//    private Scanner scanner;                                                        // scanner
//
//    // EFFECTS: initializes a Grade Calculator Application
//    public GradeCalculatorApp() throws FileNotFoundException {
//        finalGradeCalculator = new FinalGradeCalculator();
//        scanner = new Scanner(System.in);
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runFinalGradeCalculator();
//    }
//
//    // EFFECTS: runs the application
//    public void runFinalGradeCalculator() {
//        boolean keepGoing = true;
//
//        while (keepGoing) {
//            displayMenu();
//            String command = scanner.nextLine().toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nThank you for using this application!");
//    }
//
//    // EFFECTS: displays the menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("a -> Add a course");
//        System.out.println("r -> Remove a course");
//        System.out.println("v -> View all courses");
//        System.out.println("sh -> Sort courses from highest to lowest final grades");
//        System.out.println("sl -> Sort courses from highest to lowest final grades");
//        System.out.println("f -> Filter courses by session");
//        System.out.println("gpa -> Calculate my average for a certain session");
//        System.out.println("ac -> Add a new component with assignments to existing course");
//        System.out.println("s -> Save data");
//        System.out.println("l -> Load data");
//        System.out.println("q -> Quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            addCourse();
//        } else if (command.equals("r")) {
//            removeCourse();
//        } else if (command.equals("v")) {
//            viewAllCourses();
//        } else if (command.equals("sh")) {
//            sortFromHighest();
//        } else if (command.equals("sl")) {
//            sortFromLowest();
//        } else if (command.equals("f")) {
//            filterCoursesBySession();
//        } else if (command.equals("gpa")) {
//            calculateGPA();
//        } else if (command.equals("ac")) {
//            System.out.println("Enter the name of the course to which you want to add a new component:");
//            String selectedCourseName = scanner.nextLine();
//            addComponentToSelectedCourse(selectedCourseName);
//        } else if (command.equals("s")) {
//            saveFinalGradeCalculator();
//        } else if (command.equals("l")) {
//            loadFinalGradeCalculator();
//        }
//    }
//
//    // REQUIRES: name to be non-zero string, grade =< max mark,
//    //           maxMark of component  > max mark > 0
//    // MODIFIES: this
//    // EFFECTS: adds an assignment to the list of assignments for a component, asks whether
//    //          more assignments want to be added, if yes, add one more assignment and asks again,
//    //          if no, ends the loop
//    private void addAssignment(Component comp, ComponentCalculator componentCalculator) {
//        boolean addMoreAssignments = true;
//
//        while (addMoreAssignments) {
//            System.out.println("Enter the name of assignment you want to add:");
//            String name = scanner.nextLine();
//            System.out.println("Enter the grade earned for this assignment:");
//            int grade = scanner.nextInt();
//            System.out.println("Enter the max mark possible for this assignment:");
//            int maxMark = scanner.nextInt();
//            scanner.nextLine();
//
//            Assignment assignment = new Assignment(name, grade, maxMark);
//            componentCalculator.addAssignment(assignment);
//
//            System.out.println("Assignment added successfully!");
//
//            System.out.println("Do you want to add more assignments? (yes/no)");
//            String response = scanner.nextLine().toLowerCase();
//            addMoreAssignments = response.equals("yes");
//        }
//    }
//
//    // REQUIRES: name to be non-zero string,  100 > max mark > 0
//    // MODIFIES: this
//    // EFFECTS: adds a component to the list of components for a course
//    private void addComponent(Course c, CourseCalculator courseCalculator) {
//        System.out.println("Enter the name of a course component you want to add:");
//        String name = scanner.nextLine();
//        System.out.println("Enter the max mark possible for this component:");
//        int maxMark = scanner.nextInt();
//        scanner.nextLine();
//
//        ComponentCalculator componentCalculator = new ComponentCalculator();
//
//        Component component = new Component(name, maxMark, componentCalculator);
//
//        courseCalculator.addCourseComponent(component);
//        addAssignment(component, componentCalculator);
//    }
//
//    // REQUIRES: name and session to be non-zero strings, term to be either 1 or 2
//    // MODIFIES: this
//    // EFFECTS: adds a course to the list of courses
//    private void addCourse() {
//        System.out.println("Enter the name of a course:");
//        String name = scanner.nextLine();
//        System.out.println("Enter the term a course is taken:");
//        int term = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("Enter the session the course is taken (e.g: 2023W):");
//        String session = scanner.nextLine();
//
//
//        CourseCalculator courseCalculator = new CourseCalculator();
//        Course course = new Course(name, term, session, courseCalculator);
//
//        finalGradeCalculator.addCourse(course);
//
//        addComponent(course, courseCalculator);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes a course to the list of courses
//    public void removeCourse() {
//        System.out.println("Enter the name of the course you want to remove:");
//        String name = scanner.nextLine();
//
//        Course toRemove = null;
//        for (Course c : finalGradeCalculator.getCourses()) {
//            if (c.getName().equals(name)) {
//                toRemove = c;
//            }
//        }
//
//        if (toRemove != null) {
//            finalGradeCalculator.removeCourse(toRemove);
//        } else {
//            System.out.println("Course not found!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: shows the list of courses so far, with name, term, session, and final grade calculated do far
//    private void viewAllCourses() {
//        List<Course> courses = finalGradeCalculator.getCourses();
//
//        if (courses.isEmpty()) {
//            System.out.println("No courses recorded");
//        } else {
//            for (Course c : courses) {
//                System.out.println(c.getName() + ": " + c.getTerm()
//                        + " term. Session: " + c.getSession() + ",  Final grade: " + c.getFinalGrade());
//            }
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a new component to an exiting course
//    private void addComponentToSelectedCourse(String selectedCourseName) {
//        Course existingCourse = findCourseByName(selectedCourseName);
//        if (existingCourse != null) {
//            addComponentWithAssignments(existingCourse);
//        } else {
//            System.out.println("Course not found!");
//        }
//    }
//
//    // REQUIRES: course names cannot be similar
//    // EFFECTS: find the course by its name and returns the course,
//    //          if not found, returns null
//    private Course findCourseByName(String courseName) {
//        for (Course course : finalGradeCalculator.getCourses()) {
//            if (course.getName().equalsIgnoreCase(courseName)) {
//                return course;
//            }
//        }
//        return null;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a new component to an existing course
//    private void addComponentWithAssignments(Course existingCourse) {
//        System.out.println("Enter the name for a new component:");
//        String componentName = scanner.nextLine();
//        System.out.println("Enter the max mark possible for this component:");
//        int maxMark = scanner.nextInt();
//        scanner.nextLine();
//
//        ComponentCalculator componentCalculator = new ComponentCalculator();
//        Component component = new Component(componentName, maxMark, componentCalculator);
//
//        addAssignmentsToComponent(component, componentCalculator);
//
//        existingCourse.getCourseCalculator().addCourseComponent(component);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds assignments to the new component added to an existing course
//    private void addAssignmentsToComponent(Component component, ComponentCalculator componentCalculator) {
//        addAssignment(component, componentCalculator);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts the list of courses from its highest to lowest final grades
//    private void sortFromHighest() {
//        List<Course> courses = finalGradeCalculator.getCourses();
//        finalGradeCalculator.sortCoursesByFinalGradeHighest(courses);
//        System.out.println("Courses sorted from highest to lowest final grades:");
//        displaySortedCourses(courses);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts the list of courses from its lowest to highest final grades
//    private void sortFromLowest() {
//        List<Course> courses = finalGradeCalculator.getCourses();
//        finalGradeCalculator.sortCoursesByFinalGradeLowest(courses);
//        System.out.println("Courses sorted from lowest to highest final grades:");
//        displaySortedCourses(courses);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays a sorted list of courses with its name, term, session and final grade calculated so far
//    private void displaySortedCourses(List<Course> courses) {
//        if (courses.isEmpty()) {
//            System.out.println("No courses recorded");
//        } else {
//            for (Course c : courses) {
//                System.out.println(c.getName() + ": " + c.getTerm()
//                        + " term. Session: " + c.getSession() + ",  Final grade: " + c.getFinalGrade());
//            }
//        }
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays and filters a list of courses by specified session
//    private void filterCoursesBySession() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the session you want to filter (e.g., 2023W):");
//        String session = scanner.nextLine();
//
//        List<Course> filteredCourses = finalGradeCalculator.filterCoursesBySession(session);
//
//        if (filteredCourses.isEmpty()) {
//            System.out.println("No courses found for the specified session.");
//        } else {
//            System.out.println("Courses for session " + session + ":");
//            for (Course course : filteredCourses) {
//                System.out.println(course.getName() + ", Term: " + course.getTerm()
//                        + ", Session: " + course.getSession());
//            }
//        }
//    }
//
//
//    // REQUIRES: session to be non-zero string
//    // MODIFIES: this
//    // EFFECTS: calculates and displays the final grade (average from all courses) for a specified session
//    private void calculateGPA() {
//        List<Course> courses = finalGradeCalculator.getCourses();
//        if (courses.isEmpty()) {
//            System.out.println("No courses recorded");
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the session for which you want to calculate GPA (e.g: 2023W):");
//        String session = scanner.nextLine();
//
//        int gpa = finalGradeCalculator.calculateAverageForSession(session);
//
//        if (gpa != 0) {
//            System.out.println("GPA for session " + session + ": " + gpa + "%");
//        } else {
//            System.out.println("No courses found for the specified session.");
//        }
//    }
//
//    // EFFECTS: saves the final grade calculator to file
//    private void saveFinalGradeCalculator() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(finalGradeCalculator);
//            jsonWriter.close();
//            System.out.println("Saved " + "final grade calculator" + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads final grade calculator from file
//    private void loadFinalGradeCalculator() {
//        try {
//            finalGradeCalculator = jsonReader.read();
//            System.out.println("Loaded " + "final grade calculator" + " from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}
