package fr.insalyon.tc.pweb.shareameal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import fr.insalyon.tc.pweb.shareameal.object.Asso;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "searchActivity";

    private static Vector<Event> listEvent;
    private static String[] cityList;
    private static String[] assoList;
    private static ArrayList<String> selectedCity = new ArrayList<>();
    private static ArrayList<String> selectedAsso = new ArrayList<>();

    public SearchActivity(Vector<Event> listEvent) {
        this.listEvent = listEvent;
    }

    public SearchActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
    }

    @Override
    public void onResume() {
        super.onResume();

        selectedAsso.clear();
        selectedCity.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Log.d(TAG, "event list : " + listEvent.toString());

        // city list
        Vector<String> cities = new Vector<>();
        for (Event event : listEvent) {
            if (!cities.contains(event.getVille())) {
                cities.add(event.getVille());
            }
        }
        cityList = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            cityList[i] = cities.get(i);
        }
        Log.d(TAG, "city list : " + cityList);


        // Asso list
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient().create(JsonPlaceHolderApi.class);
        Call<Vector<Asso>> call = jsonPlaceHolderApi.getAsso();
        call.enqueue(new Callback<Vector<Asso>>() {
            @Override
            public void onResponse(Call<Vector<Asso>> call, Response<Vector<Asso>> response) {
                if (response.code() < 300 && response.code() >= 200) {
                    Log.d(TAG, "-->" + response.body());
                    Log.d(TAG, "retrun code -->" + response.code());

                    assoList = new String[response.body().size()];
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d(TAG, "===>" + response.body().get(i));
                        assoList[i] = response.body().get(i).getName();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Vector<Asso>> call, Throwable t) {

            }
        });

        final EditText searchTitle = findViewById(R.id.search_activity_name);
        final TextView searchDate = findViewById(R.id.search_activity_date);
        final TextView searchTime = findViewById(R.id.search_activity_time);
        Button searchCity = findViewById(R.id.search_activity_city);
        final Button searchAsso = findViewById(R.id.search_activity_association);
        Button removeFilter = findViewById(R.id.search_activity_remove_filter);
        final Switch switchForDate = findViewById(R.id.search_activity_switchForDate);

        Button cancel = findViewById(R.id.search_activity_cancel);
        final Button search = findViewById(R.id.search_activity_search);

        final Date date = new Date();
        searchDate.setText(dateFormat.format(date));
        searchTime.setText(timeFormat.format(date));

        final DatePickerDialog.OnDateSetListener mDateSetListenner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int newyear, int newmonth, int newdayOfMonth) {

                String date = newdayOfMonth + "/" + ( newmonth + 1 ) + "/" + newyear;
                searchDate.setText(date);

            }
        };

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        mDateSetListenner,
                        date.getYear() + 1900,
                        date.getMonth(),
                        date.getDay()
                );
                dialog.show();

            }
        });

        final TimePickerDialog.OnTimeSetListener mTimeSetListenner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int newhourOfDay, int newminute) {

                String time = newhourOfDay + ":" + newminute;
                searchTime.setText(time);
            }
        };

        searchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(
                        SearchActivity.this,
                        mTimeSetListenner,
                        date.getHours(),
                        date.getMinutes(),
                        true);
                dialog.show();
            }
        });

        searchAsso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle(getString(R.string.association))
                        .setMultiChoiceItems(assoList, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedAsso.add(assoList[which]);
                                }
                            }
                        })
                        .setPositiveButton(R.string.validate, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
            }
        });


        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle(getString(R.string.city))
                        .setMultiChoiceItems(cityList, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedCity.add(cityList[which]);
                                }
                            }
                        })
                        .setPositiveButton(R.string.validate,null)
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
            }
        });


        removeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent(v.getContext(), EventListActivity.class);
                setResult(RESULT_CANCELED, save);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> idlist = new ArrayList<>();
//                idlist.add("5");
                //fonction de recherche
                String title = searchTitle.getText().toString();
                String stringTime = searchTime.getText().toString();
                String stringDate = searchDate.getText().toString();

                int day = Integer.parseInt(stringDate.split("/")[0]);
                int month = Integer.parseInt(stringDate.split("/")[1]);
                int year = Integer.parseInt(stringDate.split("/")[2]);
                int hours = Integer.parseInt(stringTime.split(":")[0]);
                int min = Integer.parseInt(stringTime.split(":")[1]);

                Date searchDate = new Date(year - 1900, month - 1, day, hours, min);
                Log.d(TAG, "reshearch Date : " + searchDate.toString());

                for (Event event : listEvent) {
                    if (!idlist.contains(event.getId())) {
                        // For city
                        for (int i = 0; i < selectedCity.size(); i++) {
                            if (selectedCity.get(i).contains(event.getVille())) {
                                idlist.add(Integer.toString(event.getId()));
                            }
                        }

                        // For organizer
                        Log.d(TAG, "Name of organisation : " + event.getOrganizer().getName());
                        for (int i = 0; i < selectedAsso.size(); i++) {
                            Log.d(TAG, "Asso chercher : " + selectedAsso.get(i));
                            if (selectedAsso.get(i).contains(event.getOrganizer().getName())) {
                                idlist.add(Integer.toString(event.getId()));
                            }
                        }

                        // For start date
                        Log.d(TAG, "Date of event : " + event.getStart_datetime().toString());
                        Log.d(TAG, "Result of compare : " + (event.getStart_datetime().equals(searchDate)));
                        if (switchForDate.isChecked()) {
                            if (event.getStart_datetime().equals(searchDate)) {
                                idlist.add(Integer.toString(event.getId()));
                            }
                        }

                        //Name
                        Log.d(TAG, "Titre rechercher : _" + title + "_ | isEmpty ? : " + title.isEmpty());
                        if(!title.isEmpty()) {
                            if (event.getName().contains(title)) {
                                idlist.add(Integer.toString(event.getId()));
                            }
                        }
                    }
                }
                Log.d(TAG, "id searched event" + idlist);


                Intent save = new Intent(v.getContext(), EventListActivity.class);
                save.putStringArrayListExtra("idList", (ArrayList<String>) idlist);
                setResult(RESULT_OK, save);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
