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


                try(Connection c = MySQLConnection.getConnection()){
                        Statement statement = c.createStatement();

                        statement.addBatch("CREATE TABLE IF NOT EXISTS `tblcourse` (" +
                                "  `courseid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "  `course_code` text NOT NULL," +
                                "  `course_description` text NOT NULL," +
                                "  `units` int(11) NOT NULL" +
                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");
                        statement.addBatch("CREATE TABLE IF NOT EXISTS `tblstudent` (" +
                                "  `studid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "  `firstname` text NOT NULL," +
                                "  `lastname` text NOT NULL," +
                                "  `ID_Number` text NOT NULL," +
                                "  `institutional_email` text NOT NULL" +
                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");

                        statement.addBatch("CREATE TABLE IF NOT EXISTS `tblstudentcourse` (\n" +
                                "  `seqid` int(11) NOT NULL AUTO_INCR" +
                                "EMENT,\n" +
                                "  `courseid` int(11) NOT NULL,\n" +
                                "  `studid` int(11) NOT NULL,\n" +
                                "  PRIMARY KEY (`seqid`),\n" +
                                "  FOREIGN KEY (`studid`) REFERENCES `tblstudent` (`studid`) ON DELETE CASCADE,\n" +
                                "  FOREIGN KEY (`courseid`) REFERENCES `tblcourse` (`courseid`) ON DELETE CASCADE\n" +
                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");

                        int[] updateCtr = statement.executeBatch();
                        for(int count: updateCtr){
                                System.out.println("Created "+count+" tables");
                        }

                }catch (SQLException e){
                        e.printStackTrace();
                }





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


                GridPane courseTable = controller.getCourseTablePane();
                GridPane coursesEnrolledBox = controller.getCoursesEnrolledBox();
                ComboBox coursesNotEnrolledCMB = controller.getAvailableCoursesCMB();
                Button btnEnroll = controller.getBtnEnroll();

                AsyncActivityController.refreshStudentTable(studentTable,coursesEnrolledBox,coursesNotEnrolledCMB,btnEnroll);

                AsyncActivityController.refreshCourseTable(courseTable,coursesEnrolledBox,coursesNotEnrolledCMB,btnEnroll);






                updateStuds();

                index=0;

                AsyncActivityController.

                        refreshCourseEnrolled(index,coursesEnrolledBox,coursesNotEnrolledCMB,btnEnroll);





            primaryStage.setScene(new Scene(root));
                primaryStage.show();


        }

        public static void updateStuds(){
                index=0;
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

