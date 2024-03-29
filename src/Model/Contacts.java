package Model;

public class Contacts {
    private int Contact_ID;
    private String Contact_Name;

    public Contacts(int Contact_ID, String Contact_Name) {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    @Override
    public String toString() {
        return Contact_Name;
    }
}
