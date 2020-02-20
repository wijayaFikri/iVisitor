package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.Person;
import com.example.myapplication.Retrofit.MyRetrofit;
import com.example.myapplication.Retrofit.interfaces.NewProfile;
import com.example.myapplication.Utility.SharedPrefUtils;
import com.example.myapplication.config.Config;
import com.google.gson.Gson;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        final SharedPreferences sharedPreferences = getSharedPreferences(Config.PREFERENCE_KEY,
                MODE_PRIVATE);
        final SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(sharedPreferences);
        String personJson = sharedPrefUtils.get(Config.USER_KEY);
        final Person person = new Gson().fromJson(personJson, Person.class);
        final Person exisitingPerson = new Gson().fromJson(personJson, Person.class);

        final EditText firstNameEd = findViewById(R.id.edit_profile_firstname);
        final EditText lastNameEd = findViewById(R.id.edit_profile_lastname);
        final EditText locationEd = findViewById(R.id.edit_profile_office_desk_location);
        final EditText positionEd = findViewById(R.id.edit_profile_position);

        firstNameEd.setText(person.getFirstName());
        lastNameEd.setText(person.getLastName());
        locationEd.setText(person.getOfficeDeskLocation());
        positionEd.setText(person.getPosition());

        final Context context = this;

        Button button = findViewById(R.id.edit_profile_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameEd.getText().toString().equals("")) {
                    firstNameEd.setError("Please fill the first name");
                    return;
                } else if (lastNameEd.getText().toString().equals("")) {
                    lastNameEd.setError("Please fill the last name");
                    return;
                } else if (locationEd.getText().toString().equals("")) {
                    locationEd.setError("Please fill the location");
                    return;
                } else if (positionEd.getText().toString().equals("")) {
                    positionEd.setError("Please fill the position");
                    return;
                }

                if (firstNameEd.getText().toString().equals(person.getFirstName())
                        && lastNameEd.getText().toString().equals(person.getLastName())
                        && locationEd.getText().toString().equals(person.getOfficeDeskLocation())
                        && positionEd.getText().toString().equals(person.getPosition())) {
                    Toast.makeText(context, "No changes detected",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                person.setFirstName(firstNameEd.getText().toString());
                person.setLastName(lastNameEd.getText().toString());
                person.setOfficeDeskLocation(locationEd.getText().toString());
                person.setPosition(positionEd.getText().toString());

                MyRetrofit myRetrofit = new MyRetrofit(sharedPreferences);
                myRetrofit.sendNewProfile(person, new NewProfile() {
                    @Override
                    public void onSuccess(Person newPerson) {
                        String newPersonJson = new Gson().toJson(newPerson,Person.class);
                        sharedPrefUtils.save(Config.USER_KEY,newPersonJson);
                        Toast.makeText(context, "Changes saved successfully",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(context, errorMessage,
                                Toast.LENGTH_LONG).show();
                        person.setFirstName(exisitingPerson.getFirstName());
                        person.setLastName(exisitingPerson.getLastName());
                        person.setOfficeDeskLocation(exisitingPerson.getOfficeDeskLocation());
                        person.setPosition(exisitingPerson.getPosition());
                    }
                });
            }
        });
    }
}
