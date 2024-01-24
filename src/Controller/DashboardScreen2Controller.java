package Controller;

import Database.*;
import LampadaStuff.LampadaStuff;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
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
import main.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardScreen2Controller implements Initializable {
    /**
     * Prevents Null pointer Exception For Customers
     */
    private static Customers Selected_Customer;
    /**
     * Prevents Null pointer Exception For Appointments
     *
     * @param url
     * @param resourceBundle
     */

    private static Appointments Selected_Appointment;
    private static Countries Selected_Country;
    private static Customers customer;
    public Parent scene;
    Alert AlertError = new Alert(Alert.AlertType.ERROR);
    Stage stage;
    @FXML
    private TableView<Appointments> Appointments_Table;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_ID_Col;
    @FXML
    private TableColumn<Appointments, String> Appointment_Title_Col;
    @FXML
    private TableColumn<Appointments, String> Appointment_Type_Col;
    @FXML
    private TableColumn<Appointments, String> Appointment_Description_Col;
    @FXML
    private TableColumn<Appointments, String> Appointment_Location_Col;
    @FXML
    private TableColumn<Appointments, Timestamp> Appointment_Start_Col;
    @FXML
    private TableColumn<Appointments, Timestamp> Appointment_End_Col;
    @FXML
    private TableColumn<Appointments, String> Appointment_Contact_Col;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_Customer_ID_Col;
    @FXML
    private TableColumn<Appointments, Integer> Appointment_User_ID_Col;
    @FXML
    private TableView<Customers> Customer_Table;
    @FXML
    private TableColumn<Customers, Integer> Customer_ID_Col;
    @FXML
    private TableColumn<Customers, String> Customer_Name_Col;
    @FXML
    private TableColumn<Customers, String> Customer_Address_Col;
    @FXML
    private TableColumn<Customers, String> Customer_Phone_Number_Col;
    @FXML
    private TableColumn<Customers, String> Customer_Location_Col;
    @FXML
    private TableColumn<Customers, Integer> Customer_Postal_Col;
    @FXML
    private TableColumn<Customers, String> Country_Column;
    @FXML
    private TextField Search_Appointment_Field;
    @FXML
    private TextField Search_Customer_Field;

    public static Customers Get_Selected_Customer() {

        return Selected_Customer;
    }

    public static Appointments Get_Selected_Appointment() {

        return Selected_Appointment;
    }

    public static Countries getSelected_Country() {
        return Selected_Country;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //This makes the Appointment table visible
        Appointment_ID_Col.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Appointment_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Appointment_Type_Col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appointment_Description_Col.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Appointment_Location_Col.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Appointment_Start_Col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        Appointment_End_Col.setCellValueFactory(new PropertyValueFactory<>("End"));
        Appointment_Contact_Col.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Appointment_Customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Appointment_User_ID_Col.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        //This Makes the Customer Table Visible
        Customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name_Col.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Customer_Address_Col.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Customer_Phone_Number_Col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Customer_Location_Col.setCellValueFactory(new PropertyValueFactory<>("Division"));
        Customer_Postal_Col.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Country_Column.setCellValueFactory(new PropertyValueFactory<>("Country"));

        JDBC.openConnection();
        //This Sets All Customer Info
        CustomerDaos Customers = new CustomerImpl();
        Customer_Table.setItems(Customers.getAllCustomers());

        //This Sets All Appointment Info
        AppointmentDaos Appointments = new AppointmentImpl();
        Appointments_Table.setItems(Appointments.getAllAppointments());

        LampadaStuff.LampadaExpressMainS MainScreenEnter = (x, y) -> "Welcome to " + x + " Hope you have a " + y + ".!";
        System.out.println(MainScreenEnter.MainScreenEnter("Main Screen", "Productive Time"));
    }

    //This sends the User back to the Login Screen
    public void Exit_Program_Button_Click(ActionEvent event) throws IOException {
        //This Sends Out Confirmation if the User would Like to Sign Out or not
        Alert Confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        Confirmation.setTitle("Would you like to Sign Out ?");
        Confirmation.setContentText("Select Ok to sign out ");
        //This Sends the Box with OK button and Cancel Button if OK button is pressed
        //The User Signs Out
        Optional<ButtonType> result = Confirmation.showAndWait();
        if ((result.isPresent() && result.get() == ButtonType.OK)) {

            //Once OK is Pressed the User Is sent back to the Login Screen,Where from there they could leave the program
            Parent addParts = FXMLLoader.load(getClass().getResource("../View/LoginScreen1.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    /**
     * In the Future Add the Confirmations for Add Appointments or Users
     *
     * @param event
     * @throws IOException
     */
    //This sends the User to the Add Appointments Page
    public void Add_Appointment_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/AddAppointmentScreen3.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    //This Updates the Appointment
    public void Update_Appointment_Btn_Click(ActionEvent actionEvent) throws IOException {
        try {
            //Gets Info On the Appointment
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/UpdateAppointmentScreen4.fxml"));
            Parent scene = loader.load();
            UpdateAppointmentScreen4Controller UpdateAppointment = loader.getController();
            Appointments appointment = Appointments_Table.getSelectionModel().getSelectedItem();
            //Gets All the Appointments
            UpdateAppointment.Get_AllAppointments(appointment);

            //This sends the User away to the Update Appointments Page
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (RuntimeException e) {
            //This is what shows If Appointments Is not Selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointments Selected");
            alert.setContentText("Please select a Appointment to update!");
            alert.showAndWait();

        }

    }


    //This Button Sends the User to the Main Reports page, The Contacts Report Page

    //This sends the User to the Add Customer Page
    public void Add_Customer_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/AddCustomerScreen5.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //This Helps Update the Customers
    public void Update_Customer_Btn_Click(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/UpdateCustomerScreen6.fxml"));
            Parent scene = loader.load();
            //This gets All The Info On The Customer
            UpdateCustomerScreen6Controller UpdateCustomer = loader.getController();
            Customers customer = Customer_Table.getSelectionModel().getSelectedItem();
            UpdateCustomer.Get_Customer_Information(customer);

            //This is setting Up a Stage to send the User To the Update Customer Page
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (RuntimeException e) {
            //If A customer Is not Selected it will Cause this message to be sent out
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select a customer to update!");
            alert.showAndWait();

        }
    }

    public void Reports_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/ReportScreen7.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * This Creates the Functionality for the Button, It had to Be
     * Modified in order for it to work, as slightly Different
     * Version on The Delete Customer Button Didnt work
     *
     * @param event
     */

    //This Makes Delete Appointment Work
    public void Delete_Btn_Click_A(ActionEvent event) {
        if (Appointments_Table.getSelectionModel().getSelectedItem() == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error you did not select an Appointment");
            alertError.showAndWait();


        } else {
            //This sends out an Alert Confirming the User
            //If the user would like to delete the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deleting Appointment");
            alert.setContentText("Do you want to delete this Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            JDBC.openConnection();


            //If user presses OK it will delete the part
            //Since there are no Associates for part it will delete without
            //Additional Confirmation
            if ((result.isPresent() && result.get() == ButtonType.OK)) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Alert");
                confirmation.setContentText("Appointment ID# " + Appointments_Table.getSelectionModel().getSelectedItem().getAppointment_ID() + " for " + Appointments_Table.getSelectionModel().getSelectedItem().getType() + " has been cancelled.");
                confirmation.getButtonTypes().clear();
                confirmation.getButtonTypes().addAll(ButtonType.OK);
                confirmation.showAndWait();
                //Gets info About Selected Appointments
                Appointments selectedAppointment = Appointments_Table.getSelectionModel().getSelectedItem();
                ObservableList<Appointments> AllAppointments = FXCollections.observableArrayList();
                AppointmentDaos appointment = new AppointmentImpl();
                //Once the Info Is retrieved it Goes to the Delete Appointment Model In order to Delete it
                Utilities.deleteAppointment(Appointments_Table.getSelectionModel().getSelectedItem().getAppointment_ID());
                AllAppointments = appointment.getAllAppointments();
                //This Gets the Finished Results
                Appointments_Table.setItems(AllAppointments);
                //This Refreshes the Table
                Appointments_Table.refresh();

                LampadaStuff.DeleteSucses DeleteSucses = x -> "You have Fulfilled the " + x;
                System.out.println(DeleteSucses.DeleteSucses("Delete Process"));

            }
        }

    }

    //This Code Makes Delete Customer Functional
    public void Delete_Btn_Click_B(ActionEvent event) throws IOException {
        //This Is the Diffrentiator
        //between customers with and without Assosciated Appointments
        int count = 0;
        //This defines Appt
        AppointmentDaos Appt = new AppointmentImpl();
        //This gets All Appointments from Appointment Daos/ IMPL
        ObservableList<Appointments> appointmentList = Appt.getAllAppointments();
        //This gets the Selection from the customer table
        Customers customer = Customer_Table.getSelectionModel().getSelectedItem();

        // If no customer is selected - an error will inform the user
        if (customer == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error you did not select an Appointment");
            alertError.showAndWait();
        } else {
            int selectedCustomer = Customer_Table.getSelectionModel().getSelectedItem().getCustomer_ID();

            // Counts the number of associated appointments for the selected customer
            for (Appointments appointment : appointmentList) {
                int appointmentCustId = appointment.getCustomer_ID();
                if (appointmentCustId == selectedCustomer) {
                    count++;
                }
            }
            // Displays an alert letting the User know to delete the Assosciated AppointmentFirst
            if (count > 0) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error: This Customer has Assosciated Appointment");
                alertError.setHeaderText("Please delete Assosciated Appointments first");
                alertError.showAndWait();

            }
            // If there are no associated appointments for the selected user - an alert will be generated asking to confirm removal of the selected customer
            if (count == 0) {
                Alert confirmation = new Alert(Alert.AlertType.WARNING);
                //This is basically Confirmation to remove selected Customer
                confirmation.setTitle("Alert");
                confirmation.setContentText("Remove selected customer?");
                confirmation.getButtonTypes().clear();
                confirmation.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                confirmation.showAndWait();
                //This is what happens after user presses OK
                if (confirmation.getResult() == ButtonType.OK) {
                    CustomerDaos cust = new CustomerImpl();
                    //Goes to Utilities for deleteCustomer 2
                    Utilities.deleteCustomer2(Customer_Table.getSelectionModel().getSelectedItem().getCustomer_ID());
                    ObservableList<Customers> CustomerList = cust.getAllCustomers();
                    CustomerList = cust.getAllCustomers();
                    Customer_Table.setItems(CustomerList);
                    Customer_Table.refresh();
                    Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
                    Scene scene = new Scene(addParts);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } else if (confirmation.getResult() == ButtonType.CANCEL) {
                    confirmation.close();
                }
            }
        }

    }

    /**
     * In the Future add the Alerts Feature on the Search Appointments
     *
     * @param event
     */
    //This Is the Searching Feature for the Appointments
    public void Search_Appointment_Btn_Click(ActionEvent event) {
        JDBC.openConnection();
        //These Are the Pre requisites getting all the info needed
        AppointmentDaos Appointment = new AppointmentImpl();
        ObservableList<Appointments> All_Appointments = Appointment.getAllAppointments();
        ObservableList<Appointments> Found_Appointments = FXCollections.observableArrayList();
        //This is where it begins, The code searches the TextField and collects the Text
        String AppointmentSearch = Search_Appointment_Field.getText();
        try {
            for (Appointments appointment : All_Appointments) {
                //This Searches All the Info For the Required Info
                if (String.valueOf(appointment.getAppointment_ID()).contains(AppointmentSearch) ||
                        appointment.getTitle().contains(AppointmentSearch)) {
                    //Once the Required Info is Found it adds it to the Empty Table
                    Found_Appointments.add(appointment);
                }
            }
            //Afterwards This sets the new table and makes it visible
            Appointments_Table.setItems(Found_Appointments);
            //This Catches any exceptions
        } catch (Exception e) {
        }
    }

    //This Is A Searching Feature For Customers for once button is pressed

    /**
     * In The Future Add The Alerts Feature for the Search Customers Bar
     *
     * @param event
     */
    public void Search_Customer_Btn_Click(ActionEvent event) {
        JDBC.openConnection();
        //These Are pre requites Getting Info On Customers
        CustomerDaos Customer = new CustomerImpl();
        ObservableList<Customers> All_Customers = Customer.getAllCustomers();
        ObservableList<Customers> Found_Customers = FXCollections.observableArrayList();
        //This Part is where it begins, Customers Name is collected from the TextField
        String AppointmentSearch = Search_Customer_Field.getText();
        try {
            for (Customers customer : All_Customers) {
                //This Searches The Customer Info for the Searched String
                if (String.valueOf(customer.getCustomer_ID()).contains(AppointmentSearch) ||
                        customer.getCustomer_Name().contains(AppointmentSearch)) {//This Part Adds the Found Customers To an Empty Table
                    Found_Customers.add(customer);
                }
            }
            //This is the Important Part, It sets the Customers From an Empty Table
            //On to the Customer Table
            Customer_Table.setItems(Found_Customers);
            //This Catches Any Exceptions that May occur
        } catch (Exception e) {
        }

    }


    //This Makes All Appointments Visible Once Button Is Clicked
    public void Get_All_Appointments_Click(ActionEvent event) {
        JDBC.openConnection();
        AppointmentDaos Appointments = new AppointmentImpl();
        Appointments_Table.setItems(Appointments.getAllAppointments());

    }


    //This Makes all Appointments this Week Visible
    public void Current_Week_Radio_Click(ActionEvent actionEvent) {
        Appointments_Table.setItems(Utilities.Get_Weekly_Appointments());
    }

    //This Makes All Appointments This Month Visible
    public void Current_Month_Radio_Click(ActionEvent actionEvent) {
        Appointments_Table.setItems(Utilities.Get_Monthly_Appointments());
    }
}










