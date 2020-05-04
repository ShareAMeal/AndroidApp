package fr.insalyon.tc.pweb.shareameal.adapter;
import fr.insalyon.tc.pweb.shareameal.EditEventActivity;
import fr.insalyon.tc.pweb.shareameal.EventListActivity;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
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

    public View getView(final int position, final View convertview, ViewGroup parent){
        final LinearLayout layout;

        if(convertview == null){
            layout = (LinearLayout) m_inflater.inflate(R.layout.one_event_for_event_list, parent, false);
        } else {
            layout = (LinearLayout) convertview;
        }

        TextView name = layout.findViewById(R.id.one_event_for_event_list_eventName);
        TextView date = layout.findViewById(R.id.one_event_for_event_list_eventStartDate);
        TextView time = layout.findViewById(R.id.one_event_for_event_list_eventStartTime);
        Switch isActive = layout.findViewById(R.id.one_event_for_event_list_eventActive);
        TextView description = layout.findViewById(R.id.one_event_for_event_list_eventDescription);

        name.setText(list.get(position).getName());
        isActive.setChecked(list.get(position).isActive());
        description.setText(list.get(position).getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        date.setText(dateFormat.format(list.get(position).getStart_datetime()));
        time.setText(timeFormat.format(list.get(position).getStart_datetime()));


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(context)
//                        .setTitle(R.string.eventEdition)
//                        .setView(R.layout.one_event_for_edit)
//                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context,  "ok" + list.get(position).getName(), Toast.LENGTH_LONG).show();
//                                // changement dans la BDD ici
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel,null)
//                        .create()
//                        .show();
                context.startActivity(new Intent(context, new EditEventActivity(list.get(position).getName(),
                        list.get(position).getStart_datetime(),
                        list.get(position).isActive(),
                        list.get(position).getDescription()).getClass()));
            }
        });

        return layout;
    }

}
