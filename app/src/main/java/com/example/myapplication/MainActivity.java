package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.Fragment.Login;
import com.example.myapplication.Utility.BitmapUtils;
import com.example.myapplication.Utility.BlurUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextFragment(new Login(),R.id.Auth_container);
    }
    public void nextFragment(Fragment fragment, int fl) {
        FragmentTransaction ft = MainActivity.this.getSupportFragmentManager().beginTransaction();
        ft.replace(fl, fragment);
        ft.commit();
    }
}
