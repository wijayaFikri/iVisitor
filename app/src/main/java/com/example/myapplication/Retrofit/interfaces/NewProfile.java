package com.example.myapplication.Retrofit.interfaces;

import com.example.myapplication.Model.Person;

public interface NewProfile {
    void onSuccess(Person newPerson);
    void onFailed(String errorMessage);
}
