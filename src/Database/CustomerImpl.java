package Database;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static Database.JDBC.connection;

public class CustomerImpl implements CustomerDaos {
    ObservableList<Customers> AllCustomers = FXCollections.observableArrayList();
    @FXML
    private TableView<Customers> Customer_Table;

    /**
     * This is just a test make sure to remove in final version scratch that improvise on this
     */
    //@Override

    /**
     * @return I turned State_Province into
     * Division ID for possible future improvements
     */
    public ObservableList<Customers> getAllCustomers() {
        try {
            String sql = "SELECT * FROM customers,first_level_divisions,countries WHERE " +
                    "customers.Division_ID = first_level_divisions.Division_ID AND "
                    + "first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Phone = rs.getString("Phone");
                String Division = rs.getString("Division");
                String Postal_Code = rs.getString("Postal_Code");
                String Country_Name = rs.getString("Country");
                //Added to make boxes visble
                int Division_ID = rs.getInt("Division_ID");
                int Country_ID = rs.getInt("Country_ID");

                Customers newCustomers = new Customers(Customer_ID, Customer_Name, Address, Phone, Division, Postal_Code, Country_Name, Division_ID, Country_ID);
                AllCustomers.add(newCustomers);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
        return AllCustomers;
    }

    @Override
    public Customers getCustomer(int Customer_ID) {
        return null;
    }

    @Override
    public Object deleteCustomer(int Customer_ID, String Customer_Name) {
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID=? AND Customer_Name=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Customer_ID);
            ps.setString(2, Customer_Name);
            rowsAffected = ps.executeUpdate();

            if ((Customer_Table.getSelectionModel().getSelectedItem() == null)) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");
                alertError.setHeaderText("Error you did not select an Customer");
                alertError.showAndWait();


            } else {
                //Work on the result so confirmation Appears
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Deleting Customer");
                alert.setContentText("Do you want to delete this Customer?");
                Optional<ButtonType> result = alert.showAndWait();

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int AddCustomers(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {

        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Customer_Name);
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setInt(5, Division_ID);
            rowsAffected = ps.executeUpdate();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rowsAffected;

    }

    @Override
    public int Update_Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {

        int rowsAffected = 0;
        try {
            String sql = "UPDATE customers SET Customer_Name=?, Address=?,Postal_Code=?,Phone=?,Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Customer_Name);
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setInt(5, Division_ID);
            ps.setInt(6, Customer_ID);
            rowsAffected = ps.executeUpdate();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rowsAffected;

    }

}
