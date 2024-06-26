package com.example.csit228_f1_v2;

import java.sql.*;

public class CourseMethods {
    public static void createCourse(Connection c, String course_code, String course_description, int units){
        if(!courseExists(c,course_code)){
            try{


                PreparedStatement statement1 = c.prepareStatement("INSERT INTO tblcourse (course_code,course_description,units) VALUES (?,?,?)");
                statement1.setString(1,course_code);
                statement1.setString(2,course_description);
                statement1.setInt(3,units);
                int i = statement1.executeUpdate();
                if(i>0){
                    System.out.println("Insert course success");
                }else{
                    System.out.println("Insert course failed");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Course already exists");
        }

    };
    public static void deleteCourse(Connection c,String course_code){
        try{
            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblcourse WHERE course_code = ?");
            statement1.setString(1,course_code);
            int i = statement1.executeUpdate();
            if(i>0){
                System.out.println("Delete course success");
            }else{
                System.out.println("Delete course failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateCourse(Connection c, int courseid, String course_code, String	course_description, int	units){
        try{
            PreparedStatement statement1 = c.prepareStatement("UPDATE tblcourse SET course_code = ?, course_description=?, units=? WHERE courseid = ?");
            statement1.setString(1,course_code);
            statement1.setString(2,course_description);
            statement1.setInt(3,units);
            statement1.setInt(4,courseid);
            int i = statement1.executeUpdate();
            if(i>0){
                System.out.println("Update course success");
            }else{
                System.out.println("Update course failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getCourseCode(Connection c, int course_id){
        try{
            PreparedStatement statement1 = c.prepareStatement("Select course_code from tblcourse where courseid=?");

            statement1.setInt(1,course_id);
            ResultSet set = statement1.executeQuery();
            while (set.next()){
                return set.getString("course_code");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet allCourses(Connection c){
        try{
            Statement s = c.createStatement();
            return s.executeQuery("Select * from tblcourse");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean courseExists(Connection c,String course_code){
        try{
            PreparedStatement statement1 = c.prepareStatement("Select course_code from tblcourse WHERE course_code=? ");
            statement1.setString(1,course_code);
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

    public static int getCourseId(Connection c, String course_code){
        try{
            PreparedStatement statement1 = c.prepareStatement("Select courseid from tblcourse WHERE course_code=? ");
            statement1.setString(1,course_code);
            ResultSet i = statement1.executeQuery();
            while(i.next()){
                return i.getInt("courseid");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }


}
