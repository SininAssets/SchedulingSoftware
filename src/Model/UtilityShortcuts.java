package Model;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilityShortcuts {

    private static final ObservableList<Appointments> AllAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointments> getAllAppointments() {
        return AllAppointments;
    }

    public static ObservableList<Countries> Total_Countries_Customers() {
        ObservableList<Countries> CustomersCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country, COUNT(customers.Customer_ID) AS Count FROM countries INNER JOIN first_level_divisions ON  countries.Country_ID = first_level_divisions.Country_ID INNER JOIN customers ON customers.Division_ID = first_level_divisions.Division_ID group by countries.Country";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int Country_Total = rs.getInt("Count");
                String Country_Name = rs.getString("Country");
                Countries Countries_Results = new Countries(Country_Name, Country_Total);
                CustomersCountry.add(Countries_Results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CustomersCountry;
    }
}
