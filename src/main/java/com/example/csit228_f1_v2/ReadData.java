package com.example.csit228_f1_v2;

import java.sql.*;

public class ReadData {
    public static int userExists(String username) {
        int ctr=0;
        try(Connection c = MySQLConnection.getConnection(); Statement statement = c.createStatement()){
            PreparedStatement statement1 = c.prepareStatement("Select * FROM usernamepassword where username = ?");
            statement1.setString(1,username);
            ResultSet set = statement1.executeQuery();
//             = statement.executeQuery(query);
            while(set.next()){
//                int id = set.getInt("id");
//                String name = set.getString("name");
//                String email = set.getString("email");
//                System.out.println(id+" "+name+" "+email);
                ctr++;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return ctr;
    }
}
