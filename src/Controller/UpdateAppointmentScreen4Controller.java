package Controller;

import Database.*;
import Model.Appointments;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.TimeStuff;
import main.Utilities;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateAppointmentScreen4Controller implements Initializable {
    /**
     * This Makes sure The selected Appointment
     * works so it must be Null at start Do not Change
     */
    Appointments Selected_Appointment = null;
    @FXML
    private TextField Modify_ID;
    @FXML
    private TextField Modify_Title;
    @FXML
    private TextField Modify_Description;
    @FXML
    private TextField Modify_Location;
    @FXML
    private TextField Modify_Type;
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
    private int Appointment_ID;
    private int Customer_ID;
    private Appointments appointment;

    /**
     * Sends User Back To main Screen
     * In the Future Add the Are you sure Feature
     *
     * @param event
     * @throws IOException
     */
    public void Cancel_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * Alot of Errors Occured Here they were fixed by
     * trial and Error
     *
     * @param event
     * @throws IOException
     */
    public void Save_Btn_Clicked(ActionEvent event) throws IOException {
        try {
            boolean formatError = false;
            int Appointment_ID = Selected_Appointment.getAppointment_ID();
            Title = Modify_Title.getText();
            Type = Modify_Type.getText();
            Description = Modify_Description.getText();
            Location = Modify_Location.getText();
            Contact_ID = Contact_Drop.getValue().getContact_ID();
            User_ID = User_Drop.getValue().getUser_ID();
            Customer_ID = Customer_Drop.getValue().getCustomer_ID();
            Starting_Date = Start_Date_Calendar.getValue();
            Ending_Date = End_Date_Calendar.getValue();
            Start_Time = (LocalTime) Start_Time_Drop.getSelectionModel().getSelectedItem();
            End_Time = (LocalTime) End_Time_Drop.getSelectionModel().getSelectedItem();
            Start_Date = LocalDateTime.of(Starting_Date.getYear(), Starting_Date.getMonth(), Starting_Date.getDayOfMonth(),
                    Start_Time.getHour(), Start_Time.getMinute());
            End_Date = LocalDateTime.of(Ending_Date.getYear(), Ending_Date.getMonth(), Ending_Date.getDayOfMonth(),
                    End_Time.getHour(), End_Time.getMinute());

            if (Title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Title");
                Optional<ButtonType> result = alert.showAndWait();

            } else if ((Type.isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Type");
                Optional<ButtonType> result = alert.showAndWait();

            } else if (Description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Description");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (Location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Empty Description");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (TimeStuff.businessHours(Start_Date, End_Date)) {
                //} else if (DBTimeTest.overlapCheck(Customer_ID,Start_Date,End_Date)) {
                //    return;

            } else {
                AppointmentDaos appointment = new AppointmentImpl();
                appointment.Update_Appointment(Customer_ID, Title, Location, Type, Description, Start_Date, End_Date, User_ID, Contact_ID, Appointment_ID);

                Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
                Scene scene = new Scene(addParts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void Get_AllAppointments(Appointments appointment) {
        Selected_Appointment = appointment;
        //This makes all the ID,Title,Type,Desc,Location visible upon the press of a button
        Modify_ID.setText(String.valueOf(appointment.getAppointment_ID()));
        Modify_Title.setText(String.valueOf(appointment.getTitle()));
        Modify_Type.setText(String.valueOf(appointment.getType()));
        Modify_Description.setText(String.valueOf(appointment.getDescription()));
        Modify_Location.setText(String.valueOf(appointment.getLocation()));


        JDBC.openConnection();
        UtilityDao utility = new UtilityDaoImpl();
        CustomerDaos customer = new CustomerImpl();

        //This makes all the ComboBoxes and the time thing Visible
        Start_Date_Calendar.setValue(Selected_Appointment.getStarting_Date());
        //gets the Selected Item of Start Time
        Start_Time_Drop.getSelectionModel().select(Selected_Appointment.getStart_Time());
        //Gets Selected Item of The End Time
        End_Time_Drop.getSelectionModel().select(Selected_Appointment.getEnd_Time());
        //This Sets the value
        End_Date_Calendar.setValue(Selected_Appointment.getEnding_Date());

        Customers Selected_Customer = null;
        for (Customers customers : customer.getAllCustomers()) {
            if (customers.getCustomer_ID() == Selected_Appointment.getCustomer_ID()) {
                Selected_Customer = customers;
            }
        }

        /**
         * Do not make the Selected User or Contact not null as it will make
         * alot of Issues because its required for opertation
         */
        Customer_Drop.getSelectionModel().select(Selected_Customer);
        User Selected_User = null;
        for (User user : utility.Get_AllUsers()) {
            //This makes sure the User_ID is same as Appointments User ID
            if (user.getUser_ID() == Selected_Appointment.getUser_ID()) {
                Selected_User = user;
                break;
            }
        }
        //This is a ComboBox
        //In HEre it selects the Selected User from the User combo box drop
        User_Drop.getSelectionModel().select(Selected_User);

        Contacts Selected_Contact = null;
        //Same here as Last time
        for (Contacts contact : utility.Get_AllContacts()) {
            if (contact.getContact_ID() == Selected_Appointment.getContact_ID()) {
                Selected_Contact = contact;
                break;
            }
        }
        //This gets the Selected Contact from the Contact Drop
        Contact_Drop.getSelectionModel().select(Selected_Contact);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointment = DashboardScreen2Controller.Get_Selected_Appointment();
        /**
         * the Following below are stuff needed for time stuff
         */
        ZoneId ZoneID = ZoneId.systemDefault();
        ZoneId BizID = ZoneId.of("America/New_York");
        LocalTime BizStart = LocalTime.of(8, 0);
        int TotalWorkHrs = 8;

        JDBC.openConnection();
        UtilityDao utility = new UtilityDaoImpl();
        CustomerDaos customer = new CustomerImpl();

        //This makes all the ComboBoxes and the time thing Visible
        /**
         * This Makes all the Items Visible from the menu
         */
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


    }

    public void Clear_Type_Btn_Click(ActionEvent event) {
        Modify_Type.setText("");
    }

    public void Clear_Title_Btn_Click(ActionEvent event) {
        Modify_Title.setText("");
    }

    public void Clear_Description_Btn_Click(ActionEvent event) {
        Modify_Description.setText("");
    }

    public void Clear_Location_Btn_Click(ActionEvent event) {
        Modify_Location.setText("");
    }
}
