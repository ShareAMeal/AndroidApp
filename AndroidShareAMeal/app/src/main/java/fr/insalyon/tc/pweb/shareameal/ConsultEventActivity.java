package fr.insalyon.tc.pweb.shareameal;

import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.insalyon.tc.pweb.shareameal.object.Asso;

public class ConsultEventActivity extends AppCompatActivity {

    private static final String TAG = "ConsultEventActivity";

    private static String name;
    private static Date date;
    private static boolean active;
    private static String city;
    private static String descritption;
    private static Asso organizer;

    public ConsultEventActivity(String name, Date date, boolean active, String city, String description, Asso organizer){
        this.name = name;
        this.date = date;
        this.active = active;
        this.city = city;
        this.descritption = description;
        this.organizer = organizer;
    }

    public ConsultEventActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_event_for_consult);

        TextView name = findViewById(R.id.one_event_for_consult_eventName);
        TextView date = findViewById(R.id.one_event_for_consult_eventStartDate);
        TextView time = findViewById(R.id.one_event_for_consult_eventStartTime);
        Switch isActive = findViewById(R.id.one_event_for_consult_eventActive);
        TextView city = findViewById(R.id.one_event_for_consult_eventCity);
        TextView description = findViewById(R.id.one_event_for_consult_eventDescription);

        TextView organizerName = findViewById(R.id.one_event_for_consult_organizer_name);
        TextView organizerPhone = findViewById(R.id.one_event_for_consult_organizer_phone);
        TextView organizerEmail = findViewById(R.id.one_event_for_consult_organizer_email);



        name.setText(this.name);
        isActive.setChecked(this.active);
        isActive.setClickable(false);
        city.setText(this.city);
        description.setText(this.descritption);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        date.setText(dateFormat.format(this.date));
        time.setText(timeFormat.format(this.date));

        organizerName.setText(this.organizer.getName());
        organizerPhone.setText(this.organizer.getPhone());
        organizerEmail.setText(this.organizer.getContact_email());

        final Button cancel = findViewById(R.id.one_event_for_consult_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
