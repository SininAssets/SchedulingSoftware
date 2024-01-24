package Controller;

import Database.*;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import static Controller.DashboardScreen2Controller.Selected_Customer;

public class UpdateCustomerScreen6Controller implements Initializable {
    Customers Selected_Customer = null;
    String Customer_Name;
    String Address;
    String Postal_Code;
    String Phone;
    @FXML
    private TextField Modify_Name;
    @FXML
    private TextField Modify_Address;
    @FXML
    private TextField Modify_Phone;
    @FXML
    private TextField Modify_Postal;
    @FXML
    private ComboBox<Divisions> Modify_State;
    @FXML
    private ComboBox<Countries> Modify_Country;
    private int Division_ID;
    private Customers customer;
    private int Country_ID;
    @FXML
    private TableView Customer_Table;

    /**
     * In Future Add the Feature to ask if theyre sure
     * for convenience it has been temporarily Removed
     *
     * @param event
     * @throws IOException
     */
//sends User Back to Main Screen
    public void Cancel_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    //Makes the Country Drop Table Visisble

    /**
     * An Error has occured here it was fixed by making aditional Changed to the Filttered List
     * After building Program there was an Error that was unseen more is Documented in the Filtered List
     *
     * @param event
     */
    public void Country_Select(ActionEvent event) {
        Country_ID = Modify_Country.getValue().getCountry_ID();
        Modify_State.setItems(DataListManger.FilteredLIst(Country_ID));
        Modify_State.getSelectionModel().selectFirst();
    }

    /**
     * Do not move it from here or it will cause an Error
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountriesDaos country = new CountriesImpl();

        Modify_Country.setItems(country.getAllCountries());

    }

    //Clears Name
    public void Name_Clear_Btn_Click(ActionEvent event) {
        Modify_Name.setText("");
    }

    //Clears Address
    public void Address_Clear_Btn_Click(ActionEvent event) {
        Modify_Address.setText("");
    }

    //Clears Phone
    public void Phone_Clear_Btn_Click(ActionEvent event) {
        Modify_Phone.setText("");
    }

    //Clears Postal
    public void Postal_Clear_Btn_Click(ActionEvent event) {
        Modify_Postal.setText("");
    }
    //This Sames the Updated Customers

    /**
     * In The Future add the Are you sure Feature
     *
     * @param event
     */
    public void Save_Btn_Clicked(ActionEvent event) {
        try {
            boolean formatError = false;
            //Following Below gets Items from the Text Fields
            int Customer_ID = Selected_Customer.getCustomer_ID();
            Customer_Name = Modify_Name.getText();
            Address = Modify_Address.getText();
            Postal_Code = Modify_Postal.getText();
            Phone = Modify_Phone.getText();
            Division_ID = Modify_State.getValue().getDivision_ID();
            //If there are no Error it adds the Customer
            if (!formatError) {
                CustomerDaos customers = new CustomerImpl();
                customers.Update_Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
                //And Sends them To Main Page

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

    /**
     * This part Gets customer Information it must be here
     * previous Attempts to move it proved unsucsessfull
     * Ive attempted putting the set text in initialize but it didnt work
     * it caused many Errors until ive came up with this solution
     *
     * @param customer
     */
    public void Get_Customer_Information(Customers customer) {
        //customer = DashboardScreen2Controller.Get_Selected_Customer();
        JDBC.openConnection();
        CountriesDaos countries = new CountriesImpl();
        DivisionsDao division = new DivisionsImpl();
        //Sets All The Text Stuff
        Modify_Name.setText(String.valueOf(customer.getCustomer_Name()));
        Modify_Address.setText(String.valueOf(customer.getAddress()));
        Modify_Phone.setText(String.valueOf(customer.getPhone()));
        Modify_Postal.setText(String.valueOf(customer.getPostal_Code()));

        Selected_Customer = customer;

        Countries Selected_Country = null;
        //This gets the country ID to equal to Selected Customers Country ID
        //And in the End it equals them
        for (Countries country : countries.getAllCountries()) {
            if (country.getCountry_ID() == Selected_Customer.getCountry_ID()) {
                Selected_Country = country;
                break;
            }
        }
        //This Gets the Selected Country from the ComboBox
        Modify_Country.getSelectionModel().select(Selected_Country);
        //This gets the Country ID from Selected Country for future uses
        Country_ID = Selected_Country.getCountry_ID();
        //This Makes the State/Province Visible
        Modify_State.setItems(DataListManger.FilteredLIst(Country_ID));
        Divisions Selected_Division = null;
        //This Gets Division ID from divisions Model and Equals it to Division ID from Customer
        for (Divisions divisions : DataListManger.FilteredLIst(Country_ID)) {
            if (divisions.getDivision_ID() == Selected_Customer.getDivisionID()) {
                //In Here They Equal
                Selected_Division = divisions;
                break;
            }
        }
        //The Gets the Selected Division from the ComboBox
        Modify_State.getSelectionModel().select(Selected_Division);
        //This Gets the Division ID from the Selected Division as
        //Defined Above
        Division_ID = Selected_Division.getDivision_ID();

    }
}
