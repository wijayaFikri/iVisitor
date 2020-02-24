package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myapplication.config.Config;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        CalendarView calendarView = findViewById(R.id.calendarView);
        final Context context = this;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthString = "";
                if(month < 10){
                    monthString ="0" + (month + 1);
                }
                String date = monthString
                        + " "
                        + String.valueOf(dayOfMonth)
                        + " "
                        +  String.valueOf(year);
                Intent intent = new Intent(context,HistoryActivity.class);
                intent.putExtra(Config.DATE_STRING, date);
                startActivity(intent);
            }
        });
    }

    public void goHome(View view) {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
