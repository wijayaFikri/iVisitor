package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.NotificationData;
import com.example.myapplication.Retrofit.MyRetrofit;
import com.example.myapplication.Retrofit.interfaces.Result;
import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.config.Config;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Calendar;

public class VisitorComingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_coming);
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(getSharedPreferences(Config.PREFERENCE_KEY,MODE_PRIVATE));
        if (sharedPrefUtils.get(Config.SESSION_KEY) != null){
            Intent intent = new Intent(this,VisitorSessionActivity.class);
            startActivity(intent);
            finish();
        }
        String notificationDataJson = sharedPrefUtils.get(Config.NOTIFICATION_KEY);
        if (notificationDataJson != null && !"".equals(notificationDataJson)){
            setContentView(R.layout.activity_visitor_coming);
            final NotificationData notificationData = new Gson().fromJson(notificationDataJson,NotificationData.class);
            final Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ImageView roundedImageView = findViewById(R.id.visitor_ktp);
                    TextView visitorNameTv = findViewById(R.id.visitor_full_name);
                    TextView visitorPurpose = findViewById(R.id.visitor_meeting_purpose);
                    CardView visitorKtpContainer = findViewById(R.id.visitor_ktp_container);
                    visitorKtpContainer.setPreventCornerOverlap(false);
                    roundedImageView.setImageBitmap(bitmap);
                    visitorNameTv.setText(notificationData.getVisitorName());
                    visitorPurpose.setText(notificationData.getVisitingReason());
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Get image from data Notification
                    Picasso.get()
                            .load(Config.BASE_URL + notificationData.getImageUrl())
                            .into(target);
                }
            });

            Button acceptButton = findViewById(R.id.acceptButton);
            Button declineButton = findViewById(R.id.declineButton);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendResponse(1,notificationData.getVisitorId());
                }
            });

            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendResponse(0,notificationData.getVisitorId());
                }
            });
        } else {
            setContentView(R.layout.no_visitor_coming_activity);
        }
    }

    public void sendResponse(int response,String primaryKey){
        final Context context = this;
        final int finalResponse = response;
        final SharedPreferences sharedPreferences = getSharedPreferences(Config.PREFERENCE_KEY,MODE_PRIVATE);
        MyRetrofit myRetrofit = new MyRetrofit(sharedPreferences);
        myRetrofit.sendResponse(response, primaryKey, new Result() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(context, message,
                        Toast.LENGTH_LONG).show();
                if (finalResponse == 1) {
                    Intent intent = new Intent(context, VisitorSessionActivity.class);
                    SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(sharedPreferences);
                    sharedPrefUtils.save(Config.SESSION_KEY, "1");
                    Calendar calendar = Calendar.getInstance();
                    sharedPrefUtils.save(Config.CLOCK_IN_KEY, String.valueOf(calendar.getTimeInMillis()));
                    startActivity(intent);
                    finish();
                } else {
                    SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(sharedPreferences);
                    sharedPrefUtils.remove(Config.NOTIFICATION_KEY);
                    Intent intent = new Intent(context,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(context, message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
