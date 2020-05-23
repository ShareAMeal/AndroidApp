package fr.insalyon.tc.pweb.shareameal;
import fr.insalyon.tc.pweb.shareameal.object.Asso;
import fr.insalyon.tc.pweb.shareameal.object.Event;
import fr.insalyon.tc.pweb.shareameal.object.User;

import java.util.Vector;

import fr.insalyon.tc.pweb.shareameal.object.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface JsonPlaceHolderApi {

    @GET("/api/event/?format=json")
    Call<Vector<Event>> getEvents();

    @GET("/api/event/?format=json")
    Call<Vector<Event>> getMyEvents(
            @Query("organizer") int id
    );

    @GET("/api/asso/mine/")
    Call<Asso> getmyAsso(
            @Header("Authorization") String auth
    );

    @POST("/api/event/")
    Call<Post> createPost(
            @Body Post post,
            @Header("Authorization") String auth
    );

    @PUT("/api/event/{id}/")
    Call<Post> modifEvent(
            @Path("id") int id,
            @Body Post post,
            @Header("Authorization") String auth
    );

    @GET("/api/myuser")
    Call<User> checkUser(
            @Header("Authorization") String auth
    );
}