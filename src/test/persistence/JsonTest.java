package persistence;

import model.Course;
import model.CourseCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    public void checkCourse(Course c, String name, int term, String session, CourseCalculator cc) {
        assertEquals(c.getName(), name);
        assertEquals(c.getTerm(), term);
        assertEquals(c.getSession(), session);
        assertEquals(c.getCourseCalculator(), cc);
    }
}
