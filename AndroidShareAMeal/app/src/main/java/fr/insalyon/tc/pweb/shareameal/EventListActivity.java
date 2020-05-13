package fr.insalyon.tc.pweb.shareameal;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.adapter.EventAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import fr.insalyon.tc.pweb.shareameal.object.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {

    private static String action;
    public static String credentials;

    public final static int REQUEST_CODE = 1;

    public EventListActivity(String action, String credentials){
        this.action = action;
        this.credentials = credentials;
    }

    public EventListActivity(){
    }


    private static final String TAG = "EventListActivity";

//    private String url = "http://api.shareameal.ribes.ovh/";
    private String url = "http://192.168.1.33:8001";

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.event_list_activity);
    }


    @Override
    public void onResume() {
        super.onResume();

        final ListView list = findViewById(R.id.event_list_activity_listEvent);
        final Button back = findViewById(R.id.event_list_activity_back);

        Log.d(TAG, "/=>" + this.action);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);
        Call<Vector<Event>> call = jsonPlaceHolderApi.getEvents();

        if (this.action == "getEventsNoAccount") {

            Button new_event = findViewById(R.id.event_list_activity_custom_button);
            new_event.setText(R.string.shearch);
            new_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "A faire, page de recherche", Toast.LENGTH_SHORT).show();
                }
            });

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

                    listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent edit = new Intent(getApplicationContext(),new  ConsultEventActivity(response.body().get(position).getName(),
                                    response.body().get(position).getStart_datetime(),
                                    response.body().get(position).isActive(),
                                    response.body().get(position).getDescription()).getClass());
                            startActivityForResult(edit, REQUEST_CODE) ;
                        }
                    });


                }

                @Override
                public void onFailure(Call<Vector<Event>> call, Throwable t) {
                    Log.e(TAG, "-----> ERROR" + t.getMessage());
                }

            });

        } else if (action == "getEventsAccount"){

            Button shearch = findViewById(R.id.event_list_activity_custom_button);
            shearch.setText(R.string.new_event);
            shearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), "A faire, page de nouvelle element", Toast.LENGTH_SHORT).show();
                    Intent edit = new Intent(getApplicationContext(),new  EditEventActivity(getString(R.string.eventName),
                            new Date(),
                            true,
                            getString(R.string.eventDescription)).getClass());
                    startActivityForResult(edit, REQUEST_CODE) ;
                }
            });

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

                    listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent edit = new Intent(getApplicationContext(),new  EditEventActivity(response.body().get(position).getName(),
                                    response.body().get(position).getStart_datetime(),
                                    response.body().get(position).isActive(),
                                    response.body().get(position).getDescription()).getClass());
                            startActivityForResult(edit, REQUEST_CODE);
                        }
                    });


                }

                @Override
                public void onFailure(Call<Vector<Event>> call, Throwable t) {
                    Log.e(TAG, "-----> ERROR" + t.getMessage());
                }

            });
        }
    }

   @Override
   protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String name = data.getExtras().getString("Name");
                String dateString = data.getExtras().getString("Date");
                String timeString = data.getExtras().getString("Time");
                String description = data.getExtras().getString("Description");
                boolean isActive = Boolean.parseBoolean(data.getExtras().getString("isActive"));

                Log.d(TAG, "Date : " + dateString);  //format "DD/MM/YYYY"
                Log.d(TAG, "Time : " + timeString);   //format "HH:mm"

                int day = Integer.parseInt(dateString.split("/")[0]);
                int month = Integer.parseInt(dateString.split("/")[1]);
                int year = Integer.parseInt(dateString.split("/")[2]);
                int hours = Integer.parseInt(timeString.split(":")[0]);
                int min = Integer.parseInt(timeString.split(":")[1]);

                Log.d(TAG, "date decompose " + day + "/" + month + "/" + year + "_" + hours + ":" + min);

                Date date = new Date(year-1900, month, day, hours, min);
                Log.d(TAG, "date obj " + date.toString());
                SimpleDateFormat datePostFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
                String datePost = datePostFormat.format(date);
                Log.d(TAG, "date send " + datePost);


                Post eventPost = new Post(name, datePost, isActive, description);

                JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);
                Call<Post> post = jsonPlaceHolderApi.createPost(eventPost, credentials );

                Log.d(TAG, post.request().toString());
                Log.d(TAG, post.toString());


                post.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        String content = "code : " + response.code() + " content : " +response.body();
                        Log.d(TAG, "code : " + response.code() + " content : " +response.body());
                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.add_bdd_nok, Toast.LENGTH_SHORT).show();
                    }
                });



                Toast.makeText(getApplicationContext(), "OK, Name : " + name + " Date : " + dateString + " Time : " + timeString
                        + " description : " + description + " isActive : " + isActive, Toast.LENGTH_LONG).show();

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "KO", Toast.LENGTH_SHORT).show();
            }
        }
   }
}
