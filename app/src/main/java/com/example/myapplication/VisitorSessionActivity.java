package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.NotificationData;
import com.example.myapplication.Retrofit.MyRetrofit;
import com.example.myapplication.Retrofit.interfaces.Result;
import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.config.Config;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VisitorSessionActivity extends AppCompatActivity {

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    Handler handler;

    int Seconds, Minutes;

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            TextView textView = findViewById(R.id.timerTv);

            String time = String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds);

            textView.setText(time);

            handler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_session_activity);
        StartTime = SystemClock.uptimeMillis();
        long timeNow = Calendar.getInstance().getTimeInMillis();
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(
                Config.PREFERENCE_KEY, MODE_PRIVATE));
        long clockInTimeLong = Long.valueOf(sharedPrefUtils.get(Config.CLOCK_IN_KEY));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String clockInTime = sdf.format(clockInTimeLong);
        TextView textView = findViewById(R.id.clockInTv);
        textView.setText(clockInTime);
        StartTime = StartTime + (clockInTimeLong - timeNow);
        handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    public void goEndSession(View view) {
        final Context context = this;
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(
                Config.PREFERENCE_KEY,
                MODE_PRIVATE));
        NotificationData notificationData = new Gson().fromJson(sharedPrefUtils.get(Config.NOTIFICATION_KEY),
                NotificationData.class);
        MyRetrofit myRetrofit = new MyRetrofit(getSharedPreferences(Config.PREFERENCE_KEY,
                MODE_PRIVATE));
        myRetrofit.sendClockOut(notificationData.getVisitorId(), new Result() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(context, message,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(context, message,
                        Toast.LENGTH_LONG).show();
            }
        });
        sharedPrefUtils.remove(Config.SESSION_KEY);
        sharedPrefUtils.remove(Config.NOTIFICATION_KEY);
        sharedPrefUtils.remove(Config.CLOCK_IN_KEY);
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
