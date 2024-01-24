package main;

import Database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreenFunctions {
    /**
     * This Checks the User Names for the passwords to make sure
     * its Correct in order to login
     *
     * @param User_Name
     * @return
     */
    public static boolean User_Name_Check(String User_Name) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY User_Name = ?")) {
            ps.setString(1, User_Name);
            ResultSet resultset = ps.executeQuery();

            if (resultset.next()) {
                return true;
            }
        } catch (SQLException exceptions) {
            exceptions.printStackTrace();
        }
        return false;
    }

    /**
     * This is same as Username except it Checks for Password
     * in order to be able to log in
     *
     * @param Password
     * @return
     */
    public static boolean Password_Check(String Password) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY Password = ?")) {
            ps.setString(1, Password);
            ResultSet resultset = ps.executeQuery();

            if (resultset.next()) {
                return true;
            }
        } catch (SQLException exceptions) {
            exceptions.printStackTrace();
        }
        return false;
    }

    /**
     * This Gets the User ID in order to log in
     *
     * @param User_Name
     * @return
     * @throws SQLException
     */
    public static int Get_User_ID(String User_Name) throws SQLException {
        int User_ID = 0;
        String sqlStatement = "select User_ID, User_Name from users where User_Name = '" + User_Name + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User_ID = rs.getInt("User_ID");
            User_Name = rs.getString("User_Name");
        }
        return User_ID;
    }

}
