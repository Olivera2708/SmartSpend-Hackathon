package com.example.smartspend;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartspend.clients.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    //EXAMPLE: http://192.168.43.73:8080/api/
    public static SharedPreferences sharedPreferences = null;
    public static final String SERVICE_API_PATH = "http://192.168.10.131:8080";

    /*
     * Ovo ce nam sluziti za debug, da vidimo da li zahtevi i odgovori idu
     * odnosno dolaze i kako izgeldaju.
     * */
    public static OkHttpClient test(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        return client;
    }

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */

    public static void setClientUtils(SharedPreferences sharedPreferences){
        ClientUtils.sharedPreferences = sharedPreferences;
        ClientUtils.retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(test())
                .build();
        ClientUtils.service = retrofit.create(Service.class);
    }
    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */

    public static Retrofit retrofit = null;

    /*
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static Service service = null;
}