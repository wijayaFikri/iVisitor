package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Retrofit.MyRetrofit;
import com.example.myapplication.Retrofit.interfaces.Login;
import com.example.myapplication.config.Config;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_LOGIN_DATA";
    SharedPrefUtils sharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(Config.PREFERENCE_KEY,MODE_PRIVATE));
        if (sharedPrefUtils.get(USER_KEY) != null){
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void goLogin(View view) {
            MyRetrofit myRetrofit = new MyRetrofit(getSharedPreferences(Config.PREFERENCE_KEY, MODE_PRIVATE));
            EditText usernameEt = findViewById(R.id.login_username_editText);
            EditText passwordEt = findViewById(R.id.login_password_editText);
            final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                    "Logging in , please wait ...", true);
            final Context context = this;
            myRetrofit.login(usernameEt.getText().toString(), passwordEt.getText().toString(), new Login() {
                @Override
                public void onSuccess(Person person) {
                    dialog.dismiss();
                    String personJson = new Gson().toJson(person);
                    if (sharedPrefUtils.save(USER_KEY, personJson)) {
                        Intent intent = new Intent(context, DashboardActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailed(String errorMessage) {
                    dialog.dismiss();
                    Toast.makeText(context, errorMessage,
                            Toast.LENGTH_LONG).show();
                }
            });
    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        115);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        115);
            }
        }
    }
}
