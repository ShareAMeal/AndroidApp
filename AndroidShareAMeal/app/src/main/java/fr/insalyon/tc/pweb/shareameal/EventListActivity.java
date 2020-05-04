package fr.insalyon.tc.pweb.shareameal;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.adapter.EventAdapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity {

    private static String action;

    public EventListActivity(String action){
        this.action = action;
    }

    public EventListActivity(){
    }


    private static final String TAG = "EventListActivity";


    private String url = "http://api.shareameal.ribes.ovh/api/";

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.event_list_activity);
    }


    @Override
    public void onResume() {
        super.onResume();

        final ListView list = findViewById(R.id.event_list_activity_listEvent);

        Log.d(TAG, "/=>" + this.action);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        if (this.action == "getEventsNoAccount") {
            Call<Vector<Event>> call = jsonPlaceHolderApi.getEvents();

            call.enqueue(new Callback<Vector<Event>>() {
                @Override
                public void onResponse(Call<Vector<Event>> call, final Response<Vector<Event>> response) {

                    Log.d(TAG, "-->" + response.body());

                    for (Event event : response.body()) {
                        Log.d(TAG, "===>" + event);
                    }

                    final EventAdapter adapter = new EventAdapter(EventListActivity.this, response.body());
                    final ListView listEvent = (ListView) findViewById(R.id.event_list_activity_listEvent);

                    listEvent.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<Vector<Event>> call, Throwable t) {
                    Log.e(TAG, "-----> ERROR" + t.getMessage());
                }

            });
        } else if (action == "getEventsAccount"){
            Toast.makeText(getApplicationContext(), "other", Toast.LENGTH_LONG).show();
        }
    }
}
