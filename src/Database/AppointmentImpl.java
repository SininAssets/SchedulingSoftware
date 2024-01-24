package Database;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static Database.JDBC.connection;

public class AppointmentImpl implements AppointmentDaos {

    //Gets All Appointments as Observable Array List
    ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    //Gets the Appointment Table
    @FXML
    private TableView<Appointments> Appointments_Table;

    //Gets Appointment returns All Appointments
    public Appointments getAppointment(int Appointment_ID) {
        return (Appointments) allAppointments;
    }

    //This Gets All Appointments
    @Override
    public ObservableList<Appointments> getAllAppointments() {
        try {
            //Selects Everything From Appointments
            String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

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
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                LocalTime Start_Time = Start.toLocalTime();
                LocalTime End_Time = End.toLocalTime();
                LocalDate Starting_Date = Start.toLocalDate();
                LocalDate Ending_Date = End.toLocalDate();
                Appointments appointments = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End
                        , Customer_ID, User_ID, Contact_ID, Start_Time, End_Time, Starting_Date, Ending_Date);
                allAppointments.add(appointments);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
        return allAppointments;
    }


    //This Updates Appointments
    @Override
    public int Update_Appointment(int Customer_ID, String Title, String Type, String Description, String Location, LocalDateTime Start_Date,
                                  LocalDateTime End_Date, int User_ID, int Contact_ID, int Appointment_ID) {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE appointments SET Customer_ID=?,Title=?, Type=?, Description=?, " +
                    "Location=?, Start=?, End=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            //Below Are All the setters stuff from the Query
            ps.setInt(1, Customer_ID);
            //Sets Title
            ps.setString(2, Title);
            //Sets Type
            ps.setString(3, Type);
            //Sets Description
            ps.setString(4, Description);
            //Sets Location
            ps.setString(5, Location);
            /**
             * Below TimeStamps Must be formatted this way ive tried other methoods
             * but it did not work
             */
            ps.setTimestamp(6, Timestamp.valueOf(Start_Date));
            ps.setTimestamp(7, Timestamp.valueOf(End_Date));
            ps.setInt(8, User_ID);
            ps.setInt(9, Contact_ID);
            ps.setInt(10, Appointment_ID);
            //This Basically Executs the Query
            rowsAffected = ps.executeUpdate();

            //This makes it so If it Affects Any Rows it is counted as sucsess
            if (rowsAffected > 0) {
                System.out.println("Appointment UPDATE was successful!");
                //If Any did not get updates its a fail
            } else {
                System.out.println("Appointment UPDATE Failed!");
            }
            //This Shows what kind of Error Occured
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }


}
