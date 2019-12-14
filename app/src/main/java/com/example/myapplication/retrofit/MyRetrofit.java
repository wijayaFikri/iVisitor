package com.example.myapplication.retrofit;

import androidx.annotation.NonNull;

import com.example.myapplication.model.DjangoResponse;
import com.example.myapplication.model.ModelConstants;
import com.example.myapplication.model.Person;
import com.example.myapplication.retrofit.interfaces.Login;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private Retrofit retrofit;

    public MyRetrofit() {
        String BASE_URL = "http://rafqi.pythonanywhere.com";
        retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }

    public void login(String firstName,String password,final Login login) {
        PersonService personService = retrofit.create(PersonService.class);
        Call<List<DjangoResponse>> djangoResponseCall = personService.login(firstName,password);

        djangoResponseCall.enqueue(new Callback<List<DjangoResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<DjangoResponse>> call, @NonNull Response<List<DjangoResponse>> response) {
                try {
                    List<DjangoResponse> dataList = response.body();
                    if (dataList != null) {
                        DjangoResponse djangoResponse = dataList.get(0);
                        if (djangoResponse.getModel().equals(ModelConstants.person)) {
                            Person person = new Gson().fromJson(new Gson().toJson(djangoResponse.getFields()),Person.class);
                            login.onSuccess(person);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DjangoResponse>> call, @NonNull Throwable t) {
                login.onFailed(t);
            }
        });

    }
}
