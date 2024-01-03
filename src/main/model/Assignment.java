package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an assignment with its name, grade in % and maxMark (max % available for this assignment)
public class Assignment implements Writable {

    private String name;
    private int grade;
    private int maxMark;

    // REQUIRES: grade <= maxMark
    // EFFECTS: constructs an assignment with its name, grade earned in %,
    //          maxMark possible in %
    public Assignment(String name, int grade, int maxMark) {
        this.name = name;
        this.grade = grade;
        this.maxMark = maxMark;
    }

    // REQUIRES: non-empty string
    // EFFECTS: returns assignment name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets assignment name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns assignment grade
    public int getGrade() {
        return grade;
    }

    // REQUIRES: grade <= maxMark
    // MODIFIES: this
    // EFFECTS: sets assignment grade
    public void setGrade(int grade) {
        this.grade = grade;
    }

    // EFFECTS: returns assignment max mark possible
    public int getMaxMark() {
        return maxMark;
    }

    // REQUIRES: maxMark >= grade
    // MODIFIES: this
    // EFFECTS: sets assignment max mark possible
    public void setMaxMark(int maxMark) {
        this.maxMark = maxMark;
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("assignmentName", name);
        json.put("grade", grade);
        json.put("maxMark", maxMark);
        return json;
    }

}
