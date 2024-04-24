package com.example.csit228_f1_v2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CourseRow {
    int id;
    Label course_code;
    Label course_name;
    Label units;

    Button edit;
    Button delete;

    public CourseRow(int id, String course_code, String course_name, String units, Button edit, Button delete) {
        this.id = id;
        this.course_code = new Label(course_code);
        this.course_name = new Label(course_name);
        this.units = new Label(units);
        this.edit = edit;
        this.delete = delete;
    }
}
