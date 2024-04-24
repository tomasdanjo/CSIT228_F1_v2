package com.example.csit228_f1_v2;

import java.sql.*;

public class StudentMethods {

    //create
    public static void createStudent(Connection c, String firstname, String lastname, String id_number, String email)  {
        if(!studentExists(c,email,id_number)){
            try{

                PreparedStatement statement1 = c.prepareStatement("INSERT INTO tblstudent (firstname,lastname,ID_Number,institutional_email) VALUES (?,?,?,?)");
                statement1.setString(1,firstname);
                statement1.setString(2,lastname);
                statement1.setString(3,id_number);
                statement1.setString(4,email);
                int i = statement1.executeUpdate();
                if(i>0){
                    System.out.println("Insert student success");
                }else{
                    System.out.println("Insert student failed");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Student already exists");
        }

    }

    //update
    public static void updateStudent(Connection c,int studid, String firstname, String lastname, String id_number, String email){
        try{
            PreparedStatement statement1 = c.prepareStatement("UPDATE tblstudent SET firstname = ?, lastname=?, ID_Number=?, institutional_email=? WHERE studid = ?");
            statement1.setString(1,firstname);
            statement1.setString(2,lastname);
            statement1.setString(3,id_number);
            statement1.setString(4,email);
            statement1.setInt(5,studid);
            int i = statement1.executeUpdate();
            if(i>0){
                System.out.println("Update student success");
            }else{
                System.out.println("Update student failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Connection c,int studid){
        try{
            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblstudent WHERE studid = ?");
            statement1.setInt(1,studid);
            int i = statement1.executeUpdate();
            if(i>0){
                System.out.println("Delete student success");
            }else{
                System.out.println("Delete student failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean studentExists(Connection c, String email, String id_number){
        try{
            PreparedStatement statement1 = c.prepareStatement("Select ID_Number, institutional_email from tblstudent WHERE ID_Number=? OR institutional_email = ?");
            statement1.setString(1,id_number);
            statement1.setString(2,email);
            ResultSet i = statement1.executeQuery();
            int ctr=0;
            while (i.next()){
                ctr++;
            }
            return ctr>0;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet allStudents(Connection c){
        try{
            Statement s = c.createStatement();
            return s.executeQuery("Select * from tblstudent");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }






}
