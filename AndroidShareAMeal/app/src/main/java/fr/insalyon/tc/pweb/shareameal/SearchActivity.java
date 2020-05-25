package fr.insalyon.tc.pweb.shareameal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Vector;

import fr.insalyon.tc.pweb.shareameal.object.Event;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "searchActivity";

    private static Vector<Event> listEvent;

    public SearchActivity(Vector<Event> listEvent){
        this.listEvent = listEvent;
    }

    public SearchActivity(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        EditText searchTitle = findViewById(R.id.search_activity_name);
        EditText searchDate = findViewById(R.id.search_activity_date);
        Button searchCity = findViewById(R.id.search_activity_city);

        Button cancel = findViewById(R.id.search_activity_cancel);
        Button search = findViewById(R.id.search_activity_search);

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle(getString(R.string.city))
                        .setMessage("test")
                        .setPositiveButton(R.string.search,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                                        // changer
                                    }
                                })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, listEvent.toString());
                Toast.makeText(getApplicationContext(), "fonction de recherche a faire" , Toast.LENGTH_SHORT).show();

                //fonction de recherche

                ArrayList<String> idlist = new ArrayList<>();
                idlist.add("5");

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
