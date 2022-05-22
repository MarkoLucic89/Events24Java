package com.lucic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.databinding.ActivitySplashScreenBinding;
import com.lucic.cubes.events24.ui.activity.registration.HomeActivity;
import com.lucic.cubes.events24.ui.activity.registration.MainRegistrationActivity;

public class SplashScreenActivity extends BasicActivity {

    private ActivitySplashScreenBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private final String TAG = "EVENTS_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFirebase();
        goToHomeActivity();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                    }
                });
    }

    private void initFirebase() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void goToHomeActivity() {
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), MainRegistrationActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }
}