package com.example.utilisateur.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.utilisateur.assignment2.DatabaseConfig.COURSE_TABLE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_COURSE_ID;

public class AssignmentActivity extends AppCompatActivity {
    protected TextView titleCourseTextView;
    protected ListView assignmentListView;
    protected ArrayAdapter adapter;
    protected List<String> assignmentsString;
    protected List<Assignment> assignments;
    protected Button deleteButton;
    protected FloatingActionButton assignmentFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assignmentsString = new ArrayList<String>();
        Bundle bundle = getIntent().getExtras();

        setupUI(bundle);
        fetchData(bundle);
    }

    /* Used to go back to main activity */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void setupUI(final Bundle bundle) {
        titleCourseTextView = findViewById(R.id.courseTitleTextView);
        assignmentListView = findViewById(R.id.assignmentListView);
        deleteButton = findViewById(R.id.deleteButton);

        assignmentFloatingActionButton = findViewById(R.id.assignmentFloatingActionButton);
        assignmentFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDialogFragment insertAssignmentDialogFragment = new InsertDialogFragment();
                Bundle parameters = new Bundle();
                int courseId = bundle.getInt("id"); // We pass the course Id from the main activity to the new assignment
                parameters.putString("fromActivity", "assignmentActivity");
                parameters.putInt("courseId", courseId);
                insertAssignmentDialogFragment.setArguments(parameters);
                insertAssignmentDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete from the database with id
                DatabaseHelper databaseHelper = new DatabaseHelper(AssignmentActivity.this);
                Bundle bundle = getIntent().getExtras();
                int id = bundle.getInt("id");
                databaseHelper.deleteCourse(COURSE_TABLE, COLUMN_COURSE_ID, id);
                goToActivity(MainActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Course has been deleted!", Toast.LENGTH_LONG); // Current pointer to the add, the string and the length if stays on
                toast.show(); // We display it
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, assignmentsString);
        assignmentListView.setAdapter(adapter);
    }

    protected void fetchData(Bundle bundle) { // We get the previous extras put in the intent
        // Fetching the title of the course
        int courseId = bundle.getInt("id");
        String courseName = bundle.getString("courseName");
        String courseCode = bundle.getString("courseCode");
        if (bundle != null)
            titleCourseTextView.setText(courseName + "          " + courseCode);

        // Fetching the assignments with the proper course id
        loadAllAssignments(courseId);
    }

    public void loadAllAssignments(int id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        assignments = databaseHelper.getAllAssignments(id);
        assignmentsString.clear(); // Clear it to add new

        if (assignments != null) {
            for (int i = 0; i < assignments.size(); i++)
                assignmentsString.add(assignments.get(i).getInfo()); // Add new courses
        }
        adapter.notifyDataSetChanged();
    }

    void goToActivity(Class page) { // Function that goes from the main activity to another one
        Intent intent = new Intent(AssignmentActivity.this, page); // from the main activity to the profile class
        startActivity(intent);
    }

}
