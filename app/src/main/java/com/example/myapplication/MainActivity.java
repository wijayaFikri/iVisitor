package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.model.Person;
import com.example.myapplication.retrofit.MyRetrofit;
import com.example.myapplication.retrofit.interfaces.Login;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String MY_PREFERENCES = "MY_PREF";
    public static final String USER_KEY = "USER_LOGIN_DATA";
    SharedPrefUtils sharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(MY_PREFERENCES,MODE_PRIVATE));
        if (sharedPrefUtils.get(USER_KEY) != null){
            Intent intent = new Intent(this,dashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void goLogin(View view) {
        MyRetrofit myRetrofit = new MyRetrofit();
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
                if (sharedPrefUtils.save(USER_KEY,personJson)){
                    Intent intent = new Intent(context,dashboardActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }
}
