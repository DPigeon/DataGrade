package com.example.utilisateur.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;

import static com.example.utilisateur.assignment2.DatabaseConfig.ASSIGNMENT_TABLE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_COURSE_ID;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_GRADE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_ID;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_ASSIGNMENT_TITLE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_COURSE_CODE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_COURSE_ID;
import static com.example.utilisateur.assignment2.DatabaseConfig.COLUMN_COURSE_TITLE;
import static com.example.utilisateur.assignment2.DatabaseConfig.COURSE_TABLE;
import static com.example.utilisateur.assignment2.DatabaseConfig.DATABASE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Initialize it with a context
    }

    @Override
    public void onCreate(SQLiteDatabase database) { // Called automatically when the database is being created (first time we open the database)
        // Course Table
        String CREATE_COURSE_TABLE_QUERY = "CREATE TABLE " + COURSE_TABLE + " (" +
                COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID is primary key and will auto increment
                COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +
                COLUMN_COURSE_CODE + " TEXT NOT NULL)";
        database.execSQL(CREATE_COURSE_TABLE_QUERY);

        // Assignment Table
        String CREATE_ASSIGNMENT_TABLE_QUERY = "CREATE TABLE " + ASSIGNMENT_TABLE + " (" +
                COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID is primary key and will auto increment
                COLUMN_ASSIGNMENT_COURSE_ID + " INTEGER, " +
                COLUMN_ASSIGNMENT_TITLE + " TEXT NOT NULL, " +
                COLUMN_ASSIGNMENT_GRADE + " TEXT NOT NULL)";
        database.execSQL(CREATE_ASSIGNMENT_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Where we alter our database
    }

    public long addCourse(Course course) {
        SQLiteDatabase database = this.getWritableDatabase(); // We get the reference to the database to write
        long id = -1; // Start at -1 to get the first id at 0

        // We prepare the values for the dabatase
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_TITLE, course.getTitle());
        contentValues.put(COLUMN_COURSE_CODE, course.getCode());

        try {
            id = database.insertOrThrow(COURSE_TABLE, null, contentValues); // We put the content values inside the table
        } catch (SQLException exception) {
            Toast.makeText(context,"Error: " + exception.getMessage(), Toast.LENGTH_LONG);
        } finally {
            database.close();
        }
        return id;
    }

    public long addAssignment(Assignment assignment) {
        SQLiteDatabase database = this.getWritableDatabase(); // We get the reference to the database to write
        long id = -1; // Start at -1 to get the first id at 0

        // We prepare the values for the dabatase
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ASSIGNMENT_COURSE_ID, assignment.getCourseId());
        contentValues.put(COLUMN_ASSIGNMENT_TITLE, assignment.getTitle());
        contentValues.put(COLUMN_ASSIGNMENT_GRADE, assignment.getGrade());

        try {
            id = database.insertOrThrow(ASSIGNMENT_TABLE, null, contentValues); // We put the content values inside the table
        } catch (SQLException exception) {
            Toast.makeText(context,"Error: " + exception.getMessage(), Toast.LENGTH_LONG);
        } finally {
            database.close();
        }
        return id;
    }

    public void deleteCourse(String table, String column, int id) { // We delete by ID and will have to delete all assignments too
        SQLiteDatabase database = this.getWritableDatabase();
        String assignmentsToDeleteQuery = "DELETE FROM " + ASSIGNMENT_TABLE + " WHERE " + COLUMN_ASSIGNMENT_COURSE_ID + "=" + "'" + id + "';";
        String rowToDeleteQuery = "DELETE FROM " + table + " WHERE " + column + "=" + "'" + id + "';";

        try {
            database.execSQL(assignmentsToDeleteQuery);
            database.execSQL(rowToDeleteQuery);
        } catch (SQLException exception) {
            Toast.makeText(context,"Error: " + exception.getMessage(), Toast.LENGTH_LONG);
        } finally {
            database.close();
        }
    }

    public List<Course> getAllCourses() {
        SQLiteDatabase database = this.getReadableDatabase(); // We get the reference to the database to read
        Cursor cursor = null;

        try {
            cursor = database.query(COURSE_TABLE, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst();
                // Create a new list
                List<Course> courseList = new ArrayList<>();

                do {
                    // We get all the parameters
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_CODE));
                    Course currentCourse = new Course(id, title, code);
                    courseList.add(currentCourse);
                } while (cursor.moveToNext());
                return courseList;
            }
        } catch (SQLException exception) {
            Toast.makeText(context,"Error: " + exception.getMessage(), Toast.LENGTH_LONG);
        } finally {
            if (cursor != null)
                cursor.close();
            database.close();
        }
        return Collections.emptyList(); // Nothing to display
    }

    public List<Assignment> getAllAssignments(int cId) {
        SQLiteDatabase database = this.getReadableDatabase(); // We get the reference to the database to read
        Cursor cursor = null;

        try {
            // We select * assignments, groupBy their ID and having the course ID to show them per courses
            cursor = database.query(ASSIGNMENT_TABLE, null, null, null, COLUMN_ASSIGNMENT_ID, COLUMN_ASSIGNMENT_COURSE_ID + "=" + "'" + cId + "'", null);

            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst();
                // Create a new list
                List<Assignment> assignmentList = new ArrayList<>();

                do {
                    // We get all the parameters
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ASSIGNMENT_ID));
                    int courseId = cursor.getInt(cursor.getColumnIndex(COLUMN_ASSIGNMENT_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_TITLE));
                    String grade = cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_GRADE));
                    Assignment currentAssignment = new Assignment(id, courseId, title, grade);
                    assignmentList.add(currentAssignment);
                } while (cursor.moveToNext());
                return assignmentList;
            }
        } catch (SQLException exception) {
            Toast.makeText(context,"Error: " + exception.getMessage(), Toast.LENGTH_LONG);
        } finally {
            if (cursor != null)
                cursor.close();
            database.close();
        }
        return Collections.emptyList(); // Nothing to display
    }
}
