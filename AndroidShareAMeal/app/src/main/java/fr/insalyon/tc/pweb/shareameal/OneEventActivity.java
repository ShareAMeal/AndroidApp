package fr.insalyon.tc.pweb.shareameal;

import android.os.Bundle;
import android.text.NoCopySpan;

import androidx.appcompat.app.AppCompatActivity;

public class OneEventActivity extends AppCompatActivity {

    private static final String TAG = "OneEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_event_for_event_list);

    }
}
