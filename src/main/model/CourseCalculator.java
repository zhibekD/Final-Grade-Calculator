package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a calculator that calculates the final grade in % for a
// course by taking the sum of all course components' grades in the session
public class CourseCalculator implements SumCalculator, Writable {

    private List<Component> components;               // list of course components

    // REQUIRES: courseGrad <= 100
    // EFFECTS: constructs a course component calculator with an empty list of course components
    public CourseCalculator() {
        this.components = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a course components to the list of course components
    public void addCourseComponent(Component c) {
        components.add(c);

        EventLog.getInstance().logEvent(new Event("Course component added: " + c.getComponentName()));
    }

    // REQUIRES: non-empty list
    // MODIFIES: this
    // EFFECTS: removes a course component from the list of course components
    //          if it contains it
    public void removeCourseComponent(Component c) {
        if (components.contains(c)) {
            components.remove(c);
        }
    }

    // REQUIRES: sum <= maxMark (100%)
    // EFFECTS: calculates the sum of all course components' grades and returns it
    @Override
    public int calculateSum() {
        int sum = 0;
        for (Component c : components) {
            sum = sum + c.getGrade();
        }
        return sum;
    }

    // EFFECTS: returns the list of all course components
    public List<Component> getComponents() {
        return components;
    }

    // MODIFIES: this
    // EFFECTS: sets a list of all course components
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    // EFFECTS: compares course calculator to the other course calculator object,
    //          returns true only if their 'components' lists are equal, otherwise false
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseCalculator that = (CourseCalculator) o;
        return components.equals(that.components);
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("components", componentsToJson());
        return json;
    }

    // EFFECTS: returns components in this course calculator as a JSON array
    public JSONArray componentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Component c : components) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
