package fr.insalyon.tc.pweb.shareameal.object;

public class Asso {

    private static final String TAG = "AssoClass";

    private int id;
    private String contact_email;
    private String name;
    private String localisation;
    private String Description;
    private String phone;

    public String toString() {
        return "id = " + this.id + "| Name = " + this.name ;
    }

    public int getId() {
        return id;
    }

    public String getContact_email() {
        return contact_email;
    }

    public String getName() {
        return name;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return Description;
    }

    public String getPhone() {
        return phone;
    }
}
