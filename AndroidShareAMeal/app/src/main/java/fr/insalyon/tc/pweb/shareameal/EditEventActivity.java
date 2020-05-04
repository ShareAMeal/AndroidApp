package fr.insalyon.tc.pweb.shareameal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditEventActivity extends AppCompatActivity {

    private static String name;
    private static Date date;
    private static boolean active;
    private static String descritption;

    public EditEventActivity(String name, Date date, boolean active, String description){
        this.name = name;
        this.date = date;
        this.active = active;
        this.descritption = description;
    }

    public EditEventActivity(){}

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.one_event_for_edit);

        TextView name = findViewById(R.id.one_event_for_edit_eventName);
        TextView date = findViewById(R.id.one_event_for_edit_eventStartDate);
        TextView time = findViewById(R.id.one_event_for_edit_eventStartTime);
        Switch isActive = findViewById(R.id.one_event_for_edit_eventActive);
        TextView description = findViewById(R.id.one_event_for_edit_eventDescription);


        name.setText(this.name);
        isActive.setChecked(this.active);
        description.setText(this.descritption);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        date.setText(dateFormat.format(this.date));
        time.setText(timeFormat.format(this.date));

        final Button noAccount= findViewById(R.id.one_event_for_edit_cancel);
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
