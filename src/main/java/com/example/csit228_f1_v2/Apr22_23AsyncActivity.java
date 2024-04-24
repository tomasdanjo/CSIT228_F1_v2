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


                primaryStage.setScene(new Scene(root));
                primaryStage.show();





        }
}