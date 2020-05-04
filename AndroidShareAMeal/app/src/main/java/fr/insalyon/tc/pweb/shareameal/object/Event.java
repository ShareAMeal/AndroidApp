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


    public String toString() {
        return "id = " + this.id + "| Name = " + this.name + "| Date = " + this.start_datetime;
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
