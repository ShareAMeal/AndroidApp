package fr.insalyon.tc.pweb.shareameal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final Button login = findViewById(R.id.main_activity_login);
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EventListActivity test = new EventListActivity("getEventsAccount");
                startActivity(new Intent(getApplicationContext(), test.getClass()));
                Toast.makeText(getApplicationContext(), "Connexion...", Toast.LENGTH_SHORT).show();
            }
        });

        final Button noAccount= findViewById(R.id.main_activity_noAccount);
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), new EventListActivity("getEventsNoAccount").getClass()));
            }
        });

    }

}