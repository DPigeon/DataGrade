package com.example.utilisateur.assignment2;

public class Assignment {
    int id;
    int courseId;
    String title;
    double grade;

    Assignment(int i, int cId, String name, double percent) {
        id = i;
        courseId = cId;
        title = name;
        grade = percent;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public double getGrade() {
        return grade;
    }

    public String getInfo() {
        return this.title;
    }

    //Setters
    public void setId(int i) {
        id = i;
    }

    public void setCourseId(int i) {
        courseId = i;
    }

    public void setTitle(String name) {
        title = name;
    }

    public void setGrade(double percent) {
        grade = percent;
    }
}
