package Controller;

import Database.*;
import LampadaStuff.LampadaStuff;
import Model.Countries;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerScreen5Controller implements Initializable {
    private Stage stage;
    private Parent scene;
    @FXML
    private ComboBox<Countries> Add_Country;
    @FXML
    private ComboBox<Divisions> Add_State;
    @FXML
    private javafx.scene.control.TextField Add_Name;
    @FXML
    private javafx.scene.control.TextField Add_Address;
    @FXML
    private javafx.scene.control.TextField Add_Phone;
    @FXML
    private TextField Add_Postal;

    private int Country_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;

    //This Makes the Cancel Btn Work
    public void Cancel_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    //This Makes the Country Select Combo Box Visible and its options
    public void Country_Select(ActionEvent event) {

        Country_ID = Add_Country.getValue().getCountry_ID();
        Add_State.setItems(DataListManger.FilteredLIst(Country_ID));
        Add_State.getSelectionModel().selectFirst();
    }

    //This Saves All The Items
    public void Save_Btn_Clicked(ActionEvent event) throws IOException {
        try {
            boolean formatError = false;
            //The Below Stuff Gets All the Entered User INFO
            Customer_Name = Add_Name.getText();
            Address = Add_Address.getText();
            Postal_Code = Add_Postal.getText();
            Phone = Add_Phone.getText();
            int Division_ID = Add_State.getSelectionModel().getSelectedItem().getDivision_ID();


            //This Makes Sure as long as theres no format Errors it Will Add the Customer
            if (!formatError) {
                CustomerDaos newCustomers = new CustomerImpl();
                newCustomers.AddCustomers(Customer_Name, Address, Postal_Code, Phone, Division_ID);

                Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
                Scene scene = new Scene(addParts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

                LampadaStuff.LampadaAdd AddSucsess = x -> "You have Fulfilled the " + x;
                System.out.println(AddSucsess.AddSucsess("Add Process"));

            }
            //This Catches Any Exception were they to Ocurr
        } catch (Exception e) {

        }

    }

    /**
     * Many Issues Occured Below they were fixed with Adding the
     * Get Selection model first which auto selected an Item, which solved many hassles
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    //This Initializes the Add Customer Controller making everything visible
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JDBC.openConnection();
            CountriesDaos Countries = new CountriesImpl();

            Add_Country.setItems(Countries.getAllCountries());
            Add_Country.getSelectionModel().selectFirst();

            Country_ID = Add_Country.getValue().getCountry_ID();
            Add_State.setItems(DataListManger.FilteredLIst(Country_ID));
            Add_State.getSelectionModel().selectFirst();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();


        }

    }

    //This CLears the Name Field
    //This program is as simple as it gets
    public void Clear_Name_Btn_Click(ActionEvent event) {
        Add_Name.setText("");
    }

    //The Clears the Address
    public void Clear_Address_Btn_Click(ActionEvent event) {
        Add_Address.setText("");
    }

    //This Clears phone number
    public void Clear_Phone_Number_Btn_Click(ActionEvent event) {
        Add_Phone.setText("");
    }

    //This Clears Postal
    public void Clear_Postal_Btn_Click(ActionEvent event) {
        Add_Postal.setText("");
    }
}
