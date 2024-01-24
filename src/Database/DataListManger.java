package Database;

import Model.Divisions;
import javafx.collections.ObservableList;

public class DataListManger {
    /**
     * This is a Filtered List It gets Country ID and goes through it
     * It gets the Divisions Based on Country ID
     *
     * @param Country_ID
     * @return
     */
    public static ObservableList<Divisions> FilteredLIst(int Country_ID) {
        DivisionsDao Divisions = new DivisionsImpl();
        return Divisions.getCountryDivisions(Country_ID);

    }

}
