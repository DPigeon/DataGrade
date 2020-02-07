package com.example.utilisateur.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected FloatingActionButton floatingActionButton;
    protected ListView coursesListView;
    protected ArrayAdapter adapter;
    protected List<String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courses = new ArrayList<String>();
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertCourseDialogFragment insertCourseDialogFragment = new InsertCourseDialogFragment();
                insertCourseDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });

        loadAllCourses(); // We load all courses on create
        instantiateAdapter();
    }

    public void loadAllCourses() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Course> coursesFromDatabase = databaseHelper.getAllCourses();

        if (coursesFromDatabase != null) {
            for (int i = 0; i < coursesFromDatabase.size(); i++)
                courses.add(coursesFromDatabase.get(i).getInfo());
        }
    }

    protected void instantiateAdapter() {
        // Setting up the list view with its adapter
        if (courses != null)
            adapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.coursesTextView, courses);
        coursesListView = findViewById(R.id.coursesListView);
        coursesListView.setAdapter(adapter);
    }

}
