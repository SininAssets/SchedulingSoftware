package Model;

public class Countries {
    private int Country_ID;
    private String Country_Name;
    private int Country_Total;

    public Countries(String Country_Name, int Country_Total) {
        this.Country_Name = Country_Name;
        this.Country_Total = Country_Total;
    }

    public Countries(int Country_ID, String Country_Name) {
        this.Country_ID = Country_ID;
        this.Country_Name = Country_Name;
    }

    public int getCountry_Total() {
        return Country_Total;
    }

    public void setCountry_Total(int country_Total) {
        Country_Total = country_Total;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int Country_ID) {
        this.Country_ID = Country_ID;
    }

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String Country_Name) {
        this.Country_Name = Country_Name;
    }

    /**
     * @return
     * @ToString is a necessary to present the options in words
     * rather than in a strange format
     */
    @Override
    public String toString() {
        return (Country_Name);
    }
}
