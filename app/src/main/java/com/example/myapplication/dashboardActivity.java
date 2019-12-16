package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.model.Person;
import com.example.myapplication.retrofit.MyRetrofit;
import com.example.myapplication.retrofit.interfaces.Login;

public class dashboardActivity extends AppCompatActivity {
    public static final String MY_PREFERENCES = "MY_PREF";
    public static final String USER_KEY = "USER_LOGIN_DATA";
    SharedPrefUtils sharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(MY_PREFERENCES,MODE_PRIVATE));
        setContentView(R.layout.activity_dashboard);
    }

    public void goVisitorDetail(View view) {
        MyRetrofit myRetrofit = new MyRetrofit();
        Intent intent = new Intent(this,visitorComingActivity.class);
        startActivity(intent);
    }

    public void goLogout(View view) {
        sharedPrefUtils.remove(USER_KEY);
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
