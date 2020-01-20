package com.example.myapplication.service.interfaces;

public interface GetToken {
    void onSuccess(String token);
    void onFailed(String errorMessage);
}
