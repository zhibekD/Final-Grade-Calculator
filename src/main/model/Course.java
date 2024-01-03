package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a Course with its name, term and session it was taken in, final grade earned for it in %,
// a list of course components that a course is comprised (sum of course components' grades is equal to course
// final grade, sum of course components' maxMarks has to be equal to course maxMark), and a max mark possible to
// earn for a course in % (100%), and a course calculator
public class Course implements Writable {

    private String name;                        // a course name
    private int term;                           // terms where False is term 1, True is term 2
    private String session;                     // the term the course is taken
    private int finalGrade;                     // the final course grade
    private CourseCalculator courseCalculator;  // a course calculator
    private final int maxMark = 100;            // max mark possible in % for the course

    // REQUIRES: course name cannot be similar
    // EFFECTS: constructs a course with name, term and session it is taken in, a course calculator as parameters,
    //          while a final grade in % is set to 0, a list of components for course grade
    //          calculation, and a max mark possible for the course is set 100
    public Course(String name, int term, String session, CourseCalculator courseCalculator) {
        this.name = name;
        this.term = term;
        this.session = session;
        this.finalGrade = 0;
        this.courseCalculator = courseCalculator;

        EventLog.getInstance().logEvent(new Event("Course created: " + name));
    }

    // EFFECTS: returns a course name
    public String getName() {
        return name;
    }

    // REQUIRES: non-empty string
    // MODIFIES: this
    // EFFECTS: sets a course name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns a term a course is taken
    public int getTerm() {
        return term;
    }

    // REQUIRES: 1 for term one, 2 for term two
    // MODIFIES: this
    // EFFECTS: sets a term a course is taken
    public void setTerm(int term) {
        this.term = term;
    }

    // EFFECTS: returns a session a course is taken
    public String getSession() {
        return session;
    }

    // REQUIRES: year and letter W for Winter session and S for Summer session
    // MODIFIES: this
    // EFFECTS: sets a session a course is taken
    public void setSession(String session) {
        this.session = session;
    }

    // REQUIRES: finalGrade <= maxMark (100%)
    // MODIFIES: this
    // EFFECTS: returns a final grade for a course
    public int getFinalGrade() {
        this.finalGrade = courseCalculator.calculateSum();
        return finalGrade;
    }

    // EFFECTS: returns a max available mark in % for the course
    public int getMaxMark() {
        return maxMark;
    }

    // EFFECTS: returns a course calculator for the course
    public CourseCalculator getCourseCalculator() {
        return courseCalculator;
    }

    // MODIFIES: this
    // EFFECTS: sets a course calculator for a course
    public void setCourseCalculator(CourseCalculator courseCalculator) {
        this.courseCalculator = courseCalculator;
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseName", name);
        json.put("term", term);
        json.put("session", session);
        json.put("finalGrade", finalGrade);
        json.put("courseCalculator", courseCalculator.toJson());
        return json;
    }

    // EFFECTS: return name, term and session as one string
    @Override
    public String toString() {
        return getName() + " - Term: " + getTerm() + ", Session: " + getSession();
    }
}
