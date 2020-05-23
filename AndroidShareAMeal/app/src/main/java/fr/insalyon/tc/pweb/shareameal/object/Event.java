package fr.insalyon.tc.pweb.shareameal.object;

import android.util.Log;
import java.util.Date;

public class Event {

    private final static String TAG = "EventClass";

    private int id;
    private String name;
    private Date start_datetime;
    private boolean active;
    private String description;
    private String ville;

    public Event(int id, String name, Date start_dateTime, boolean active, String description, String city){
        this.id = id;
        this.name = name;
        this.start_datetime = start_dateTime;
        this.active = active;
        this.description = description;
        this.ville = city;
    }


    public String toString() {
        return "{ "+
                "id : " + id +
                " , name : " + name +
                " , start_datetime : " + start_datetime +
                ", active : " + active +
                " , description : " + description +
                ", ville : " + ville +
                "}";
    }

    public String getVille() {
        return ville;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStart_datetime() {
        return start_datetime;
    }

    public boolean isActive() {
        return active;
    }

    public void active(Boolean active){
        this.active = active;
        Log.d(TAG, Boolean.toString(this.active));
    }

    public String getDescription() {
        return description;
    }
}
