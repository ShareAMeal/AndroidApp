package fr.insalyon.tc.pweb.shareameal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class EventAdapter extends BaseAdapter {

    private static final String TAG = "EventAdapter";

    private Context context;
    private Vector<Event> list;
    private LayoutInflater m_inflater;


    public EventAdapter(Context context, Vector<Event> list){
        this.list = list;
        this.context = context;
        m_inflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return list.size();
    }

    public Object getItem (int position){
        return list.get(position) ;
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertview, ViewGroup parent){
        LinearLayout layout;

        if(convertview == null){
            layout = (LinearLayout) m_inflater.inflate(R.layout.oneevent, parent, false);
        } else {
            layout = (LinearLayout) convertview;
        }

//        TextView id = layout.findViewById(R.id.eventID);
        TextView name = layout.findViewById(R.id.eventName);
        final TextView mdate = layout.findViewById(R.id.eventStartDate);
        final TextView mTime = layout.findViewById(R.id.eventStartTime);
        Switch active = layout.findViewById(R.id.eventActive);
        TextView description = layout.findViewById(R.id.eventDescription);

//        id.setText(Integer.toString(list.get(position).getId()));
        name.setText(list.get(position).getName());
//        mdate.setText(list.get(position).getStart_datetime().toString());
        active.setChecked(list.get(position).isActive());
        description.setText(list.get(position).getDescription());

        Date getdate = list.get(position).getStart_datetime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm");
        SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat hoursFormat = new SimpleDateFormat("hh");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

        final String year = yearFormat.format(getdate);
        final String month = monthFormat.format(getdate);
        final String day = dayFormat.format(getdate);
        final String hours = hoursFormat.format(getdate);
        final String min = minuteFormat.format(getdate);


        Log.d(TAG, dateFormat.format(getdate));

        mdate.setText(day + "/" + month + "/" + year); //A changer avec un format adaptatif
        mTime.setText(hours + ":" + min);               // a changer avec un ofrmat adaptatif

        final DatePickerDialog.OnDateSetListener mDateSetListenner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int newyear, int newmonth, int newdayOfMonth) {

                String date = newdayOfMonth + "/" + newmonth + "/" + newyear; //A changer avec un format adaptatif

                // Ici on peut envoyer les changements a la BDD pour la date

                mdate.setText(date);

            }
        };

        final TimePickerDialog.OnTimeSetListener mTimeSetListenner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int newhourOfDay, int newminute) {


                String time = newhourOfDay + ":" + newminute; //A changer avec un format adaptatif

                // ici on peut envoyer les changements a la BDD pour l'heure

                mTime.setText(time);
            }
        };

        mdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        mDateSetListenner,
                        Integer.parseInt(year),
                        Integer.parseInt(month),
                        Integer.parseInt(day)
                        );
                dialog.show();

            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(
                        context,
                        mTimeSetListenner,
                        Integer.parseInt(hours),
                        Integer.parseInt(min),
                        true);
                dialog.show();
            }
        });

        active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).active(isChecked);

                // On peut changer la valeur Active dans la BDD
            }
        });

        return layout;
    }

}
