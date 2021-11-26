package com.sysirohub.academicapp.Retrofit;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper extends Application {

    public  static  final String BASE_URL="https://campsitehub.000webhostapp.com/";
    public  static Retrofit retrofit= null;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public  static Retrofit getClient(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().client(new OkHttpClient.Builder()
                    .build()).addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return  retrofit;
    }


}
