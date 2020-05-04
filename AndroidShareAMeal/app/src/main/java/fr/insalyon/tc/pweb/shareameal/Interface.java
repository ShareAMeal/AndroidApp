package fr.insalyon.tc.pweb.shareameal;
import fr.insalyon.tc.pweb.shareameal.object.Asso;
import fr.insalyon.tc.pweb.shareameal.object.Event;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.http.GET;

interface JsonPlaceHolderApi {

    @GET("event/?format=json")
    Call<Vector<Event>> getEvents();

    @GET("asso/?format=json")
    Call<Vector<Asso>> getAssos();
}