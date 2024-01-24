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

public class ReportsTypeCountryController implements Initializable {
    public TableView Country_Table;
    @FXML
    private javafx.scene.control.TableColumn Country_Column;
    @FXML
    private TableColumn Customer_Num_Column;

    //This Brings User Back to Main Reports page with the Contact Select
    public void Reports_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/ReportScreen7.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //This Brings User Back to Main Screen

    public void Main_Screen_Btn_Click(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //This Makes the Tables Visible Country and Country Total

        //This brings the resources for the Country Table
        Country_Table.setItems(Model.UtilityShortcuts.Total_Countries_Customers());
        //This Makes the Country Total Column Visible
        Customer_Num_Column.setCellValueFactory(new PropertyValueFactory<>("Country_Total"));
        //This makes the Country name Visible
        Country_Column.setCellValueFactory(new PropertyValueFactory<>("Country_Name"));

    }
}
