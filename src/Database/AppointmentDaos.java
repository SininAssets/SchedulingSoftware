package Database;

import Model.Appointments;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface AppointmentDaos {
    //This Gets All Appointments
    ObservableList<Appointments> getAllAppointments();
    //This Deletes The Appointments
    //public int deleteAppointment(int Appointment_ID, int Customer_ID, String Type);

    //public int Update_Appointment(int Appointment_ID,int Customer_ID,String Title,String Type,String Description,String Location,LocalDateTime Start_Date,
    //                              LocalDateTime End_Date,int User_ID,int Contact_ID);
    //This Helps Update Appointment the Above is Simmilar it is Used for testing
    int Update_Appointment(int Customer_ID, String Title, String Type, String Description, String Location, LocalDateTime Start_Date,
                           LocalDateTime End_Date, int User_ID, int Contact_ID, int Appointment_ID);


}



