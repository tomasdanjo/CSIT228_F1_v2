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
import java.util.List;

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

    @FXML
    private TextField inputCourseCode;

    @FXML
    private TextField inputCourseName;

    @FXML
    private TextField inputUnits;

    @FXML
    private Button createCourseBtn;

    public GridPane getCourseTablePane() {
        return courseTablePane;
    }

    @FXML
    private GridPane courseTablePane;

    public GridPane getCoursesEnrolledBox() {
        return coursesEnrolledBox;
    }

    @FXML
    private GridPane coursesEnrolledBox;

    @FXML
    private Button btnNextCoursesEnrolled;

    @FXML
    private Button btnPreviousCoursesEnrolled;

    public ComboBox getAvailableCoursesCMB() {
        return availableCoursesCMB;
    }

    @FXML
    private ComboBox availableCoursesCMB;

    public Button getBtnEnroll() {
        return btnEnroll;
    }

    @FXML
    private Button btnEnroll;

    @FXML
    private Text courseEnrolledStudName;




    public GridPane getStudentTablePane() {
        return studentTablePane;
    }

    public void onAddCourse(){
        System.out.println("Add og course");

        try(Connection c = MySQLConnection.getConnection()){

            String course_code = inputCourseCode.getText();
            String course_name = inputCourseName.getText();
            String units = inputUnits.getText();
            if(course_code.isEmpty() || course_name.isEmpty() || units.isEmpty()){
                System.out.println("Input all fields");
            }else{
                CourseMethods.createCourse(c,course_code,course_name,Integer.parseInt(units));
                inputCourseCode.setText("");
                inputCourseName.setText("");
                inputUnits.setText("");
            }

            Apr22_23AsyncActivity.updateStuds();
            refreshCourseTable (courseTablePane,  coursesEnrolledBox,  availableCoursesCMB,  btnEnroll);
            refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);


        }catch (SQLException e){
            e.printStackTrace();
        }
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

            Apr22_23AsyncActivity.updateStuds();
            refreshStudentTable(studentTablePane,coursesEnrolledBox,availableCoursesCMB,btnEnroll);
            refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);

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

    public static void refreshCourseTable (GridPane p, GridPane coursesEnrolledBox, ComboBox availableCoursesCMB, Button btnEnroll) {
        try (Connection c = MySQLConnection.getConnection()) {
            ResultSet courses = CourseMethods.allCourses(c);
            if (courses == null) {
                System.out.println("Course is null");
                return;
            }
            ArrayList<CourseRow> courseRows = new ArrayList<>();

            // Clear and initialize the GridPane
            p.getChildren().clear();
            Label course_code_label = new Label("Course Code");
            Label course_name_label = new Label("Course Name");
            Label units_label = new Label("Units");
            Label actionlabel = new Label("Action");

            p.add(course_code_label, 0, 0);
            p.add(course_name_label, 1, 0);
            p.add(units_label, 2, 0);
            p.add(actionlabel, 4, 0, 2, 1);
            p.setAlignment(Pos.BASELINE_CENTER);
            p.setVgap(10);


            p.setHalignment(course_code_label, HPos.CENTER);
            p.setHalignment(course_name_label, HPos.CENTER);
            p.setHalignment(units_label, HPos.CENTER);
            p.setHalignment(actionlabel, HPos.CENTER);

            int row = 1;
            while (courses.next()) {
                int courseid = courses.getInt("courseid");
                String course_code = courses.getString("course_code");
                String course_name = courses.getString("course_description");
                int units  = courses.getInt("units");

                Button edit = new Button("Edit");
                Button delete = new Button("Delete");

                CourseRow courseRow = new CourseRow(courseid,course_code,course_name,String.valueOf(units),edit,delete);
                courseRows.add(courseRow);

                p.add(courseRow.course_code, 0, row);
                p.add(courseRow.course_name, 1, row);
                p.add(courseRow.units, 2, row);
                p.add(courseRow.edit, 3, row);
                p.add(courseRow.delete, 4, row);


                courseRow.delete.setOnAction(event -> {
                    try (Connection conn = MySQLConnection.getConnection()) {
                        CourseMethods.deleteCourse(conn,courseRow.course_code.getText());
                        Apr22_23AsyncActivity.updateStuds();
                        refreshCourseTable (p, coursesEnrolledBox,  availableCoursesCMB,  btnEnroll);
                        refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                courseRow.edit.setOnAction(event -> {
                    TextField courseCodeField = new TextField(courseRow.course_code.getText());
                    TextField courseNameField = new TextField(courseRow.course_name.getText());
                    TextField unitsField = new TextField(courseRow.units.getText());
                    Button saveButton = new Button("Save");

                    replaceNode(p, courseRow.course_code, courseCodeField);
                    replaceNode(p, courseRow.course_name, courseNameField);
                    replaceNode(p, courseRow.units, unitsField);
                    replaceNode(p, courseRow.edit, saveButton);

                    saveButton.setOnAction(e -> {
                        try (Connection conn = MySQLConnection.getConnection()) {
                            CourseMethods.updateCourse(conn,courseRow.id,
                                    courseCodeField.getText(),
                                    courseNameField.getText(),
                                    Integer.parseInt(unitsField.getText())
                                    );
                            refreshCourseTable ( p,  coursesEnrolledBox,  availableCoursesCMB,  btnEnroll);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }


                        refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);
                    });


                });

                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void refreshStudentTable(GridPane p, GridPane coursesEnrolledBox, ComboBox availableCoursesCMB, Button btnEnroll) {
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
                        Apr22_23AsyncActivity.updateStuds();
                        refreshStudentTable(p,coursesEnrolledBox,availableCoursesCMB,btnEnroll);
                        refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);
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
                            Apr22_23AsyncActivity.updateStuds();
                            refreshStudentTable(p,coursesEnrolledBox,availableCoursesCMB,btnEnroll);

                            refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);

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

    public void onNext(){
        System.out.println("Next clicked");
        Apr22_23AsyncActivity.index++;
        if(Apr22_23AsyncActivity.studs.size()!=0) Apr22_23AsyncActivity.index%=Apr22_23AsyncActivity.studs.size();

        refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);

    }

    public void onPrev(){

        System.out.println("Prev clicked");
        Apr22_23AsyncActivity.index--;
        Apr22_23AsyncActivity.index = Apr22_23AsyncActivity.index < 0 ? Apr22_23AsyncActivity.index + Apr22_23AsyncActivity.studs.size(): Apr22_23AsyncActivity.index;

        if(Apr22_23AsyncActivity.studs.size()!=0) Apr22_23AsyncActivity.index=Apr22_23AsyncActivity.index%Apr22_23AsyncActivity.studs.size();

        refreshCourseEnrolled(Apr22_23AsyncActivity.index, coursesEnrolledBox, availableCoursesCMB, btnEnroll);
    }

    public static void refreshCourseEnrolled(int index, GridPane b, ComboBox cmb,Button btnEnroll){

        ArrayList<Student> students = Apr22_23AsyncActivity.studs;
        if(students.isEmpty()){
            return;
        }
        Student current = students.get(index);

        Label name = new Label(current.name);
        b.getChildren().clear();
        b.add(name,0,0,2,1);
        b.setHalignment(name,HPos.CENTER);


        int row=1;
        if(!current.courses_enrolled.isEmpty()){
            for(String s : current.courses_enrolled){
                System.out.println(s);
                Label course = new Label(s);
                b.add(course,0,row);

                Button unenroll = new Button("Unenroll");
                b.add(unenroll,1,row);
                row++;
                unenroll.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try(Connection c = MySQLConnection.getConnection()){
                            int studid = current.id;
                            int courseid = CourseMethods.getCourseId(c,course.getText());
                            CourseStudentMethods.deEnrollStudent(c,studid,courseid);
                            Apr22_23AsyncActivity.updateStuds();
                            refreshCourseEnrolled(index, b, cmb, btnEnroll);

                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                });


            }





        }

        refreshCmb(cmb,current.courses_enrolled);




        btnEnroll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cmb.getValue()==null){
                    System.out.println("Combo box is empty");
                    return;
                }else{
                    String course_code = cmb.getValue().toString();
                    int studid = current.id;
                    try(Connection c = MySQLConnection.getConnection()){
                        int courseid = CourseMethods.getCourseId(c,course_code);
                        if(courseid==-1) return;
                        CourseStudentMethods.enrollSudentCourse(c,studid,courseid);
                        Apr22_23AsyncActivity.updateStuds();
                        refreshCourseEnrolled(index, b, cmb, btnEnroll);

                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }


            }
        });




    }

    public static void refreshCmb(ComboBox cmb, List<String> coursesList){


        try(Connection c = MySQLConnection.getConnection()){
            ResultSet courses = CourseMethods.allCourses(c);
            cmb.getItems().clear();
            while(courses.next()){
                String course_code = courses.getString("course_code");
                if(!coursesList.contains(course_code)){
                    cmb.getItems().add(course_code);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
