package fr.insalyon.tc.pweb.shareameal;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("event/?format=json")
    Call<Vector<Event>> getEvents();
}
