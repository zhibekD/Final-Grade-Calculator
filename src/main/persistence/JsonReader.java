package persistence;

import model.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads final grade calculator from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads final grade calculator from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public FinalGradeCalculator read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFinalGradeCalculator(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        EventLog.getInstance().logEvent(new Event("Courses loaded"));
        return contentBuilder.toString();
    }

    // EFFECTS: parses a final grade calculator from Json object and returns it
    private FinalGradeCalculator parseFinalGradeCalculator(JSONObject jsonObject) {
        FinalGradeCalculator finalGradeCalculator = new FinalGradeCalculator();
        JSONArray coursesArray = jsonObject.getJSONArray("listOfCourses");

        for (Object courseObj : coursesArray) {
            JSONObject courseJson = (JSONObject) courseObj;
            Course course = parseCourse(courseJson);
            finalGradeCalculator.addCourse(course);
        }
        return finalGradeCalculator;
    }

    // EFFECTS: parses a course from JSON object and returns it
    private Course parseCourse(JSONObject jsonObject) {
        String courseName = jsonObject.getString("courseName");
        int term = jsonObject.getInt("term");
        String session = jsonObject.getString("session");
        CourseCalculator courseCalculator = parseCourseCalculator(jsonObject.getJSONObject("courseCalculator"));
        Course course = new Course(courseName, term, session, courseCalculator);
        return course;
    }


    // EFFECTS: parses a course calculator from JSON object and returns it
    private CourseCalculator parseCourseCalculator(JSONObject jsonObject) {
        CourseCalculator courseCalculator = new CourseCalculator();
        JSONArray componentsArray = jsonObject.getJSONArray("components");

        for (Object componentObj : componentsArray) {
            JSONObject componentJson = (JSONObject) componentObj;
            Component component = parseComponent(componentJson);
            courseCalculator.addCourseComponent(component);
        }

        return courseCalculator;
    }

    // EFFECTS: parses a component from JSON object and returns it
    private Component parseComponent(JSONObject jsonObject) {
        String componentName = jsonObject.getString("componentName");
        int maxMark = jsonObject.getInt("maxMark");
        ComponentCalculator componentCalculator =
                parseComponentCalculator(jsonObject.getJSONObject("componentCalculator"));
        Component component = new Component(componentName, maxMark, componentCalculator);
        return component;
    }

    // EFFECTS: parses component calculator from JSON object and returns it
    private ComponentCalculator parseComponentCalculator(JSONObject jsonObject) {
        ComponentCalculator componentCalculator = new ComponentCalculator();
        JSONArray assignmentsArray = jsonObject.getJSONArray("assignments");

        for (Object assignmentObj : assignmentsArray) {
            JSONObject assignmentJson = (JSONObject) assignmentObj;
            Assignment assignment = parseAssignment(assignmentJson);
            componentCalculator.addAssignment(assignment);
        }

        return componentCalculator;
    }

    // EFFECTS: parses an assignment from JSON object and returns it
    private Assignment parseAssignment(JSONObject jsonObject) {
        String assignmentName = jsonObject.getString("assignmentName");
        int grade = jsonObject.getInt("grade");
        int maxMark = jsonObject.getInt("maxMark");
        return new Assignment(assignmentName, grade, maxMark);
    }
}
