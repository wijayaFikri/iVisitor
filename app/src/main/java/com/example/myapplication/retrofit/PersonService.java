package com.example.myapplication.retrofit;

import com.example.myapplication.model.DjangoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface PersonService {
    @FormUrlEncoded
    @GET("/login")
    public Call<List<DjangoResponse>> login(@Field("first_name") String first, @Field("password") String last);
}
