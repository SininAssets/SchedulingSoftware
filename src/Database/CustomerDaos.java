package Database;

import Model.Customers;
import javafx.collections.ObservableList;

public interface CustomerDaos {
    //This Gets All Customers
    ObservableList<Customers> getAllCustomers();

    //This Gets Customers ID
    Customers getCustomer(int Customer_ID);

    //These Are the Features to Delete the Customer
    Object deleteCustomer(int Customer_ID, String Customer_Name);

    //These Are the Features to ADD customer
    int AddCustomers(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID);

    //These Are the Features to Update the Customer
    int Update_Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID);


}
