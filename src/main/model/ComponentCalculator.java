package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a calculator that calculates the final grade in % for each
// course component by taking the sum of all assignments  grades in the session
public class ComponentCalculator implements SumCalculator, Writable {

    private List<Assignment> assignments;       // a list of assignments

    // REQUIRES: componentGrade <= maxMark
    // EFFECTS: constructs a course component calculator with an empty list of assignments
    public ComponentCalculator() {
        this.assignments = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment to the list of assignments
    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    // REQUIRES: non-empty list
    // MODIFIES: this
    // EFFECTS: removes an assignment from the list of assignments if it contains it
    public void removeAssignment(Assignment a) {
        if (assignments.contains(a)) {
            assignments.remove(a);
        }
    }

    // REQUIRES: sum <= maxMark of courseComponent
    // EFFECTS: calculates the sum of all assignment grades and returns it
    @Override
    public int calculateSum() {
        int sum = 0;
        for (Assignment a : assignments) {
            sum = sum + a.getGrade();
        }
        return sum;
    }

    // EFFECTS: returns a list of all assignments
    public List<Assignment> getAssignments() {
        return assignments;
    }

    // REQUIRES: non-empty list
    // EFFECTS: sets a list of assignments for a course component
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    // EFFECTS: creates a new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("assignments", componentsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this component calculator as a JSON array
    public JSONArray componentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment a : assignments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
