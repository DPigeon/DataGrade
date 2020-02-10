package com.example.utilisateur.assignment2;

public class Assignment {
    int id;
    int courseId;
    String title;
    String grade;

    Assignment(int i, int cId, String name, String percent) {
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

    public String getGrade() {
        return grade;
    }

    public String getInfo() {
        return this.title + "\n" + this.grade + "%";
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

    public void setGrade(String percent) {
        grade = percent;
    }
}
