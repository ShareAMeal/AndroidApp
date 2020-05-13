package fr.insalyon.tc.pweb.shareameal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.insalyon.tc.pweb.shareameal.object.User;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String url = "http://api.shareameal.ribes.ovh/";
//    private String url = "http:192.168.1.33:8001";
    private String credentials = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final Button login = findViewById(R.id.main_activity_login);
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                final EditText username = findViewById(R.id.main_activity_username);
                final EditText password = findViewById(R.id.main_activity_password);

//                RetrofitClient.getClient()


                credentials = Credentials.basic(username.getText().toString(), password.getText().toString());

                // connexion au backEnd
                JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitClient.getClient(url).create(JsonPlaceHolderApi.class);
                jsonPlaceHolderApi.checkUser(credentials).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            startActivity(new Intent(getApplicationContext(), new EventListActivity("getEventsAccount", credentials).getClass()));
                            Toast.makeText(getApplicationContext(), R.string.connecting, Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403){
                            Toast.makeText(getApplicationContext(), R.string.badUser_pass, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.connectFailed, Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        final Button noAccount= findViewById(R.id.main_activity_noAccount);
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), new EventListActivity("getEventsNoAccount", credentials).getClass()));
            }
        });

    }

}