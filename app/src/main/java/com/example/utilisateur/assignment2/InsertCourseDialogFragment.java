package com.example.utilisateur.assignment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InsertCourseDialogFragment extends DialogFragment {
    protected EditText courseTitleEditText;
    protected EditText courseCodeEditText;
    protected Button saveButton;
    protected Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_dialogue_fragment, container, false); // Inflate the layout to use it

        setupUiWithView(view);
        return view;
    }

    protected void setupUiWithView(View view) {
        courseTitleEditText = view.findViewById(R.id.courseTitleEditText);
        courseCodeEditText = view.findViewById(R.id.courseCodeEditText);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity()); // We get a reference of the database

                // We gather all the info to create a new course
                int id = -1;
                String courseTitle = courseTitleEditText.getText().toString();
                String courseCode = courseCodeEditText.getText().toString();
                Course course = new Course(id, courseTitle, courseCode);

                // We add the course into the database
                databaseHelper.addCourse(course);
                Toast toast = Toast.makeText(getActivity(), "Course has been saved!", Toast.LENGTH_LONG);
                toast.show();
                ((MainActivity)getActivity()).loadAllCourses(); // We cast the main activity to reload the courses
                getDialog().dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss(); // We cancel
            }
        });
    }

}
