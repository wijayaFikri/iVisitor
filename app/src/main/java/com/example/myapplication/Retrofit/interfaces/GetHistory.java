package com.example.myapplication.Retrofit.interfaces;

import com.example.myapplication.Model.Visitor;

import java.util.List;

public interface GetHistory {
    void onSuccess(List<Visitor> visitorList);
    void onFailed(String message);

}
