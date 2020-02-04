package com.example.utilisateur.assignment2;

/*
* Model in the MVC architecture
*/

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

    public String getInfo() { // Will be used as a toast
        return this.title + this.code + " has been created!";
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
