package fr.insalyon.tc.pweb.shareameal;
import fr.insalyon.tc.pweb.shareameal.object.Asso;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.adapter.EventAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {

    private static String action;
    public static String credentials;
    protected Vector<Event> eventList = new Vector<>();

    public EventListActivity(String action, String credentials){
        this.action = action;
        this.credentials = credentials;
    }

    public EventListActivity(){
    }


    private static final String TAG = "EventListActivity";

//    private String url = "http://api.shareameal.ribes.ovh";
    private String url = "http://192.168.1.33:8001";

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.event_list_activity);
    }


    @Override
    public void onResume() {
        super.onResume();

        final ListView listEvent = findViewById(R.id.event_list_activity_listEvent);
        final Button back = findViewById(R.id.event_list_activity_back);

        Log.d(TAG, "/=>" + this.action);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);


        if (this.action == "getEventsNoAccount") {

            Button shearch = findViewById(R.id.event_list_activity_custom_button);
            shearch.setText(R.string.shearch);
            shearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "A faire, page de recherche", Toast.LENGTH_SHORT).show();
                }
            });

            Call<Vector<Event>> call = jsonPlaceHolderApi.getEvents();
            call.enqueue(new Callback<Vector<Event>>() {
                @Override
                public void onResponse(Call<Vector<Event>> call, final Response<Vector<Event>> response) {

                    if(response.code() < 300 && response.code() >= 200){
                        Log.d(TAG, "-->" + response.body());
                        Log.d(TAG, "retrun code -->" + response.code());

                        for (Event event : response.body()) {
                            Log.d(TAG, "===>" + event);
                        }

                        EventAdapter adapter = new EventAdapter(EventListActivity.this, response.body(), action, credentials);
                        listEvent.setAdapter(adapter);

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Vector<Event>> call, Throwable t) {
                    Log.e(TAG, "-----> ERROR" + t.getMessage());
                }
            });

        } else if (action == "getEventsAccount"){

            Button newEvent = findViewById(R.id.event_list_activity_custom_button);
            newEvent.setText(R.string.new_event);
            newEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "credentials : " + credentials);
                    Intent edit = new Intent(getApplicationContext(),new  EditEventActivity(getString(R.string.eventName),
                            new Date(),
                            true,
                            getString(R.string.eventDescription),
                            0,
                            "new",
                            credentials).getClass());
                    startActivity(edit);
                }
            });

            Call<Asso> asso = jsonPlaceHolderApi.getmyAsso(credentials);
            asso.enqueue(new Callback<Asso>() {
                @Override
                public void onResponse(Call<Asso> call, Response<Asso> response) {

                    if(response.code() < 300 && response.code() >= 200) {
                        Asso myAsso = response.body();
                        int id = myAsso.getId();

                        Log.d(TAG, "Asso: " + myAsso);
                        Log.d(TAG, "Asso id: " + id);

                        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);
                        Call<Vector<Event>> myEvents = jsonPlaceHolderApi.getMyEvents(id);

                        myEvents.enqueue(new Callback<Vector<Event>>() {
                            @Override
                            public void onResponse(Call<Vector<Event>> call, final Response<Vector<Event>> response) {

                                if (response.code() < 300 && response.code() >= 200) {
                                    Log.d(TAG, "-->" + response.body());

                                    for (Event event : response.body()) {
                                        Log.d(TAG, "===>" + event);
                                    }

                                    if(response.body().isEmpty()){
                                        Toast.makeText(getApplicationContext(), R.string.noValue, Toast.LENGTH_SHORT).show();
                                    } else {
                                        final EventAdapter adapter = new EventAdapter(EventListActivity.this, response.body(), action, credentials);
                                        final ListView listEvent = (ListView) findViewById(R.id.event_list_activity_listEvent);

                                        listEvent.setAdapter(adapter);
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Vector<Event>> call, Throwable t) {
                                Log.e(TAG, "-----> ERROR" + t.getMessage());
                            }

                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Asso> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
