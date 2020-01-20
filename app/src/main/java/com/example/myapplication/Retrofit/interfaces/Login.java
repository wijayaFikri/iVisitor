package com.example.myapplication.Retrofit.interfaces;

import com.example.myapplication.Model.Person;

public interface Login {
    void onSuccess(Person person);
    void onFailed(String errorMessage);
}
