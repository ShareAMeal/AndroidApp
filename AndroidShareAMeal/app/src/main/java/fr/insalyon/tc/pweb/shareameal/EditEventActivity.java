package fr.insalyon.tc.pweb.shareameal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditEventActivity extends AppCompatActivity {

    private static final String TAG = "EditEventActivity";

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

        Log.d(TAG, this.date.toString());

        TextView name = findViewById(R.id.one_event_for_edit_eventName);
        final TextView mDate = findViewById(R.id.one_event_for_edit_eventStartDate);
        final TextView mTime = findViewById(R.id.one_event_for_edit_eventStartTime);
        Switch isActive = findViewById(R.id.one_event_for_edit_eventActive);
        TextView description = findViewById(R.id.one_event_for_edit_eventDescription);


        name.setText(this.name);
        isActive.setChecked(this.active);
        description.setText(this.descritption);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        mDate.setText(dateFormat.format(this.date));
        mTime.setText(timeFormat.format(this.date));

        final int year = this.date.getYear() + 1900;
        final int month = this.date.getMonth();
        final int day = this.date.getDate();
        final int hours = this.date.getHours();
        final int min = this.date.getMinutes();

        Log.d(TAG, day + "/" + (month+1) + "/" + year + "|" + day + ":" + min);

        final DatePickerDialog.OnDateSetListener mDateSetListenner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int newyear, int newmonth, int newdayOfMonth) {

                String date = newdayOfMonth + "/" + newmonth + "/" + newyear; //A changer avec un format adaptatif
                mDate.setText(date);

            }
        };

        final TimePickerDialog.OnTimeSetListener mTimeSetListenner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int newhourOfDay, int newminute) {

                String time = newhourOfDay + ":" + newminute; //A changer avec un format adaptatif
                mTime.setText(time);
            }
        };

        mDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DatePickerDialog dialog = new DatePickerDialog(
                        EditEventActivity.this,
                        mDateSetListenner,
                        year,
                        month,
                        day
                );
                dialog.show();

            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(
                        EditEventActivity.this,
                        mTimeSetListenner,
                        hours,
                        min,
                        true);
                dialog.show();
            }
        });

        final Button cancel = findViewById(R.id.one_event_for_edit_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        final Button submit = findViewById(R.id.one_event_for_edit_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView name = findViewById(R.id.one_event_for_edit_eventName);
                TextView date = findViewById(R.id.one_event_for_edit_eventStartDate);
                TextView time = findViewById(R.id.one_event_for_edit_eventStartTime);
                Switch isActive = findViewById(R.id.one_event_for_edit_eventActive);
                TextView description = findViewById(R.id.one_event_for_edit_eventDescription);

                Intent save = new Intent(v.getContext(), EventListActivity.class);

                save.putExtra("Name", name.getText().toString());
                save.putExtra("Date", date.getText().toString());
                save.putExtra("Time", time.getText().toString());
                save.putExtra("isActive", Boolean.toString(isActive.isChecked()));
                save.putExtra("Description", description.getText().toString());
                setResult(RESULT_OK, save);
                finish();

            }
        });
    }
}
