package fr.insalyon.tc.pweb.shareameal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Vector;

public class EventAdapter extends BaseAdapter {

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

    public View getView(int position, View convertview, ViewGroup parent){
        LinearLayout layout;

        if(convertview == null){
            layout = (LinearLayout) m_inflater.inflate(R.layout.oneevent, parent, false);
        } else {
            layout = (LinearLayout) convertview;
        }

        TextView id = layout.findViewById(R.id.eventID);
        TextView name = layout.findViewById(R.id.eventName);
        TextView date = layout.findViewById(R.id.eventStartDate);
        Switch active = layout.findViewById(R.id.eventActive);
        TextView description = layout.findViewById(R.id.eventDescription);

        id.setText(Integer.toString(list.get(position).getId()));
        name.setText(list.get(position).getName());
        date.setText(list.get(position).getStart_date());
        active.setChecked(list.get(position).isActive());
        description.setText(list.get(position).getDescription());

        return layout;
    }

}
