package Database;

import Model.Contacts;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static Database.JDBC.connection;

public class UtilityDaoImpl implements UtilityDao {

    ObservableList<Contacts> AllContacts = FXCollections.observableArrayList();
    //This Defines All Users in FXCollections Observable Array List
    ObservableList<User> AllUsers = FXCollections.observableArrayList();

    @Override
    //This Gets All the Contacts
    public ObservableList<Contacts> Get_AllContacts() {
        try {
            //Selects Everything From Contact and puts it into Contact ID and Contact Name
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Contact_ID = rs.getInt("Contact_ID");
                String Contact_Name = rs.getString("Contact_Name");
                Contacts contact = new Contacts(Contact_ID, Contact_Name);
                AllContacts.add(contact);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return AllContacts;
    }

    @Override
    //This Gets All Users
    public ObservableList<User> Get_AllUsers() {
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int User_ID = rs.getInt("User_ID");
                String User_Name = rs.getString("User_Name");
                String Password = rs.getString("Password");
                User user = new User(User_ID, User_Name, Password);
                AllUsers.add(user);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return AllUsers;
    }

    @Override
    //This Feature Adds Appointments
    public int Add_Appointment(int Customer_ID, String Title, String Type, String Description, String Location, LocalDateTime Start_Date, LocalDateTime End_Date,
                               int User_ID, int Contact_ID) {
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO appointments (Customer_ID,Title, Description, Location, Type, Start,End,User_ID,Contact_ID) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Customer_ID);
            ps.setString(2, Title);
            ps.setString(3, Type);
            ps.setString(4, Description);
            ps.setString(5, Location);
            ps.setTimestamp(6, Timestamp.valueOf(Start_Date));
            ps.setTimestamp(7, Timestamp.valueOf(End_Date));
            ps.setInt(8, User_ID);
            ps.setInt(9, Contact_ID);
            rowsAffected = ps.executeUpdate();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rowsAffected;
    }


}


//This will be filtered list for the Contacts


