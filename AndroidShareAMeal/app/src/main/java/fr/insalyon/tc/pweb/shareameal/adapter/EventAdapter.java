package fr.insalyon.tc.pweb.shareameal.adapter;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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
        isActive.setClickable(false);
        description.setText(list.get(position).getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        date.setText(dateFormat.format(list.get(position).getStart_datetime()));
        time.setText(timeFormat.format(list.get(position).getStart_datetime()));

        Log.d(TAG, (String) date.getText().toString());


        return layout;
    }

}
