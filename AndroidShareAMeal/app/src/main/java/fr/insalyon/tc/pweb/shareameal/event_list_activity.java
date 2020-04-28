package fr.insalyon.tc.pweb.shareameal;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Vector;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class event_list_activity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";


    private String url = "http://api.shareameal.ribes.ovh/api/";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_activity);

       final ListView list = findViewById(R.id.listEvent);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Vector<Event>> call = jsonPlaceHolderApi.getEvents();
        call.enqueue(new Callback<Vector<Event>>(){
            @Override
            public void onResponse(Call<Vector<Event>> call, Response<Vector<Event>> response){

                Log.d(TAG,"-->" + response.body());

                for(Event event: response.body()){
                   Log.d(TAG, "===>" + event);
                }

                EventAdapter adapter = new EventAdapter(event_list_activity.this,response.body());
                ListView listEvent = (ListView) findViewById(R.id.listEvent);

                listEvent.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Vector<Event>> call, Throwable t ){
                Log.e(TAG,"-----> ERROR" + t.getMessage());
            }

        });

    }
}
