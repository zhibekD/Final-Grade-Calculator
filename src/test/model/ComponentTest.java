package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentTest {

    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    private List<Assignment> testList;
    private ComponentCalculator componentCalculator;

    private Component testComponent;

    @BeforeEach
    void runBefore() {
        this.a1 = new Assignment("Lab1", 2, 2);
        this.a2 = new Assignment("Lab2", 1, 2);
        this.a3 = new Assignment("Project - Phase 1", 30, 30);

        this.componentCalculator = new ComponentCalculator();
        this.testList = componentCalculator.getAssignments();

        this.testComponent = new Component("Labs", 4, componentCalculator);
    }

    @Test
    void testConstructor() {
        assertEquals("Lab1", a1.getName());
        assertEquals(2, a1.getGrade());
        assertEquals(2, a1.getMaxMark());
        assertEquals(true, testList.isEmpty());
        assertEquals(0, componentCalculator.calculateSum());
        assertEquals(new ArrayList<>(), componentCalculator.getAssignments());

        assertEquals("Labs", testComponent.getComponentName());
        assertEquals(4, testComponent.getMaxMark());
        assertEquals(0, testComponent.getGrade());
        assertEquals(componentCalculator, testComponent.getComponentCalculator());
    }

    @Test
    void testAddAssignmentOne() {
        componentCalculator.addAssignment(a1);
        assertEquals(false, testList.isEmpty());
        assertEquals(1, testList.size());
        assertEquals(2, componentCalculator.calculateSum());
        assertEquals(2, testComponent.getGrade());
    }

    @Test
    void testAddAssignmentMultiple() {
        componentCalculator.addAssignment(a1);
        componentCalculator.addAssignment(a2);
        componentCalculator.addAssignment(a3);
        assertEquals(false, testList.isEmpty());
        assertEquals(3, testList.size());
        assertEquals(33, componentCalculator.calculateSum());
        assertEquals(33, testComponent.getGrade());
    }

    @Test
    void testRemoveAssignmentNotFound() {
        componentCalculator.addAssignment(a1);
        componentCalculator.removeAssignment(a2);
        assertEquals(false, testList.isEmpty());
        assertEquals(1, testList.size());
    }

    @Test
    void testRemoveAssignmentOneEmptyList() {
        componentCalculator.addAssignment(a1);
        componentCalculator.removeAssignment(a1);
        assertEquals(true, testList.isEmpty());
        assertEquals(0, testList.size());
    }

    @Test
    void testRemoveAssignmentSome() {
        componentCalculator.addAssignment(a1);
        componentCalculator.addAssignment(a2);
        componentCalculator.addAssignment(a3);

        componentCalculator.removeAssignment(a1);
        componentCalculator.removeAssignment(a3);
        assertEquals(false, testList.isEmpty());
        assertEquals(1, testList.size());
        assertEquals(a2, testList.get(0));
    }

    @Test
    void testRemoveAssignmentAll() {
        componentCalculator.addAssignment(a1);
        componentCalculator.addAssignment(a2);
        componentCalculator.addAssignment(a3);

        componentCalculator.removeAssignment(a1);
        componentCalculator.removeAssignment(a2);
        componentCalculator.removeAssignment(a3);
        assertEquals(true, testList.isEmpty());
        assertEquals(0, testList.size());
    }

    @Test
    void testSetAssignments() {
        componentCalculator.setAssignments(new ArrayList<>());
        assertEquals(true, componentCalculator.getAssignments().isEmpty());
        assertEquals(0, componentCalculator.getAssignments().size());
    }

    @Test
    void testSetters() {
        testComponent.setComponentName("Midterms");
        assertEquals("Midterms", testComponent.getComponentName());

        testComponent.setMaxMark(20);
        assertEquals(20, testComponent.getMaxMark());

        ComponentCalculator newComponentCalc = new ComponentCalculator();
        testComponent.setComponentCalculator(newComponentCalc);
        assertEquals(newComponentCalc, testComponent.getComponentCalculator());

        newComponentCalc.setAssignments(new ArrayList<>());
        assertEquals(0, newComponentCalc.getAssignments().size());
        assertEquals(true, newComponentCalc.getAssignments().isEmpty());

    }

    @Test
    void testToJson() {
        componentCalculator.addAssignment(a1);
        testComponent = new Component("Labs", 4, componentCalculator);
        testComponent.getGrade();
        JSONObject json = testComponent.toJson();

        assertEquals("Labs", json.getString("componentName"));
        assertEquals(4, json.getInt("maxMark"));
        assertEquals(2, json.getInt("finalGrade"));

        JSONObject componentCalculatorJson = json.getJSONObject("componentCalculator");
        JSONArray expectedAssignments = componentCalculator.componentsToJson();
        JSONArray actualAssignments = componentCalculatorJson.getJSONArray("assignments");

        assertEquals(expectedAssignments.toString(), actualAssignments.toString());
    }
}
