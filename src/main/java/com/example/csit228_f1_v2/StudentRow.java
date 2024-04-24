package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentRow {
    public int id;
    public Label firstname;
    public Label lastname;

    public Label id_number;

    public Label email;

    public Button edit;
    public Button delete;

    public StudentRow(int id, String firstname, String lastname, String id_number, String  email, Button edit, Button delete) {
        this.id = id;
        this.firstname = new Label(firstname);
        this.lastname = new Label(lastname);
        this.id_number = new Label(id_number);
        this.email = new Label(email);
        this.edit = edit;
        this.delete = delete;

    }


}
