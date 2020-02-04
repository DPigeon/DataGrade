package com.example.utilisateur.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        String CREATE_TABLE_QUERY = "CREATE TABLE " + COURSE_TABLE + " (" +
                COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID is primary key and will auto increment
                COLUMN_COURSE_TITLE + " TEXT NOT NULL, " +
                COLUMN_COURSE_CODE + " TEXT NOT NULL)";
        database.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Where we alter our database
    }

    public long addCourse(Course course) {
        SQLiteDatabase database = this.getWritableDatabase(); // We get the reference to the database
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

}
