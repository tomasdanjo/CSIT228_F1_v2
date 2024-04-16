package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Scanner;



public class databasecontroller {
    @FXML
    private Button getPasswordBtn;

    @FXML
    public GridPane pnLogin;

    @FXML
    private TextField getPasswordUsernameInput;

    @FXML
    private Label getPasswordPrompt;


    @FXML
    protected void onSigninClick() throws IOException {
        Parent homeview = FXMLLoader.load(HelloApplication.class
                .getResource("databaseactivity.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(homeview);
    }


    @FXML
    protected void getPasswordClicked(){
        //check if username exists
        String username = getPasswordUsernameInput.getText();
        if(ReadData.userExists(username)>0){
            System.out.println("User exists");
        }else{
            getPasswordPrompt.setText("Wala pani siya na user");
        }
        //if not display prompt
        //if yes get password


    }
}
