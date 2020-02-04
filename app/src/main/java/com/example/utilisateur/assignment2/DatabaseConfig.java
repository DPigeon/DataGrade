package com.example.utilisateur.assignment2;

/*
* Database Variables to keep
*/

public class DatabaseConfig {
    // Database
    public static final String DATABASE_NAME = "course_database";

    // Course Table
    public static final String COURSE_TABLE = "course_table";
    public static final String COLUMN_COURSE_ID = "course_id"; // Primary key
    public static final String COLUMN_COURSE_TITLE = "course_title";
    public static final String COLUMN_COURSE_CODE = "course_code";

    // Assignment Table
    public static final String ASSIGNMENT_TABLE = "assignment_table";
    public static final String COLUMN_ASSIGNMENT_ID = "assignment_id"; // Primary key
    public static final String COLUMN_ASSIGNMENT_COURSE_ID = "assignment_course_id"; // Foreign key
    public static final String COLUMN_ASSIGNMENT_TITLE = "assignment_title";
    public static final String COLUMN_ASSIGNMENT_GRADE = "assignment_id";
}
