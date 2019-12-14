package com.example.myapplication.retrofit.interfaces;

import com.example.myapplication.model.Person;

public interface Login {
    void onSuccess(Person person);
    void onFailed(Throwable t);
}
