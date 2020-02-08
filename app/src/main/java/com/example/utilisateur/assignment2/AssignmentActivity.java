package com.example.utilisateur.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AssignmentActivity extends AppCompatActivity {
    protected TextView titleCourseTextView;
    protected ListView assignmentListView;
    protected ArrayAdapter adapter;
    protected List<String> assignments;
    protected Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assignments = new ArrayList<String>();
        Bundle bundle = getIntent().getExtras();

        setupUI();
        fetchData();
    }

    protected void setupUI() {
        titleCourseTextView = findViewById(R.id.courseTitleTextView);
        assignmentListView = findViewById(R.id.assignmentListView);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete from the database with id
                DatabaseHelper databaseHelper = new DatabaseHelper(AssignmentActivity.this);
                Bundle bundle = getIntent().getExtras();
                int id = bundle.getInt("id");
                databaseHelper.deleteCourse(id);
                goToActivity(MainActivity.class);
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.activity_assignment, R.id.courseTitleTextView, assignments);
        assignmentListView.setAdapter(adapter);
    }

    protected void fetchData() { // We get the previous extras put in the intent
        Bundle bundle = getIntent().getExtras();
        String courseName = bundle.getString("courseName");
        String courseCode = bundle.getString("courseCode");
        if (bundle != null)
            titleCourseTextView.setText(courseName + "          " + courseCode);
    }

    void goToActivity(Class page) { // Function that goes from the main activity to another one
        Intent intent = new Intent(AssignmentActivity.this, page); // from the main activity to the profile class
        startActivity(intent);
    }

}
