package com.example.utilisateur.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        setupUI();
    }

    protected void setupUI() {
        titleCourseTextView = findViewById(R.id.courseTitleTextView);
        assignmentListView = findViewById(R.id.assignmentListView);
        deleteButton = findViewById(R.id.deleteButton);

        adapter = new ArrayAdapter<String>(this, R.layout.activity_assignment, R.id.courseTitleTextView, assignments);
        assignmentListView.setAdapter(adapter);
    }

}
