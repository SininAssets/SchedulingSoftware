package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {


    private final int Country_ID;
    private final int Division_ID;
    private final int Customer_ID;
    private final String Customer_Name;
    private final String Address;
    private final String Phone;
    private final String Division;
    private final String Postal_Code;
    private String Country;
    private final ObservableList<Customers> AllCustomers = FXCollections.observableArrayList();

    public Customers(int Customer_ID, String Customer_Name, String Address, String Phone, String Division, String Postal_Code, String Country, int Division_ID, int Country_ID) {

        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Phone = Phone;
        this.Division = Division;
        this.Postal_Code = Postal_Code;
        this.Country = Country;
        this.Division_ID = Division_ID;
        this.Country_ID = Country_ID;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDivision() {
        return Division;
    }

    public int getDivisionID() {
        return Division_ID;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public ObservableList<Customers> getAllCustomers() {
        return AllCustomers;
    }

    public void addCustomers(Customers newCustomers) {
        AllCustomers.add(newCustomers);
    }

    @Override
    public String toString() {
        return Customer_Name;
    }
}
