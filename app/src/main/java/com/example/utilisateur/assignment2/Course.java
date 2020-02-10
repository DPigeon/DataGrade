package com.example.utilisateur.assignment2;

/*
* Model in the MVC architecture
*/

import android.util.Log;

public class Course {
    int id; // Primary key
    String title;
    String code;

    Course(int i, String name, String number) {
        id = i;
        title = name;
        code = number;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getInfo(String avg) {
        String percentSign = "%";
        if (avg == "NA")
            percentSign = "";
        else {
            double doubleAvg = Double.parseDouble(avg);
            avg = String.format("%.2f", doubleAvg);
        }
        return this.title + "\n" + this.code + "\n\n" + "Assignment Average: " + avg + percentSign;
    }

    // Setters
    public void setId(int i) {
        id = i;
    }

    public void setTitle(String name) {
        title = name;
    }

    public void setCode(String number) {
        code = number;
    }

}
