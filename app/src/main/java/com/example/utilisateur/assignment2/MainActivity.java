package com.example.utilisateur.assignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_COURSE_ID;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_ID;

/*
* The main activity to add courses, view them when clicking on them in the list view
*/

public class MainActivity extends AppCompatActivity {

    protected FloatingActionButton floatingActionButton;
    protected TextView coursesTextView;
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
        coursesTextView = findViewById(R.id.coursesTextView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertCourseDialogFragment insertCourseDialogFragment = new InsertCourseDialogFragment();
                insertCourseDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });
        setupAction();
    }

    public double getAverageOfAllAssignments() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        courses = databaseHelper.getAllCourses();
        int iteration = 0;
        double total = 0;

        for (int i = 0; i < courses.size(); i++) {
            int courseId = courses.get(i).getId();
            String average = databaseHelper.getAssignmentsAverage(courseId, null, null);
            if (average == "NA")
                total = total + 0;
            else {
                total = total + Double.parseDouble(average);
                iteration = iteration + 1;
            }
        }
        double avg = total / iteration;
        return  avg;
    }

    public void loadAllCourses() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        courses = databaseHelper.getAllCourses();
        coursesString.clear(); // Clear it to add new

        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                int courseId = courses.get(i).getId();
                String average = databaseHelper.getAssignmentsAverage(courseId, COLUMN_ASSIGNMENT_ID, COLUMN_ASSIGNMENT_COURSE_ID + "=" + "'" + courseId + "'"); // We get the average for each course
                coursesString.add(courses.get(i).getInfo(average)); // Add new courses
            }
        }
        adapter.notifyDataSetChanged();
    }

    protected void instantiateAdapter() {
        // Setting up the list view with its adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coursesString);
        coursesListView = findViewById(R.id.coursesListView);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) { // As soon as we click an item in the course view list
                int courseId = courses.get(position).getId();
                String name = courses.get(position).getTitle();
                String code = courses.get(position).getCode();
                goToActivity(AssignmentActivity.class, courseId, name, code);
            }
        });
        coursesListView.setAdapter(adapter);

        String avg = "";
        if (Double.toString(getAverageOfAllAssignments()) == "NaN")
            avg = "NA";
        else
            avg = String.format("%.2f", getAverageOfAllAssignments()) + "%";
        coursesTextView.setText("Average of All Assignments: " + avg); // The top title with the average
    }

    protected void goToActivity(Class page, int id, String courseName, String courseCode) { // Function that goes from the main activity to another one
        Intent intent = new Intent(MainActivity.this, page); // from the main activity to the profile class
        intent.putExtra(getString(R.string.id), id);
        intent.putExtra(getString(R.string.courseName), courseName);
        intent.putExtra(getString(R.string.courseCode), courseCode);
        startActivity(intent);
        finish();
    }

}
