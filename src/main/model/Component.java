package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a course component with its name, grade earned, and weight in percentage (max marks possible),
// and its component calculator
public class Component implements Writable {

    private String componentName;                       // a component name
    private int maxMark;                                // a weight in % of a course component
    private int grade;                                  // a grade in % for course component
    private ComponentCalculator componentCalculator;    // a component calculator

    // REQUIRES: grade <= maxMark
    // EFFECTS: constructs a course component with name, max mark possible in %, a component
    //          calculator as a parameters, a grade earned in % is set to 0
    public Component(String componentName, int maxMark, ComponentCalculator componentCalculator) {
        this.componentName = componentName;
        this.maxMark = maxMark;
        this.grade = 0;
        this.componentCalculator = componentCalculator;

        EventLog.getInstance().logEvent(new Event("Component for a course created: " + componentName));
    }

    // EFFECTS: returns a component name
    public String getComponentName() {
        return componentName;
    }

    // REQUIRES: non-empty string
    // MODIFIES: this
    // EFFECTS: sets a component name
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    // REQUIRES: grade <= maxMark
    // EFFECTS: returns a final grade earned for a component
    public int getGrade() {
        this.grade = componentCalculator.calculateSum();
        return grade;
    }

    // EFFECTS: returns a max possible mark in % for a component
    public int getMaxMark() {
        return maxMark;
    }

    // REQUIRES: maxMark >= grade
    // MODIFIES: this
    // EFFECTS: sets a max possible mark in % for a component
    public void setMaxMark(int maxMark) {
        this.maxMark = maxMark;
    }


    // EFFECTS: returns a component calculator
    public ComponentCalculator getComponentCalculator() {
        return componentCalculator;
    }

    // MODIFIES: this
    // EFFECTS: sets a component calculator
    public void setComponentCalculator(ComponentCalculator componentCalculator) {
        this.componentCalculator = componentCalculator;
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("componentName", componentName);
        json.put("maxMark", maxMark);
        json.put("finalGrade", grade);
        json.put("componentCalculator", componentCalculator.toJson());
        return json;
    }
}
