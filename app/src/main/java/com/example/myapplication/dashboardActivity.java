package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.model.Person;
import com.example.myapplication.retrofit.MyRetrofit;
import com.example.myapplication.retrofit.interfaces.Login;

public class dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goVisitorDetail(View view) {
        MyRetrofit myRetrofit = new MyRetrofit();
        Intent intent = new Intent(this,visitorComingActivity.class);
        startActivity(intent);
    }
}
