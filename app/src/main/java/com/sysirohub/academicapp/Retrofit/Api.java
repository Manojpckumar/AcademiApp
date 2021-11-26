package com.sysirohub.academicapp.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST(APIClient.APPEND_URL + "register")
    Call<JsonObject> registerUser(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getStatusbyemail")
    Call<JsonObject> getStatusByEmail(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "login")
    Call<JsonObject> userLogin(@Body JsonObject object);


}
