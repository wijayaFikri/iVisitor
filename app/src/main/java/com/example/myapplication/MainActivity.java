package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.Fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextFragment(new LoginFragment(),R.id.Auth_container);
    }
    public void nextFragment(Fragment fragment, int fl) {
        FragmentTransaction ft = MainActivity.this.getSupportFragmentManager().beginTransaction();
        ft.replace(fl, fragment);
        ft.commit();
    }
}
