package Database;

import Model.Divisions;
import javafx.collections.ObservableList;

public interface DivisionsDao {
    //Gets the Country Division By the Country ID
    ObservableList<Divisions> getCountryDivisions(int Country_ID);


}
