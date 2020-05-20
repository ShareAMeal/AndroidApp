package fr.insalyon.tc.pweb.shareameal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.insalyon.tc.pweb.shareameal.object.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventActivity extends AppCompatActivity {

    private static final String TAG = "EditEventActivity";

    //    private String url = "http://api.shareameal.ribes.ovh";
    private String url = "http://192.168.1.33:8001";

    private static String name;
    private static Date date;
    private static boolean active;
    private static String descritption;
    private static int id;
    private static String action;
    private static String credentials;

    public EditEventActivity(String name, Date date, boolean active, String description, int id, String action, String credentials){
        this.name = name;
        this.date = date;
        this.active = active;
        this.descritption = description;
        this.id = id;
        this.action = action;
        this.credentials = credentials;
    }

    public EditEventActivity(){}

    @Override
    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(R.layout.one_event_for_edit);


        Log.d(TAG, "action : " + this.action);
        Log.d(TAG, "credentials : "  + this.credentials);

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

        Log.d(TAG, day + "/" + (month + 1) + "/" + year + "|" + day + ":" + min);

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

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String postName = name.getText().toString();
                String stringDate = date.getText().toString();
                String StringTime = time.getText().toString();
                boolean postActive = isActive.isChecked();
                String postDescription = description.getText().toString();

                Log.d(TAG, "Date : " + stringDate);  //format "DD/MM/YYYY"
                Log.d(TAG, "Time : " + StringTime);   //format "HH:mm"

                int day = Integer.parseInt(stringDate.split("/")[0]);
                int month = Integer.parseInt(stringDate.split("/")[1]);
                int year = Integer.parseInt(stringDate.split("/")[2]);
                int hours = Integer.parseInt(StringTime.split(":")[0]);
                int min = Integer.parseInt(StringTime.split(":")[1]);

                Log.d(TAG, "date decompose " + day + "/" + month + "/" + year + "_" + hours + ":" + min);
                Date objdate = new Date(year-1900, month, day, hours, min);
                Log.d(TAG, "date obj " + date.toString());
                SimpleDateFormat datePostFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
                String datePost = datePostFormat.format(objdate);
                Log.d(TAG, "date send " + datePost);

                Toast.makeText(getApplicationContext(), "OK, Name : " + name + " Date : " + stringDate + " Time : " + StringTime
                        + " description : " + description + " isActive : " + isActive, Toast.LENGTH_LONG).show();


                if (action == "new") {

                    Post eventPost = new Post(postName, datePost, postActive, postDescription, 0);

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

                    finish();

                } else if (action == "modif") {

                    Post eventPost = new Post(postName, datePost, postActive, postDescription, id);

                    JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);
                    Call<Post> post = jsonPlaceHolderApi.modifEvent(id, eventPost, credentials);

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

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
