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
 * Used for the assignment dialog fragment to add an assignment
 */

public class InsertAssignmentDialogFragment extends DialogFragment {
    protected EditText assignmentTitleEditText;
    protected EditText assignmentGradeEditText;
    protected Button saveButton;
    protected Button cancelButton;
    int maxLengthTitle = 20, maxLengthCode = 10, maxLengthGrade = 3, minGrade = 0, maxGrade = 100; // For validation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_assignment_dialog_fragment, container, false); // Inflate the layout to use it

        setupUiWithView(view);
        return view;
    }

    protected void setupUiWithView(View view) {
        // Setting the right title depending on what activity we are in
        assignmentTitleEditText = view.findViewById(R.id.assignmentTitleEditText);

        // Validation and fragment type check
        InputFilter[] FilterArrayTitle = new InputFilter[1];
        FilterArrayTitle[0] = new InputFilter.LengthFilter(maxLengthTitle);
        assignmentTitleEditText.setFilters(FilterArrayTitle);

        assignmentGradeEditText = view.findViewById(R.id.gradeEditText);

        InputFilter[] FilterArrayGrade = new InputFilter[1];
        FilterArrayGrade[0] = new InputFilter.LengthFilter(maxLengthGrade);
        assignmentGradeEditText.setFilters(FilterArrayGrade);
        assignmentGradeEditText.setInputType(InputType.TYPE_CLASS_NUMBER); // Set input type to numbers

        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        actionButtons();
    }

    protected void actionButtons() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity()); // We get a reference of the database

                // We gather all the info to create a new course
                int id = -1;
                int courseId = 0;
                String title = assignmentTitleEditText.getText().toString();
                String grade = assignmentGradeEditText.getText().toString(); // course code or assignment grade
                Assignment assignment; // id, courseID, title, grade

                if (validate(title, grade)) {
                    courseId = getArguments().getInt("courseId"); // Comes from assignmentActivity --> add an assignment
                    assignment = new Assignment(id, courseId, title, grade);
                    // We add the assignment into the database
                    databaseHelper.addAssignment(assignment);

                    Toast toast = Toast.makeText(getActivity(), "Assignment has been saved!", Toast.LENGTH_LONG);
                    toast.show();

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

    protected boolean validate(String title, String grade) {
        // Check if all are empty
        // Check the grade (must be between 0 and 100)
        if (title.matches("") || grade.matches("")) {
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
