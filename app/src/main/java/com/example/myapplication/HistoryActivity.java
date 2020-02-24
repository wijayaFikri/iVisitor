package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Visitor;
import com.example.myapplication.Retrofit.MyRetrofit;
import com.example.myapplication.Retrofit.interfaces.GetHistory;
import com.example.myapplication.Utility.HistoryAdapter;
import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.config.Config;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        final Context context = this;
        Intent intent = getIntent();
        final ListView listView = findViewById(R.id.historyListView);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREFERENCE_KEY,MODE_PRIVATE);
        String personJson = new SharedPrefUtils(sharedPreferences).get(Config.USER_KEY);
        Person person = new Gson().fromJson(personJson,Person.class);
        MyRetrofit myRetrofit = new MyRetrofit(sharedPreferences);
        String dateInString = intent.getStringExtra(Config.DATE_STRING);
        final ProgressDialog dialog = ProgressDialog.show(HistoryActivity.this, "",
                "Getting history data , please wait ...", true);
        myRetrofit.getHistory(person.getId(), dateInString, new GetHistory() {
            @Override
            public void onSuccess(List<Visitor> visitorList) {
                dialog.dismiss();
                listView.setDivider(null);
                if (visitorList.isEmpty()){
                    setContentView(R.layout.no_visitor_coming_activity);
                    TextView tv = findViewById(R.id.no_title_textView);
                    tv.setText("No visitor activity founded.");
                } else {
                    HistoryAdapter historyAdapter = new HistoryAdapter(context,R.layout.history_item,visitorList);
                    listView.setAdapter(historyAdapter);
                }
            }

            @Override
            public void onFailed(String message) {
                dialog.dismiss();
                Toast.makeText(context, message,
                        Toast.LENGTH_LONG).show();
            }
        });
/*        Visitor visitor = new Visitor();
        visitor.setVisitorName("Hanzo");
        visitor.setIsAccepted("0");
        visitor.setVisitingReason("test doang");
        visitor.setClockIn("15:00");
        visitor.setClockOut("18:00");
        visitor.setVisitDate("17-agt-1945");
        visitor.setVisitorKtp("/media/image/visitor_13.png");
        List<Visitor> visitorList = new ArrayList<Visitor>();
        visitorList.add(visitor);*/
    }
}
