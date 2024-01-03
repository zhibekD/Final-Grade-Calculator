package persistence;

import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FinalGradeCalculator fgc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFinalGradeCalculator() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFinalGradeCalculator.json");
        try {
            FinalGradeCalculator fgc = reader.read();
            assertEquals(0, fgc.getNumOfCourses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFinalGradeCalculator() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFinalGradeCalculator.json");
        try {
            FinalGradeCalculator fgc = reader.read();
            List<Course> courses = fgc.getCourses();
            assertEquals(2, courses.size());

            checkCourse(courses.get(0), "cs", 1, "2023W", new CourseCalculator());
            checkCourse(courses.get(1), "math", 2, "2023S", new CourseCalculator());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCourseWithComponentsAndAssignments() {
        JsonReader reader = new JsonReader("./data/testReaderCourseWithComponentsAndAssignments.json");
        try {
            FinalGradeCalculator fgc = reader.read();
            List<Course> courses = fgc.getCourses();
            assertEquals(1, courses.size());

            Course course = courses.get(0);
            assertEquals("cs101", course.getName());
            assertEquals(1, course.getTerm());
            assertEquals("2023W", course.getSession());

            CourseCalculator courseCalculator = course.getCourseCalculator();
            assertEquals(2, courseCalculator.getComponents().size());

            Component component1 = courseCalculator.getComponents().get(0);
            assertEquals("Labs", component1.getComponentName());
            assertEquals(100, component1.getMaxMark());
            assertEquals(2, component1.getComponentCalculator().getAssignments().size());

            Component component2 = courseCalculator.getComponents().get(1);
            assertEquals("Exams", component2.getComponentName());
            assertEquals(100, component2.getMaxMark());
            assertEquals(2, component2.getComponentCalculator().getAssignments().size());

            // For component 1 assignments:
            List<Assignment> assignments1 = component1.getComponentCalculator().getAssignments();

            assertEquals(2, assignments1.size());
            assertEquals("Lab 1", assignments1.get(0).getName());
            assertEquals(2, assignments1.get(0).getGrade());
            assertEquals(2, assignments1.get(0).getMaxMark());

            assertEquals("Lab 2", assignments1.get(1).getName());
            assertEquals(20, assignments1.get(1).getGrade());
            assertEquals(50, assignments1.get(1).getMaxMark());


            // For component 2 assignments:
            List<Assignment> assignments2 = component2.getComponentCalculator().getAssignments();

            assertEquals(2, assignments2.size());
            assertEquals("Midterm", assignments2.get(0).getName());
            assertEquals(40, assignments2.get(0).getGrade());
            assertEquals(45, assignments2.get(0).getMaxMark());

            assertEquals("Final Exam", assignments2.get(1).getName());
            assertEquals(40, assignments2.get(1).getGrade());
            assertEquals(50, assignments2.get(1).getMaxMark());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
