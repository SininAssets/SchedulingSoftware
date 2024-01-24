package Controller;

import Database.*;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportScreen7Controller implements Initializable {
    public Parent scene;
    UtilityDao utility = new UtilityDaoImpl();
    ObservableList<Contacts> AllContacts = utility.Get_AllContacts();
    AppointmentDaos appointment = new AppointmentImpl();
    @FXML
    private TableView<Appointments> Reports_Table;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_ID;
    @FXML
    private TableColumn<Appointments, String> Appointment_Title;
    @FXML
    private TableColumn<Appointments, String> Appointment_Type;
    @FXML
    private TableColumn<Appointments, String> Appointment_Description;
    @FXML
    private TableColumn<Appointments, String> Appointment_Location;
    @FXML
    private TableColumn<Appointments, Timestamp> Appointment_Start;
    @FXML
    private TableColumn<Appointments, Timestamp> Appointment_End;
    @FXML
    private TableColumn<Appointments, String> Appointment_Contact;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_Customer_ID;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_User_ID;
    @FXML
    private ComboBox Contact_Drop;
    @FXML
    private TextField Reports_Search_Field;

    //This Button Returns to the Main Page
    public void Return_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //This Makes All The Columns Visisble
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Appointment_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Appointment_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appointment_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Appointment_Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Appointment_Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        Appointment_End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Appointment_Contact.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Appointment_Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Appointment_User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));


        //This sets the Items for the Contact Drop
        Contact_Drop.setItems(AllContacts);
        //This Is A placeHolder
        Reports_Table.setPlaceholder(new Label("Please Select a Contact"));
        //Reports_Table.setItems(Appointments.getAllAppointments());
        Reports_Table.refresh();
    }

    //This is for the Search Bar in order to make it work
    public void Reports_Search_Btn_Click(ActionEvent event) {
        JDBC.openConnection();
        //This is basically Setting up Pre-Requisistes to make search bar work
        AppointmentDaos Appointment = new AppointmentImpl();
        ObservableList<Appointments> All_Appointments = Appointment.getAllAppointments();
        ObservableList<Appointments> Found_Appointments = FXCollections.observableArrayList();
        //This is where it begins
        //The Code Collects the Text From the Search Field
        String AppointmentSearch = Reports_Search_Field.getText();
        try {
            for (Appointments appointment : All_Appointments) {
                if (String.valueOf(appointment.getAppointment_ID()).contains(AppointmentSearch) ||
                        //Then It searched Appointments based on the texts
                        appointment.getTitle().contains(AppointmentSearch)) {
                    //Once The Appointments Are found it Goes into an empty Table
                    //That Will Be Displayed
                    Found_Appointments.add(appointment);
                }
            }
            //This part of the Code Actually Displays the Table
            Reports_Table.setItems(Found_Appointments);
            //This Part Catches any Exception
        } catch (Exception e) {
        }
    }

    //This is needed in order to make the Contact Drop work, once selected it Shows all things by this
    //Contact
    public void Populate_Table_By_Contact(ActionEvent event) throws SQLException {
        //This part of Code gets the Selected Contact and its value
        String Contact = String.valueOf(Contact_Drop.getValue());
        //This Collects the Contact ID from the selection
        int Contact_ID = main.Utilities.Get_Contact_ID(Contact);

        if (main.Utilities.getContactAppointment(Contact_ID).isEmpty()) {
            //This part of the Code makes the Place holder in case there are no Appointments by a certain User
            Reports_Table.setPlaceholder(new Label(Contact + "has no Appointments"));
            //This Refreshes the Table
            Reports_Table.refresh();
            //This does same things But for others
            for (int i = 0; i < Reports_Table.getItems().size(); i++) {
                Reports_Table.getItems().clear();
                Reports_Table.setPlaceholder(new Label(Contact + " has no appointments."));
            }
        } else {
            //This is the most important part as it makes things visible after selection
            Reports_Table.setItems(main.Utilities.getContactAppointment(Contact_ID));
        }
    }

    //This brings User to the Country Name and Country Total  Reports Page
    public void Country_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/ReportsTypeCountry.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //This Brings User to The Reports Page with Month and Month total
    //As well as Type and Type Total, a 2 in one basically
    public void Month_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/ReportsTypeMonthTotal8.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
}
