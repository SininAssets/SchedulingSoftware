package Database;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Database.JDBC.connection;

public class CountriesImpl implements CountriesDaos {

    ObservableList<Countries> AllCountries = FXCollections.observableArrayList();

    @Override
    public ObservableList<Countries> getAllCountries() {
        try {
            //As Shows this Selects everything from country and enters it into Country ID and Country Name
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                Countries country = new Countries(Country_ID, Country);
                AllCountries.add(country);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return AllCountries;
    }

    //This gets Country ID
    //It selects everything from Country where Country ID matches
    @Override
    public Countries getCountry_ID(int Country_ID) {
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Country_ID);

            ResultSet result = ps.executeQuery();
            Countries countryResult = null;
            if (result.next()) {
                Country_ID = result.getInt("Country_ID");
                String Country_Name = result.getString("Country");
                countryResult = new Countries(Country_ID, Country_Name);
            }
            return countryResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;

    }


}
