# GPA Calculator Application

## Simply calculate your grades

A helpful tool for anyone who would like to track and calculate their grades. This application allows users to add 
courses and specify the weight for each one of its components. Users are able to input the grades they received
for each of the course components and the application can calculate the final grade. It a truly handy 
and interesting application project for me to stay on track of my academic progress.

A *user stories* list:
- As a user, I want to be able to add a course to the list of courses and see them displayed.
- As a user, I want to be able to remove a course from the list and see the displayed changes.
- As a user, I want to be able to set the name, the term and session the course is taken,
  components of the course with its names and max marks, assignments for each of component with
  its grade earned and max mark possible for the assignment.
- As a user, I want to be able to add more new components with its assignments for existing courses.
- As a user, I want to be able to view a list of courses so far with name, term, session and current grade.
- As a user, I want to be able to calculate my final grade for a course so far and 
  see it directly from the view option.
- As a user, I want to be able to sort my list from lowest to highest course grades, and the other way around.
- As a user, I want to be able to filter my list of courses by session and see the filtered results displayed.
- As a user, I want to be able to see the final grade (average from all courses) in % for the specified session.
- As a user, I want to be able to save my list of courses with its final grades so far and the max mark possible
  to file (if I so choose)
- As a user, I want to be able to save my list of course components with its grades so far and the max mark
  to file (if I so choose)
- As a user, I want to be able to save my list of assignments with its grades and a max mark possible 
  to file (if I so choose) byt clicking on the save button.
- As a user, I want to be able to be able to load my list of courses with all of its components, assignments, 
  the final grade earned so far and max mark possible from file (if I so choose) by clicking on the load button,
  and be able to see the loaded data displayed.
- As a user, I want to be reminded to save my courses before closing the application by having a confirmation window
  to close the application.


## Instructions for Grader:

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by filling out the
  input fields on the top and then clicking on the add course button.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by selecting a 
  course and clicking on it, and on the right side a more detailed information about the selected course will be 
  displayed. 
- You can generate the third action related to the user story "adding multiple Xs to a Y" by selecting the
  course (click on it in the scroll pane) and click on the add component button to add a component for a course. You 
  will see the right side the box displaying the course and its components added so far.
- You can generate the fourth action related to the user story "adding multiple Xs to a Y" by selecting the
  course (click on it in the scroll pane) and click on the remove button to remove the course.
- You can generate the fifth action related to the user story "adding multiple Xs to a Y" by clicking on the filter 
  session button and the dialog window will appear where you can input the session you want to filter for
- You can locate my visual component by running the application and seeing the displayed logo images.
- You can save the state of my application by pressing on the save button and a dialog window will get displayed if
  courses were saved successfully
- You can reload the state of my application by pressing on the load button and a dialog window will get displayed if
  courses were loaded successfully, you will also be able to see all courses displayed on the scroll pane.

## Phase 4: Task 2

### Sample 1 with adding and removing course

- Logged Events:
- Tue Nov 28 22:20:26 PST 2023
- Component for a course created: midterms
- Tue Nov 28 22:20:26 PST 2023
- Course component added: midterms
- Tue Nov 28 22:20:26 PST 2023
- Component for a course created: midterms
- Tue Nov 28 22:20:26 PST 2023
- Course component added: midterms
- Tue Nov 28 22:20:26 PST 2023
- Course created: cs
- Tue Nov 28 22:20:26 PST 2023
- Course added: cs
- Tue Nov 28 22:20:26 PST 2023
- Component for a course created: assignments
- Tue Nov 28 22:20:26 PST 2023
- Course component added: assignments
- Tue Nov 28 22:20:26 PST 2023
- Component for a course created: exams
- Tue Nov 28 22:20:26 PST 2023
- Course component added: exams
- Tue Nov 28 22:20:26 PST 2023
- Course created: math
- Tue Nov 28 22:20:26 PST 2023
- Course added: math
- Tue Nov 28 22:20:26 PST 2023
- Component for a course created: labs
- Tue Nov 28 22:20:26 PST 2023 
- Course component added: labs 
- Tue Nov 28 22:20:26 PST 2023 
- Course created: math101 
- Tue Nov 28 22:20:26 PST 2023 
- Course added: math101 
- Tue Nov 28 22:20:26 PST 2023 
- Component for a course created: labs 
- Tue Nov 28 22:20:26 PST 2023 
- Course component added: labs 
- Tue Nov 28 22:20:26 PST 2023 
- Course created: cs121 
- Tue Nov 28 22:20:26 PST 2023 
- Course added: cs121 
- Tue Nov 28 22:20:38 PST 2023 
- Course created: engl 
- Tue Nov 28 22:20:52 PST 2023 
- Course removed: engl

## Sample 2 with filtering

- Logged Events:
- Tue Nov 28 22:24:07 PST 2023
- Component for a course created: midterms
- Tue Nov 28 22:24:07 PST 2023 
- Course component added: midterms 
- Tue Nov 28 22:24:07 PST 2023 
- Component for a course created: midterms 
- Tue Nov 28 22:24:07 PST 2023 
- Course component added: midterms 
- Tue Nov 28 22:24:07 PST 2023 
- Course created: cs 
- Tue Nov 28 22:24:07 PST 2023 
- Course added: cs 
- Tue Nov 28 22:24:07 PST 2023 
- Component for a course created: assignments 
- Tue Nov 28 22:24:07 PST 2023 
- Course component added: assignments 
- Tue Nov 28 22:24:07 PST 2023 
- Component for a course created: exams 
- Tue Nov 28 22:24:07 PST 2023 
- Course component added: exams 
- Tue Nov 28 22:24:07 PST 2023 
- Course created: math 
- Tue Nov 28 22:24:07 PST 2023
- Course added: math
- Tue Nov 28 22:24:07 PST 2023
- Component for a course created: labs
- Tue Nov 28 22:24:07 PST 2023
- Course component added: labs
- Tue Nov 28 22:24:07 PST 2023
- Course created: math101
- Tue Nov 28 22:24:07 PST 2023
- Course added: math101
- Tue Nov 28 22:24:07 PST 2023
- Component for a course created: labs
- Tue Nov 28 22:24:07 PST 2023
- Course component added: labs
- Tue Nov 28 22:24:07 PST 2023
- Course created: cs121
- Tue Nov 28 22:24:07 PST 2023
- Course added: cs121
- Tue Nov 28 22:24:31 PST 2023
- Course created: engl
- Tue Nov 28 22:24:40 PST 2023
- Filtered courses by session.

## Phase 4: Task 3

If I had more time to work on Grade Calculator Application, one potential refactoring I would consider 
is to separate classes by their responsibilities, for instance, I could separate FinalGradeCalculator 
class into two classes, the one that will be responsible for filtering and the other one be responsible 
for calculations. I would also consider refactoring in ui package classes. At the moment, those classes have 
various responsibilities that handle adding, removing items, as well as filtering, saving and loading. Also, 
I found that having only GradeCalculatorGUI and FurtherFeaturesGUI classes made code a bit complicated 
and hard to read, hence I think refactoring would improve the readability and performance of the application overall.
Adding assignments to each component is something I had no time to make it to GUI. Hence, I would like to 
implement further the GUI so that the application can fully function as a proper grade calculator.
