package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static final String URL =
            "jdbc:mysql://localhost:3306/mock";
    public static final String USERNAME = "root";
    public static final String PASSSWORD = "";
    static Connection getConnection(){


        Connection c = null;
        try{
            c= DriverManager.getConnection(URL, USERNAME,PASSSWORD);
            System.out.println("DB Connection Success");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {


       Connection c= getConnection();
       try{
           c.close();
       }catch (SQLException e){
           e.printStackTrace();
       }
    }
}
