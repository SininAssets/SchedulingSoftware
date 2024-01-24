package Model;

public class Divisions {


    private int Division_ID;
    private final String Division;
    private int Country_ID;
    private String Country;

    public Divisions(int Division_ID, String division, int Country_ID, String Division, String Country) {
        this.Division_ID = Division_ID;
        this.Country_ID = Country_ID;
        this.Division = Division; //This is Division Name
        this.Country = Country; //This is Country Name
    }

    public Divisions(int Division_ID, String Division) {
        this.Division_ID = Division_ID;
        this.Division = Division;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision_Name() {
        return Division;
    }

    public void setDivision_Name(String Division) {
        Division = Division;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry_Name() {
        return Country;
    }

    public void setCountry_Name(String country_Name) {
        Country = country_Name;
    }

    @Override
    public String toString() {
        return Division;
    }


}
