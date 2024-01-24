package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsTypeMonthController implements Initializable {
    public TableView Type_Total;
    public TableView Month_Total;
    @FXML
    private TableColumn Apt_Type;
    @FXML
    private TableColumn Apt_Type_Total;
    @FXML
    private TableColumn Apt_Month;
    @FXML
    private TableColumn Apt_Month_Total;

    //Brings User Back to Main Screen
    public void Main_Screen_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Brings the User to the Default Reports Table
    //With the Contact Select
    public void Reports_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/ReportScreen7.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Month_Total and Month must be used for Both in order to be visible on the tables
     * I had to do it in order to fix the problem with Invisible Table Content
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Appointments By Months

        //This gets the Resources for the Table
        Month_Total.setItems(main.Utilities.Get_Appointment_Type_Month());
        //Makes the Total Column Visible
        Apt_Month_Total.setCellValueFactory(new PropertyValueFactory<>("Month_Total"));
        //Makes the Month Column Visible
        Apt_Month.setCellValueFactory(new PropertyValueFactory<>("Month"));

        //Appointments Type and Total

        //This gets Resources for the Type and Total Table
        Type_Total.setItems(main.Utilities.Get_Appointment_Type_Total());
        //Makes the Type Visible don't Get Confused with the name
        Apt_Type.setCellValueFactory(new PropertyValueFactory<>("Month"));
        //Makes the Type Total Visible don't get Confused with the name
        Apt_Type_Total.setCellValueFactory(new PropertyValueFactory<>("Month_Total"));

    }
}
