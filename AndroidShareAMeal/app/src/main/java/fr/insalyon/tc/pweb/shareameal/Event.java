package fr.insalyon.tc.pweb.shareameal;


public class Event {

    private int id;
    private String name;
    private String start_date;
    private boolean active;
    private String description;


    public String toString() {
        return "id = " + this.id + "| Name = " + this.name ;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }
}
