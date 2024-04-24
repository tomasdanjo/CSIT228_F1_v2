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


                PreparedStatement statement1 = c.prepareStatement("INSERT INTO tblstudentcourse (course_id,student_id) VALUES (?,?)");
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
            PreparedStatement statement1 = c.prepareStatement("Select course_id, student_id from tblstudentcourse WHERE course_id=? AND student_id=?  ");
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

            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblstudentcourse WHERE course_id = ? AND student_id=?");
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

    //de-enroll student to class


}
