package Database;

import Model.Countries;
import javafx.collections.ObservableList;

public interface CountriesDaos {
    //This Gets All The Countries

    ObservableList<Countries> getAllCountries();

    //This Gets the Country ID
    Countries getCountry_ID(int Country_ID);


}
