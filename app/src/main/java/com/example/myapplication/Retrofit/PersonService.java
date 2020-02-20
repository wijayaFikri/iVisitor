package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.ApiResponse;
import com.example.myapplication.Model.DjangoResponse;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Visitor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PersonService {
    @FormUrlEncoded
    @POST("/login")
    Call<List<DjangoResponse>> login(@Field("username") String first,
                                     @Field("password") String last,
                                     @Field("token") String token);

    @FormUrlEncoded
    @POST("/MobileResponse")
    Call<ApiResponse> sendResponse(@Field("response") int response,
                                   @Field("primary_key") String primaryKey);

    @FormUrlEncoded
    @POST("/Submit/clock/out")
    Call<ApiResponse> sendClockOut( @Field("primary_key") String primaryKey);

    @FormUrlEncoded
    @POST("/History")
    Call<List<Visitor>> getHistory(@Field("target") String primaryKey,
                                   @Field("target_date") String date);

    @POST("/Edit/Profile")
    Call<ApiResponse> sendNewProfile (@Body Person person);
}
