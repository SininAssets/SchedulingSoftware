package Database;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Database.JDBC.connection;

public class DivisionsImpl implements DivisionsDao {

    /**
     * There must be 2 version of Division Array list made
     * because if you removed the newest one DivisionsInCountry it will cause
     * the DataListManger Filter to malfunction
     * and if AllDivisions is removed it will cause other features to Malfunction
     */


    static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
    ObservableList<Divisions> DivisionsInCountry = FXCollections.observableArrayList();

    @Override
    public ObservableList<Divisions> getCountryDivisions(int Country_ID) {
        try {
            /**
             * This Gets everything from Divisions and Countries where the Countries ID matches in both
             * Division And Country
             */
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID " +
                    "= countries.Country_ID AND first_level_divisions.Country_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            /**
             * Then The following info is sent into here
             */
            ps.setInt(1, Country_ID);

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int Division_ID = result.getInt("Division_ID");
                Country_ID = result.getInt("Country_ID");
                String Division = result.getString("Division");
                String Country = result.getString("Country");
                Divisions newdivision = new Divisions(Division_ID, Division, Country_ID, Division, Country);
                DivisionsInCountry.add(newdivision);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return DivisionsInCountry;
    }


}
