package com.example.myapplication.service;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.service.interfaces.GetToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("TOKEN ", s);
        sendRegistrationToServer(s);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        super.onNewToken(s);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN ", refreshedToken);
    }

    public void getToken(final GetToken getToken) {
        final String[] token = new String[1];
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            getToken.onFailed("getInstanceId failed");
                            return;
                        }

                        // Get new Instance ID token
                        token[0] = task.getResult().getToken();
                        getToken.onSuccess(token[0]);
                        // Log and toast
                        Log.d(TAG, token[0]);
                    }
                });
    }
}