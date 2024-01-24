package main;

import Database.JDBC;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static Database.JDBC.connection;

public class Utilities {
    private static final ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /**
     * This is an Older methood of deleting it is still Kept here for version control
     *
     * @param customer
     * @return
     */
    public static Boolean Delete_Customer(Customers customer) {
        if (allCustomers.contains(customer)) {
            allCustomers.remove(customer);
            return true;
        }
        return false;
    }

    /**
     * This is for the Reports Functions it gets the Appointment Type and the Total of them
     *
     * @return
     */
    public static ObservableList<Appointments> Get_Appointment_Type_Total() {
        ObservableList<Appointments> appointmentListType = FXCollections.observableArrayList();
        try {
            //It selects Type and counts every one of them from appointments
            String sql = "SELECT Type, Count(*) AS NUM FROM appointments GROUP BY Type";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //Then info is inserted into here
                int Month_Total = rs.getInt("NUM");
                String Month = rs.getString("Type");
                Appointments results = new Appointments(Month, Month_Total);
                appointmentListType.add(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentListType;
    }

    /**
     * This is My updated Appointment Feature adter the old one stopped working
     *
     * @param appointmentId
     */
    public static void deleteAppointment(int appointmentId) {
        try {
            String sqldelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteAppoint = JDBC.connection.prepareStatement(sqldelete);
            deleteAppoint.setInt(1, appointmentId);
            deleteAppoint.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is also for the reports features it gets the Appointment and Tpye and Month
     * it is used to display
     *
     * @return
     */
    public static ObservableList<Appointments> Get_Appointment_Type_Month() {
        ObservableList<Appointments> All_Appointments_Month = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS NUM FROM appointments GROUP BY Month";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //The Info From the query is inserted into here
                int Month_Total = rs.getInt("NUM");
                String Month = rs.getString("Month");


                Appointments results = new Appointments(Month, Month_Total);
                All_Appointments_Month.add(results);
            }
        } catch (SQLException e) {
            //This is Just in case to print Error Message if one were to Occur
            e.printStackTrace();
        }
        return All_Appointments_Month;
    }

    /**
     * This Gets Contact ID from Contacts it is used in Appoinment scheduling when selecting a contact
     *
     * @param Contact
     * @return
     * @throws SQLException
     */
    public static int Get_Contact_ID(String Contact) throws SQLException {
        int contactId = 0;
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, Contact);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * This Gets the Appoinment Based on the USER ID
     * it is used in the Contacts Report where it shows
     * Appointments booked by a particular Contact
     *
     * @param User_ID
     * @return
     */
    public static ObservableList<Appointments> Get_Appointments_User_ID(int User_ID) {
        ObservableList<Appointments> All_User_Appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = '" + User_ID + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            All_User_Appointments = FXCollections.observableArrayList();
            //And it inserts all the query in the ITems Below
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                //LocalDateTime start = convertTimeDateLocal(rs.getTimestamp("Start").toLocalDateTime());
                LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();
                //LocalDateTime end = convertTimeDateLocal(rs.getTimestamp("End").toLocalDateTime());
                LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                LocalTime Start_Time = Start.toLocalTime();
                LocalTime End_Time = End.toLocalTime();
                LocalDate Starting_Date = Start.toLocalDate();
                LocalDate Ending_Date = End.toLocalDate();
                Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End
                        , Customer_ID, user_ID, Contact_ID, Start_Time, End_Time, Starting_Date, Ending_Date);
                All_User_Appointments.add(appointment);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
        return All_User_Appointments;
    }

    /**
     * This Feature gets All the Working Times and as well as adding 30 minutes increments
     *
     * @return
     */
    public static ObservableList<LocalTime> getTimes() {
        ObservableList<LocalTime> appointmentTimeList = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(1, 00);
        LocalTime end = LocalTime.MIDNIGHT.minusHours(1);

        while (start.isBefore(end.plusSeconds(2))) {

            appointmentTimeList.add(start);
            start = start.plusMinutes(30);

        }
        return appointmentTimeList;
    }

    /**
     * This gets Contact ID from Appointments
     *
     * @param Contact_ID
     * @return
     */
    public static ObservableList<Appointments> getContactAppointment(int Contact_ID) {
        ObservableList<Appointments> Contact_Appointment = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID  = '" + Contact_ID + "'";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                int contact_ID = rs.getInt("Contact_ID");
                //String Contact_Name = rs.getString("Contact_Name");
                String Type = rs.getString("Type");
                LocalDateTime Start_Date = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End_Date = rs.getTimestamp("End").toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Location = rs.getString("Location");
                LocalTime Start_Time = Start_Date.toLocalTime();
                LocalTime End_Time = End_Date.toLocalTime();
                LocalDate Starting_Date = Start_Date.toLocalDate();
                LocalDate Ending_Date = End_Date.toLocalDate();
                Appointments Appointment_View = new Appointments(Appointment_ID, Title, Type, Description, Location,
                        Start_Date, End_Date, Customer_ID, User_ID, contact_ID, Start_Time, End_Time, Starting_Date, Ending_Date);
                Contact_Appointment.add(Appointment_View);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Contact_Appointment;
    }

    public static void deleteCustomer2(int customerId) {
        try {
            String sqldelete = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement deleteCust = connection.prepareStatement(sqldelete);
            deleteCust.setInt(1, customerId);
            deleteCust.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This Gets Monthly Appointments for the Radio Buttons and then it displays All Appointments during that month
     *
     * @return
     */
    public static ObservableList<Appointments> Get_Monthly_Appointments() {
        ObservableList<Appointments> Monthly_Appointment = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE MONTH(START) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                int contact_ID = rs.getInt("Contact_ID");
                //String Contact_Name = rs.getString("Contact_Name");
                String Type = rs.getString("Type");
                LocalDateTime Start_Date = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End_Date = rs.getTimestamp("End").toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Location = rs.getString("Location");
                LocalTime Start_Time = Start_Date.toLocalTime();
                LocalTime End_Time = End_Date.toLocalTime();
                LocalDate Starting_Date = Start_Date.toLocalDate();
                LocalDate Ending_Date = End_Date.toLocalDate();
                Appointments Appointment_View = new Appointments(Appointment_ID, Title, Type, Description, Location,
                        Start_Date, End_Date, Customer_ID, User_ID, contact_ID, Start_Time, End_Time, Starting_Date, Ending_Date);
                Monthly_Appointment.add(Appointment_View);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Monthly_Appointment;
    }

    /**
     * This Gets All the Weekly Appointment
     *
     * @return
     */
    public static ObservableList<Appointments> Get_Weekly_Appointments() {
        ObservableList<Appointments> Weekly_Appointments = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                int contact_ID = rs.getInt("Contact_ID");
                //String Contact_Name = rs.getString("Contact_Name");
                String Type = rs.getString("Type");
                LocalDateTime Start_Date = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End_Date = rs.getTimestamp("End").toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Location = rs.getString("Location");
                LocalTime Start_Time = Start_Date.toLocalTime();
                LocalTime End_Time = End_Date.toLocalTime();
                LocalDate Starting_Date = Start_Date.toLocalDate();
                LocalDate Ending_Date = End_Date.toLocalDate();
                Appointments Appointment_View = new Appointments(Appointment_ID, Title, Type, Description, Location,
                        Start_Date, End_Date, Customer_ID, User_ID, contact_ID, Start_Time, End_Time, Starting_Date, Ending_Date);
                Weekly_Appointments.add(Appointment_View);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Weekly_Appointments;
    }


}
