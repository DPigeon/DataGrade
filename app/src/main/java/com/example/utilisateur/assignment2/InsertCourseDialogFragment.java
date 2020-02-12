package com.example.utilisateur.assignment2;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/*
* Used for the course dialog fragment when adding a new course
*/

public class InsertCourseDialogFragment extends DialogFragment {
    protected EditText courseOrAssignmentTitleEditText;
    protected EditText courseCodeOrAssignmentGradeEditText;
    protected Button saveButton;
    protected Button cancelButton;
    int maxLengthTitle = 20, maxLengthCode = 10, maxLengthGrade = 3, minGrade = 0, maxGrade = 100; // For validation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_dialog_fragment, container, false); // Inflate the layout to use it

        String fromActivity = getArguments().getString("fromActivity");
        setupUiWithView(view, fromActivity);
        return view;
    }

    protected void setupUiWithView(View view, String parameter) {
        // Setting the right title depending on what activity we are in
        courseOrAssignmentTitleEditText = view.findViewById(R.id.courseTitleEditText);

        // Validation and fragment type check
        InputFilter[] FilterArrayTitle = new InputFilter[1];
        FilterArrayTitle[0] = new InputFilter.LengthFilter(maxLengthTitle);
        courseOrAssignmentTitleEditText.setFilters(FilterArrayTitle);
        if (parameter == "mainActivity")
            courseOrAssignmentTitleEditText.setText("Course Title");
        else
            courseOrAssignmentTitleEditText.setText("Assignment Title");

        courseCodeOrAssignmentGradeEditText = view.findViewById(R.id.courseCodeEditText);

        if (parameter == "mainActivity") {
            courseCodeOrAssignmentGradeEditText.setText("Code");
            InputFilter[] FilterArrayCode = new InputFilter[1];
            FilterArrayCode[0] = new InputFilter.LengthFilter(maxLengthCode);
            courseCodeOrAssignmentGradeEditText.setFilters(FilterArrayCode);
        } else {
            courseCodeOrAssignmentGradeEditText.setText("%");
            InputFilter[] FilterArrayGrade = new InputFilter[1];
            FilterArrayGrade[0] = new InputFilter.LengthFilter(maxLengthGrade);
            courseCodeOrAssignmentGradeEditText.setFilters(FilterArrayGrade);
            courseCodeOrAssignmentGradeEditText.setInputType(InputType.TYPE_CLASS_NUMBER); // Set input type to numbers
        }

        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        actionButtons(parameter);
    }

    protected void actionButtons(final String parameter) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity()); // We get a reference of the database

                // We gather all the info to create a new course
                int id = -1;
                int courseId = 0;
                String title = courseOrAssignmentTitleEditText.getText().toString();
                String codeOrGrade = courseCodeOrAssignmentGradeEditText.getText().toString(); // course code or assignment grade
                Course course; // id, title, code
                Assignment assignment; // id, courseID, title, grade
                String word = "";
                if (validate(title, codeOrGrade)) {
                    if (parameter.equals("mainActivity")) { // Comes from mainActivity --> add a course
                        course = new Course(id, title, codeOrGrade);
                        // We add the course into the database
                        databaseHelper.addCourse(course);
                        word = "Course";
                    } else {
                        courseId = getArguments().getInt("courseId"); // Comes from assignmentActivity --> add an assignment
                        assignment = new Assignment(id, courseId, title, codeOrGrade);
                        // We add the assignment into the database
                        databaseHelper.addAssignment(assignment);
                        word = "Assignment";
                    }
                    Toast toast = Toast.makeText(getActivity(), word + " has been saved!", Toast.LENGTH_LONG);
                    toast.show();
                    if (parameter.equals("mainActivity"))
                        ((MainActivity)getActivity()).loadAllCourses(); // We cast the main activity to reload the courses
                    else
                        ((AssignmentActivity)getActivity()).loadAllAssignments(courseId); // Same thing here for assignments
                    getDialog().dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss(); // We cancel
            }
        });
    }

    protected boolean validate(String title, String codeOrGrade) {
        // Check if all are empty
        // Check the grade (must be between 0 and 100)
        if (title.matches("") || codeOrGrade.matches("")) {
            toastMessage("Fields cannot be empty!");
            return false;
        }/* else if () {
            toastMessage("Grade must be in between 0% and 100%!");
            return false;
        }*/

        return true;
    }

    protected void toastMessage(String message) { // Shows a toast message
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

}
