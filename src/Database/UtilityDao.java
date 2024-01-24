package Database;

import Model.Contacts;
import Model.User;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface UtilityDao {

    //This Gets All Contacts
    ObservableList<Contacts> Get_AllContacts();

    //This Gets All The Users
    ObservableList<User> Get_AllUsers();


    //Removed Appointment_ID as its self Generating
    int Add_Appointment(int Customer_ID, String Title, String Type, String Description, String Location, LocalDateTime Start_Date,
                        LocalDateTime End_Date, int User_ID, int Contact_ID);


}
