package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseStudentMethods {
    //enroll student to class
    public static void enrollSudentCourse(Connection c, int studid, int courseid){
        //check if student is already enrolled in course
        if(!alreadyEnrolled(c,studid,courseid)){
            try{


                PreparedStatement statement1 = c.prepareStatement("INSERT INTO tblstudentcourse (courseid,studid) VALUES (?,?)");
                statement1.setInt(1,courseid);
                statement1.setInt(2,studid);
                int i = statement1.executeUpdate();
                if(i>0){
                    System.out.println("Student enrolled success");
                }else{
                    System.out.println("Student enrolled failed");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Student is already enrolled");
        }

    }

    public static boolean alreadyEnrolled(Connection c, int student_id, int course_id){
        try{
            PreparedStatement statement1 = c.prepareStatement("Select courseid, studid from tblstudentcourse WHERE courseid=? AND studid=?  ");
            statement1.setInt(1,course_id);
            statement1.setInt(2,student_id);
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

    public static void deEnrollStudent (Connection c, int studid, int courseid){
        //check if student is already enrolled in course

        try{

            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblstudentcourse WHERE courseid = ? AND studid=?");
            statement1.setInt(1,courseid);
            statement1.setInt(2,studid);
            int i = statement1.executeUpdate();
            if(i>0){
                System.out.println("De-enroll course success");
            }else{
                System.out.println("De-enroll course failed");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public static ResultSet getEnrolledCourses(Connection c, int studid){
        try{

            PreparedStatement statement1 = c.prepareStatement("Select courseid from tblstudentcourse where studid=?");
            statement1.setInt(1,studid);
            return statement1.executeQuery();


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getNotEnrolledCourses(Connection c, int studid){
        try{


            PreparedStatement statement1 = c.prepareStatement("Select courseid from tblstudentcourse where studid=?");
            statement1.setInt(1,studid);
            return statement1.executeQuery();


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //de-enroll student to class


}
