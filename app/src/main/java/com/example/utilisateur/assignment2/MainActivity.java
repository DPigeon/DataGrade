package com.example.utilisateur.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected FloatingActionButton floatingActionButton;
    protected TextView coursesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesTextView = findViewById(R.id.coursesTextView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertCourseDialogFragment insertCourseDialogFragment = new InsertCourseDialogFragment();
                insertCourseDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });

    }

    public void loadAllCourses() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Course> course = databaseHelper.getAllCourses();

        String coursesInformation = "";
        for (int i = 0; i < course.size(); i++)
            coursesInformation += course.get(i).getInfo() + "\n";

        coursesTextView.setText(coursesInformation);
    }

}
