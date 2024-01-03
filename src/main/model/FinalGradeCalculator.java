package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a calculator that calculates the final grade in % by taking
// the average (sum of all final grades from each course divided by the number
// of courses taken in the session)
public class FinalGradeCalculator implements SumCalculator, Writable {

    private List<Course> courses;   // list of courses
    private int finalGrade;         // final grade in % (average of all courses)

    // REQUIRES: final grade <= 100%
    // EFFECTS: constructs a final grade calculator with an empty list of courses,
    //          and a final grade in % is set to 0
    public FinalGradeCalculator() {
        this.courses = new ArrayList<>();
        this.finalGrade = 0;
    }

    // MODIFIES: this
    // EFFECTS: add a course to the list of courses
    public void addCourse(Course c) {
        courses.add(c);

        EventLog.getInstance().logEvent(new Event("Course added: " + c.getName()));
    }

    // REQUIRES: non-empty list
    // MODIFIES: this
    // EFFECTS: removes a course from the list of courses if it contains it
    public void removeCourse(Course c) {
        if (courses.contains(c)) {
            courses.remove(c);
        }

        EventLog.getInstance().logEvent(new Event("Course removed: " + c.getName()));
    }

    // EFFECTS: returns number of items in this list
    public int getNumOfCourses() {
        return courses.size();
    }

    // REQUIRES: average is a final grade in % <= 100, non-empty list of courses
    // EFFECTS: calculates the average grade for all course grades and returns it
    public int calculateAverage() {
        return calculateSum() / courses.size();

    }

    // REQUIRES: sum <= sum of all maxMarks for courses
    // EFFECTS: calculates the sum of all course grades and returns it
    @Override
    public int calculateSum() {
        int sum = 0;
        for (Course c : courses) {
            sum = sum + c.getFinalGrade();
        }
        return sum;
    }

    // EFFECTS: returns the list of all courses
    public List<Course> getCourses() {
        return courses;
    }

    // MODIFIES: this
    // EFFECTS: sets a list of courses
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // REQUIRES: non-empty list of courses
    // MODIFIES: this
    // EFFECTS: sorts list of courses from highest to lowest by their final grade
    //          if grades are equal, add first one to the sorted list
    public void sortCoursesByFinalGradeHighest(List<Course> courses) {
        int n = courses.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (courses.get(j).getFinalGrade() < courses.get(j + 1).getFinalGrade()) {
                    Course temp = courses.get(j);
                    courses.set(j, courses.get(j + 1));
                    courses.set(j + 1, temp);
                }
            }
        }
    }

    // REQUIRES: non-empty list of courses
    // MODIFIES: courses
    // EFFECTS: sorts list of courses from lowest to highest by their final grade,
    //          if grades are equal, add first one to the sorted list
    public void sortCoursesByFinalGradeLowest(List<Course> courses) {
        int n = courses.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (courses.get(j).getFinalGrade() > courses.get(j + 1).getFinalGrade()) {
                    Course temp = courses.get(j);
                    courses.set(j, courses.get(j + 1));
                    courses.set(j + 1, temp);
                }
            }
        }
    }

    // REQUIRES: non-empty list of courses
    // EFFECTS: returns a list of courses belonging to the specified session
    public List<Course> filterCoursesBySession(String session) {
        List<Course> filteredCourses = new ArrayList<>();

        for (Course course : courses) {
            if (course.getSession().equals(session)) {
                filteredCourses.add(course);
            }
        }

        EventLog.getInstance().logEvent(new Event("Filtered courses by session."));

        return filteredCourses;
    }

    // EFFECTS: calculates and returns the average final grade for the specified session,
    //          if list is empty returns 0
    public int calculateAverageForSession(String session) {
        List<Course> filteredCourses = filterCoursesBySession(session);

        if (filteredCourses.isEmpty()) {
            return 0;
        }

        int totalFinalGrade = 0;
        for (Course course : filteredCourses) {
            totalFinalGrade += course.getFinalGrade();
        }

        return totalFinalGrade / filteredCourses.size();
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfCourses", coursesToJson());
        json.put("finalGrade", finalGrade);
        return json;
    }

    // EFFECTS: returns courses in this final grade calculator as a JSON array
    public JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


}
