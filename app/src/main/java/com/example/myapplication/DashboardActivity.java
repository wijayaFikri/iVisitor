package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.config.Config;

public class DashboardActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_LOGIN_DATA";
    SharedPrefUtils sharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(Config.PREFERENCE_KEY,MODE_PRIVATE));
        setContentView(R.layout.activity_dashboard);
    }

    public void goVisitorDetail(View view) {
        Intent intent = new Intent(this, VisitorComingActivity.class);
        startActivity(intent);
    }

    public void goLogout(View view) {
        sharedPrefUtils.remove(USER_KEY);
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goCalendarActivity(View view) {
        Intent intent = new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }
}
