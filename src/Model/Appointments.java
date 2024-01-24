package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointments {


    //setting up variables for the Appointment class

    String Month;
    int Type_Total;
    int Month_Total;
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private LocalDateTime Start_Date;
    private LocalDateTime End_Date;
    private String Type;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;
    private LocalTime Start_Time;
    private LocalTime End_Time;
    private LocalDate Starting_Date;
    private LocalDate Ending_Date;
    private ObservableList<Appointments> AllAppointments = FXCollections.observableArrayList();
    //This is Required to make the Appointment Month and Total Visible in the Reports Page
    public Appointments(String Month, int Month_Total) {
        this.Month = Month;
        this.Month_Total = Month_Total;
    }
    public Appointments(int Appointment_ID, String Title, String Type, String Description, String Location,
                        LocalDateTime Start_Date, LocalDateTime End_Date, int Customer_ID, int User_ID, int Contact_ID, LocalTime Start_Time, LocalTime End_Time, LocalDate Starting_Date, LocalDate Ending_Date) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Type = Type;
        this.Description = Description;
        this.Location = Location;
        this.Start_Date = Start_Date;
        this.End_Date = End_Date;

        //this.Start_DateTime = Start_DateTime;
        //this.End_DateTime = End_DateTime
        this.Contact_ID = Contact_ID;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Start_Time = Start_Time;
        this.End_Time = End_Time;
        this.Starting_Date = Starting_Date;
        this.Ending_Date = Ending_Date;


    }

    public LocalDateTime getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(LocalDateTime Start_Date) {
        this.Start_Date = Start_Date;
    }

    public LocalDateTime getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(LocalDateTime End_Date) {
        this.End_Date = End_Date;
    }

    public LocalTime getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(LocalTime start_Time) {
        Start_Time = start_Time;
    }

    public LocalTime getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(LocalTime end_Time) {
        End_Time = end_Time;
    }

    public LocalDate getStarting_Date() {
        return Starting_Date;
    }

    public void setStarting_Date(LocalDate starting_Date) {
        Starting_Date = starting_Date;
    }

    public LocalDate getEnding_Date() {
        return Ending_Date;
    }

    public void setEnding_Date(LocalDate ending_Date) {
        Ending_Date = ending_Date;
    }

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int Appointment_ID) {
        this.Appointment_ID = Appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public LocalDateTime getStart() {
        return Start_Date;
    }

    public LocalDateTime getEnd() {
        return End_Date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID = Customer_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int Contact_ID) {
        this.Contact_ID = Contact_ID;
    }

    public int getMonth_Total() {
        return Month_Total;
    }

    public void setMonth_Total(int Month_Total) {
        this.Month_Total = Month_Total;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public ObservableList<Appointments> getAllAppointments() {
        return AllAppointments;
    }

    public void setAllAppointments(ObservableList<Appointments> AllAppointments) {
        this.AllAppointments = AllAppointments;
    }

    public void addAppointment(Appointments newAppointment) {
        AllAppointments.add(newAppointment);
    }


}