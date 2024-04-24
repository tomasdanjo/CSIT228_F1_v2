package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class Apr22_23AsyncActivity extends Application {

        public static void main(String[] args) {


                launch(args);

        }

        public static ArrayList<Student> studs;

        public static int index;

        @Override
        public void start(Stage primaryStage)  {

                studs = new ArrayList<>();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Apr22_23AsyncActivity.fxml"));
                Parent root = null;
                try {
                        root = loader.load();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

                AsyncActivityController controller = loader.getController(); // Replace YourController with the actual controller class name

                GridPane studentTable = controller.getStudentTablePane();
                AsyncActivityController.refreshStudentTable(studentTable);

                GridPane courseTable = controller.getCourseTablePane();
                AsyncActivityController.refreshCourseTable(courseTable);

                GridPane coursesEnrolledBox = controller.getCoursesEnrolledBox();
                ComboBox coursesNotEnrolledCMB = controller.getAvailableCoursesCMB();
                Button btnEnroll = controller.getBtnEnroll();



                updateStuds();

                index=0;

                AsyncActivityController.refreshCourseEnrolled(index,coursesEnrolledBox,coursesNotEnrolledCMB,btnEnroll);





            primaryStage.setScene(new Scene(root));
                primaryStage.show();


        }

        public static void updateStuds(){
                studs.clear();
                try (Connection c = MySQLConnection.getConnection()){
                        ResultSet set = StudentMethods.allStudents(c);
                        while(set.next()){
                                int id = set.getInt("studid");
                                String fname = set.getString("firstname");
                                String lname = set.getString("lastname");
                                List<String> courses_enrolled = new ArrayList<>();

                                ResultSet enrolledcourse = CourseStudentMethods.getEnrolledCourses(c,id);
                                while(enrolledcourse.next()){
                                        int course_id = enrolledcourse.getInt("courseid");
                                        String course_code = CourseMethods.getCourseCode(c,course_id);
                                        courses_enrolled.add(course_code);
                                }

                                Student s = new Student(id,fname+" "+lname,courses_enrolled);
                                studs.add(s);
                        }
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }
}

