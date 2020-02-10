package com.example.utilisateur.assignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected FloatingActionButton floatingActionButton;
    protected ListView coursesListView;
    protected ArrayAdapter adapter;
    protected List<String> coursesString; // Used to display the list
    protected List<Course> courses; // Used to manage the clicks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        instantiateAdapter();
        loadAllCourses(); // We load all courses on create
    }

    protected void setupAction() { // No action bar for the main activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    void setupUI() {
        coursesString = new ArrayList<String>();
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertCourseDialogFragment insertCourseDialogFragment = new InsertCourseDialogFragment();
                Bundle parameters = new Bundle();
                parameters.putString("fromActivity", "mainActivity");
                insertCourseDialogFragment.setArguments(parameters);
                insertCourseDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });
        setupAction();
    }

    public void loadAllCourses() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        courses = databaseHelper.getAllCourses();
        coursesString.clear(); // Clear it to add new

        if (courses != null) {
            for (int i = 0; i < courses.size(); i++)
                coursesString.add(courses.get(i).getInfo()); // Add new courses
        }
        adapter.notifyDataSetChanged();
    }

    protected void instantiateAdapter() {
        // Setting up the list view with its adapter
        adapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.coursesTextView, coursesString);
        coursesListView = findViewById(R.id.coursesListView);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int courseId = courses.get(position).getId();
                String name = courses.get(position).getTitle();
                String code = courses.get(position).getCode();
                goToActivity(AssignmentActivity.class, courseId, name, code);
            }
        });
        coursesListView.setAdapter(adapter);
    }

    protected void goToActivity(Class page, int id, String courseName, String courseCode) { // Function that goes from the main activity to another one
        Intent intent = new Intent(MainActivity.this, page); // from the main activity to the profile class
        intent.putExtra("id", id);
        intent.putExtra("courseName", courseName);
        intent.putExtra("courseCode", courseCode);
        startActivity(intent);
        finish();
    }

}
