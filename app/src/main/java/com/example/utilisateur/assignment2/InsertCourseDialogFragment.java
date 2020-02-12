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
    protected EditText courseTitleEditText;
    protected EditText courseCodeEditText;
    protected Button saveButton;
    protected Button cancelButton;
    int maxLengthTitle = 20, maxLengthCode = 10; // For validation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_dialog_fragment, container, false); // Inflate the layout to use it

        setupUiWithView(view);
        return view;
    }

    protected void setupUiWithView(View view) {
        // Setting the right title depending on what activity we are in
        courseTitleEditText = view.findViewById(R.id.courseTitleEditText);

        // Validation and fragment type check
        InputFilter[] FilterArrayTitle = new InputFilter[1];
        FilterArrayTitle[0] = new InputFilter.LengthFilter(maxLengthTitle);
        courseTitleEditText.setFilters(FilterArrayTitle);

        courseCodeEditText = view.findViewById(R.id.courseCodeEditText);

        InputFilter[] FilterArrayCode = new InputFilter[1];
        FilterArrayCode[0] = new InputFilter.LengthFilter(maxLengthCode);
        courseCodeEditText.setFilters(FilterArrayCode);

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
                String title = courseTitleEditText.getText().toString();
                String code = courseCodeEditText.getText().toString(); // course code or assignment grade
                Course course; // id, title, code

                if (validate(title, code)) {
                    course = new Course(id, title, code);
                    // We add the course into the database
                    databaseHelper.addCourse(course);

                    Toast toast = Toast.makeText(getActivity(), "Course has been saved!", Toast.LENGTH_LONG);
                    toast.show();

                    ((MainActivity)getActivity()).loadAllCourses(); // We cast the main activity to reload the courses
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
        if (title.matches("") || codeOrGrade.matches("")) {
            toastMessage("Fields cannot be empty!");
            return false;
        }

        return true;
    }

    protected void toastMessage(String message) { // Shows a toast message
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

}
