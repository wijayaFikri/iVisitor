package com.example.myapplication.Retrofit;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Model.ApiResponse;
import com.example.myapplication.Model.DjangoResponse;
import com.example.myapplication.Model.ModelConstants;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Visitor;
import com.example.myapplication.Retrofit.interfaces.GetHistory;
import com.example.myapplication.Retrofit.interfaces.Login;
import com.example.myapplication.Retrofit.interfaces.NewProfile;
import com.example.myapplication.Retrofit.interfaces.Result;
import com.example.myapplication.config.Config;
import com.example.myapplication.service.MyFirebaseInstanceService;
import com.example.myapplication.service.interfaces.GetToken;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;

    public MyRetrofit(SharedPreferences sharedPreferences) {
        String BASE_URL = Config.BASE_URL;
        retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        this.sharedPreferences = sharedPreferences;
    }

    public void login(final String firstName, final String password, final Login login) {

        final MyFirebaseInstanceService instanceService = new MyFirebaseInstanceService();
        instanceService.getToken(new GetToken() {
            @Override
            public void onSuccess(String token) {
                PersonService personService = retrofit.create(PersonService.class);
                Call<List<DjangoResponse>> djangoResponseCall = personService.login(firstName, password, token);

                djangoResponseCall.enqueue(new Callback<List<DjangoResponse>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<DjangoResponse>> call, @NonNull Response<List<DjangoResponse>> response) {
                        try {
                            List<DjangoResponse> dataList = response.body();
                            if (dataList != null) {
                                DjangoResponse djangoResponse = dataList.get(0);
                                if (djangoResponse.getModel().equals(ModelConstants.person) && djangoResponse.getPk() != null) {
                                    Person person = new Gson().fromJson(new Gson().toJson(djangoResponse.getFields()), Person.class);
                                    person.setId(djangoResponse.getPk());
                                    login.onSuccess(person);
                                } else {
                                    login.onFailed("Username not found or Wrong password");
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<DjangoResponse>> call, @NonNull Throwable t) {
                        Log.e("RETROFIT ERROR", Objects.requireNonNull(t.getMessage()));
                        login.onFailed("Login failed , please check your internet connection");
                    }
                });
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });

    }

    public void sendResponse(int response, String primaryKey, final Result result) {
        PersonService personService = retrofit.create(PersonService.class);
        final int finalResponse = response;
        Call<ApiResponse> djangoResponseCall = personService.sendResponse(response, primaryKey);
        djangoResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus().equals("Success")){
                        if (finalResponse == 1) {
                            result.onSuccess("Visitor has been accepted successfully");
                        } else {
                            result.onSuccess("Visitor has been rejected successfully");
                        }
                    }
                } catch (Exception e){
                    String errorMessage = e.getMessage();
                    Log.e("RETROFIT ERROR",errorMessage);
                    result.onFailed("Error occurred during sending the response, please try again");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                result.onFailed("Sending response failed , please check your internet connection");
            }
        });
    }

    public void sendClockOut(String primaryKey,final Result result){
        PersonService personService = retrofit.create(PersonService.class);
        Call<ApiResponse> djangoResponseCall = personService.sendClockOut(primaryKey);

        djangoResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                try {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus().equals("Success")){
                        result.onSuccess("Session has been ended successfully");
                    }
                } catch (Exception e){
                    String errorMessage = e.getMessage();
                    Log.e("RETROFIT ERROR",errorMessage);
                    result.onFailed("Error occurred during sending the clock out time, please try again");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                result.onFailed("Submit clock out failed , please check your internet connection");
            }
        });
    }

    public void getHistory(String primaryKey, String date, final GetHistory getHistory){
        PersonService personService = retrofit.create(PersonService.class);
        Call<List< Visitor>> getHistoryCall = personService.getHistory(primaryKey,date);
        getHistoryCall.enqueue(new Callback<List<Visitor>>() {
            @Override
            public void onResponse(@NonNull Call<List<Visitor>> call, @NonNull Response<List<Visitor>> response) {

                if (response.isSuccessful()){
                    List<Visitor> visitorList = response.body();
                    getHistory.onSuccess(visitorList);
                } else {
                    getHistory.onFailed(" An error occurred during retrieving the history, please try again");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Visitor>> call,@NonNull Throwable t) {
                getHistory.onFailed("getting history failed , please check your internet connection");
            }
        });
    }

    public void sendNewProfile(final Person person, final NewProfile newProfile){
        PersonService personService = retrofit.create(PersonService.class);
        /*String personJson = new Gson().toJson(person);
        Log.d("INI JSON NYAAAAAAA" , personJson);*/
        Call<ApiResponse> apiResponseCall = personService.sendNewProfile(person);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus().equals("Success")){
                        Log.d("RETROFIT RESULT" , "Success");
                        newProfile.onSuccess(person);
                    } else {
                        newProfile.onFailed("An error occured during saving the profile , " +
                                "Please try again");
                    }
                } else {
                    newProfile.onFailed("An error occured during saving the profile , " +
                            "Please try again");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                        newProfile.onFailed("Saving profile failed , Please check your internet" +
                                "connection");
            }
        });
    }
}
