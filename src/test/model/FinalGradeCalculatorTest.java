package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalGradeCalculatorTest {

    private Assignment a1;
    private Assignment a2;
    private Assignment a3;
    private Assignment a4;

    private Component comp1;
    private Component comp2;
    private Component comp3;

    private Course c1;
    private Course c2;
    private Course c3;
    private Course c4;

    private ComponentCalculator componentCalcLabs;
    private ComponentCalculator componentCalcMidterm;
    private ComponentCalculator componentCalcAssignment;

    private CourseCalculator courseCalcCS210;
    private CourseCalculator courseCalcCS121;
    private CourseCalculator courseCalcMath;
    private CourseCalculator courseCalcStat;

    private FinalGradeCalculator finalGradeCalculator;
    private List<Course> courses;

    @BeforeEach
    void runBefore() {
        this.componentCalcLabs = new ComponentCalculator();
        this.componentCalcMidterm = new ComponentCalculator();
        this.componentCalcAssignment = new ComponentCalculator();

        this.a1 = new Assignment("Lab1", 5, 6);
        this.a2 = new Assignment("Midterm", 20, 20);
        this.a3 = new Assignment("Assignment1", 2, 10);
        this.a4 = new Assignment("Assignment2", 8, 10);

        this.comp1 = new Component("Labs", 10, componentCalcLabs);
        this.comp2 = new Component("Midterms", 20, componentCalcMidterm);
        this.comp3 = new Component("Assignments", 20, componentCalcAssignment);

        this.courseCalcCS210 = new CourseCalculator();
        this.courseCalcCS121 = new CourseCalculator();
        this.courseCalcMath = new CourseCalculator();
        this.courseCalcStat = new CourseCalculator();

        this.c1 = new Course("CPSC210", 1, "2023W", courseCalcCS210);
        this.c2 = new Course("CPSC121", 1, "2023W", courseCalcCS121);
        this.c3 = new Course("MATH200", 2, "2022W", courseCalcMath);
        this.c4 = new Course("STAT300", 2, "2023S", courseCalcStat);

        this.finalGradeCalculator = new FinalGradeCalculator();
        courses = finalGradeCalculator.getCourses();
    }

    // NOTE: for the purpose of testing disregard that the sum of maxMark for all course components
    //      should be equal to 100%

    @Test
    void testConstructor() {
        assertEquals("CPSC210", c1.getName());
        assertEquals(1, c1.getTerm());
        assertEquals("2023W", c1.getSession());
        assertEquals(courseCalcCS210, c1.getCourseCalculator());
        assertEquals(100, c1.getMaxMark());
        assertEquals(0, c1.getFinalGrade());

        assertEquals(true, courses.isEmpty());
        assertEquals(0, courses.size());
        assertEquals(0, finalGradeCalculator.calculateSum());
        assertEquals(0, finalGradeCalculator.getNumOfCourses());

        assertEquals(0, finalGradeCalculator.calculateAverageForSession("2023W"));
        List<Course> filtered = finalGradeCalculator.filterCoursesBySession("2010W");
        assertEquals(0, filtered.size());
        assertEquals(true, filtered.isEmpty());
    }

    // Tests for Calculating the average, sum of all course grades, and filtering.

    @Test
    void testAddCourseOneWithOneComponent() {
        componentCalcLabs.addAssignment(a1);
        courseCalcCS210.addCourseComponent(comp1);
        finalGradeCalculator.addCourse(c1);

        assertEquals(false, courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals(c1, courses.get(0));
        assertEquals(1, finalGradeCalculator.getNumOfCourses());

        assertEquals(5, finalGradeCalculator.calculateSum());
        assertEquals(5, c1.getFinalGrade());
        assertEquals(5, finalGradeCalculator.calculateAverage());

        finalGradeCalculator.sortCoursesByFinalGradeHighest(courses);
        assertEquals(c1, courses.get(0));

        finalGradeCalculator.sortCoursesByFinalGradeLowest(courses);
        assertEquals(c1, courses.get(0));

        assertEquals(courses, finalGradeCalculator.filterCoursesBySession("2023W"));
        assertEquals(5, finalGradeCalculator.calculateAverageForSession("2023W"));
    }

    // Sorting method tested here:
    @Test
    void testAddCourseMultipleWithOneComponentEach() {
        componentCalcLabs.addAssignment(a1);
        courseCalcCS210.addCourseComponent(comp1);
        finalGradeCalculator.addCourse(c1);

        componentCalcMidterm.addAssignment(a2);
        courseCalcCS121.addCourseComponent(comp2);
        finalGradeCalculator.addCourse(c2);

        componentCalcAssignment.addAssignment(a3);
        componentCalcAssignment.addAssignment(a4);
        courseCalcMath.addCourseComponent(comp3);
        finalGradeCalculator.addCourse(c3);

        finalGradeCalculator.addCourse(c4);

        assertEquals(false, courses.isEmpty());
        assertEquals(4, courses.size());
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));
        assertEquals(c3, courses.get(2));
        assertEquals(c4, courses.get(3));
        assertEquals(4, finalGradeCalculator.getNumOfCourses());

        assertEquals(35, finalGradeCalculator.calculateSum());
        assertEquals(8, finalGradeCalculator.calculateAverage());

        // Tests for sorting:
        finalGradeCalculator.sortCoursesByFinalGradeHighest(courses);
        assertEquals(c2, courses.get(0));
        assertEquals(c3, courses.get(1));
        assertEquals(c1, courses.get(2));
        assertEquals(c4, courses.get(3));

        finalGradeCalculator.sortCoursesByFinalGradeLowest(courses);
        assertEquals(c4, courses.get(0));
        assertEquals(c1, courses.get(1));
        assertEquals(c3, courses.get(2));
        assertEquals(c2, courses.get(3));

        // Tests for filtering:
        List<Course> filtered = finalGradeCalculator.filterCoursesBySession("2023W");
        assertEquals(2, filtered.size());
        assertEquals(c1, filtered.get(0));
        assertEquals(c2, filtered.get(1));
        assertEquals(12, finalGradeCalculator.calculateAverageForSession("2023W"));
    }

    @Test
    void testAddCourseOneWithMultipleComponents() {
        componentCalcLabs.addAssignment(a1);
        componentCalcAssignment.addAssignment(a3);
        componentCalcAssignment.addAssignment(a4);
        courseCalcCS210.addCourseComponent(comp1);
        courseCalcCS210.addCourseComponent(comp3);
        finalGradeCalculator.addCourse(c1);

        assertEquals(false, courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals(c1, courses.get(0));

        assertEquals(15, finalGradeCalculator.calculateSum());
        assertEquals(15, finalGradeCalculator.calculateAverage());
    }

    @Test
    void testAddCourseMultipleWithMultipleComponents() {
        componentCalcLabs.addAssignment(a1);
        componentCalcAssignment.addAssignment(a3);
        componentCalcAssignment.addAssignment(a4);
        courseCalcCS210.addCourseComponent(comp1);
        courseCalcCS210.addCourseComponent(comp3);
        finalGradeCalculator.addCourse(c1);

        componentCalcMidterm.addAssignment(a2);
        courseCalcCS121.addCourseComponent(comp2);
        finalGradeCalculator.addCourse(c2);

        assertEquals(false, courses.isEmpty());
        assertEquals(2, courses.size());
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));

        assertEquals(35, finalGradeCalculator.calculateSum());
        assertEquals(17, finalGradeCalculator.calculateAverage());

        // Tests for sorting:
        finalGradeCalculator.sortCoursesByFinalGradeHighest(courses);
        assertEquals(c2, courses.get(0));
        assertEquals(c1, courses.get(1));

        finalGradeCalculator.sortCoursesByFinalGradeLowest(courses);
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));
    }

    @Test
    void testAddCourseMultipleComponentsSimilarGrades() {
        Assignment a5 = new Assignment("Lab1", 5, 6);

        componentCalcLabs.addAssignment(a1);
        componentCalcAssignment.addAssignment(a5);

        courseCalcCS210.addCourseComponent(comp1);
        courseCalcCS121.addCourseComponent(comp3);

        finalGradeCalculator.addCourse(c1);
        finalGradeCalculator.addCourse(c2);


        assertEquals(false, courses.isEmpty());
        assertEquals(2, courses.size());
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));

        assertEquals(10, finalGradeCalculator.calculateSum());
        assertEquals(5, finalGradeCalculator.calculateAverage());

        // Tests for sorting:
        finalGradeCalculator.sortCoursesByFinalGradeHighest(courses);
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));

        finalGradeCalculator.sortCoursesByFinalGradeLowest(courses);
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));
    }

    @Test
    void testRemoveCourseNotFound() {
        finalGradeCalculator.addCourse(c1);
        finalGradeCalculator.removeCourse(c2);
        assertEquals(false, courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals(0, finalGradeCalculator.calculateSum());
    }

    @Test
    void testRemoveOneNoCoursesLeft() {
        finalGradeCalculator.addCourse(c1);
        finalGradeCalculator.removeCourse(c1);
        assertEquals(true, courses.isEmpty());
        assertEquals(0, courses.size());
        assertEquals(0, finalGradeCalculator.calculateSum());
    }

    @Test
    void testRemoveCourseOneFromMultiple() {
        componentCalcLabs.addAssignment(a1);
        courseCalcCS210.addCourseComponent(comp1);
        finalGradeCalculator.addCourse(c1);

        componentCalcMidterm.addAssignment(a2);
        courseCalcCS121.addCourseComponent(comp2);
        finalGradeCalculator.addCourse(c2);

        componentCalcAssignment.addAssignment(a3);
        componentCalcAssignment.addAssignment(a4);
        courseCalcMath.addCourseComponent(comp3);
        finalGradeCalculator.addCourse(c3);
        finalGradeCalculator.addCourse(c4);

        finalGradeCalculator.removeCourse(c3);

        assertEquals(false, courses.isEmpty());
        assertEquals(3, courses.size());
        assertEquals(c1, courses.get(0));
        assertEquals(c2, courses.get(1));
        assertEquals(c4, courses.get(2));

        assertEquals(25, finalGradeCalculator.calculateSum());
        assertEquals(8, finalGradeCalculator.calculateAverage());
    }


    @Test
    void testRemoveCourseSomeFromMultiple() {
        componentCalcLabs.addAssignment(a1);
        courseCalcCS210.addCourseComponent(comp1);
        finalGradeCalculator.addCourse(c1);

        componentCalcMidterm.addAssignment(a2);
        courseCalcCS121.addCourseComponent(comp2);
        finalGradeCalculator.addCourse(c2);

        componentCalcAssignment.addAssignment(a3);
        componentCalcAssignment.addAssignment(a4);
        courseCalcMath.addCourseComponent(comp3);
        finalGradeCalculator.addCourse(c3);
        finalGradeCalculator.addCourse(c4);

        finalGradeCalculator.removeCourse(c1);
        finalGradeCalculator.removeCourse(c3);

        assertEquals(false, courses.isEmpty());
        assertEquals(2, courses.size());
        assertEquals(c2, courses.get(0));
        assertEquals(c4, courses.get(1));

        assertEquals(20, finalGradeCalculator.calculateSum());
        assertEquals(10, finalGradeCalculator.calculateAverage());
        assertEquals(20, finalGradeCalculator.calculateAverageForSession("2023W"));
        assertEquals(0, finalGradeCalculator.calculateAverageForSession("2023S"));
    }

    @Test
    void testSetCourses() {
        List<Course> newCourses = new ArrayList<>();
        newCourses.add(c1);
        newCourses.add(c2);
        finalGradeCalculator.setCourses(newCourses);
        assertEquals(newCourses, finalGradeCalculator.getCourses());
        assertEquals(false, finalGradeCalculator.getCourses().isEmpty());
    }

    @Test
    void testToJson() {
        componentCalcLabs.addAssignment(a1);
        courseCalcCS210.addCourseComponent(comp1);
        finalGradeCalculator.addCourse(c1);
        c1.getFinalGrade();

        componentCalcMidterm.addAssignment(a2);
        courseCalcCS121.addCourseComponent(comp2);
        finalGradeCalculator.addCourse(c2);
        c2.getFinalGrade();

        JSONObject jsonC1 = c1.toJson();
        JSONObject jsonC2 = c2.toJson();

        assertEquals("CPSC210", jsonC1.getString("courseName"));
        assertEquals(1, jsonC1.getInt("term"));
        assertEquals("2023W", jsonC1.getString("session"));
        assertEquals(5, jsonC1.getInt("finalGrade"));

        assertEquals("CPSC121", jsonC2.getString("courseName"));
        assertEquals(1, jsonC2.getInt("term"));
        assertEquals("2023W", jsonC2.getString("session"));
        assertEquals(20, jsonC2.getInt("finalGrade"));

        JSONObject finalGradeCalculatorJson = finalGradeCalculator.toJson();
        JSONArray expectedCourses = finalGradeCalculator.coursesToJson();
        JSONArray actualCourses = finalGradeCalculatorJson.getJSONArray("listOfCourses");

        assertEquals(expectedCourses.toString(), actualCourses.toString());
    }
}