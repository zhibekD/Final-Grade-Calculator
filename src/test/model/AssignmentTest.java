package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTest {

    private Assignment a1;

    @BeforeEach
    void runBefore() {
        this.a1 = new Assignment("Lab1", 5, 5);
    }

    @Test
    void testConstructor() {
        assertEquals("Lab1", a1.getName());
        assertEquals(5, a1.getGrade());
        assertEquals(5, a1.getMaxMark());
    }

    @Test
    void testSetters() {
        a1.setName("Lab2");
        a1.setGrade(4);
        a1.setMaxMark(10);
        assertEquals("Lab2", a1.getName());
        assertEquals(4, a1.getGrade());
        assertEquals(10, a1.getMaxMark());
    }

    @Test
    void testToJson() {
        JSONObject json = a1.toJson();
        assertEquals("Lab1", json.getString("assignmentName"));
        assertEquals(5, json.getInt("grade"));
        assertEquals(5, json.getInt("maxMark"));
    }

}
