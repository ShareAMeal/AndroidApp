package fr.insalyon.tc.pweb.shareameal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        String url = "http://api.shareameal.ribes.ovh";
//        String url = "http:192.168.1.33:8001";

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
