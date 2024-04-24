package com.example.csit228_f1_v2;

import java.util.List;

public class Student {

    int id;
    String name;

    List<String> courses_enrolled;

    public Student(int id, String name, List<String> courses_enrolled) {
        this.id = id;
        this.name = name;
        this.courses_enrolled = courses_enrolled;
    }
}
