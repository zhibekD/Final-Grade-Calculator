package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            FinalGradeCalculator st = new FinalGradeCalculator();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterIOException() {
        try {
            FinalGradeCalculator fgc = new FinalGradeCalculator();
            fgc.addCourse(new Course("English", 1, "2023W", new CourseCalculator()));

            JsonWriter writer = new JsonWriter("/readonly/testWriterIOException.json");
            writer.open();
            writer.write(fgc);
            writer.close();

            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFinalGradeCalculator() {
        try {
            FinalGradeCalculator fgc = new FinalGradeCalculator();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFinalGradeCalculator.json");
            writer.open();
            writer.write(fgc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFinalGradeCalculator.json");
            fgc = reader.read();
            assertEquals(0, fgc.getCourses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralWorkroom() {
        try {
            FinalGradeCalculator fgc = new FinalGradeCalculator();
            CourseCalculator cc1 = new CourseCalculator();
            CourseCalculator cc2 = new CourseCalculator();

            fgc.addCourse(new Course("cs", 1, "2023W", cc1));
            fgc.addCourse(new Course("math", 2, "2023S", cc2));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFinalGradeCalculator.json");
            writer.open();
            writer.write(fgc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFinalGradeCalculator.json");
            fgc = reader.read();
            List<Course> courses = fgc.getCourses();
            assertEquals(2, courses.size());

            checkCourse(courses.get(0), "cs", 1, "2023W", cc1);
            checkCourse(courses.get(1), "math", 2, "2023S", cc2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterDeepStructure() {
        try {
            // Creating a deep structure with assignments, components, and courses
            Assignment assignment1 = new Assignment("Assignment1", 90, 100);
            Assignment assignment2 = new Assignment("Assignment2", 85, 100);

            ComponentCalculator componentCalculator = new ComponentCalculator();
            componentCalculator.addAssignment(assignment1);
            componentCalculator.addAssignment(assignment2);

            Component component = new Component("Assignments", 100, componentCalculator);

            CourseCalculator courseCalculator = new CourseCalculator();
            courseCalculator.addCourseComponent(component);

            Course course = new Course("Computer Science", 1, "2023W", courseCalculator);

            FinalGradeCalculator finalGradeCalculator = new FinalGradeCalculator();
            finalGradeCalculator.addCourse(course);

            // Write the deep structure to a file
            JsonWriter writer = new JsonWriter("./data/testWriterDeepStructure.json");
            writer.open();
            writer.write(finalGradeCalculator);
            writer.close();

            // Read the file back and check the deep structure
            JsonReader reader = new JsonReader("./data/testWriterDeepStructure.json");
            finalGradeCalculator = reader.read();
            assertEquals(1, finalGradeCalculator.getCourses().size());

            Course readCourse = finalGradeCalculator.getCourses().get(0);
            assertEquals("Computer Science", readCourse.getName());
            assertEquals(1, readCourse.getTerm());
            assertEquals("2023W", readCourse.getSession());

            CourseCalculator readCourseCalculator = readCourse.getCourseCalculator();
            assertEquals(1, readCourseCalculator.getComponents().size());

            Component readComponent = readCourseCalculator.getComponents().get(0);
            assertEquals("Assignments", readComponent.getComponentName());
            assertEquals(100, readComponent.getMaxMark());

            ComponentCalculator readComponentCalculator = readComponent.getComponentCalculator();
            assertEquals(2, readComponentCalculator.getAssignments().size());

            Assignment readAssignment1 = readComponentCalculator.getAssignments().get(0);
            assertEquals("Assignment1", readAssignment1.getName());
            assertEquals(90, readAssignment1.getGrade());
            assertEquals(100, readAssignment1.getMaxMark());

            Assignment readAssignment2 = readComponentCalculator.getAssignments().get(1);
            assertEquals("Assignment2", readAssignment2.getName());
            assertEquals(85, readAssignment2.getGrade());
            assertEquals(100, readAssignment2.getMaxMark());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
