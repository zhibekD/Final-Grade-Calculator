package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Component c1;
    private Component c2;
    private Component c3;

    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    private List<Component> components;

    private CourseCalculator courseCalculator;
    private ComponentCalculator componentCalculatorLabs;
    private ComponentCalculator componentCalculatorMidterm;
    private ComponentCalculator componentCalculatorProj;
    private Course testCourse;

    @BeforeEach
    void runBefore() {
        this.componentCalculatorLabs = new ComponentCalculator();
        this.componentCalculatorMidterm = new ComponentCalculator();
        this.componentCalculatorProj = new ComponentCalculator();


        this.a1 = new Assignment("Lab1", 2, 2);
        this.a2 = new Assignment("Midterm1", 1, 1);
        this.a3 = new Assignment("Midterm2", 1, 1);

        this.c1 = new Component("Labs", 2, componentCalculatorLabs);
        this.c2 = new Component("Midterm",  2, componentCalculatorMidterm);
        this.c3 = new Component("Project", 30, componentCalculatorProj);

        this.courseCalculator = new CourseCalculator();
        this.components = courseCalculator.getComponents();

        this.testCourse = new Course("CPSC210", 1, "2023W", courseCalculator);
    }

    // NOTE: for the purpose of testing disregard that the sum of maxMark for all course components
    //      should be equal to 100%
    @Test
    void testConstructor() {
        assertEquals("Labs", c1.getComponentName());
        assertEquals(2, c1.getMaxMark());
        assertEquals(0, c1.getGrade());
        assertEquals(true, components.isEmpty());
        assertEquals(0, components.size());

        assertEquals(0, testCourse.getFinalGrade());
        assertEquals("CPSC210", testCourse.getName());
        assertEquals(1, testCourse.getTerm());
        assertEquals("2023W", testCourse.getSession());
        assertEquals(courseCalculator, testCourse.getCourseCalculator());
    }

    @Test
    void testAddComponentOne() {
        componentCalculatorLabs.addAssignment(a1);
        courseCalculator.addCourseComponent(c1);

        assertEquals(false, components.isEmpty());
        assertEquals(1, components.size());
        assertEquals(c1, components.get(0));
        assertEquals(true, components.contains(c1));

        assertEquals(2, c1.getGrade());
        assertEquals(2, testCourse.getFinalGrade());
    }

    @Test
    void testAddCourseComponentMultiple() {
        componentCalculatorLabs.addAssignment(a1);
        componentCalculatorMidterm.addAssignment(a2);
        componentCalculatorMidterm.addAssignment(a3);

        courseCalculator.addCourseComponent(c1);
        courseCalculator.addCourseComponent(c2);
        courseCalculator.addCourseComponent(c3);

        assertEquals(false, components.isEmpty());
        assertEquals(3, components.size());
        assertEquals(c1, components.get(0));
        assertEquals(c2, components.get(1));
        assertEquals(c3, components.get(2));

        assertEquals(2, c1.getGrade());
        assertEquals(2, c2.getGrade());
        assertEquals(0, c3.getGrade());

        assertEquals(4, testCourse.getFinalGrade());
    }

    @Test
    void testRemoveCourseComponentNotFound() {
        componentCalculatorLabs.addAssignment(a1);
        courseCalculator.addCourseComponent(c1);

        courseCalculator.removeCourseComponent(c2);

        assertEquals(false, components.isEmpty());
        assertEquals(1, components.size());
        assertEquals(2, testCourse.getFinalGrade());
    }

    @Test
    void testRemoveCourseComponentOne() {
        componentCalculatorLabs.addAssignment(a1);
        courseCalculator.addCourseComponent(c1);

        courseCalculator.removeCourseComponent(c1);

        assertEquals(true, components.isEmpty());
        assertEquals(0, components.size());
        assertEquals(0, testCourse.getFinalGrade());
    }

    @Test
    void testRemoveCourseComponentSome() {
        componentCalculatorLabs.addAssignment(a1);
        componentCalculatorMidterm.addAssignment(a2);
        componentCalculatorMidterm.addAssignment(a3);

        courseCalculator.addCourseComponent(c1);
        courseCalculator.addCourseComponent(c2);
        courseCalculator.addCourseComponent(c3);

        courseCalculator.removeCourseComponent(c1);
        courseCalculator.removeCourseComponent(c3);

        assertEquals(false, components.isEmpty());
        assertEquals(1, components.size());
        assertEquals(c2, components.get(0));
        assertEquals(2, testCourse.getFinalGrade());
    }

    @Test
    void testRemoveCourseComponentAll() {
        courseCalculator.addCourseComponent(c1);
        courseCalculator.addCourseComponent(c2);
        courseCalculator.addCourseComponent(c3);
        courseCalculator.removeCourseComponent(c1);
        courseCalculator.removeCourseComponent(c2);
        courseCalculator.removeCourseComponent(c3);
        assertEquals(true, components.isEmpty());
        assertEquals(0, components.size());
    }

    @Test
    void testSettersAndConstant() {
        testCourse.setSession("2022W");
        assertEquals("2022W", testCourse.getSession());

        testCourse.setName("CPSC110");
        assertEquals("CPSC110", testCourse.getName());

        testCourse.setTerm(2);
        assertEquals(2, testCourse.getTerm());

        testCourse.setCourseCalculator(courseCalculator);
        assertEquals(courseCalculator, testCourse.getCourseCalculator());

        assertEquals(100, testCourse.getMaxMark());

        courseCalculator.setComponents(new ArrayList<>());
        assertEquals(0, components.size());
        assertEquals(true, components.isEmpty());
    }

    @Test
    void testToJson() {
        componentCalculatorLabs.addAssignment(a1);
        c1.setComponentCalculator(componentCalculatorLabs);
        courseCalculator.addCourseComponent(c1);
        testCourse.getFinalGrade();
        JSONObject json = testCourse.toJson();

        assertEquals("CPSC210", json.getString("courseName"));
        assertEquals(1, json.getInt("term"));
        assertEquals("2023W", json.getString("session"));
        assertEquals(2, json.getInt("finalGrade"));


        JSONObject courseCalculatorJson = json.getJSONObject("courseCalculator");
        JSONArray expectedComponents = courseCalculator.componentsToJson();
        JSONArray actualComponents = courseCalculatorJson.getJSONArray("components");

        assertEquals(expectedComponents.toString(), actualComponents.toString());
    }

    @Test
    void testToJsonWithTwoComponents() {
        componentCalculatorLabs.addAssignment(a1);
        componentCalculatorMidterm.addAssignment(a2);
        componentCalculatorProj.addAssignment(a3);

        courseCalculator.addCourseComponent(c1);
        courseCalculator.addCourseComponent(c2);
        courseCalculator.addCourseComponent(c3);


        testCourse.getFinalGrade();
        JSONObject json = testCourse.toJson();

        assertEquals("CPSC210", json.getString("courseName"));
        assertEquals(1, json.getInt("term"));
        assertEquals("2023W", json.getString("session"));
        assertEquals(4, json.getInt("finalGrade"));

        JSONObject courseCalculatorJson = json.getJSONObject("courseCalculator");
        JSONArray expectedComponents = courseCalculator.componentsToJson();
        JSONArray actualComponents = courseCalculatorJson.getJSONArray("components");

        assertEquals(expectedComponents.toString(), actualComponents.toString());
    }

    @Test
    void testToJsonWithMinimumData() {
        Course minimalCourse = new Course("Minimal Course", 1, "2023W", new CourseCalculator());
        minimalCourse.getFinalGrade();

        JSONObject json = minimalCourse.toJson();

        assertEquals("Minimal Course", json.getString("courseName"));
        assertEquals(1, json.getInt("term"));
        assertEquals("2023W", json.getString("session"));
        assertEquals(0, json.getInt("finalGrade")); // has no components

        // courseCalculator JSON object is present
        assertTrue(json.has("courseCalculator"));

        // course components array is empty
        JSONObject courseCalculatorJson = json.getJSONObject("courseCalculator");
        assertTrue(courseCalculatorJson.has("components"));
        JSONArray componentsJsonArray = courseCalculatorJson.getJSONArray("components");
        assertTrue(componentsJsonArray.isEmpty());
    }

    @Test
    void testEqualsSameInstance() {
        assertTrue(courseCalculator.equals(courseCalculator));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(courseCalculator.equals("Not a CourseCalculator"));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(courseCalculator.equals(null));
    }

    @Test
    void testEqualsDifferentComponents() {
        List<Component> components1 = new ArrayList<>();
        components1.add(new Component("Comp1", 20, new ComponentCalculator()));
        courseCalculator.setComponents(components1);

        CourseCalculator calculator2 = new CourseCalculator();
        List<Component> components2 = new ArrayList<>();
        components2.add(new Component("Comp2", 30, new ComponentCalculator()));
        calculator2.setComponents(components2);

        assertFalse(courseCalculator.equals(calculator2));
    }

    @Test
    void testToString() {
        assertEquals("CPSC210 - Term: 1, Session: 2023W", testCourse.toString());
    }
}
