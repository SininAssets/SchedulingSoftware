package Controller;

import Database.*;
import LampadaStuff.LampadaStuff;
import Model.Contacts;
import Model.Customers;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import main.TimeStuff;
import main.Utilities;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

//Most Be implemented as initializable in order to Make the Combo box visible
public class AddAppointmentScreen3Controller implements Initializable {
    @FXML
    private javafx.scene.control.TextField Add_Description;
    @FXML
    private javafx.scene.control.TextField Add_Type;
    @FXML
    private javafx.scene.control.TextField Add_Title;
    @FXML
    private javafx.scene.control.TextField Add_Location;
    @FXML
    private DatePicker Start_Date_Calendar;
    @FXML
    private DatePicker End_Date_Calendar;
    @FXML
    private ComboBox Start_Time_Drop;

    @FXML
    private ComboBox End_Time_Drop;
    @FXML
    private ComboBox<Customers> Customer_Drop;
    @FXML
    private ComboBox<User> User_Drop;
    @FXML
    private ComboBox<Contacts> Contact_Drop;

    //ID's for items

    //Must Be made public or else it would not get
    //Recognized
    private int Customer_ID;

    private String Title;
    private String Type;
    private String Description;
    private String Location;
    private LocalDateTime Start_Date;
    private LocalDateTime End_Date;
    private LocalDate Starting_Date;
    private LocalDate Ending_Date;
    private LocalTime Start_Time;
    private LocalTime End_Time;
    private LocalDateTime Start_DateTime;
    private LocalDateTime End_DateTime;
    private int Contact_ID;
    private int User_ID;

    //This sends the User Back to the Main Screen
    public void Cancel_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    //This Saves the Apppointment the User Wants to ADd
    public void Save_Btn_Clicked(ActionEvent event) throws IOException {
        try {
            //This makes sure everything it correct
            boolean formatError = false;
            //AppointmentDaos appointment = new AppointmentImpl();
            Customer_ID = Customer_Drop.getSelectionModel().getSelectedItem().getCustomer_ID();
            Title = Add_Title.getText();
            Type = Add_Type.getText();
            Description = Add_Description.getText();
            Location = Add_Location.getText();
            Starting_Date = Start_Date_Calendar.getValue();
            Ending_Date = End_Date_Calendar.getValue();
            Start_Time = (LocalTime) Start_Time_Drop.getSelectionModel().getSelectedItem();
            End_Time = (LocalTime) End_Time_Drop.getSelectionModel().getSelectedItem();
            //This part is for the Display
            Start_Date = LocalDateTime.of(Starting_Date.getYear(), Starting_Date.getMonth(), Starting_Date.getDayOfMonth(),
                    Start_Time.getHour(), Start_Time.getMinute());
            End_Date = LocalDateTime.of(Ending_Date.getYear(), Ending_Date.getMonth(), Ending_Date.getDayOfMonth(),
                    End_Time.getHour(), End_Time.getMinute());
            User_ID = User_Drop.getSelectionModel().getSelectedItem().getUser_ID();
            Contact_ID = Contact_Drop.getSelectionModel().getSelectedItem().getContact_ID();
            //Makes sure Title isnt Empty
            if (Title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Title");
                Optional<ButtonType> result = alert.showAndWait();
                //This Makes Sure Type isnt Empty
            } else if ((Type.isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Type");
                Optional<ButtonType> result = alert.showAndWait();
                //This Makes Sure Description isnt Empty
            } else if (Description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Description");
                Optional<ButtonType> result = alert.showAndWait();
                //This Makes Sure Location Isnt Empty
            } else if (Location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Description");
                Optional<ButtonType> result = alert.showAndWait();
                //This Makes Sure its within Buissness Hours
            } else if (TimeStuff.businessHours(Start_Date, End_Date)) {
                //This Makes Sure there are no overlaps
            } else if (DBTimeTest.overlapCheck(Customer_ID, Start_Date, End_Date)) {

            } else {
                //This Gets information from the Utility Dao and Impl
                UtilityDao appointment = new UtilityDaoImpl();
                //This Adds the Appointment once All the previous Steps were fullfilled
                appointment.Add_Appointment(Customer_ID, Title, Type, Description, Location, Start_Date, End_Date, User_ID, Contact_ID);

                Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
                Scene scene = new Scene(addParts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

                LampadaStuff.LampadaAdd AddSucsess = x -> "You have Fulfilled the " + x;
                System.out.println(AddSucsess.AddSucsess("Add Process"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }


    }

    //This Is an Extra Feature Added it clears the Titles
    public void Clear_Title_Btn_Click(ActionEvent event) {
        Add_Title.setText("");

    }

    //This Clears the Type
    public void Clear_Type_Btn_Click(ActionEvent event) {
        Add_Type.setText("");
    }

    //This Clears The Description
    public void Clear_Description_Btn_Click(ActionEvent event) {
        Add_Description.setText("");
    }

    //This Clears the Location
    public void Clear_Location_Btn_Click(ActionEvent event) {
        Add_Location.setText("");
    }

    @Override
    //This Sets the Items Like Combo Boxes
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Change the new york to something else later if needed
            ZoneId ZoneID = ZoneId.systemDefault();
            ZoneId BizID = ZoneId.of("America/New_York");
            LocalTime BizStart = LocalTime.of(8, 0);
            int TotalWorkHrs = 8;

            JDBC.openConnection();
            UtilityDao utility = new UtilityDaoImpl();
            CustomerDaos customer = new CustomerImpl();

            //This makes all the ComboBoxes and the time thing Visible
            Start_Date_Calendar.setValue(LocalDate.now());
            Start_Time_Drop.setItems(Utilities.getTimes());
            Start_Time_Drop.getSelectionModel().selectFirst();
            End_Date_Calendar.setValue(LocalDate.now());
            End_Time_Drop.setItems(Utilities.getTimes());
            End_Time_Drop.getSelectionModel().selectFirst();
            Customer_Drop.setItems(customer.getAllCustomers());
            Customer_Drop.getSelectionModel().selectFirst();
            User_Drop.setItems(utility.Get_AllUsers());
            User_Drop.getSelectionModel().selectFirst();
            Contact_Drop.setItems(utility.Get_AllContacts());
            Contact_Drop.getSelectionModel().selectFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
