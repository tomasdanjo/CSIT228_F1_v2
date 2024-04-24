package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static javafx.application.Application.launch;

public class Apr22_23AsyncActivity extends Application {

        public static void main(String[] args) {


                launch(args);

        }

        @Override
        public void start(Stage primaryStage)  {

//                try(Connection c = MySQLConnection.getConnection()){
//                        StudentMethods.createStudent(c,"thomas","danjo","22-1105","thomas@cit.edu");
//                        StudentMethods.createStudent(c,"thomas","manulat","22-1105-364","thomasd@cit.edu");
//                        CourseMethods.createCourse(c,"CSIT226","OOP",3);
//                        CourseMethods.createCourse(c,"CS1234","IDK",2);
//
//
//
//                }catch (SQLException e){
//                        e.printStackTrace();
//                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Apr22_23AsyncActivity.fxml"));
                Parent root = null;
                try {
                        root = loader.load();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

                AsyncActivityController controller = loader.getController(); // Replace YourController with the actual controller class name

                GridPane p = controller.getStudentTablePane();
                AsyncActivityController.refreshStudentTable(p);
                // Accessing the child node and its parent GridPane



                primaryStage.setScene(new Scene(root));
                primaryStage.show();





        }
}