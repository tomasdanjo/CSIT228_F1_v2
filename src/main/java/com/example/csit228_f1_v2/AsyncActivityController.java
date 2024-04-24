package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AsyncActivityController {
    @FXML
    private Button btnAddStudent;

    @FXML
    private TextField input_fname;

    @FXML
    private TextField input_lname;

    @FXML
    private TextField input_idNum;

    @FXML
    private TextField input_email;

    @FXML
    private GridPane studentTablePane;

    @FXML
    private ScrollPane scrollpaneStudent;

    @FXML
    private VBox vbStudentTable;


    public GridPane getStudentTablePane() {
        return studentTablePane;
    }

    public void onAddStudent(){
        System.out.println("Add og student");

        try(Connection c = MySQLConnection.getConnection()){

            String firstname = input_fname.getText().toString();
            String lastname = input_lname.getText().toString();
            String idNum = input_idNum.getText().toString();
            String email = input_email.getText().toString();
            if(firstname.isEmpty() || lastname.isEmpty() || idNum.isEmpty()||email.isEmpty()){
                System.out.println("Input all fields");
            }else{
                StudentMethods.createStudent(c,firstname,lastname,idNum,email);
                input_fname.setText("");
                input_lname.setText("");
                input_idNum.setText("");
                input_email.setText("");
            }
            refreshStudentTable(studentTablePane);

        }catch (SQLException e){
                        e.printStackTrace();
        }
    }

    public static void replaceNode(GridPane gridPane, javafx.scene.Node oldNode, javafx.scene.Node newNode) {
        // Get the column index and row index of the old node
        Integer columnIndex = GridPane.getColumnIndex(oldNode);
        Integer rowIndex = GridPane.getRowIndex(oldNode);

        if (columnIndex != null && rowIndex != null) {
            // Remove the old node from the grid pane
            gridPane.getChildren().remove(oldNode);

            // Add the new node to the same position
            gridPane.add(newNode, columnIndex, rowIndex);
        }
    }

    public static void refreshStudentTable(GridPane p) {
        try (Connection c = MySQLConnection.getConnection()) {
            ResultSet students = StudentMethods.allStudents(c);
            if (students == null) {
                System.out.println("Student is null");
                return;
            }
            ArrayList<StudentRow> studentRows = new ArrayList<>();

            // Clear and initialize the GridPane
            p.getChildren().clear();
            Label firstNameLabel = new Label("First Name");
            Label lastNameLabel = new Label("Last Name");
            Label idLabel = new Label("ID Number");
            Label emailLabel = new Label("Email");
            Label actionlabel = new Label("Action");

            p.add(firstNameLabel, 0, 0);
            p.add(lastNameLabel, 1, 0);
            p.add(idLabel, 2, 0);
            p.add(emailLabel, 3, 0);
            p.add(actionlabel, 4, 0, 2, 1);
            p.setAlignment(Pos.BASELINE_CENTER);
            p.setVgap(10);


            p.setHalignment(firstNameLabel, HPos.CENTER);
            p.setHalignment(lastNameLabel, HPos.CENTER);
            p.setHalignment(idLabel, HPos.CENTER);
            p.setHalignment(emailLabel, HPos.CENTER);
            p.setHalignment(actionlabel, HPos.CENTER);

            int row = 1;
            while (students.next()) {
                int studid = students.getInt("studid");
                String fname = students.getString("firstname");
                String lname = students.getString("lastname");
                String id_num = students.getString("ID_Number");
                String email = students.getString("institutional_email");

                Button edit = new Button("Edit");
                Button delete = new Button("Delete");

                StudentRow studentRow = new StudentRow(studid, fname, lname, id_num, email, edit, delete);
                studentRows.add(studentRow);

                p.add(studentRow.firstname, 0, row);
                p.add(studentRow.lastname, 1, row);
                p.add(studentRow.id_number, 2, row);
                p.add(studentRow.email, 3, row);
                p.add(studentRow.edit, 4, row);
                p.add(studentRow.delete, 5, row);


                studentRow.delete.setOnAction(event -> {
                    try (Connection conn = MySQLConnection.getConnection()) {
                        StudentMethods.deleteStudent(conn, studentRow.id);
                        refreshStudentTable(p);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                studentRow.edit.setOnAction(event -> {
                    TextField firstNameField = new TextField(studentRow.firstname.getText());
                    TextField lastNameField = new TextField(studentRow.lastname.getText());
                    TextField idNumberField = new TextField(studentRow.id_number.getText());
                    TextField emailField = new TextField(studentRow.email.getText());
                    Button saveButton = new Button("Save");

                    replaceNode(p, studentRow.firstname, firstNameField);
                    replaceNode(p, studentRow.lastname, lastNameField);
                    replaceNode(p, studentRow.id_number, idNumberField);
                    replaceNode(p, studentRow.email, emailField);
                    replaceNode(p, studentRow.edit, saveButton);

                    saveButton.setOnAction(e -> {
                        try (Connection conn = MySQLConnection.getConnection()) {
                            StudentMethods.updateStudent(
                                    conn,
                                    studentRow.id,
                                    firstNameField.getText(),
                                    lastNameField.getText(),
                                    idNumberField.getText(),
                                    emailField.getText()
                            );
                            refreshStudentTable(p);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });
                });

                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}